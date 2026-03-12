import React from 'react';
import { View, StyleSheet, ViewStyle } from 'react-native';
import { colors, borderRadius, spacing, shadows } from '../../config/theme';

interface CardProps {
  children: React.ReactNode;
  style?: ViewStyle;
  variant?: 'default' | 'elevated' | 'outlined';
}

export const Card: React.FC<CardProps> = ({
  children,
  style,
  variant = 'default',
}) => {
  return (
    <View style={[styles.card, variantStyles[variant], style]}>
      {children}
    </View>
  );
};

const styles = StyleSheet.create({
  card: {
    backgroundColor: colors.surface,
    borderRadius: borderRadius.xl,
    padding: spacing.md,
    marginVertical: spacing.xs,
  },
});

const variantStyles = StyleSheet.create({
  default: {
    ...shadows.small,
    borderWidth: 1,
    borderColor: colors.divider,
  },
  elevated: {
    ...shadows.medium,
    backgroundColor: '#1E1E1E',
    borderWidth: 1,
    borderColor: 'rgba(58, 58, 58, 0.4)',
  },
  outlined: {
    borderWidth: 1,
    borderColor: colors.border,
  },
});
