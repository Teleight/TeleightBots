import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { colors, borderRadius, spacing, fontSize } from '../../config/theme';
import { PaymentStatus, SessionStatus } from '../../types';

const statusColors: Record<string, string> = {
  // Payment
  paid: colors.success,
  pending: colors.warning,
  overdue: colors.error,
  // Session
  scheduled: colors.info,
  completed: colors.success,
  cancelled_by_student: colors.textSecondary,
  cancelled_late: colors.error,
  no_show: colors.error,
};

const statusLabels: Record<string, string> = {
  paid: 'Pagato',
  pending: 'In attesa',
  overdue: 'Scaduto',
  scheduled: 'Programmata',
  completed: 'Completata',
  cancelled_by_student: 'Annullata',
  cancelled_late: 'Annullata tardi',
  no_show: 'Assente',
};

interface BadgeProps {
  status: PaymentStatus | SessionStatus | string;
  label?: string;
}

export const Badge: React.FC<BadgeProps> = ({ status, label }) => {
  const bgColor = statusColors[status] || colors.textSecondary;
  const text = label || statusLabels[status] || status;

  return (
    <View style={[styles.badge, { backgroundColor: bgColor + '20' }]}>
      <View style={[styles.dot, { backgroundColor: bgColor }]} />
      <Text style={[styles.text, { color: bgColor }]}>{text}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  badge: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: spacing.sm,
    paddingVertical: spacing.xs,
    borderRadius: borderRadius.round,
    alignSelf: 'flex-start',
    gap: 4,
  },
  dot: {
    width: 6,
    height: 6,
    borderRadius: 3,
  },
  text: {
    fontSize: fontSize.xs,
    fontWeight: '600',
  },
});
