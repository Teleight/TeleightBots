import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';

export interface KPIData {
  label: string;
  value: string | number;
  target?: string | number;
  trend?: 'up' | 'down' | 'stable';
  unit?: string;
  color?: string;
}

interface KPICardProps {
  title: string;
  kpis: KPIData[];
  accentColor?: string;
}

const TrendIndicator = ({ trend }: { trend: 'up' | 'down' | 'stable' }) => {
  const symbol = trend === 'up' ? '▲' : trend === 'down' ? '▼' : '─';
  const color = trend === 'up' ? colors.success : trend === 'down' ? colors.error : colors.textLight;
  return <Text style={[styles.trend, { color }]}>{symbol}</Text>;
};

const ProgressBar = ({ current, target, color }: { current: number; target: number; color: string }) => {
  const percent = target > 0 ? Math.min((current / target) * 100, 100) : 0;
  return (
    <View style={styles.progressContainer}>
      <View style={styles.progressTrack}>
        <View style={[styles.progressFill, { width: `${percent}%`, backgroundColor: color }]} />
      </View>
      <Text style={styles.progressText}>{Math.round(percent)}%</Text>
    </View>
  );
};

export const KPICard: React.FC<KPICardProps> = ({ title, kpis, accentColor = colors.accent }) => {
  return (
    <View style={[styles.container, { borderLeftColor: accentColor }]}>
      <Text style={styles.title}>{title}</Text>
      {kpis.map((kpi, index) => (
        <View key={index} style={[styles.kpiRow, index < kpis.length - 1 && styles.kpiDivider]}>
          <View style={styles.kpiHeader}>
            <Text style={styles.kpiLabel}>{kpi.label}</Text>
            {kpi.trend && <TrendIndicator trend={kpi.trend} />}
          </View>
          <View style={styles.kpiValues}>
            <Text style={[styles.kpiValue, { color: kpi.color || colors.text }]}>
              {kpi.unit && kpi.unit !== '%' ? kpi.unit : ''}{kpi.value}{kpi.unit === '%' ? '%' : ''}
            </Text>
            {kpi.target !== undefined && (
              <Text style={styles.kpiTarget}>
                / Obiettivo: {kpi.unit && kpi.unit !== '%' ? kpi.unit : ''}{kpi.target}{kpi.unit === '%' ? '%' : ''}
              </Text>
            )}
          </View>
          {kpi.target !== undefined && typeof kpi.value === 'number' && typeof kpi.target === 'number' && (
            <ProgressBar current={kpi.value} target={kpi.target} color={accentColor} />
          )}
        </View>
      ))}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    backgroundColor: colors.surface,
    borderRadius: borderRadius.xl,
    padding: spacing.lg,
    borderWidth: 1,
    borderColor: colors.border,
    borderLeftWidth: 4,
  },
  title: {
    fontSize: fontSize.lg,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.md,
  },
  kpiRow: {
    paddingVertical: spacing.sm,
  },
  kpiDivider: {
    borderBottomWidth: 1,
    borderBottomColor: colors.divider,
    paddingBottom: spacing.md,
    marginBottom: spacing.xs,
  },
  kpiHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  kpiLabel: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    flex: 1,
  },
  trend: {
    fontSize: fontSize.sm,
    fontWeight: '700',
  },
  kpiValues: {
    flexDirection: 'row',
    alignItems: 'baseline',
    marginTop: spacing.xs,
  },
  kpiValue: {
    fontSize: fontSize.xl,
    fontWeight: '700',
  },
  kpiTarget: {
    fontSize: fontSize.sm,
    color: colors.textLight,
    marginLeft: spacing.sm,
  },
  progressContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: spacing.sm,
    gap: spacing.sm,
  },
  progressTrack: {
    flex: 1,
    height: 6,
    backgroundColor: colors.surfaceLight,
    borderRadius: borderRadius.round,
    overflow: 'hidden',
  },
  progressFill: {
    height: '100%',
    borderRadius: borderRadius.round,
  },
  progressText: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
    fontWeight: '600',
    width: 35,
    textAlign: 'right',
  },
});
