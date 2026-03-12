import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';

export interface BarData {
  label: string;
  value: number;
  color?: string;
}

interface BarChartProps {
  data: BarData[];
  title?: string;
  maxValue?: number;
  height?: number;
  showValues?: boolean;
  formatValue?: (value: number) => string;
}

export const BarChart: React.FC<BarChartProps> = ({
  data,
  title,
  maxValue,
  height = 200,
  showValues = true,
  formatValue = (v) => `€${v.toLocaleString()}`,
}) => {
  const max = maxValue || Math.max(...data.map((d) => d.value), 1);
  const chartHeight = height - 28; // space for labels below bars

  return (
    <View style={styles.container}>
      {title && <Text style={styles.title}>{title}</Text>}
      <View style={[styles.chartArea, { height }]}>
        {/* Y-axis labels */}
        <View style={styles.yAxis}>
          <Text style={styles.yLabel}>{formatValue(max)}</Text>
          <Text style={styles.yLabel}>{formatValue(Math.round(max / 2))}</Text>
          <Text style={styles.yLabel}>{formatValue(0)}</Text>
        </View>

        {/* Bars */}
        <View style={styles.barsContainer}>
          {/* Grid lines */}
          <View style={[styles.gridLine, { top: 0 }]} />
          <View style={[styles.gridLine, { top: chartHeight / 2 }]} />
          <View style={[styles.gridLine, { top: chartHeight }]} />

          {data.map((item, index) => {
            const barPixelHeight = max > 0 ? (item.value / max) * chartHeight : 0;
            const barColor = item.color || colors.accent;

            return (
              <View key={index} style={styles.barWrapper}>
                <View style={[styles.barColumn, { height: chartHeight }]}>
                  {showValues && item.value > 0 && (
                    <Text style={styles.barValue}>{formatValue(item.value)}</Text>
                  )}
                  <View
                    style={[
                      styles.bar,
                      {
                        height: Math.max(barPixelHeight, 2),
                        backgroundColor: barColor,
                      },
                    ]}
                  />
                </View>
                <Text style={styles.barLabel} numberOfLines={2}>
                  {item.label}
                </Text>
              </View>
            );
          })}
        </View>
      </View>
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
  },
  title: {
    fontSize: fontSize.lg,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.md,
  },
  chartArea: {
    flexDirection: 'row',
  },
  yAxis: {
    width: 55,
    justifyContent: 'space-between',
    alignItems: 'flex-end',
    paddingRight: spacing.sm,
    paddingBottom: 28,
  },
  yLabel: {
    fontSize: fontSize.xs,
    color: colors.textLight,
  },
  barsContainer: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'flex-end',
    paddingBottom: 28,
    position: 'relative',
  },
  gridLine: {
    position: 'absolute',
    left: 0,
    right: 0,
    height: 1,
    backgroundColor: colors.divider,
  },
  barWrapper: {
    flex: 1,
    alignItems: 'center',
  },
  barColumn: {
    width: '100%',
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  bar: {
    width: '55%',
    minHeight: 2,
    borderTopLeftRadius: borderRadius.md,
    borderTopRightRadius: borderRadius.md,
  },
  barValue: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
    fontWeight: '600',
    marginBottom: 2,
  },
  barLabel: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
    textAlign: 'center',
    marginTop: spacing.xs,
    height: 24,
  },
});
