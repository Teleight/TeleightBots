// ============================================================
// ESSĒRE - THEME CONFIGURATION
// Palette: Nero, Rosso, Bianco/Crema
// ============================================================

export const colors = {
  // --- Brand ---
  primary: '#0D0D0D',
  primaryLight: '#1A1A1A',
  accent: '#CC0000',
  accentLight: '#E63333',

  // --- Status ---
  success: '#4CAF50',
  warning: '#FF9800',
  error: '#F44336',
  info: '#2196F3',

  // --- Superfici (Dark Mode) ---
  background: '#0D0D0D',
  surface: '#1A1A1A',
  surfaceLight: '#242424',
  border: '#3A3A3A',
  divider: '#2A2A2A',

  // --- Testo ---
  text: '#F5F0E8',
  textSecondary: '#A0A0A0',
  textLight: '#666666',
  textOnPrimary: '#F5F0E8',
  textOnAccent: '#FFFFFF',

  // --- Badge ruoli ---
  ownerBadge: '#CC0000',
  managerBadge: '#9C27B0',
  collaboratorBadge: '#00BCD4',
  studentBadge: '#FF9800',
};

export const spacing = {
  xs: 3,
  sm: 6,
  md: 12,
  lg: 18,
  xl: 24,
  xxl: 36,
};

export const borderRadius = {
  sm: 4,
  md: 8,
  lg: 12,
  xl: 16,
  round: 999,
};

export const fontSize = {
  xs: 9,
  sm: 11,
  md: 12,
  lg: 14,
  xl: 17,
  xxl: 20,
  title: 22,
  hero: 28,
};

export const shadows = {
  small: {
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.3,
    shadowRadius: 2,
    elevation: 2,
  },
  medium: {
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.4,
    shadowRadius: 4,
    elevation: 4,
  },
  large: {
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.5,
    shadowRadius: 8,
    elevation: 8,
  },
};
