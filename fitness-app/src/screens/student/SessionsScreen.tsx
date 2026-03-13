import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  RefreshControl,
  ActivityIndicator,
  Alert,
  TouchableOpacity,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { Badge } from '../../components/common/Badge';
import { TrainingSession } from '../../types';
import { cancelSession, getStudentSessions } from '../../services/sessionService';
import { useAuth } from '../../hooks/useAuth';

const CANCELLATION_HOURS = 10;

export const SessionsScreen: React.FC = () => {
  const { user } = useAuth();
  const [sessions, setSessions] = useState<TrainingSession[]>([]);

  const loadSessions = useCallback(async () => {
    if (!user) return;
    try {
      const data = await getStudentSessions(user.id);
      setSessions(data);
    } catch {
      // Silently handle
    }
  }, [user]);

  useEffect(() => {
    loadSessions();
  }, [loadSessions]);

  const canCancel = (sessionDate: Date): boolean => {
    const now = new Date();
    const hours = (new Date(sessionDate).getTime() - now.getTime()) / (1000 * 60 * 60);
    return hours >= CANCELLATION_HOURS;
  };

  const handleCancel = async (session: TrainingSession) => {
    const sessionDate = new Date(session.date);
    const hoursLeft = (sessionDate.getTime() - Date.now()) / (1000 * 60 * 60);

    if (hoursLeft < CANCELLATION_HOURS) {
      Alert.alert(
        'Attenzione',
        `Non puoi annullare la sessione meno di ${CANCELLATION_HOURS} ore prima. La sessione sarà considerata come eseguita e verrà addebitata.`,
        [
          { text: 'Ho capito', style: 'cancel' },
          {
            text: 'Annulla comunque',
            style: 'destructive',
            onPress: async () => {
              const result = await cancelSession(session.id, sessionDate);
              if (result.isLate) {
                Alert.alert(
                  'Sessione annullata',
                  'La sessione è stata annullata ma sarà conteggiata e addebitata poiché annullata con meno di 10 ore di preavviso.'
                );
              }
            },
          },
        ]
      );
      return;
    }

    Alert.alert(
      'Conferma annullamento',
      'Sei sicuro di voler annullare questa sessione?',
      [
        { text: 'No', style: 'cancel' },
        {
          text: 'Sì, annulla',
          onPress: async () => {
            await cancelSession(session.id, sessionDate);
            Alert.alert('Fatto', 'Sessione annullata con successo');
          },
        },
      ]
    );
  };

  const renderSession = ({ item }: { item: TrainingSession }) => {
    const sessionDate = new Date(item.date);
    const isFuture = sessionDate > new Date();
    const cancellable = isFuture && item.status === 'scheduled';

    return (
      <Card variant="elevated">
        <View style={styles.sessionHeader}>
          <View>
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
          </View>
          <Badge status={item.status} />
        </View>

        {item.notes && (
          <Text style={styles.sessionNotes}>{item.notes}</Text>
        )}

        {cancellable && (
          <View style={styles.cancelContainer}>
            {canCancel(sessionDate) ? (
              <Button
                title="Annulla Sessione"
                onPress={() => handleCancel(item)}
                variant="danger"
              />
            ) : (
              <View>
                <Button
                  title="Annulla (sarà addebitata)"
                  onPress={() => handleCancel(item)}
                  variant="danger"
                />
                <Text style={styles.lateWarning}>
                  Meno di {CANCELLATION_HOURS} ore - la sessione sarà conteggiata
                </Text>
              </View>
            )}
          </View>
        )}
      </Card>
    );
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Le Mie Sessioni</Text>
        <Text style={styles.subtitle}>
          Puoi annullare solo {CANCELLATION_HOURS}+ ore prima
        </Text>
      </View>

      <FlatList
        data={sessions}
        renderItem={renderSession}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        ListEmptyComponent={
          <Card>
            <Text style={styles.emptyText}>
              Nessuna sessione programmata
            </Text>
          </Card>
        }
      />
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
    fontSize: fontSize.sm,
    color: colors.warning,
    marginTop: spacing.xs,
  },
  list: {
    padding: spacing.md,
  },
  sessionHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
  },
  sessionDate: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
    textTransform: 'capitalize',
  },
  sessionTime: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    marginTop: 2,
  },
  sessionNotes: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    marginTop: spacing.sm,
    fontStyle: 'italic',
    borderLeftWidth: 3,
    borderLeftColor: colors.accent,
    paddingLeft: spacing.sm,
  },
  cancelContainer: {
    marginTop: spacing.md,
    paddingTop: spacing.sm,
    borderTopWidth: 1,
    borderTopColor: colors.divider,
  },
  lateWarning: {
    fontSize: fontSize.xs,
    color: colors.error,
    textAlign: 'center',
    marginTop: spacing.xs,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.lg,
  },
});
