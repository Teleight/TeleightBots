import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Modal,
  Alert,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { InputField } from '../../components/common/InputField';
import { WorkoutPlan, Exercise, ExerciseCategory } from '../../types';

const DAYS = ['Lunedi', 'Martedi', 'Mercoledi', 'Giovedi', 'Venerdi', 'Sabato', 'Domenica'];

const CATEGORIES: { value: ExerciseCategory; label: string }[] = [
  { value: 'forza', label: 'Forza' },
  { value: 'cardio', label: 'Cardio' },
  { value: 'mobilita', label: 'Mobilita' },
  { value: 'stretching', label: 'Stretching' },
  { value: 'funzionale', label: 'Funzionale' },
  { value: 'posturale', label: 'Posturale' },
  { value: 'altro', label: 'Altro' },
];

export const WorkoutPlanScreen: React.FC = () => {
  const [planTitle, setPlanTitle] = useState('');
  const [selectedDay, setSelectedDay] = useState(0);
  const [exercises, setExercises] = useState<Record<number, Exercise[]>>({});
  const [showExerciseModal, setShowExerciseModal] = useState(false);

  // Form esercizio
  const [exName, setExName] = useState('');
  const [exDescription, setExDescription] = useState('');
  const [exSets, setExSets] = useState('');
  const [exReps, setExReps] = useState('');
  const [exRest, setExRest] = useState('');
  const [exCategory, setExCategory] = useState<ExerciseCategory>('forza');
  const [exVideoUrl, setExVideoUrl] = useState('');
  const [exNotes, setExNotes] = useState('');

  const addExercise = () => {
    if (!exName || !exSets || !exReps) {
      Alert.alert('Errore', 'Compila nome, serie e ripetizioni');
      return;
    }

    const newExercise: Exercise = {
      id: Date.now().toString(),
      name: exName,
      description: exDescription,
      sets: parseInt(exSets, 10),
      reps: exReps,
      restSeconds: parseInt(exRest, 10) || 60,
      videoUrl: exVideoUrl || undefined,
      notes: exNotes,
      category: exCategory,
    };

    setExercises((prev) => ({
      ...prev,
      [selectedDay]: [...(prev[selectedDay] || []), newExercise],
    }));

    // Reset form
    setExName('');
    setExDescription('');
    setExSets('');
    setExReps('');
    setExRest('');
    setExVideoUrl('');
    setExNotes('');
    setShowExerciseModal(false);
  };

  const removeExercise = (dayIndex: number, exerciseIndex: number) => {
    setExercises((prev) => ({
      ...prev,
      [dayIndex]: prev[dayIndex]?.filter((_, i) => i !== exerciseIndex) || [],
    }));
  };

  const savePlan = () => {
    if (!planTitle) {
      Alert.alert('Errore', 'Inserisci un titolo per la programmazione');
      return;
    }
    Alert.alert('Successo', 'Programmazione salvata e inviata all\'allievo!');
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Crea Programmazione</Text>
        <Text style={styles.subtitle}>
          Crea il piano settimanale con video e descrizioni
        </Text>
      </View>

      <View style={styles.content}>
        <InputField
          label="Titolo Programmazione"
          value={planTitle}
          onChangeText={setPlanTitle}
          placeholder="Es: Scheda Ipertrofia - Fase 1"
        />

        {/* Selettore giorno */}
        <Text style={styles.sectionTitle}>Giorno della settimana</Text>
        <ScrollView
          horizontal
          showsHorizontalScrollIndicator={false}
          style={styles.dayScroll}
        >
          {DAYS.map((day, index) => {
            const exerciseCount = exercises[index]?.length || 0;
            return (
              <TouchableOpacity
                key={day}
                style={[
                  styles.dayButton,
                  selectedDay === index && styles.dayButtonActive,
                ]}
                onPress={() => setSelectedDay(index)}
              >
                <Text
                  style={[
                    styles.dayText,
                    selectedDay === index && styles.dayTextActive,
                  ]}
                >
                  {day.substring(0, 3)}
                </Text>
                {exerciseCount > 0 && (
                  <View style={styles.dayBadge}>
                    <Text style={styles.dayBadgeText}>{exerciseCount}</Text>
                  </View>
                )}
              </TouchableOpacity>
            );
          })}
        </ScrollView>

        {/* Esercizi del giorno selezionato */}
        <View style={styles.exerciseSection}>
          <View style={styles.exerciseHeader}>
            <Text style={styles.sectionTitle}>
              {DAYS[selectedDay]} - Esercizi
            </Text>
            <Button
              title="+ Esercizio"
              onPress={() => setShowExerciseModal(true)}
              variant="primary"
            />
          </View>

          {(!exercises[selectedDay] || exercises[selectedDay].length === 0) ? (
            <Card>
              <Text style={styles.emptyText}>
                Nessun esercizio per questo giorno.{'\n'}
                Aggiungi il primo esercizio!
              </Text>
            </Card>
          ) : (
            exercises[selectedDay].map((ex, index) => (
              <Card key={ex.id} variant="outlined">
                <View style={styles.exerciseRow}>
                  <View style={styles.exerciseNumber}>
                    <Text style={styles.exerciseNumberText}>{index + 1}</Text>
                  </View>
                  <View style={styles.exerciseInfo}>
                    <Text style={styles.exerciseName}>{ex.name}</Text>
                    <Text style={styles.exerciseDetails}>
                      {ex.sets}x{ex.reps} | Rec: {ex.restSeconds}s
                    </Text>
                    {ex.description && (
                      <Text style={styles.exerciseDesc}>{ex.description}</Text>
                    )}
                    {ex.videoUrl && (
                      <Text style={styles.videoLink}>Video allegato</Text>
                    )}
                    {ex.notes && (
                      <Text style={styles.exerciseNotes}>
                        Note: {ex.notes}
                      </Text>
                    )}
                  </View>
                  <TouchableOpacity
                    onPress={() => removeExercise(selectedDay, index)}
                  >
                    <Text style={styles.removeBtn}>X</Text>
                  </TouchableOpacity>
                </View>
              </Card>
            ))
          )}
        </View>

        <Button
          title="Salva e Invia Programmazione"
          onPress={savePlan}
          style={styles.saveButton}
        />
      </View>

      {/* Modale Aggiungi Esercizio */}
      <Modal visible={showExerciseModal} animationType="slide" transparent>
        <View style={styles.modalOverlay}>
          <ScrollView style={styles.modalContent}>
            <Text style={styles.modalTitle}>Nuovo Esercizio</Text>

            <InputField
              label="Nome esercizio"
              value={exName}
              onChangeText={setExName}
              placeholder="Es: Panca piana con bilanciere"
            />

            <InputField
              label="Descrizione / Istruzioni"
              value={exDescription}
              onChangeText={setExDescription}
              placeholder="Come eseguire l'esercizio..."
              multiline
              numberOfLines={3}
            />

            <View style={styles.row}>
              <View style={styles.halfField}>
                <InputField
                  label="Serie"
                  value={exSets}
                  onChangeText={setExSets}
                  keyboardType="number-pad"
                  placeholder="4"
                />
              </View>
              <View style={styles.halfField}>
                <InputField
                  label="Ripetizioni"
                  value={exReps}
                  onChangeText={setExReps}
                  placeholder="8-12"
                />
              </View>
            </View>

            <InputField
              label="Recupero (secondi)"
              value={exRest}
              onChangeText={setExRest}
              keyboardType="number-pad"
              placeholder="90"
            />

            {/* Categoria */}
            <Text style={styles.fieldLabel}>Categoria</Text>
            <View style={styles.categoryRow}>
              {CATEGORIES.map((cat) => (
                <TouchableOpacity
                  key={cat.value}
                  style={[
                    styles.categoryChip,
                    exCategory === cat.value && styles.categoryChipActive,
                  ]}
                  onPress={() => setExCategory(cat.value)}
                >
                  <Text
                    style={[
                      styles.categoryChipText,
                      exCategory === cat.value && styles.categoryChipTextActive,
                    ]}
                  >
                    {cat.label}
                  </Text>
                </TouchableOpacity>
              ))}
            </View>

            <InputField
              label="URL Video (opzionale)"
              value={exVideoUrl}
              onChangeText={setExVideoUrl}
              placeholder="Link al video dell'esercizio"
              autoCapitalize="none"
            />

            <InputField
              label="Note (opzionale)"
              value={exNotes}
              onChangeText={setExNotes}
              placeholder="Note aggiuntive..."
              multiline
            />

            <View style={styles.modalButtons}>
              <Button
                title="Annulla"
                onPress={() => setShowExerciseModal(false)}
                variant="outline"
                style={styles.modalButton}
              />
              <Button
                title="Aggiungi"
                onPress={addExercise}
                style={styles.modalButton}
              />
            </View>

            <View style={styles.bottomSpacer} />
          </ScrollView>
        </View>
      </Modal>

      <View style={styles.bottomSpacer} />
    </ScrollView>
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
  content: {
    padding: spacing.md,
  },
  sectionTitle: {
    fontSize: fontSize.lg,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.sm,
  },
  dayScroll: {
    marginBottom: spacing.md,
  },
  dayButton: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    backgroundColor: colors.surface,
    marginRight: spacing.sm,
    ...shadows.small,
    alignItems: 'center',
  },
  dayButtonActive: {
    backgroundColor: colors.accent,
  },
  dayText: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.textSecondary,
  },
  dayTextActive: {
    color: colors.textOnAccent,
  },
  dayBadge: {
    backgroundColor: colors.success,
    borderRadius: 10,
    width: 20,
    height: 20,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 4,
  },
  dayBadgeText: {
    color: '#FFF',
    fontSize: fontSize.xs,
    fontWeight: '700',
  },
  exerciseSection: {
    marginTop: spacing.md,
  },
  exerciseHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: spacing.sm,
  },
  exerciseRow: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    gap: spacing.md,
  },
  exerciseNumber: {
    width: 28,
    height: 28,
    borderRadius: 14,
    backgroundColor: colors.accent,
    justifyContent: 'center',
    alignItems: 'center',
  },
  exerciseNumberText: {
    color: '#FFF',
    fontWeight: '700',
    fontSize: fontSize.sm,
  },
  exerciseInfo: {
    flex: 1,
  },
  exerciseName: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
  },
  exerciseDetails: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 2,
  },
  exerciseDesc: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 4,
  },
  videoLink: {
    fontSize: fontSize.sm,
    color: colors.info,
    marginTop: 4,
    fontWeight: '600',
  },
  exerciseNotes: {
    fontSize: fontSize.sm,
    color: colors.warning,
    marginTop: 4,
    fontStyle: 'italic',
  },
  removeBtn: {
    color: colors.error,
    fontSize: fontSize.lg,
    fontWeight: '700',
    padding: spacing.xs,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.lg,
    lineHeight: 22,
  },
  saveButton: {
    marginTop: spacing.xl,
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
  row: {
    flexDirection: 'row',
    gap: spacing.md,
  },
  halfField: {
    flex: 1,
  },
  fieldLabel: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
    marginBottom: spacing.sm,
  },
  categoryRow: {
    flexDirection: 'row',
    gap: spacing.sm,
    flexWrap: 'wrap',
    marginBottom: spacing.md,
  },
  categoryChip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.xs,
    borderRadius: borderRadius.round,
    borderWidth: 1,
    borderColor: colors.border,
  },
  categoryChipActive: {
    backgroundColor: colors.accent,
    borderColor: colors.accent,
  },
  categoryChipText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
  },
  categoryChipTextActive: {
    color: colors.textOnAccent,
  },
  modalButtons: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginTop: spacing.md,
  },
  modalButton: {
    flex: 1,
  },
  bottomSpacer: {
    height: spacing.xxl * 2,
  },
});
