import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  FlatList,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { StatCard } from '../../components/common/StatCard';
import { Badge } from '../../components/common/Badge';
import { PaymentPlan, Installment, CollaboratorEarning, Collaborator } from '../../types';
import { calculateCollaboratorEarnings, getUpcomingInstallments, getCollaboratorEarnings } from '../../services/paymentService';
import { useAuth } from '../../hooks/useAuth';

export const EarningsScreen: React.FC = () => {
  const { user } = useAuth();
  const collaborator = user as Collaborator | null;
  const [commissionPercentage, setCommissionPercentage] = useState(60);
  const [totalEarned, setTotalEarned] = useState(0);
  const [toPayOwner, setToPayOwner] = useState(0);
  const [paymentPlans, setPaymentPlans] = useState<PaymentPlan[]>([]);
  const [upcomingInstallments, setUpcomingInstallments] = useState<
    { plan: PaymentPlan; installment: Installment }[]
  >([]);

  const loadData = useCallback(async () => {
    if (!user) return;
    try {
      if (collaborator?.commissionPercentage) {
        setCommissionPercentage(collaborator.commissionPercentage);
      }

      const now = new Date();
      const period = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`;
      const [earnings, upcoming] = await Promise.all([
        getCollaboratorEarnings(user.id, period),
        getUpcomingInstallments(user.id, 'collaborator'),
      ]);

      const totalCollabShare = earnings.reduce((sum, e) => sum + e.collaboratorShare, 0);
      const totalOwnerShare = earnings.reduce((sum, e) => sum + e.ownerShare, 0);
      setTotalEarned(totalCollabShare);
      setToPayOwner(totalOwnerShare);
      setUpcomingInstallments(upcoming);
    } catch {
      // Silently handle
    }
  }, [user, collaborator]);

  useEffect(() => {
    loadData();
  }, [loadData]);

  // Esempio calcolo automatico
  const exampleCalc = calculateCollaboratorEarnings(1000, commissionPercentage);

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>I Miei Guadagni</Text>
        <Text style={styles.subtitle}>
          Commissione: {commissionPercentage}%
        </Text>
      </View>

      {/* Riepilogo */}
      <View style={styles.statsRow}>
        <StatCard
          title="Guadagnato"
          value={`€${totalEarned.toLocaleString()}`}
          subtitle="Questo mese"
          color={colors.success}
        />
        <StatCard
          title="Da versare"
          value={`€${toPayOwner.toLocaleString()}`}
          subtitle="Al titolare"
          color={colors.warning}
        />
      </View>

      {/* Calcolatore commissione */}
      <Card variant="elevated">
        <Text style={styles.calcTitle}>Calcolatore Commissione</Text>
        <View style={styles.calcRow}>
          <Text style={styles.calcLabel}>Incasso totale:</Text>
          <Text style={styles.calcValue}>€1.000</Text>
        </View>
        <View style={styles.calcRow}>
          <Text style={styles.calcLabel}>La tua parte ({commissionPercentage}%):</Text>
          <Text style={[styles.calcValue, { color: colors.success }]}>
            €{exampleCalc.collaboratorShare}
          </Text>
        </View>
        <View style={styles.calcRow}>
          <Text style={styles.calcLabel}>Parte titolare ({100 - commissionPercentage}%):</Text>
          <Text style={[styles.calcValue, { color: colors.accent }]}>
            €{exampleCalc.ownerShare}
          </Text>
        </View>
      </Card>

      {/* Rate in scadenza */}
      <Text style={styles.sectionTitle}>Rate in Scadenza</Text>
      {upcomingInstallments.length === 0 ? (
        <Card>
          <Text style={styles.emptyText}>Nessuna rata in scadenza</Text>
        </Card>
      ) : (
        upcomingInstallments.map(({ plan, installment }) => (
          <Card key={installment.id}>
            <View style={styles.installmentRow}>
              <View>
                <Text style={styles.installmentAmount}>
                  €{installment.amount.toLocaleString()}
                </Text>
                <Text style={styles.installmentDate}>
                  Scadenza:{' '}
                  {new Date(installment.dueDate as unknown as string).toLocaleDateString('it-IT')}
                </Text>
              </View>
              <Badge status={installment.status} />
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
  },
  title: {
    fontSize: fontSize.xxl,
    fontWeight: '700',
    color: colors.textOnPrimary,
  },
  subtitle: {
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
  calcTitle: {
    fontSize: fontSize.lg,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.md,
  },
  calcRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: spacing.xs,
    borderBottomWidth: 1,
    borderBottomColor: colors.divider,
  },
  calcLabel: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
  },
  calcValue: {
    fontSize: fontSize.lg,
    fontWeight: '700',
    color: colors.text,
  },
  sectionTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginHorizontal: spacing.md,
    marginTop: spacing.lg,
    marginBottom: spacing.sm,
  },
  installmentRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  installmentAmount: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
  },
  installmentDate: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 2,
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
