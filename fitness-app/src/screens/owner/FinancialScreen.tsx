import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Modal,
  Alert,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { StatCard } from '../../components/common/StatCard';
import { Button } from '../../components/common/Button';
import { InputField } from '../../components/common/InputField';
import {
  FinancialTransaction,
  TransactionType,
  TransactionCategory,
} from '../../types';
import { addTransaction, getTransactions, getFinancialSummary, deleteTransaction } from '../../services/financialService';

const CATEGORIES: { value: TransactionCategory; label: string }[] = [
  { value: 'student_payment', label: 'Pagamento allievi' },
  { value: 'collaborator_payment', label: 'Pagamento collaboratori' },
  { value: 'rent', label: 'Affitto' },
  { value: 'equipment', label: 'Attrezzatura' },
  { value: 'marketing', label: 'Marketing' },
  { value: 'insurance', label: 'Assicurazione' },
  { value: 'utilities', label: 'Utenze' },
  { value: 'other', label: 'Altro' },
];

export const FinancialScreen: React.FC = () => {
  const [transactions, setTransactions] = useState<FinancialTransaction[]>([]);
  const [showAddModal, setShowAddModal] = useState(false);
  const [newType, setNewType] = useState<TransactionType>('income');
  const [newAmount, setNewAmount] = useState('');
  const [newDescription, setNewDescription] = useState('');
  const [newCategory, setNewCategory] = useState<TransactionCategory>('other');
  const [totalIncome, setTotalIncome] = useState(0);
  const [totalExpenses, setTotalExpenses] = useState(0);

  const loadData = useCallback(async () => {
    try {
      const [txs, summary] = await Promise.all([
        getTransactions(),
        getFinancialSummary(),
      ]);
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

  const handleAddTransaction = async () => {
    if (!newAmount || !newDescription) {
      Alert.alert('Errore', 'Compila tutti i campi');
      return;
    }

    const amount = parseFloat(newAmount);
    if (isNaN(amount) || amount <= 0) {
      Alert.alert('Errore', 'Importo non valido');
      return;
    }

    try {
      await addTransaction({
        type: newType,
        category: newCategory,
        amount,
        description: newDescription,
        date: new Date(),
      });

      setShowAddModal(false);
      setNewAmount('');
      setNewDescription('');
      await loadData();
      Alert.alert('Successo', 'Transazione aggiunta');
    } catch {
      Alert.alert('Errore', 'Impossibile salvare la transazione');
    }
  };

  const handleDeleteTransaction = (txId: string, description: string) => {
    Alert.alert(
      'Elimina Transazione',
      `Eliminare "${description}"?`,
      [
        { text: 'Annulla', style: 'cancel' },
        {
          text: 'Elimina',
          style: 'destructive',
          onPress: async () => {
            try {
              await deleteTransaction(txId);
              await loadData();
            } catch {
              Alert.alert('Errore', 'Impossibile eliminare la transazione');
            }
          },
        },
      ]
    );
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Gestione Economica</Text>
      </View>

      {/* Riepilogo */}
      <View style={styles.statsRow}>
        <StatCard
          title="Ricavi"
          value={`€${totalIncome.toLocaleString()}`}
          color={colors.success}
        />
        <StatCard
          title="Spese"
          value={`€${totalExpenses.toLocaleString()}`}
          color={colors.error}
        />
      </View>

      <View style={styles.netProfitContainer}>
        <StatCard
          title="Profitto Netto"
          value={`€${(totalIncome - totalExpenses).toLocaleString()}`}
          color={totalIncome - totalExpenses >= 0 ? colors.success : colors.error}
        />
      </View>

      {/* Aggiungi transazione */}
      <Button
        title="+ Nuova Transazione"
        onPress={() => setShowAddModal(true)}
        style={styles.addButton}
      />

      {/* Lista transazioni */}
      <Text style={styles.sectionTitle}>Ultime Transazioni</Text>
      {transactions.length === 0 ? (
        <Card>
          <Text style={styles.emptyText}>Nessuna transazione registrata</Text>
        </Card>
      ) : (
        transactions.map((t) => (
          <Card key={t.id}>
            <View style={styles.transactionRow}>
              <View style={styles.transactionInfo}>
                <View style={styles.transactionHeader}>
                  <View
                    style={[
                      styles.typeIndicator,
                      {
                        backgroundColor:
                          t.type === 'income' ? colors.success : colors.error,
                      },
                    ]}
                  />
                  <Text style={styles.transactionDesc}>{t.description}</Text>
                </View>
                <Text style={styles.transactionCategory}>
                  {CATEGORIES.find((c) => c.value === t.category)?.label}
                </Text>
                <Text style={styles.transactionDate}>
                  {new Date(t.date as unknown as string).toLocaleDateString('it-IT')}
                </Text>
              </View>
              <View style={styles.transactionRight}>
                <Text
                  style={[
                    styles.transactionAmount,
                    { color: t.type === 'income' ? colors.success : colors.error },
                  ]}
                >
                  {t.type === 'income' ? '+' : '-'}€{t.amount.toLocaleString()}
                </Text>
                <TouchableOpacity
                  onPress={() => handleDeleteTransaction(t.id, t.description)}
                  hitSlop={{ top: 8, bottom: 8, left: 8, right: 8 }}
                >
                  <Text style={styles.deleteText}>Elimina</Text>
                </TouchableOpacity>
              </View>
            </View>
          </Card>
        ))
      )}

      {/* Modale aggiungi transazione */}
      <Modal visible={showAddModal} animationType="slide" transparent>
        <View style={styles.modalOverlay}>
          <View style={styles.modalContent}>
            <Text style={styles.modalTitle}>Nuova Transazione</Text>

            {/* Tipo */}
            <View style={styles.typeSelector}>
              <TouchableOpacity
                style={[
                  styles.typeButton,
                  newType === 'income' && styles.typeButtonIncome,
                ]}
                onPress={() => setNewType('income')}
              >
                <Text
                  style={[
                    styles.typeButtonText,
                    newType === 'income' && styles.typeButtonTextActive,
                  ]}
                >
                  Ricavo
                </Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={[
                  styles.typeButton,
                  newType === 'expense' && styles.typeButtonExpense,
                ]}
                onPress={() => setNewType('expense')}
              >
                <Text
                  style={[
                    styles.typeButtonText,
                    newType === 'expense' && styles.typeButtonTextActive,
                  ]}
                >
                  Spesa
                </Text>
              </TouchableOpacity>
            </View>

            <InputField
              label="Importo (€)"
              value={newAmount}
              onChangeText={setNewAmount}
              keyboardType="decimal-pad"
              placeholder="0.00"
            />

            <InputField
              label="Descrizione"
              value={newDescription}
              onChangeText={setNewDescription}
              placeholder="Descrizione della transazione"
            />

            {/* Categoria */}
            <Text style={styles.categoryLabel}>Categoria</Text>
            <ScrollView horizontal showsHorizontalScrollIndicator={false}>
              <View style={styles.categoryRow}>
                {CATEGORIES.map((cat) => (
                  <TouchableOpacity
                    key={cat.value}
                    style={[
                      styles.categoryChip,
                      newCategory === cat.value && styles.categoryChipActive,
                    ]}
                    onPress={() => setNewCategory(cat.value)}
                  >
                    <Text
                      style={[
                        styles.categoryChipText,
                        newCategory === cat.value && styles.categoryChipTextActive,
                      ]}
                    >
                      {cat.label}
                    </Text>
                  </TouchableOpacity>
                ))}
              </View>
            </ScrollView>

            <View style={styles.modalButtons}>
              <Button
                title="Annulla"
                onPress={() => setShowAddModal(false)}
                variant="outline"
                style={styles.modalButton}
              />
              <Button
                title="Salva"
                onPress={handleAddTransaction}
                style={styles.modalButton}
              />
            </View>
          </View>
        </View>
      </Modal>

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
  statsRow: {
    flexDirection: 'row',
    padding: spacing.md,
    gap: spacing.sm,
  },
  netProfitContainer: {
    paddingHorizontal: spacing.md,
  },
  addButton: {
    margin: spacing.md,
  },
  sectionTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginHorizontal: spacing.md,
    marginTop: spacing.md,
    marginBottom: spacing.sm,
  },
  transactionRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  transactionInfo: {
    flex: 1,
  },
  transactionHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: spacing.sm,
  },
  typeIndicator: {
    width: 8,
    height: 8,
    borderRadius: 4,
  },
  transactionDesc: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
  },
  transactionCategory: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 2,
    marginLeft: spacing.md + spacing.sm,
  },
  transactionDate: {
    fontSize: fontSize.xs,
    color: colors.textLight,
    marginTop: 2,
    marginLeft: spacing.md + spacing.sm,
  },
  transactionRight: {
    alignItems: 'flex-end',
    gap: spacing.xs,
  },
  transactionAmount: {
    fontSize: fontSize.lg,
    fontWeight: '700',
  },
  deleteText: {
    fontSize: fontSize.xs,
    color: colors.error,
    fontWeight: '600',
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.md,
  },
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.5)',
    justifyContent: 'flex-end',
  },
  modalContent: {
    backgroundColor: colors.surface,
    borderTopLeftRadius: borderRadius.xl,
    borderTopRightRadius: borderRadius.xl,
    padding: spacing.lg,
    maxHeight: '85%',
  },
  modalTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.lg,
  },
  typeSelector: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginBottom: spacing.md,
  },
  typeButton: {
    flex: 1,
    padding: spacing.sm,
    borderRadius: borderRadius.md,
    borderWidth: 1,
    borderColor: colors.border,
    alignItems: 'center',
  },
  typeButtonIncome: {
    backgroundColor: colors.success,
    borderColor: colors.success,
  },
  typeButtonExpense: {
    backgroundColor: colors.error,
    borderColor: colors.error,
  },
  typeButtonText: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.textSecondary,
  },
  typeButtonTextActive: {
    color: colors.textOnPrimary,
  },
  categoryLabel: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
    marginBottom: spacing.sm,
  },
  categoryRow: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginBottom: spacing.md,
  },
  categoryChip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    borderWidth: 1,
    borderColor: colors.border,
  },
  categoryChipActive: {
    backgroundColor: colors.accent,
    borderColor: colors.accent,
  },
  categoryChipText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
  },
  categoryChipTextActive: {
    color: colors.textOnAccent,
  },
  modalButtons: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginTop: spacing.md,
  },
  modalButton: {
    flex: 1,
  },
  bottomSpacer: {
    height: spacing.xxl,
  },
});
