import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  Modal,
  TouchableOpacity,
  Alert,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { InputField } from '../../components/common/InputField';
import { DiaryEntry } from '../../types';
import { addDiaryEntry } from '../../services/contentService';

const MOODS = [
  { value: 'great' as const, emoji: 'Ottimo', color: '#4CAF50' },
  { value: 'good' as const, emoji: 'Bene', color: '#8BC34A' },
  { value: 'ok' as const, emoji: 'OK', color: '#FFC107' },
  { value: 'tired' as const, emoji: 'Stanco', color: '#FF9800' },
  { value: 'bad' as const, emoji: 'Male', color: '#F44336' },
];

export const DiaryScreen: React.FC = () => {
  const [entries, setEntries] = useState<DiaryEntry[]>([]);
  const [showAddModal, setShowAddModal] = useState(false);
  const [newContent, setNewContent] = useState('');
  const [newMood, setNewMood] = useState<DiaryEntry['mood']>('good');
  const [newPainLevel, setNewPainLevel] = useState(0);

  const handleAddEntry = async () => {
    if (!newContent.trim()) {
      Alert.alert('Errore', 'Scrivi qualcosa nel diario');
      return;
    }

    try {
      await addDiaryEntry({
        studentId: '', // dall'auth context
        date: new Date(),
        content: newContent,
        mood: newMood,
        painLevel: newPainLevel,
        createdAt: new Date(),
      });

      setShowAddModal(false);
      setNewContent('');
      setNewMood('good');
      setNewPainLevel(0);
      Alert.alert('Fatto', 'Nota aggiunta al diario');
    } catch {
      Alert.alert('Errore', 'Impossibile salvare la nota');
    }
  };

  const getMoodInfo = (mood?: string) =>
    MOODS.find((m) => m.value === mood) || MOODS[2];

  const renderEntry = ({ item }: { item: DiaryEntry }) => {
    const mood = getMoodInfo(item.mood);
    return (
      <Card variant="elevated">
        <View style={styles.entryHeader}>
          <Text style={styles.entryDate}>
            {new Date(item.date as unknown as string).toLocaleDateString('it-IT', {
              weekday: 'long',
              day: 'numeric',
              month: 'long',
            })}
          </Text>
          <View style={[styles.moodBadge, { backgroundColor: mood.color + '20' }]}>
            <Text style={[styles.moodText, { color: mood.color }]}>
              {mood.emoji}
            </Text>
          </View>
        </View>

        <Text style={styles.entryContent}>{item.content}</Text>

        {item.painLevel !== undefined && item.painLevel > 0 && (
          <View style={styles.painRow}>
            <Text style={styles.painLabel}>Livello dolore:</Text>
            <View style={styles.painBar}>
              <View
                style={[
                  styles.painFill,
                  {
                    width: `${(item.painLevel / 10) * 100}%`,
                    backgroundColor:
                      item.painLevel <= 3
                        ? colors.success
                        : item.painLevel <= 6
                        ? colors.warning
                        : colors.error,
                  },
                ]}
              />
            </View>
            <Text style={styles.painValue}>{item.painLevel}/10</Text>
          </View>
        )}
      </Card>
    );
  };

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Il Mio Diario</Text>
        <Text style={styles.subtitle}>Tieni traccia dei tuoi progressi</Text>
      </View>

      <Button
        title="+ Nuova Nota"
        onPress={() => setShowAddModal(true)}
        style={styles.addButton}
      />

      <FlatList
        data={entries}
        renderItem={renderEntry}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        ListEmptyComponent={
          <Card>
            <Text style={styles.emptyText}>
              Il tuo diario e vuoto.{'\n'}
              Inizia a scrivere le tue note!
            </Text>
          </Card>
        }
      />

      {/* Modale nuova nota */}
      <Modal visible={showAddModal} animationType="slide" transparent>
        <View style={styles.modalOverlay}>
          <View style={styles.modalContent}>
            <Text style={styles.modalTitle}>Nuova Nota</Text>

            {/* Selezione umore */}
            <Text style={styles.fieldLabel}>Come ti senti?</Text>
            <View style={styles.moodSelector}>
              {MOODS.map((mood) => (
                <TouchableOpacity
                  key={mood.value}
                  style={[
                    styles.moodOption,
                    newMood === mood.value && {
                      backgroundColor: mood.color + '30',
                      borderColor: mood.color,
                    },
                  ]}
                  onPress={() => setNewMood(mood.value)}
                >
                  <Text
                    style={[
                      styles.moodOptionText,
                      newMood === mood.value && { color: mood.color },
                    ]}
                  >
                    {mood.emoji}
                  </Text>
                </TouchableOpacity>
              ))}
            </View>

            {/* Livello dolore */}
            <Text style={styles.fieldLabel}>Livello dolore (0-10)</Text>
            <View style={styles.painSelector}>
              {Array.from({ length: 11 }, (_, i) => (
                <TouchableOpacity
                  key={i}
                  style={[
                    styles.painDot,
                    newPainLevel === i && styles.painDotActive,
                  ]}
                  onPress={() => setNewPainLevel(i)}
                >
                  <Text
                    style={[
                      styles.painDotText,
                      newPainLevel === i && styles.painDotTextActive,
                    ]}
                  >
                    {i}
                  </Text>
                </TouchableOpacity>
              ))}
            </View>

            <InputField
              label="Le tue note"
              value={newContent}
              onChangeText={setNewContent}
              placeholder="Come e andata oggi? Come ti senti?..."
              multiline
              numberOfLines={5}
              style={styles.textArea}
            />

            <View style={styles.modalButtons}>
              <Button
                title="Annulla"
                onPress={() => setShowAddModal(false)}
                variant="outline"
                style={styles.modalButton}
              />
              <Button
                title="Salva"
                onPress={handleAddEntry}
                style={styles.modalButton}
              />
            </View>
          </View>
        </View>
      </Modal>
    </View>
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
    color: colors.textLight,
    marginTop: spacing.xs,
  },
  addButton: {
    margin: spacing.md,
  },
  list: {
    paddingHorizontal: spacing.md,
    paddingBottom: spacing.xxl,
  },
  entryHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: spacing.sm,
  },
  entryDate: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
    textTransform: 'capitalize',
  },
  moodBadge: {
    paddingHorizontal: spacing.sm,
    paddingVertical: spacing.xs,
    borderRadius: borderRadius.round,
  },
  moodText: {
    fontSize: fontSize.sm,
    fontWeight: '600',
  },
  entryContent: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    lineHeight: 22,
  },
  painRow: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: spacing.sm,
    marginTop: spacing.sm,
    paddingTop: spacing.sm,
    borderTopWidth: 1,
    borderTopColor: colors.divider,
  },
  painLabel: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
  },
  painBar: {
    flex: 1,
    height: 6,
    backgroundColor: colors.border,
    borderRadius: 3,
    overflow: 'hidden',
  },
  painFill: {
    height: '100%',
    borderRadius: 3,
  },
  painValue: {
    fontSize: fontSize.sm,
    fontWeight: '600',
    color: colors.text,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.lg,
    lineHeight: 22,
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
    maxHeight: '90%',
  },
  modalTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginBottom: spacing.lg,
  },
  fieldLabel: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
    marginBottom: spacing.sm,
  },
  moodSelector: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginBottom: spacing.lg,
  },
  moodOption: {
    flex: 1,
    padding: spacing.sm,
    borderRadius: borderRadius.md,
    borderWidth: 1,
    borderColor: colors.border,
    alignItems: 'center',
  },
  moodOptionText: {
    fontSize: fontSize.sm,
    fontWeight: '600',
    color: colors.textSecondary,
  },
  painSelector: {
    flexDirection: 'row',
    gap: 4,
    marginBottom: spacing.lg,
    justifyContent: 'space-between',
  },
  painDot: {
    width: 28,
    height: 28,
    borderRadius: 14,
    borderWidth: 1,
    borderColor: colors.border,
    justifyContent: 'center',
    alignItems: 'center',
  },
  painDotActive: {
    backgroundColor: colors.accent,
    borderColor: colors.accent,
  },
  painDotText: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
  },
  painDotTextActive: {
    color: colors.textOnAccent,
    fontWeight: '700',
  },
  textArea: {
    height: 120,
    textAlignVertical: 'top',
  },
  modalButtons: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginTop: spacing.md,
  },
  modalButton: {
    flex: 1,
  },
});
