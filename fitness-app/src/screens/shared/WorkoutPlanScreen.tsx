import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Modal,
  Alert,
  ActivityIndicator,
} from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import { ref, uploadBytes, getDownloadURL } from 'firebase/storage';
import { storage } from '../../config/firebase';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { InputField } from '../../components/common/InputField';
import { ModalHeader } from '../../components/common/ModalHeader';
import { Exercise, ExerciseCategory, WeeklyDay, Student } from '../../types';
import { useAuth } from '../../hooks/useAuth';
import { createWorkoutPlan, getActiveWorkoutPlan } from '../../services/programService';
import { getStudents } from '../../services/authService';
import {
  suggestWorkoutProgression,
  suggestExercises,
  AIProgressionSuggestion,
  getAIApiKey,
} from '../../services/aiService';

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
  const { user, isOwner, isCollaborator } = useAuth();
  const [students, setStudents] = useState<Student[]>([]);
  const [selectedStudentId, setSelectedStudentId] = useState('');
  const [planTitle, setPlanTitle] = useState('');
  const [selectedDay, setSelectedDay] = useState(0);
  const [exercises, setExercises] = useState<Record<number, Exercise[]>>({});
  const [showExerciseModal, setShowExerciseModal] = useState(false);
  const [saving, setSaving] = useState(false);

  // Form esercizio
  const [exName, setExName] = useState('');
  const [exDescription, setExDescription] = useState('');
  const [exSets, setExSets] = useState('');
  const [exReps, setExReps] = useState('');
  const [exRest, setExRest] = useState('');
  const [exCategory, setExCategory] = useState<ExerciseCategory>('forza');
  const [exVideoUrl, setExVideoUrl] = useState('');
  const [exNotes, setExNotes] = useState('');
  const [uploadingVideo, setUploadingVideo] = useState(false);

  // AI State
  const [aiLoading, setAiLoading] = useState(false);
  const [aiSuggestion, setAiSuggestion] = useState<AIProgressionSuggestion | null>(null);
  const [showAiModal, setShowAiModal] = useState(false);
  const [aiExercisesLoading, setAiExercisesLoading] = useState(false);

  const loadStudents = useCallback(async () => {
    if (!user) return;
    try {
      const allStudents = await getStudents();
      if (isOwner) {
        setStudents(allStudents);
      } else if (isCollaborator) {
        setStudents(allStudents.filter((s) => s.assignedCollaboratorId === user.id));
      }
    } catch {
      // Silently handle
    }
  }, [user, isOwner, isCollaborator]);

  useEffect(() => {
    loadStudents();
  }, [loadStudents]);

  // Upload video esercizio su Firebase Storage
  const pickAndUploadVideo = async () => {
    try {
      const result = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ImagePicker.MediaTypeOptions.Videos,
        quality: 0.7,
        videoMaxDuration: 120,
      });

      if (result.canceled || !result.assets[0]) return;

      setUploadingVideo(true);
      const videoUri = result.assets[0].uri;
      const timestamp = Date.now();
      const videoRef = ref(storage, `exercise-videos/${timestamp}.mp4`);

      const response = await fetch(videoUri);
      const blob = await response.blob();
      await uploadBytes(videoRef, blob);
      const downloadUrl = await getDownloadURL(videoRef);

      setExVideoUrl(downloadUrl);
      Alert.alert('Video caricato!');
    } catch {
      Alert.alert('Errore', 'Impossibile caricare il video');
    } finally {
      setUploadingVideo(false);
    }
  };

  // AI: genera progressione dalla scheda attuale
  const handleAIProgression = async () => {
    if (!selectedStudentId) {
      Alert.alert('Errore', 'Seleziona prima un allievo');
      return;
    }
    if (!getAIApiKey()) {
      Alert.alert('API Key mancante', 'Inserisci la chiave API Anthropic nelle impostazioni.');
      return;
    }

    setAiLoading(true);
    try {
      const activePlan = await getActiveWorkoutPlan(selectedStudentId);
      if (!activePlan) {
        Alert.alert('Nessuna scheda', 'L\'allievo non ha una scheda attiva da cui generare la progressione.');
        return;
      }

      const student = students.find((s) => s.id === selectedStudentId);
      if (!student) return;

      const suggestion = await suggestWorkoutProgression(
        {
          title: activePlan.title,
          weeklySchedule: activePlan.weeklySchedule,
        },
        {
          name: `${student.name} ${student.surname}`,
          goals: student.goals,
          medicalNotes: student.medicalNotes,
        },
        4 // settimana corrente (semplificato)
      );

      setAiSuggestion(suggestion);
      setShowAiModal(true);
    } catch (err: unknown) {
      const msg = err instanceof Error ? err.message : 'Errore AI';
      Alert.alert('Errore', msg);
    } finally {
      setAiLoading(false);
    }
  };

  // AI: applica la progressione suggerita
  const applyAISuggestion = () => {
    if (!aiSuggestion) return;

    const dayMap: Record<string, number> = {
      Lunedi: 0, Martedi: 1, Mercoledi: 2, Giovedi: 3,
      Venerdi: 4, Sabato: 5, Domenica: 6,
    };

    const newExercises: Record<number, Exercise[]> = {};
    for (const day of aiSuggestion.weeklySchedule) {
      const dayIndex = dayMap[day.day] ?? 0;
      newExercises[dayIndex] = day.exercises.map((ex, i) => ({
        id: `${Date.now()}_${dayIndex}_${i}`,
        name: ex.name,
        description: '',
        sets: ex.sets,
        reps: ex.reps,
        restSeconds: ex.restSeconds,
        notes: ex.notes,
        category: (ex.category as ExerciseCategory) || 'forza',
      }));
    }

    setExercises(newExercises);
    setPlanTitle(aiSuggestion.title);
    setShowAiModal(false);
    Alert.alert('Applicata!', 'La progressione AI e\' stata applicata. Puoi modificarla prima di salvare.');
  };

  // AI: suggerisci esercizi per categoria
  const handleAIExerciseSuggestion = async () => {
    if (!getAIApiKey()) {
      Alert.alert('API Key mancante', 'Inserisci la chiave API Anthropic nelle impostazioni.');
      return;
    }

    const student = students.find((s) => s.id === selectedStudentId);
    const goal = student?.goals || 'fitness generale';

    setAiExercisesLoading(true);
    try {
      const suggestions = await suggestExercises(exCategory, goal);
      if (suggestions.length === 0) {
        Alert.alert('Nessun suggerimento', 'L\'AI non ha generato suggerimenti');
        return;
      }

      // Mostra i suggerimenti e lascia scegliere
      const firstSuggestion = suggestions[0];
      Alert.alert(
        'Suggerimento AI',
        `${firstSuggestion.name}\n${firstSuggestion.sets}x${firstSuggestion.reps} (rec ${firstSuggestion.restSeconds}s)\n\n${firstSuggestion.description}`,
        [
          { text: 'Ignora', style: 'cancel' },
          {
            text: 'Usa',
            onPress: () => {
              setExName(firstSuggestion.name);
              setExSets(String(firstSuggestion.sets));
              setExReps(firstSuggestion.reps);
              setExRest(String(firstSuggestion.restSeconds));
              setExDescription(firstSuggestion.description);
            },
          },
        ]
      );
    } catch {
      Alert.alert('Errore', 'Impossibile ottenere suggerimenti AI');
    } finally {
      setAiExercisesLoading(false);
    }
  };

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

  const savePlan = async () => {
    if (!planTitle || !user) {
      Alert.alert('Errore', 'Inserisci un titolo per la programmazione');
      return;
    }
    if (!selectedStudentId) {
      Alert.alert('Errore', 'Seleziona un allievo');
      return;
    }

    const totalExercises = Object.values(exercises).reduce((sum, exs) => sum + exs.length, 0);
    if (totalExercises === 0) {
      Alert.alert('Errore', 'Aggiungi almeno un esercizio');
      return;
    }

    setSaving(true);
    try {
      const weeklySchedule: WeeklyDay[] = Array.from({ length: 7 }, (_, i) => ({
        dayOfWeek: i,
        exercises: exercises[i] || [],
        notes: '',
      }));

      const startDate = new Date();
      const endDate = new Date();
      endDate.setDate(endDate.getDate() + 28);

      await createWorkoutPlan({
        studentId: selectedStudentId,
        collaboratorId: user.id,
        title: planTitle,
        startDate,
        endDate,
        weeklySchedule,
        createdAt: new Date(),
        isActive: true,
      });

      Alert.alert('Successo', 'Programmazione salvata e inviata all\'allievo!');
      setPlanTitle('');
      setExercises({});
      setSelectedStudentId('');
    } catch {
      Alert.alert('Errore', 'Impossibile salvare la programmazione');
    } finally {
      setSaving(false);
    }
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
        {/* Selezione allievo */}
        <Text style={styles.sectionTitle}>Seleziona Allievo</Text>
        {students.length === 0 ? (
          <Card>
            <Text style={styles.emptyText}>Nessun allievo disponibile</Text>
          </Card>
        ) : (
          <ScrollView horizontal showsHorizontalScrollIndicator={false} style={styles.studentScroll}>
            {students.map((s) => (
              <TouchableOpacity
                key={s.id}
                style={[
                  styles.studentChip,
                  selectedStudentId === s.id && styles.studentChipActive,
                ]}
                onPress={() => setSelectedStudentId(s.id)}
              >
                <Text
                  style={[
                    styles.studentChipText,
                    selectedStudentId === s.id && styles.studentChipTextActive,
                  ]}
                >
                  {s.name} {s.surname}
                </Text>
              </TouchableOpacity>
            ))}
          </ScrollView>
        )}

        <InputField
          label="Titolo Programmazione"
          value={planTitle}
          onChangeText={setPlanTitle}
          placeholder="Es: Scheda Ipertrofia - Fase 1"
        />

        {/* AI Progression Button */}
        {selectedStudentId && (
          <Button
            title={aiLoading ? 'Generazione AI...' : 'Genera Progressione con AI'}
            onPress={handleAIProgression}
            variant="outline"
            loading={aiLoading}
            style={{ marginBottom: spacing.md }}
          />
        )}

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
          title={saving ? 'Salvataggio...' : 'Salva e Invia Programmazione'}
          onPress={savePlan}
          style={styles.saveButton}
          loading={saving}
        />
      </View>

      {/* Modale Aggiungi Esercizio */}
      <Modal visible={showExerciseModal} animationType="slide" transparent>
        <View style={styles.modalOverlay}>
          <ScrollView style={styles.modalContent}>
            <ModalHeader title="Nuovo Esercizio" onClose={() => setShowExerciseModal(false)} />

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

            {/* Video Upload */}
            <Text style={styles.fieldLabel}>Video Esercizio (opzionale)</Text>
            {exVideoUrl ? (
              <View style={styles.videoUploaded}>
                <Text style={styles.videoUploadedText}>Video caricato</Text>
                <TouchableOpacity onPress={() => setExVideoUrl('')}>
                  <Text style={styles.removeBtn}>X</Text>
                </TouchableOpacity>
              </View>
            ) : (
              <Button
                title={uploadingVideo ? 'Caricamento...' : 'Carica Video'}
                onPress={pickAndUploadVideo}
                variant="outline"
                loading={uploadingVideo}
              />
            )}

            {/* AI Exercise Suggestion */}
            <Button
              title={aiExercisesLoading ? 'AI...' : 'Suggerisci con AI'}
              onPress={handleAIExerciseSuggestion}
              variant="outline"
              loading={aiExercisesLoading}
              style={{ marginTop: spacing.sm }}
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

      {/* Modale AI Progressione */}
      <Modal visible={showAiModal} animationType="slide" transparent>
        <View style={styles.modalOverlay}>
          <ScrollView style={styles.modalContent}>
            <ModalHeader title="Progressione AI" onClose={() => setShowAiModal(false)} />

            {aiSuggestion && (
              <>
                <Card variant="elevated">
                  <Text style={styles.aiTitle}>{aiSuggestion.title}</Text>
                  <Text style={styles.aiReasoning}>{aiSuggestion.reasoning}</Text>
                </Card>

                {aiSuggestion.weeklySchedule.map((day, di) => (
                  <Card key={di} variant="outlined">
                    <Text style={styles.aiDayTitle}>{day.day}</Text>
                    {day.exercises.map((ex, ei) => (
                      <Text key={ei} style={styles.aiExercise}>
                        {ei + 1}. {ex.name} - {ex.sets}x{ex.reps} (rec {ex.restSeconds}s)
                        {ex.notes ? ` | ${ex.notes}` : ''}
                      </Text>
                    ))}
                  </Card>
                ))}

                {aiSuggestion.generalNotes && (
                  <Card>
                    <Text style={styles.aiNotes}>{aiSuggestion.generalNotes}</Text>
                  </Card>
                )}

                <View style={styles.modalButtons}>
                  <Button
                    title="Annulla"
                    onPress={() => setShowAiModal(false)}
                    variant="outline"
                    style={styles.modalButton}
                  />
                  <Button
                    title="Applica"
                    onPress={applyAISuggestion}
                    style={styles.modalButton}
                  />
                </View>
              </>
            )}

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
  studentScroll: {
    marginBottom: spacing.md,
  },
  studentChip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    backgroundColor: colors.surface,
    marginRight: spacing.sm,
    borderWidth: 1,
    borderColor: colors.border,
    ...shadows.small,
  },
  studentChipActive: {
    backgroundColor: colors.accent,
    borderColor: colors.accent,
  },
  studentChipText: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.textSecondary,
  },
  studentChipTextActive: {
    color: colors.textOnAccent,
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
  videoUploaded: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: spacing.md,
    backgroundColor: colors.success + '20',
    borderRadius: borderRadius.md,
    marginBottom: spacing.md,
  },
  videoUploadedText: {
    color: colors.success,
    fontWeight: '600',
    fontSize: fontSize.md,
  },
  aiTitle: {
    fontSize: fontSize.lg,
    fontWeight: '700',
    color: colors.accent,
    marginBottom: spacing.sm,
  },
  aiReasoning: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    lineHeight: 20,
  },
  aiDayTitle: {
    fontSize: fontSize.md,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.xs,
  },
  aiExercise: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    lineHeight: 18,
    marginBottom: 2,
  },
  aiNotes: {
    fontSize: fontSize.md,
    color: colors.info,
    fontStyle: 'italic',
    lineHeight: 20,
  },
  bottomSpacer: {
    height: spacing.xxl * 2,
  },
});
