import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Modal,
  Alert,
  FlatList,
  RefreshControl,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { InputField } from '../../components/common/InputField';
import { Badge } from '../../components/common/Badge';
import { TrainingSession, Student, Collaborator } from '../../types';
import { useAuth } from '../../hooks/useAuth';
import {
  createSession,
  getAllSessions,
  getCollaboratorSessions,
  updateSessionStatus,
} from '../../services/sessionService';
import { getStudents, getCollaborators } from '../../services/authService';

const TIME_SLOTS = [
  '07:00', '08:00', '09:00', '10:00', '11:00', '12:00',
  '13:00', '14:00', '15:00', '16:00', '17:00', '18:00',
  '19:00', '20:00', '21:00',
];

export const ScheduleSessionScreen: React.FC = () => {
  const { user, isOwner, isCollaborator } = useAuth();
  const [sessions, setSessions] = useState<TrainingSession[]>([]);
  const [students, setStudents] = useState<Student[]>([]);
  const [collaborators, setCollaborators] = useState<Collaborator[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [refreshing, setRefreshing] = useState(false);
  const [saving, setSaving] = useState(false);

  // Form
  const [selectedStudentId, setSelectedStudentId] = useState('');
  const [selectedCollaboratorId, setSelectedCollaboratorId] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const [startTime, setStartTime] = useState('09:00');
  const [endTime, setEndTime] = useState('10:00');
  const [notes, setNotes] = useState('');

  const loadData = useCallback(async () => {
    if (!user) return;
    try {
      const [studs, collabs] = await Promise.all([
        getStudents(),
        isOwner ? getCollaborators() : Promise.resolve([]),
      ]);

      if (isCollaborator) {
        setStudents(studs.filter((s) => s.assignedCollaboratorId === user.id));
      } else {
        setStudents(studs);
      }
      setCollaborators(collabs);

      const allSessions = isOwner
        ? await getAllSessions()
        : await getCollaboratorSessions(user.id);
      // Ordina per data
      allSessions.sort((a, b) => {
        const dateA = new Date(a.date as unknown as string).getTime();
        const dateB = new Date(b.date as unknown as string).getTime();
        return dateB - dateA;
      });
      setSessions(allSessions);
    } catch {
      // Silently handle
    }
  }, [user, isOwner, isCollaborator]);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const onRefresh = async () => {
    setRefreshing(true);
    await loadData();
    setRefreshing(false);
  };

  // Genera prossimi 14 giorni
  const getNextDays = (): { label: string; value: string }[] => {
    const days: { label: string; value: string }[] = [];
    for (let i = 0; i < 14; i++) {
      const d = new Date();
      d.setDate(d.getDate() + i);
      const value = d.toISOString().split('T')[0];
      const label = d.toLocaleDateString('it-IT', {
        weekday: 'short',
        day: 'numeric',
        month: 'short',
      });
      days.push({ label, value });
    }
    return days;
  };

  const handleCreate = async () => {
    if (!selectedStudentId || !selectedDate || !user) {
      Alert.alert('Errore', 'Seleziona allievo e data');
      return;
    }

    const collabId = isOwner ? selectedCollaboratorId : user.id;
    if (!collabId) {
      Alert.alert('Errore', 'Seleziona un collaboratore');
      return;
    }

    setSaving(true);
    try {
      await createSession({
        studentId: selectedStudentId,
        collaboratorId: collabId,
        date: new Date(selectedDate),
        startTime,
        endTime,
        status: 'scheduled',
        notes,
        isCountedAsCompleted: false,
      });
      Alert.alert('Successo', 'Sessione programmata!');
      resetForm();
      setShowModal(false);
      loadData();
    } catch {
      Alert.alert('Errore', 'Impossibile creare la sessione');
    } finally {
      setSaving(false);
    }
  };

  const handleComplete = (sessionId: string) => {
    Alert.alert(
      'Conferma',
      'Segna questa sessione come completata?',
      [
        { text: 'Annulla', style: 'cancel' },
        {
          text: 'Completata',
          onPress: async () => {
            try {
              await updateSessionStatus(sessionId, 'completed');
              loadData();
            } catch {
              Alert.alert('Errore', 'Impossibile aggiornare');
            }
          },
        },
      ]
    );
  };

  const resetForm = () => {
    setSelectedStudentId('');
    setSelectedCollaboratorId('');
    setSelectedDate('');
    setStartTime('09:00');
    setEndTime('10:00');
    setNotes('');
  };

  const getStudentName = (id: string) => {
    const s = students.find((st) => st.id === id);
    return s ? `${s.name} ${s.surname}` : 'Allievo';
  };

  const getCollabName = (id: string) => {
    const c = collaborators.find((co) => co.id === id);
    return c ? `${c.name} ${c.surname}` : '';
  };

  const nextDays = getNextDays();

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Sessioni</Text>
        <Text style={styles.subtitle}>
          {sessions.length} sessioni totali
        </Text>
      </View>

      <View style={styles.addButtonContainer}>
        <Button
          title="+ Nuova Sessione"
          onPress={() => setShowModal(true)}
        />
      </View>

      <FlatList
        data={sessions}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
        }
        renderItem={({ item }) => {
          const sessionDate = new Date(item.date as unknown as string);
          const isFuture = sessionDate > new Date();
          const canComplete = isFuture && item.status === 'scheduled';

          return (
            <Card variant="elevated">
              <View style={styles.sessionHeader}>
                <View style={styles.sessionInfo}>
                  <Text style={styles.sessionDate}>
                    {sessionDate.toLocaleDateString('it-IT', {
                      weekday: 'long',
                      day: 'numeric',
                      month: 'long',
                    })}
                  </Text>
                  <Text style={styles.sessionTime}>
                    {item.startTime} - {item.endTime}
                  </Text>
                  <Text style={styles.sessionStudent}>
                    {getStudentName(item.studentId)}
                  </Text>
                  {isOwner && item.collaboratorId && (
                    <Text style={styles.sessionCollab}>
                      Trainer: {getCollabName(item.collaboratorId)}
                    </Text>
                  )}
                </View>
                <Badge status={item.status} />
              </View>
              {item.notes ? (
                <Text style={styles.sessionNotes}>{item.notes}</Text>
              ) : null}
              {canComplete && (
                <View style={styles.completeContainer}>
                  <Button
                    title="Segna Completata"
                    onPress={() => handleComplete(item.id)}
                    variant="secondary"
                  />
                </View>
              )}
            </Card>
          );
        }}
        ListEmptyComponent={
          <Card>
            <Text style={styles.emptyText}>
              Nessuna sessione programmata.{'\n'}Crea la prima sessione!
            </Text>
          </Card>
        }
      />

      {/* Modale nuova sessione */}
      <Modal visible={showModal} animationType="slide" transparent>
        <View style={styles.modalOverlay}>
          <ScrollView style={styles.modalContent}>
            <Text style={styles.modalTitle}>Nuova Sessione</Text>

            {/* Allievo */}
            <Text style={styles.fieldLabel}>Allievo</Text>
            <ScrollView horizontal showsHorizontalScrollIndicator={false}>
              <View style={styles.chipRow}>
                {students.map((s) => (
                  <TouchableOpacity
                    key={s.id}
                    style={[
                      styles.chip,
                      selectedStudentId === s.id && styles.chipActive,
                    ]}
                    onPress={() => setSelectedStudentId(s.id)}
                  >
                    <Text
                      style={[
                        styles.chipText,
                        selectedStudentId === s.id && styles.chipTextActive,
                      ]}
                    >
                      {s.name} {s.surname}
                    </Text>
                  </TouchableOpacity>
                ))}
              </View>
            </ScrollView>

            {/* Collaboratore (solo owner) */}
            {isOwner && (
              <>
                <Text style={styles.fieldLabel}>Collaboratore</Text>
                <ScrollView horizontal showsHorizontalScrollIndicator={false}>
                  <View style={styles.chipRow}>
                    {collaborators.map((c) => (
                      <TouchableOpacity
                        key={c.id}
                        style={[
                          styles.chip,
                          selectedCollaboratorId === c.id && styles.chipActive,
                        ]}
                        onPress={() => setSelectedCollaboratorId(c.id)}
                      >
                        <Text
                          style={[
                            styles.chipText,
                            selectedCollaboratorId === c.id && styles.chipTextActive,
                          ]}
                        >
                          {c.name} {c.surname}
                        </Text>
                      </TouchableOpacity>
                    ))}
                  </View>
                </ScrollView>
              </>
            )}

            {/* Data */}
            <Text style={styles.fieldLabel}>Data</Text>
            <ScrollView horizontal showsHorizontalScrollIndicator={false}>
              <View style={styles.chipRow}>
                {nextDays.map((d) => (
                  <TouchableOpacity
                    key={d.value}
                    style={[
                      styles.dateChip,
                      selectedDate === d.value && styles.chipActive,
                    ]}
                    onPress={() => setSelectedDate(d.value)}
                  >
                    <Text
                      style={[
                        styles.chipText,
                        selectedDate === d.value && styles.chipTextActive,
                      ]}
                    >
                      {d.label}
                    </Text>
                  </TouchableOpacity>
                ))}
              </View>
            </ScrollView>

            {/* Orario */}
            <View style={styles.timeRow}>
              <View style={styles.timeField}>
                <Text style={styles.fieldLabel}>Inizio</Text>
                <ScrollView horizontal showsHorizontalScrollIndicator={false}>
                  <View style={styles.chipRow}>
                    {TIME_SLOTS.map((t) => (
                      <TouchableOpacity
                        key={`start-${t}`}
                        style={[
                          styles.timeChip,
                          startTime === t && styles.chipActive,
                        ]}
                        onPress={() => setStartTime(t)}
                      >
                        <Text
                          style={[
                            styles.chipText,
                            startTime === t && styles.chipTextActive,
                          ]}
                        >
                          {t}
                        </Text>
                      </TouchableOpacity>
                    ))}
                  </View>
                </ScrollView>
              </View>
              <View style={styles.timeField}>
                <Text style={styles.fieldLabel}>Fine</Text>
                <ScrollView horizontal showsHorizontalScrollIndicator={false}>
                  <View style={styles.chipRow}>
                    {TIME_SLOTS.map((t) => (
                      <TouchableOpacity
                        key={`end-${t}`}
                        style={[
                          styles.timeChip,
                          endTime === t && styles.chipActive,
                        ]}
                        onPress={() => setEndTime(t)}
                      >
                        <Text
                          style={[
                            styles.chipText,
                            endTime === t && styles.chipTextActive,
                          ]}
                        >
                          {t}
                        </Text>
                      </TouchableOpacity>
                    ))}
                  </View>
                </ScrollView>
              </View>
            </View>

            <InputField
              label="Note (opzionale)"
              value={notes}
              onChangeText={setNotes}
              placeholder="Note sulla sessione..."
              multiline
              numberOfLines={3}
            />

            <View style={styles.modalButtons}>
              <Button
                title="Annulla"
                onPress={() => { setShowModal(false); resetForm(); }}
                variant="outline"
                style={styles.modalButton}
              />
              <Button
                title={saving ? 'Salvataggio...' : 'Programma'}
                onPress={handleCreate}
                style={styles.modalButton}
                loading={saving}
              />
            </View>

            <View style={styles.bottomSpacer} />
          </ScrollView>
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
  subtitle: {
    fontSize: fontSize.md,
    color: colors.textLight,
    marginTop: spacing.xs,
  },
  addButtonContainer: {
    padding: spacing.md,
  },
  list: {
    paddingHorizontal: spacing.md,
    paddingBottom: spacing.xxl,
  },
  sessionHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
  },
  sessionInfo: {
    flex: 1,
  },
  sessionDate: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
    textTransform: 'capitalize',
  },
  sessionTime: {
    fontSize: fontSize.md,
    color: colors.accent,
    fontWeight: '600',
    marginTop: 2,
  },
  sessionStudent: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 4,
  },
  sessionCollab: {
    fontSize: fontSize.xs,
    color: colors.collaboratorBadge,
    marginTop: 2,
  },
  sessionNotes: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: spacing.sm,
    fontStyle: 'italic',
    borderLeftWidth: 3,
    borderLeftColor: colors.accent,
    paddingLeft: spacing.sm,
  },
  completeContainer: {
    marginTop: spacing.md,
    paddingTop: spacing.sm,
    borderTopWidth: 1,
    borderTopColor: colors.divider,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.lg,
    lineHeight: 22,
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
    maxHeight: '90%',
  },
  modalTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.lg,
  },
  fieldLabel: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
    marginBottom: spacing.sm,
    marginTop: spacing.sm,
  },
  chipRow: {
    flexDirection: 'row',
    gap: spacing.sm,
    paddingBottom: spacing.sm,
  },
  chip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    borderWidth: 1,
    borderColor: colors.border,
    backgroundColor: colors.surfaceLight,
  },
  chipActive: {
    backgroundColor: colors.accent,
    borderColor: colors.accent,
  },
  chipText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    fontWeight: '600',
  },
  chipTextActive: {
    color: colors.textOnAccent,
  },
  dateChip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.lg,
    borderWidth: 1,
    borderColor: colors.border,
    backgroundColor: colors.surfaceLight,
  },
  timeRow: {
    gap: spacing.sm,
  },
  timeField: {},
  timeChip: {
    paddingHorizontal: spacing.sm,
    paddingVertical: spacing.xs,
    borderRadius: borderRadius.md,
    borderWidth: 1,
    borderColor: colors.border,
    backgroundColor: colors.surfaceLight,
  },
  modalButtons: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginTop: spacing.lg,
  },
  modalButton: {
    flex: 1,
  },
  bottomSpacer: {
    height: spacing.xxl * 2,
  },
});
