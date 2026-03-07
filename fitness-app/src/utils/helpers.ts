/**
 * Formatta un importo in euro
 */
export const formatCurrency = (amount: number): string => {
  return `€${amount.toLocaleString('it-IT', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  })}`;
};

/**
 * Formatta una data in formato italiano
 */
export const formatDate = (date: Date | string): string => {
  const d = typeof date === 'string' ? new Date(date) : date;
  return d.toLocaleDateString('it-IT', {
    day: 'numeric',
    month: 'long',
    year: 'numeric',
  });
};

/**
 * Formatta data e ora
 */
export const formatDateTime = (date: Date | string): string => {
  const d = typeof date === 'string' ? new Date(date) : date;
  return d.toLocaleDateString('it-IT', {
    day: 'numeric',
    month: 'short',
    hour: '2-digit',
    minute: '2-digit',
  });
};

/**
 * Calcola le ore rimanenti fino a una data
 */
export const hoursUntil = (date: Date | string): number => {
  const target = typeof date === 'string' ? new Date(date) : date;
  return (target.getTime() - Date.now()) / (1000 * 60 * 60);
};

/**
 * Controlla se una sessione puo essere annullata (10+ ore prima)
 */
export const canCancelSession = (sessionDate: Date | string): boolean => {
  return hoursUntil(sessionDate) >= 10;
};

/**
 * Genera un ID univoco semplice
 */
export const generateId = (): string => {
  return Date.now().toString(36) + Math.random().toString(36).substring(2);
};

/**
 * Calcola la percentuale del collaboratore e del titolare
 */
export const calculateShares = (
  totalAmount: number,
  collaboratorPercentage: number
): { collaboratorShare: number; ownerShare: number } => {
  const collaboratorShare = Math.round((totalAmount * collaboratorPercentage) / 100 * 100) / 100;
  const ownerShare = Math.round((totalAmount - collaboratorShare) * 100) / 100;
  return { collaboratorShare, ownerShare };
};

/**
 * Raggruppa un array per una chiave
 */
export const groupBy = <T>(
  array: T[],
  keyFn: (item: T) => string
): Record<string, T[]> => {
  return array.reduce((groups, item) => {
    const key = keyFn(item);
    return {
      ...groups,
      [key]: [...(groups[key] || []), item],
    };
  }, {} as Record<string, T[]>);
};

/**
 * Periodo corrente in formato YYYY-MM
 */
export const getCurrentPeriod = (): string => {
  const now = new Date();
  return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`;
};
