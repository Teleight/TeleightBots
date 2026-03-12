import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  RefreshControl,
  TouchableOpacity,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { StatCard } from '../../components/common/StatCard';
import { Badge } from '../../components/common/Badge';
import { BarChart, BarData } from '../../components/charts/BarChart';
import { Collaborator, TrainingSession, FinancialTransaction } from '../../types';
import { getCollaborators, getStudents } from '../../services/authService';
import { getAllSessions } from '../../services/sessionService';
import { getFinancialSummary, getTransactions } from '../../services/financialService';
import { useAuth } from '../../hooks/useAuth';

export const DashboardScreen: React.FC = () => {
  const { logout, user } = useAuth();
  const [refreshing, setRefreshing] = useState(false);
  const [collaborators, setCollaborators] = useState<Collaborator[]>([]);
  const [allSessions, setAllSessions] = useState<TrainingSession[]>([]);
  const [todaySessions, setTodaySessions] = useState<TrainingSession[]>([]);
  const [transactions, setTransactions] = useState<FinancialTransaction[]>([]);
  const [totalRevenue, setTotalRevenue] = useState(0);
  const [totalExpenses, setTotalExpenses] = useState(0);
  const [totalStudents, setTotalStudents] = useState(0);
  const [selectedPeriod, setSelectedPeriod] = useState<'week' | 'month' | 'year'>('month');

  const loadData = useCallback(async () => {
    try {
      const [collabs, studs, sessions, txs, summary] = await Promise.all([
        getCollaborators(),
        getStudents(),
        getAllSessions(),
        getTransactions(),
        getFinancialSummary(),
      ]);
      setCollaborators(collabs);
      setTotalStudents(studs.length);
      setAllSessions(sessions);
      setTransactions(txs);
      setTotalRevenue(summary.totalIncome);
      setTotalExpenses(summary.totalExpenses);

      const today = new Date();
      today.setHours(0, 0, 0, 0);
      const tomorrow = new Date(today);
      tomorrow.setDate(tomorrow.getDate() + 1);
      const todayOnly = sessions.filter((s) => {
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

  // Grafico ricavi per collaboratore
  const getRevenueByCoach = (): BarData[] => {
    return collaborators.map((collab) => {
      const coachIncome = transactions
        .filter((t) => t.type === 'income' && t.collaboratorId === collab.id)
        .reduce((sum, t) => sum + t.amount, 0);
      const estimatedRevenue = coachIncome > 0
        ? coachIncome
        : collab.assignedStudents.length * 150;

      return {
        label: `${collab.name} ${collab.surname?.charAt(0) || ''}.`,
        value: estimatedRevenue,
        color: colors.collaboratorBadge,
      };
    });
  };

  // Grafico sessioni per stato
  const getSessionsByStatus = (): BarData[] => {
    const completed = allSessions.filter((s) => s.status === 'completed').length;
    const scheduled = allSessions.filter((s) => s.status === 'scheduled').length;
    const cancelled = allSessions.filter(
      (s) => s.status === 'cancelled_by_student' || s.status === 'cancelled_late'
    ).length;
    const noShow = allSessions.filter((s) => s.status === 'no_show').length;

    return [
      { label: 'Completate', value: completed, color: colors.success },
      { label: 'In programma', value: scheduled, color: colors.info },
      { label: 'Cancellate', value: cancelled, color: colors.warning },
      { label: 'No-show', value: noShow, color: colors.error },
    ];
  };

  // Grafico allievi per coach
  const getStudentsByCoach = (): BarData[] => {
    return collaborators.map((collab) => ({
      label: `${collab.name} ${collab.surname?.charAt(0) || ''}.`,
      value: collab.assignedStudents.length,
      color: colors.accent,
    }));
  };

  const periods = [
    { key: 'week' as const, label: 'Settimana' },
    { key: 'month' as const, label: 'Mese' },
    { key: 'year' as const, label: 'Anno' },
  ];

  const coachRevenueData = getRevenueByCoach();
  const sessionsData = getSessionsByStatus();
  const studentsByCoachData = getStudentsByCoach();

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

      {/* Profitto netto */}
      <View style={styles.profitCard}>
        <Text style={styles.profitLabel}>Profitto Netto</Text>
        <Text style={[
          styles.profitValue,
          { color: totalRevenue - totalExpenses >= 0 ? colors.success : colors.error },
        ]}>
          €{(totalRevenue - totalExpenses).toLocaleString()}
        </Text>
        <View style={styles.profitBar}>
          <View style={[
            styles.profitBarFill,
            {
              width: totalRevenue > 0
                ? `${Math.min((totalRevenue / (totalRevenue + totalExpenses)) * 100, 100)}%`
                : '0%',
              backgroundColor: colors.success,
            },
          ]} />
        </View>
        <View style={styles.profitLegend}>
          <Text style={[styles.profitLegendText, { color: colors.success }]}>
            Ricavi €{totalRevenue.toLocaleString()}
          </Text>
          <Text style={[styles.profitLegendText, { color: colors.error }]}>
            Spese €{totalExpenses.toLocaleString()}
          </Text>
        </View>
      </View>

      {/* Grafico Ricavi per Coach */}
      <View style={styles.chartSection}>
        <BarChart
          data={coachRevenueData.length > 0 ? coachRevenueData : [{ label: 'Nessun coach', value: 0 }]}
          title="Ricavi per Coach"
          height={200}
        />
      </View>

      {/* Grafico Sessioni */}
      <View style={styles.chartSection}>
        <BarChart
          data={sessionsData}
          title="Riepilogo Sessioni"
          height={180}
          formatValue={(v) => String(v)}
        />
      </View>

      {/* Grafico Allievi per Coach */}
      <View style={styles.chartSection}>
        <BarChart
          data={studentsByCoachData.length > 0 ? studentsByCoachData : [{ label: 'Nessun coach', value: 0 }]}
          title="Allievi per Coach"
          height={180}
          formatValue={(v) => String(v)}
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
    fontSize: fontSize.xl,
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
  profitCard: {
    marginHorizontal: spacing.md,
    marginTop: spacing.sm,
    backgroundColor: colors.surface,
    borderRadius: borderRadius.xl,
    padding: spacing.lg,
    borderWidth: 1,
    borderColor: colors.border,
    ...shadows.small,
  },
  profitLabel: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    fontWeight: '600',
    textTransform: 'uppercase',
    letterSpacing: 0.5,
  },
  profitValue: {
    fontSize: fontSize.xxl,
    fontWeight: '700',
    marginTop: spacing.xs,
  },
  profitBar: {
    height: 8,
    backgroundColor: colors.error + '30',
    borderRadius: 4,
    marginTop: spacing.md,
    overflow: 'hidden',
  },
  profitBarFill: {
    height: '100%',
    borderRadius: 4,
  },
  profitLegend: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: spacing.sm,
  },
  profitLegendText: {
    fontSize: fontSize.sm,
    fontWeight: '600',
  },
  chartSection: {
    paddingHorizontal: spacing.md,
    marginTop: spacing.lg,
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
