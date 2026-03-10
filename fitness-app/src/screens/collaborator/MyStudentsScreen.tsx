import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  Modal,
  ScrollView,
  Alert,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { InputField } from '../../components/common/InputField';
import { ModalHeader } from '../../components/common/ModalHeader';
import { Badge } from '../../components/common/Badge';
import { Student, TrainingProgram, Exercise } from '../../types';
import { useAuth } from '../../hooks/useAuth';
import { getStudents } from '../../services/authService';
import { createProgram } from '../../services/programService';

export const MyStudentsScreen: React.FC = () => {
  const { user, logout } = useAuth();
  const [students, setStudents] = useState<Student[]>([]);
  const [selectedStudent, setSelectedStudent] = useState<Student | null>(null);
  const [showProgramModal, setShowProgramModal] = useState(false);
  const [showNotesModal, setShowNotesModal] = useState(false);

  // Form programma
  const [programTitle, setProgramTitle] = useState('');
  const [programDescription, setProgramDescription] = useState('');
  const [sessionNotes, setSessionNotes] = useState('');
  const [progressNotes, setProgressNotes] = useState('');

  const loadStudents = useCallback(async () => {
    if (!user) return;
    try {
      const allStudents = await getStudents();
      // Filtra solo gli allievi assegnati a questo collaboratore
      const myStudents = allStudents.filter(
        (s) => s.assignedCollaboratorId === user.id
      );
      setStudents(myStudents);
    } catch {
      // Silently handle
    }
  }, [user]);

  useEffect(() => {
    loadStudents();
  }, [loadStudents]);

  const handleCreateProgram = async () => {
    if (!selectedStudent || !programTitle || !user) {
      Alert.alert('Errore', 'Seleziona un allievo e inserisci il titolo');
      return;
    }
    try {
      await createProgram({
        studentId: selectedStudent.id,
        collaboratorId: user.id,
        title: programTitle,
        description: programDescription,
        exercises: [],
        sessionNumber: 1,
        progressNotes: sessionNotes,
        createdAt: new Date(),
      });
      Alert.alert('Successo', 'Programma creato con successo');
      setShowProgramModal(false);
      setProgramTitle('');
      setProgramDescription('');
      setSessionNotes('');
    } catch {
      Alert.alert('Errore', 'Impossibile creare il programma');
    }
  };

  const handleSaveNotes = async () => {
    if (!selectedStudent || !progressNotes || !user) return;
    try {
      await createProgram({
        studentId: selectedStudent.id,
        collaboratorId: user.id,
        title: 'Note progresso',
        description: '',
        exercises: [],
        sessionNumber: 0,
        progressNotes,
        createdAt: new Date(),
      });
      Alert.alert('Successo', 'Note salvate');
      setShowNotesModal(false);
      setProgressNotes('');
    } catch {
      Alert.alert('Errore', 'Impossibile salvare le note');
    }
  };

  const renderStudent = ({ item }: { item: Student }) => (
    <Card variant="elevated">
      <TouchableOpacity
        style={styles.studentCard}
        onPress={() => setSelectedStudent(item)}
      >
        <View style={styles.avatar}>
          <Text style={styles.avatarText}>
            {item.name[0]}
            {item.surname[0]}
          </Text>
        </View>
        <View style={styles.studentInfo}>
          <Text style={styles.studentName}>
            {item.name} {item.surname}
          </Text>
          <Text style={styles.studentGoals}>{item.goals}</Text>
          <Text style={styles.sessionCount}>
            Consulenze nutrizionali: {item.nutritionalConsultations}
          </Text>
        </View>
      </TouchableOpacity>

      {selectedStudent?.id === item.id && (
        <View style={styles.actionButtons}>
          <Button
            title="Nuovo Programma"
            onPress={() => setShowProgramModal(true)}
            variant="primary"
            style={styles.actionButton}
          />
          <Button
            title="Note Progresso"
            onPress={() => setShowNotesModal(true)}
            variant="secondary"
            style={styles.actionButton}
          />
        </View>
      )}
    </Card>
  );

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <View style={styles.headerTop}>
          <View>
            <Text style={styles.title}>I Miei Allievi</Text>
            <Text style={styles.subtitle}>{students.length} allievi assegnati</Text>
          </View>
          <TouchableOpacity style={styles.logoutButton} onPress={logout}>
            <Text style={styles.logoutText}>Esci</Text>
          </TouchableOpacity>
        </View>
      </View>

      <FlatList
        data={students}
        renderItem={renderStudent}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        ListEmptyComponent={
          <Card>
            <Text style={styles.emptyText}>
              Nessun allievo assegnato al momento
            </Text>
          </Card>
        }
      />

      {/* Modale Nuovo Programma */}
      <Modal visible={showProgramModal} animationType="slide" transparent>
        <View style={styles.modalOverlay}>
          <ScrollView style={styles.modalContent}>
            <ModalHeader title={`Nuovo Programma - ${selectedStudent?.name} ${selectedStudent?.surname}`} onClose={() => setShowProgramModal(false)} />

            <InputField
              label="Titolo Sessione"
              value={programTitle}
              onChangeText={setProgramTitle}
              placeholder="Es: Sessione forza - Upper body"
            />

            <InputField
              label="Descrizione"
              value={programDescription}
              onChangeText={setProgramDescription}
              placeholder="Descrizione del programma..."
              multiline
              numberOfLines={4}
            />

            <InputField
              label="Note sulla sessione"
              value={sessionNotes}
              onChangeText={setSessionNotes}
              placeholder="Note su come è andata la sessione..."
              multiline
              numberOfLines={3}
            />

            <Text style={styles.exerciseHint}>
              Gli esercizi possono essere aggiunti dalla libreria esercizi dopo
              la creazione del programma.
            </Text>

            <View style={styles.modalButtons}>
              <Button
                title="Annulla"
                onPress={() => setShowProgramModal(false)}
                variant="outline"
                style={styles.modalButton}
              />
              <Button
                title="Crea Programma"
                onPress={handleCreateProgram}
                style={styles.modalButton}
              />
            </View>
          </ScrollView>
        </View>
      </Modal>

      {/* Modale Note Progresso */}
      <Modal visible={showNotesModal} animationType="slide" transparent>
        <View style={styles.modalOverlay}>
          <View style={styles.modalContent}>
            <ModalHeader title={`Note Avanzamento - ${selectedStudent?.name}`} onClose={() => setShowNotesModal(false)} />

            <InputField
              label="Note sul progresso"
              value={progressNotes}
              onChangeText={setProgressNotes}
              placeholder="Appunti sull'avanzamento dell'allievo..."
              multiline
              numberOfLines={6}
            />

            <View style={styles.modalButtons}>
              <Button
                title="Annulla"
                onPress={() => setShowNotesModal(false)}
                variant="outline"
                style={styles.modalButton}
              />
              <Button
                title="Salva Note"
                onPress={handleSaveNotes}
                style={styles.modalButton}
              />
            </View>
          </View>
        </View>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background,
  },
  header: {
    backgroundColor: colors.primary,
    padding: spacing.lg,
    paddingTop: spacing.xxl,
  },
  title: {
    fontSize: fontSize.xxl,
    fontWeight: '700',
    color: colors.textOnPrimary,
  },
  headerTop: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
  },
  logoutButton: {
    backgroundColor: colors.surfaceLight,
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.md,
    borderWidth: 1,
    borderColor: colors.border,
  },
  logoutText: {
    color: colors.accent,
    fontSize: fontSize.sm,
    fontWeight: '600',
  },
  subtitle: {
    fontSize: fontSize.md,
    color: colors.textLight,
    marginTop: spacing.xs,
  },
  list: {
    padding: spacing.md,
  },
  studentCard: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: spacing.md,
  },
  avatar: {
    width: 50,
    height: 50,
    borderRadius: 25,
    backgroundColor: colors.accent,
    justifyContent: 'center',
    alignItems: 'center',
  },
  avatarText: {
    color: colors.textOnAccent,
    fontSize: fontSize.lg,
    fontWeight: '700',
  },
  studentInfo: {
    flex: 1,
  },
  studentName: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
  },
  studentGoals: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 2,
  },
  sessionCount: {
    fontSize: fontSize.xs,
    color: colors.info,
    marginTop: 4,
  },
  actionButtons: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginTop: spacing.md,
    paddingTop: spacing.md,
    borderTopWidth: 1,
    borderTopColor: colors.divider,
  },
  actionButton: {
    flex: 1,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.lg,
  },
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.5)',
    justifyContent: 'flex-end',
  },
  modalContent: {
    backgroundColor: colors.surface,
    borderTopLeftRadius: borderRadius.xl,
    borderTopRightRadius: borderRadius.xl,
    padding: spacing.lg,
    maxHeight: '85%',
  },
  modalTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.lg,
  },
  exerciseHint: {
    fontSize: fontSize.sm,
    color: colors.info,
    fontStyle: 'italic',
    marginVertical: spacing.md,
    padding: spacing.md,
    backgroundColor: colors.info + '10',
    borderRadius: borderRadius.md,
  },
  modalButtons: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginTop: spacing.md,
    marginBottom: spacing.lg,
  },
  modalButton: {
    flex: 1,
  },
});
