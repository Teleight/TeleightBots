// ============================================================
// ESSĒRE - THEME CONFIGURATION
// Palette: Nero, Rosso, Bianco/Crema
// ============================================================

export const colors = {
  // --- Brand ---
  primary: '#0A0A0A',
  primaryLight: '#161616',
  accent: '#D40000',
  accentLight: '#E63333',
  accentDark: '#990000',

  // --- Status ---
  success: '#34C759',
  warning: '#FF9F0A',
  error: '#FF453A',
  info: '#64D2FF',

  // --- Superfici (Dark Mode) ---
  background: '#0A0A0A',
  surface: '#161616',
  surfaceLight: '#1F1F1F',
  border: '#2C2C2E',
  divider: '#1C1C1E',

  // --- Testo ---
  text: '#F2F2F7',
  textSecondary: '#8E8E93',
  textLight: '#636366',
  textOnPrimary: '#F2F2F7',
  textOnAccent: '#FFFFFF',

  // --- Badge ruoli ---
  ownerBadge: '#D40000',
  managerBadge: '#AF52DE',
  collaboratorBadge: '#32D4DE',
  studentBadge: '#FF9F0A',
};

export const spacing = {
  xs: 3,
  sm: 6,
  md: 12,
  lg: 16,
  xl: 24,
  xxl: 32,
};

export const borderRadius = {
  sm: 4,
  md: 8,
  lg: 12,
  xl: 16,
  round: 999,
};

export const fontSize = {
  xs: 10,
  sm: 11,
  md: 13,
  lg: 14,
  xl: 16,
  xxl: 19,
  title: 21,
  hero: 26,
};

export const shadows = {
  small: {
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.25,
    shadowRadius: 3,
    elevation: 2,
  },
  medium: {
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 3 },
    shadowOpacity: 0.35,
    shadowRadius: 6,
    elevation: 4,
  },
  large: {
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 6 },
    shadowOpacity: 0.45,
    shadowRadius: 12,
    elevation: 8,
  },
};
