import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  RefreshControl,
  TouchableOpacity,
  FlatList,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { StatCard } from '../../components/common/StatCard';
import { Badge } from '../../components/common/Badge';
import { Collaborator, TrainingSession, CollaboratorEarning } from '../../types';
import { getCollaborators, getStudents } from '../../services/authService';
import { getAllSessions } from '../../services/sessionService';
import { getFinancialSummary } from '../../services/financialService';
import { useAuth } from '../../hooks/useAuth';

export const DashboardScreen: React.FC = () => {
  const { logout, user } = useAuth();
  const [refreshing, setRefreshing] = useState(false);
  const [collaborators, setCollaborators] = useState<Collaborator[]>([]);
  const [todaySessions, setTodaySessions] = useState<TrainingSession[]>([]);
  const [totalRevenue, setTotalRevenue] = useState(0);
  const [totalStudents, setTotalStudents] = useState(0);
  const [selectedPeriod, setSelectedPeriod] = useState<'week' | 'month' | 'year'>('month');

  const loadData = useCallback(async () => {
    try {
      const [collabs, studs, allSessions, summary] = await Promise.all([
        getCollaborators(),
        getStudents(),
        getAllSessions(),
        getFinancialSummary(),
      ]);
      setCollaborators(collabs);
      setTotalStudents(studs.length);
      setTotalRevenue(summary.totalIncome);

      const today = new Date();
      today.setHours(0, 0, 0, 0);
      const tomorrow = new Date(today);
      tomorrow.setDate(tomorrow.getDate() + 1);
      const todayOnly = allSessions.filter((s) => {
        const d = new Date(s.date as unknown as string);
        return d >= today && d < tomorrow;
      });
      setTodaySessions(todayOnly);
    } catch {
      // Silently handle
    }
  }, []);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const onRefresh = async () => {
    setRefreshing(true);
    await loadData();
    setRefreshing(false);
  };

  const periods = [
    { key: 'week' as const, label: 'Settimana' },
    { key: 'month' as const, label: 'Mese' },
    { key: 'year' as const, label: 'Anno' },
  ];

  return (
    <ScrollView
      style={styles.container}
      refreshControl={
        <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
      }
    >
      <View style={styles.header}>
        <View style={styles.headerTop}>
          <View>
            <Text style={styles.greeting}>Buongiorno{user?.name ? `, ${user.name}` : ''}!</Text>
            <Text style={styles.date}>
              {new Date().toLocaleDateString('it-IT', {
                weekday: 'long',
                day: 'numeric',
                month: 'long',
              })}
            </Text>
          </View>
          <TouchableOpacity style={styles.logoutButton} onPress={logout}>
            <Text style={styles.logoutText}>Esci</Text>
          </TouchableOpacity>
        </View>
        <Text style={styles.roleBadge}>Ruolo: {user?.role?.toUpperCase()}</Text>
      </View>

      {/* Filtro periodo */}
      <View style={styles.periodFilter}>
        {periods.map((p) => (
          <TouchableOpacity
            key={p.key}
            style={[
              styles.periodButton,
              selectedPeriod === p.key && styles.periodButtonActive,
            ]}
            onPress={() => setSelectedPeriod(p.key)}
          >
            <Text
              style={[
                styles.periodText,
                selectedPeriod === p.key && styles.periodTextActive,
              ]}
            >
              {p.label}
            </Text>
          </TouchableOpacity>
        ))}
      </View>

      {/* Statistiche rapide */}
      <View style={styles.statsRow}>
        <StatCard
          title="Ricavi"
          value={`€${totalRevenue.toLocaleString()}`}
          subtitle="Questo mese"
          color={colors.success}
        />
        <StatCard
          title="Allievi"
          value={totalStudents}
          subtitle="Attivi"
          color={colors.info}
        />
      </View>

      <View style={styles.statsRow}>
        <StatCard
          title="Sessioni Oggi"
          value={todaySessions.length}
          color={colors.accent}
        />
        <StatCard
          title="Collaboratori"
          value={collaborators.length}
          color={colors.collaboratorBadge}
        />
      </View>

      {/* Sessioni di oggi */}
      <Text style={styles.sectionTitle}>Sessioni di Oggi</Text>
      {todaySessions.length === 0 ? (
        <Card>
          <Text style={styles.emptyText}>Nessuna sessione programmata per oggi</Text>
        </Card>
      ) : (
        todaySessions.map((session) => (
          <Card key={session.id} variant="elevated">
            <View style={styles.sessionRow}>
              <View>
                <Text style={styles.sessionTime}>
                  {session.startTime} - {session.endTime}
                </Text>
                <Text style={styles.sessionStudent}>
                  Allievo ID: {session.studentId}
                </Text>
              </View>
              <Badge status={session.status} />
            </View>
          </Card>
        ))
      )}

      {/* Rendimento collaboratori */}
      <Text style={styles.sectionTitle}>Rendimento Collaboratori</Text>
      {collaborators.length === 0 ? (
        <Card>
          <Text style={styles.emptyText}>Nessun collaboratore registrato</Text>
        </Card>
      ) : (
        collaborators.map((collab) => (
          <Card key={collab.id} variant="elevated">
            <View style={styles.collabRow}>
              <View style={styles.collabInfo}>
                <Text style={styles.collabName}>
                  {collab.name} {collab.surname}
                </Text>
                <Text style={styles.collabStudents}>
                  {collab.assignedStudents.length} allievi
                </Text>
              </View>
              <View style={styles.collabEarnings}>
                <Text style={styles.collabAmount}>
                  {collab.commissionPercentage}%
                </Text>
                <Text style={styles.collabLabel}>commissione</Text>
              </View>
            </View>
          </Card>
        ))
      )}

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
    borderBottomLeftRadius: borderRadius.xl,
    borderBottomRightRadius: borderRadius.xl,
  },
  greeting: {
    fontSize: fontSize.title,
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
  roleBadge: {
    color: colors.textLight,
    fontSize: fontSize.xs,
    marginTop: spacing.xs,
  },
  date: {
    fontSize: fontSize.md,
    color: colors.textLight,
    marginTop: spacing.xs,
    textTransform: 'capitalize',
  },
  periodFilter: {
    flexDirection: 'row',
    margin: spacing.md,
    backgroundColor: colors.surface,
    borderRadius: borderRadius.lg,
    padding: spacing.xs,
    ...shadows.small,
  },
  periodButton: {
    flex: 1,
    paddingVertical: spacing.sm,
    alignItems: 'center',
    borderRadius: borderRadius.md,
  },
  periodButtonActive: {
    backgroundColor: colors.accent,
  },
  periodText: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    fontWeight: '500',
  },
  periodTextActive: {
    color: colors.textOnAccent,
    fontWeight: '700',
  },
  statsRow: {
    flexDirection: 'row',
    paddingHorizontal: spacing.md,
    gap: spacing.sm,
    marginBottom: spacing.sm,
  },
  sectionTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginHorizontal: spacing.md,
    marginTop: spacing.lg,
    marginBottom: spacing.sm,
  },
  sessionRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  sessionTime: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
  },
  sessionStudent: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    marginTop: 2,
  },
  collabRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  collabInfo: {},
  collabName: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
  },
  collabStudents: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 2,
  },
  collabEarnings: {
    alignItems: 'flex-end',
  },
  collabAmount: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.success,
  },
  collabLabel: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.md,
  },
  bottomSpacer: {
    height: spacing.xxl,
  },
});
