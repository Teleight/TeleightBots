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
import { BarChart, BarData } from '../../components/charts/BarChart';
import { KPICard, KPIData } from '../../components/charts/KPICard';
import { getCollaborators, getManagers, getStudents } from '../../services/authService';
import { getTransactions, getFinancialSummary } from '../../services/financialService';
import { getAllSessions } from '../../services/sessionService';
import { Collaborator, Manager, Student, TrainingSession, FinancialTransaction } from '../../types';

type Period = 'month' | 'quarter' | 'year';

export const AnalyticsScreen: React.FC = () => {
  const [refreshing, setRefreshing] = useState(false);
  const [selectedPeriod, setSelectedPeriod] = useState<Period>('month');
  const [collaborators, setCollaborators] = useState<Collaborator[]>([]);
  const [managers, setManagers] = useState<Manager[]>([]);
  const [students, setStudents] = useState<Student[]>([]);
  const [sessions, setSessions] = useState<TrainingSession[]>([]);
  const [transactions, setTransactions] = useState<FinancialTransaction[]>([]);
  const [totalIncome, setTotalIncome] = useState(0);
  const [totalExpenses, setTotalExpenses] = useState(0);

  const loadData = useCallback(async () => {
    try {
      const [collabs, mgrs, studs, allSessions, txs, summary] = await Promise.all([
        getCollaborators(),
        getManagers(),
        getStudents(),
        getAllSessions(),
        getTransactions(),
        getFinancialSummary(),
      ]);
      setCollaborators(collabs);
      setManagers(mgrs);
      setStudents(studs);
      setSessions(allSessions);
      setTransactions(txs);
      setTotalIncome(summary.totalIncome);
      setTotalExpenses(summary.totalExpenses);
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

  // --- Calcolo ricavi per coach ---
  const getCoachRevenueData = (): BarData[] => {
    return collaborators.map((collab) => {
      // Ricavi associati a questo coach (pagamenti dei suoi allievi)
      const coachIncome = transactions
        .filter((t) => t.type === 'income' && t.collaboratorId === collab.id)
        .reduce((sum, t) => sum + t.amount, 0);

      // Stima basata su studenti assegnati se non ci sono transazioni dirette
      const estimatedRevenue = coachIncome > 0
        ? coachIncome
        : collab.assignedStudents.length * 150; // Stima media per allievo

      return {
        label: `${collab.name} ${collab.surname?.charAt(0) || ''}.`,
        value: estimatedRevenue,
        color: colors.collaboratorBadge,
      };
    });
  };

  // --- Calcolo ricavi per manager ---
  const getManagerRevenueData = (): BarData[] => {
    return managers.map((mgr) => {
      // Ricavi del team gestito dal manager
      const managedCollabs = collaborators.filter((c) =>
        mgr.assignedCollaborators.includes(c.id)
      );
      const teamRevenue = managedCollabs.reduce((sum, collab) => {
        const coachIncome = transactions
          .filter((t) => t.type === 'income' && t.collaboratorId === collab.id)
          .reduce((s, t) => s + t.amount, 0);
        return sum + (coachIncome > 0 ? coachIncome : collab.assignedStudents.length * 150);
      }, 0);

      return {
        label: `${mgr.name} ${mgr.surname?.charAt(0) || ''}.`,
        value: teamRevenue,
        color: colors.managerBadge,
      };
    });
  };

  // --- KPI Manager ---
  const getManagerKPIs = (): { name: string; kpis: KPIData[] }[] => {
    return managers.map((mgr) => {
      const managedCollabs = collaborators.filter((c) =>
        mgr.assignedCollaborators.includes(c.id)
      );
      const managedStudents = students.filter((s) =>
        mgr.assignedStudents.includes(s.id)
      );
      const teamSessions = sessions.filter((s) =>
        managedCollabs.some((c) => c.id === s.collaboratorId)
      );
      const completedSessions = teamSessions.filter((s) => s.status === 'completed');
      const cancelledSessions = teamSessions.filter(
        (s) => s.status === 'cancelled_by_student' || s.status === 'cancelled_late'
      );
      const noShows = teamSessions.filter((s) => s.status === 'no_show');
      const activeStudents = managedStudents.filter((s) => s.isActive);

      return {
        name: `${mgr.name} ${mgr.surname}`,
        kpis: [
          {
            label: 'Allievi attivi',
            value: activeStudents.length,
            target: Math.max(activeStudents.length + 5, 20),
            trend: activeStudents.length > 0 ? 'up' as const : 'stable' as const,
          },
          {
            label: 'Coach gestiti',
            value: managedCollabs.length,
            target: Math.max(managedCollabs.length + 2, 5),
            trend: 'stable' as const,
          },
          {
            label: 'Sessioni completate',
            value: completedSessions.length,
            target: Math.max(teamSessions.length, 1),
            trend: completedSessions.length > cancelledSessions.length ? 'up' as const : 'down' as const,
          },
          {
            label: 'Tasso di cancellazione',
            value: teamSessions.length > 0
              ? Math.round((cancelledSessions.length / teamSessions.length) * 100)
              : 0,
            target: 10,
            unit: '%',
            color: cancelledSessions.length > teamSessions.length * 0.1 ? colors.error : colors.success,
            trend: cancelledSessions.length > teamSessions.length * 0.1 ? 'down' as const : 'up' as const,
          },
          {
            label: 'No-show',
            value: noShows.length,
            color: noShows.length > 3 ? colors.error : colors.success,
            trend: noShows.length > 3 ? 'down' as const : 'up' as const,
          },
          {
            label: 'Retention allievi',
            value: managedStudents.length > 0
              ? Math.round((activeStudents.length / managedStudents.length) * 100)
              : 100,
            target: 90,
            unit: '%',
            trend: 'up' as const,
          },
        ],
      };
    });
  };

  // --- KPI Coach ---
  const getCoachKPIs = (): { name: string; kpis: KPIData[] }[] => {
    return collaborators.map((collab) => {
      const coachSessions = sessions.filter((s) => s.collaboratorId === collab.id);
      const completedSessions = coachSessions.filter((s) => s.status === 'completed');
      const assignedStudents = students.filter((s) => s.assignedCollaboratorId === collab.id);
      const activeStudents = assignedStudents.filter((s) => s.isActive);

      // Calcolo guadagni coach
      const coachIncome = transactions
        .filter((t) => t.type === 'income' && t.collaboratorId === collab.id)
        .reduce((sum, t) => sum + t.amount, 0);
      const coachEarnings = coachIncome * (collab.commissionPercentage / 100);

      return {
        name: `${collab.name} ${collab.surname}`,
        kpis: [
          {
            label: 'Allievi assegnati',
            value: assignedStudents.length,
            target: Math.max(assignedStudents.length + 3, 10),
            trend: assignedStudents.length > 0 ? 'up' as const : 'stable' as const,
          },
          {
            label: 'Allievi attivi',
            value: activeStudents.length,
            target: assignedStudents.length,
            trend: activeStudents.length === assignedStudents.length ? 'up' as const : 'down' as const,
          },
          {
            label: 'Sessioni completate',
            value: completedSessions.length,
            target: Math.max(coachSessions.length, 1),
            trend: 'up' as const,
          },
          {
            label: 'Tasso completamento',
            value: coachSessions.length > 0
              ? Math.round((completedSessions.length / coachSessions.length) * 100)
              : 0,
            target: 85,
            unit: '%',
            trend: completedSessions.length / Math.max(coachSessions.length, 1) > 0.85 ? 'up' as const : 'down' as const,
          },
          {
            label: 'Guadagni (commissione)',
            value: coachEarnings,
            unit: '€',
            color: colors.success,
            trend: coachEarnings > 0 ? 'up' as const : 'stable' as const,
          },
          {
            label: 'Commissione',
            value: collab.commissionPercentage,
            unit: '%',
          },
          {
            label: 'Specializzazioni',
            value: collab.specializations?.join(', ') || 'N/A',
          },
        ],
      };
    });
  };

  // --- KPI Nutrizionista ---
  const getNutritionistKPIs = (): KPIData[] => {
    // Consulenze nutrizionali aggregate
    const totalConsultations = students.reduce(
      (sum, s) => sum + (s.nutritionalConsultations || 0),
      0
    );
    const studentsWithNutrition = students.filter((s) => (s.nutritionalConsultations || 0) > 0);
    const avgConsultations = students.length > 0
      ? (totalConsultations / students.length).toFixed(1)
      : '0';

    return [
      {
        label: 'Consulenze totali',
        value: totalConsultations,
        target: Math.max(totalConsultations + 10, 50),
        trend: totalConsultations > 0 ? 'up' as const : 'stable' as const,
      },
      {
        label: 'Allievi seguiti',
        value: studentsWithNutrition.length,
        target: students.length,
        trend: studentsWithNutrition.length > students.length / 2 ? 'up' as const : 'down' as const,
      },
      {
        label: 'Media consulenze per allievo',
        value: avgConsultations,
        target: 3,
      },
      {
        label: 'Copertura allievi',
        value: students.length > 0
          ? Math.round((studentsWithNutrition.length / students.length) * 100)
          : 0,
        target: 80,
        unit: '%',
        trend: studentsWithNutrition.length > students.length * 0.5 ? 'up' as const : 'down' as const,
      },
      {
        label: 'Allievi senza consulenza',
        value: students.length - studentsWithNutrition.length,
        color: students.length - studentsWithNutrition.length > 5 ? colors.warning : colors.success,
        trend: students.length - studentsWithNutrition.length > 5 ? 'down' as const : 'up' as const,
      },
    ];
  };

  const periods = [
    { key: 'month' as const, label: 'Mese' },
    { key: 'quarter' as const, label: 'Trimestre' },
    { key: 'year' as const, label: 'Anno' },
  ];

  const coachData = getCoachRevenueData();
  const managerData = getManagerRevenueData();
  const managerKPIs = getManagerKPIs();
  const coachKPIs = getCoachKPIs();
  const nutritionistKPIs = getNutritionistKPIs();

  return (
    <ScrollView
      style={styles.container}
      refreshControl={
        <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
      }
    >
      <View style={styles.header}>
        <Text style={styles.title}>Analisi & KPI</Text>
        <Text style={styles.subtitle}>Monitoraggio prestazioni</Text>
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

      {/* Riepilogo generale */}
      <View style={styles.summaryRow}>
        <View style={[styles.summaryCard, { borderLeftColor: colors.success }]}>
          <Text style={styles.summaryLabel}>Ricavi Totali</Text>
          <Text style={[styles.summaryValue, { color: colors.success }]}>
            €{totalIncome.toLocaleString()}
          </Text>
        </View>
        <View style={[styles.summaryCard, { borderLeftColor: colors.error }]}>
          <Text style={styles.summaryLabel}>Spese Totali</Text>
          <Text style={[styles.summaryValue, { color: colors.error }]}>
            €{totalExpenses.toLocaleString()}
          </Text>
        </View>
      </View>

      {/* ================================================== */}
      {/* GRAFICO A BARRE - RICAVI PER COACH */}
      {/* ================================================== */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Ricavi per Coach</Text>
        {coachData.length > 0 ? (
          <BarChart
            data={coachData}
            title="Produzione in €"
            height={220}
          />
        ) : (
          <View style={styles.emptyCard}>
            <Text style={styles.emptyText}>Nessun coach registrato</Text>
          </View>
        )}
      </View>

      {/* ================================================== */}
      {/* GRAFICO A BARRE - RICAVI PER MANAGER */}
      {/* ================================================== */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Ricavi per Manager</Text>
        {managerData.length > 0 ? (
          <BarChart
            data={managerData}
            title="Produzione Team in €"
            height={220}
          />
        ) : (
          <View style={styles.emptyCard}>
            <Text style={styles.emptyText}>Nessun manager registrato</Text>
          </View>
        )}
      </View>

      {/* ================================================== */}
      {/* KPI MANAGER */}
      {/* ================================================== */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>KPI Manager</Text>
        {managerKPIs.length > 0 ? (
          managerKPIs.map((mgr, index) => (
            <View key={index} style={styles.kpiBlock}>
              <KPICard
                title={mgr.name}
                kpis={mgr.kpis}
                accentColor={colors.managerBadge}
              />
            </View>
          ))
        ) : (
          <View style={styles.emptyCard}>
            <Text style={styles.emptyText}>Nessun manager registrato</Text>
          </View>
        )}
      </View>

      {/* ================================================== */}
      {/* KPI COACH */}
      {/* ================================================== */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>KPI Coach</Text>
        {coachKPIs.length > 0 ? (
          coachKPIs.map((coach, index) => (
            <View key={index} style={styles.kpiBlock}>
              <KPICard
                title={coach.name}
                kpis={coach.kpis}
                accentColor={colors.collaboratorBadge}
              />
            </View>
          ))
        ) : (
          <View style={styles.emptyCard}>
            <Text style={styles.emptyText}>Nessun coach registrato</Text>
          </View>
        )}
      </View>

      {/* ================================================== */}
      {/* KPI NUTRIZIONISTA */}
      {/* ================================================== */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>KPI Nutrizionista</Text>
        <KPICard
          title="Consulenze Nutrizionali"
          kpis={nutritionistKPIs}
          accentColor={colors.warning}
        />
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
  subtitle: {
    fontSize: fontSize.md,
    color: colors.textLight,
    marginTop: spacing.xs,
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
  summaryRow: {
    flexDirection: 'row',
    paddingHorizontal: spacing.md,
    gap: spacing.sm,
    marginBottom: spacing.sm,
  },
  summaryCard: {
    flex: 1,
    backgroundColor: colors.surface,
    borderRadius: borderRadius.lg,
    padding: spacing.md,
    borderWidth: 1,
    borderColor: colors.border,
    borderLeftWidth: 4,
  },
  summaryLabel: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
  },
  summaryValue: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    marginTop: spacing.xs,
  },
  section: {
    paddingHorizontal: spacing.md,
    marginTop: spacing.lg,
  },
  sectionTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.md,
  },
  kpiBlock: {
    marginBottom: spacing.md,
  },
  emptyCard: {
    backgroundColor: colors.surface,
    borderRadius: borderRadius.xl,
    padding: spacing.lg,
    borderWidth: 1,
    borderColor: colors.border,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
  },
  bottomSpacer: {
    height: spacing.xxl * 2,
  },
});
