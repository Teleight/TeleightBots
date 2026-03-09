import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { StatCard } from '../../components/common/StatCard';
import { Badge } from '../../components/common/Badge';
import { PaymentPlan, Installment } from '../../types';
import { useAuth } from '../../hooks/useAuth';
import { getStudentPaymentPlans } from '../../services/paymentService';

export const PaymentsScreen: React.FC = () => {
  const { user } = useAuth();
  const [paymentPlans, setPaymentPlans] = useState<PaymentPlan[]>([]);

  const loadPayments = useCallback(async () => {
    if (!user) return;
    try {
      const plans = await getStudentPaymentPlans(user.id);
      setPaymentPlans(plans);
    } catch {
      // Silently handle
    }
  }, [user]);

  useEffect(() => {
    loadPayments();
  }, [loadPayments]);

  const getNextDueInstallment = (
    plan: PaymentPlan
  ): Installment | undefined => {
    return plan.installments.find((i) => i.status !== 'paid');
  };

  const getPaidCount = (plan: PaymentPlan): number => {
    return plan.installments.filter((i) => i.status === 'paid').length;
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>I Miei Pagamenti</Text>
      </View>

      {paymentPlans.length === 0 ? (
        <Card style={styles.emptyCard}>
          <Text style={styles.emptyText}>
            Nessun piano di pagamento attivo
          </Text>
        </Card>
      ) : (
        paymentPlans.map((plan) => {
          const nextDue = getNextDueInstallment(plan);
          const paidCount = getPaidCount(plan);

          return (
            <Card key={plan.id} variant="elevated" style={styles.planCard}>
              {/* Riepilogo piano */}
              <View style={styles.planHeader}>
                <Text style={styles.planAmount}>
                  €{plan.totalAmount.toLocaleString()}
                </Text>
                <Badge
                  status={
                    plan.paymentType === 'full' ? 'scheduled' : 'pending'
                  }
                  label={
                    plan.paymentType === 'full'
                      ? 'Pagamento unico'
                      : `${plan.installments.length} rate`
                  }
                />
              </View>

              {/* Progresso pagamento */}
              <View style={styles.progressContainer}>
                <View style={styles.progressBar}>
                  <View
                    style={[
                      styles.progressFill,
                      {
                        width: `${
                          (paidCount / plan.installments.length) * 100
                        }%`,
                      },
                    ]}
                  />
                </View>
                <Text style={styles.progressText}>
                  {paidCount}/{plan.installments.length} rate pagate
                </Text>
              </View>

              {/* Prossima scadenza */}
              {nextDue && (
                <View style={styles.nextDueContainer}>
                  <Text style={styles.nextDueLabel}>Prossima rata:</Text>
                  <View style={styles.nextDueInfo}>
                    <Text style={styles.nextDueAmount}>
                      €{nextDue.amount.toLocaleString()}
                    </Text>
                    <Text style={styles.nextDueDate}>
                      Scadenza:{' '}
                      {new Date(
                        nextDue.dueDate as unknown as string
                      ).toLocaleDateString('it-IT')}
                    </Text>
                  </View>
                </View>
              )}

              {/* Lista rate */}
              <View style={styles.installmentsList}>
                {plan.installments.map((inst, index) => (
                  <View key={inst.id} style={styles.installmentItem}>
                    <View style={styles.installmentLeft}>
                      <Text style={styles.installmentNumber}>
                        Rata {index + 1}
                      </Text>
                      <Text style={styles.installmentDue}>
                        {new Date(
                          inst.dueDate as unknown as string
                        ).toLocaleDateString('it-IT')}
                      </Text>
                    </View>
                    <View style={styles.installmentRight}>
                      <Text style={styles.installmentAmount}>
                        €{inst.amount}
                      </Text>
                      <Badge status={inst.status} />
                    </View>
                  </View>
                ))}
              </View>
            </Card>
          );
        })
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
  emptyCard: {
    margin: spacing.md,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.lg,
  },
  planCard: {
    margin: spacing.md,
  },
  planHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: spacing.md,
  },
  planAmount: {
    fontSize: fontSize.xxl,
    fontWeight: '700',
    color: colors.text,
  },
  progressContainer: {
    marginBottom: spacing.md,
  },
  progressBar: {
    height: 8,
    backgroundColor: colors.border,
    borderRadius: 4,
    overflow: 'hidden',
    marginBottom: spacing.xs,
  },
  progressFill: {
    height: '100%',
    backgroundColor: colors.success,
    borderRadius: 4,
  },
  progressText: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
  },
  nextDueContainer: {
    backgroundColor: colors.warning + '10',
    borderRadius: borderRadius.md,
    padding: spacing.md,
    marginBottom: spacing.md,
  },
  nextDueLabel: {
    fontSize: fontSize.sm,
    color: colors.warning,
    fontWeight: '600',
    marginBottom: spacing.xs,
  },
  nextDueInfo: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  nextDueAmount: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
  },
  nextDueDate: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
  },
  installmentsList: {
    borderTopWidth: 1,
    borderTopColor: colors.divider,
    paddingTop: spacing.sm,
  },
  installmentItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: spacing.sm,
    borderBottomWidth: 1,
    borderBottomColor: colors.divider,
  },
  installmentLeft: {},
  installmentNumber: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
  },
  installmentDue: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
    marginTop: 2,
  },
  installmentRight: {
    alignItems: 'flex-end',
    gap: spacing.xs,
  },
  installmentAmount: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
  },
  bottomSpacer: {
    height: spacing.xxl,
  },
});
