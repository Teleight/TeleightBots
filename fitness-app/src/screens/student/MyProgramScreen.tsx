import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Linking,
  Alert,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { StatCard } from '../../components/common/StatCard';
import { WorkoutPlan, Exercise, WeeklyDay, Student } from '../../types';
import { useAuth } from '../../hooks/useAuth';
import { getActiveWorkoutPlan } from '../../services/programService';
import { getCompletedSessionsCount } from '../../services/sessionService';
import { getStudentNutritionalConsultations } from '../../services/contentService';

const DAYS = ['Lunedi', 'Martedi', 'Mercoledi', 'Giovedi', 'Venerdi', 'Sabato', 'Domenica'];

export const MyProgramScreen: React.FC = () => {
  const { user } = useAuth();
  const student = user as Student | null;
  const [activePlan, setActivePlan] = useState<WorkoutPlan | null>(null);
  const [selectedDay, setSelectedDay] = useState(new Date().getDay() === 0 ? 6 : new Date().getDay() - 1);
  const [completedSessions, setCompletedSessions] = useState(0);
  const [nutritionalConsultations, setNutritionalConsultations] = useState(0);

  const loadData = useCallback(async () => {
    if (!user) return;
    try {
      const [plan, sessionsCount, consultations] = await Promise.all([
        getActiveWorkoutPlan(user.id),
        getCompletedSessionsCount(user.id),
        getStudentNutritionalConsultations(user.id),
      ]);
      setActivePlan(plan);
      setCompletedSessions(sessionsCount);
      setNutritionalConsultations(student?.nutritionalConsultations ?? consultations.length);
    } catch {
      // Silently handle
    }
  }, [user, student]);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const renderExercise = (exercise: Exercise, index: number) => (
    <Card key={exercise.id} variant="outlined">
      <View style={styles.exerciseHeader}>
        <View style={styles.exerciseNumber}>
          <Text style={styles.exerciseNumberText}>{index + 1}</Text>
        </View>
        <View style={styles.exerciseInfo}>
          <Text style={styles.exerciseName}>{exercise.name}</Text>
          <Text style={styles.exerciseDetails}>
            {exercise.sets} serie x {exercise.reps} reps
            {exercise.restSeconds > 0 && ` | Recupero: ${exercise.restSeconds}s`}
          </Text>
        </View>
      </View>

      {exercise.description && (
        <Text style={styles.exerciseDescription}>{exercise.description}</Text>
      )}

      {exercise.videoUrl && (
        <TouchableOpacity
          style={styles.videoButton}
          onPress={() => {
            Linking.openURL(exercise.videoUrl!).catch(() =>
              Alert.alert('Errore', 'Impossibile aprire il video')
            );
          }}
        >
          <Text style={styles.videoButtonText}>Guarda Video</Text>
        </TouchableOpacity>
      )}

      {exercise.notes && (
        <Text style={styles.exerciseNotes}>{exercise.notes}</Text>
      )}
    </Card>
  );

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Il Mio Programma</Text>
        {activePlan && (
          <Text style={styles.planTitle}>{activePlan.title}</Text>
        )}
      </View>

      {/* Statistiche */}
      <View style={styles.statsRow}>
        <StatCard
          title="Sessioni"
          value={completedSessions}
          subtitle="Completate"
          color={colors.success}
        />
        <StatCard
          title="Consulenze"
          value={nutritionalConsultations}
          subtitle="Nutrizionali"
          color={colors.info}
        />
      </View>

      {/* Selettore giorno */}
      <ScrollView
        horizontal
        showsHorizontalScrollIndicator={false}
        style={styles.daySelector}
        contentContainerStyle={styles.daySelectorContent}
      >
        {DAYS.map((day, index) => (
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
          </TouchableOpacity>
        ))}
      </ScrollView>

      {/* Esercizi del giorno */}
      <View style={styles.exerciseList}>
        <Text style={styles.sectionTitle}>
          Esercizi - {DAYS[selectedDay]}
        </Text>

        {!activePlan ? (
          <Card>
            <Text style={styles.emptyText}>
              Nessun programma attivo al momento.{'\n'}
              Il tuo allenatore creerà presto il tuo programma!
            </Text>
          </Card>
        ) : activePlan.weeklySchedule[selectedDay]?.exercises.length === 0 ? (
          <Card>
            <Text style={styles.emptyText}>Giorno di riposo</Text>
          </Card>
        ) : (
          activePlan.weeklySchedule[selectedDay]?.exercises.map(
            (ex, i) => renderExercise(ex, i)
          )
        )}
      </View>

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
  planTitle: {
    fontSize: fontSize.md,
    color: colors.accent,
    marginTop: spacing.xs,
    fontWeight: '600',
  },
  statsRow: {
    flexDirection: 'row',
    padding: spacing.md,
    gap: spacing.sm,
  },
  daySelector: {
    marginVertical: spacing.sm,
  },
  daySelectorContent: {
    paddingHorizontal: spacing.md,
    gap: spacing.sm,
  },
  dayButton: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    backgroundColor: colors.surface,
    ...shadows.small,
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
  exerciseList: {
    padding: spacing.md,
  },
  sectionTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.sm,
  },
  exerciseHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: spacing.md,
  },
  exerciseNumber: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: colors.accent,
    justifyContent: 'center',
    alignItems: 'center',
  },
  exerciseNumberText: {
    color: colors.textOnAccent,
    fontWeight: '700',
    fontSize: fontSize.md,
  },
  exerciseInfo: {
    flex: 1,
  },
  exerciseName: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
  },
  exerciseDetails: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 2,
  },
  exerciseDescription: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    marginTop: spacing.sm,
    marginLeft: spacing.xxl,
    lineHeight: 20,
  },
  videoButton: {
    alignSelf: 'flex-start',
    marginTop: spacing.sm,
    marginLeft: spacing.xxl,
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.xs,
    backgroundColor: colors.info + '20',
    borderRadius: borderRadius.round,
  },
  videoButtonText: {
    color: colors.info,
    fontSize: fontSize.sm,
    fontWeight: '600',
  },
  exerciseNotes: {
    fontSize: fontSize.sm,
    color: colors.warning,
    marginTop: spacing.sm,
    marginLeft: spacing.xxl,
    fontStyle: 'italic',
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.lg,
    lineHeight: 22,
  },
  bottomSpacer: {
    height: spacing.xxl,
  },
});
