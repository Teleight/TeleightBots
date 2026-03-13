import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  Modal,
  ScrollView,
  Alert,
  RefreshControl,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { InputField } from '../../components/common/InputField';
import { ModalHeader } from '../../components/common/ModalHeader';
import { SpecialContent, ContentType, Student } from '../../types';
import { useAuth } from '../../hooks/useAuth';
import { addContent, getAllContent, deleteContent } from '../../services/contentService';
import { getStudents } from '../../services/authService';

const CONTENT_TYPES: { value: ContentType; label: string; color: string }[] = [
  { value: 'podcast', label: 'Podcast', color: '#9C27B0' },
  { value: 'video', label: 'Video', color: '#F44336' },
  { value: 'article', label: 'Articolo', color: '#2196F3' },
  { value: 'resource', label: 'Risorsa', color: '#4CAF50' },
];

export const ContentManagementScreen: React.FC = () => {
  const { user } = useAuth();
  const [contents, setContents] = useState<SpecialContent[]>([]);
  const [students, setStudents] = useState<Student[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [refreshing, setRefreshing] = useState(false);
  const [saving, setSaving] = useState(false);

  // Form
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [url, setUrl] = useState('');
  const [contentType, setContentType] = useState<ContentType>('video');
  const [tags, setTags] = useState('');
  const [selectedStudentIds, setSelectedStudentIds] = useState<string[]>([]);

  const loadData = useCallback(async () => {
    try {
      const [allContent, allStudents] = await Promise.all([
        getAllContent(),
        getStudents(),
      ]);
      setContents(allContent);
      setStudents(allStudents);
    } catch {
      // Silently handle
    }
  }, []);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const onRefresh = async () => {
    setRefreshing(true);
    await loadData();
    setRefreshing(false);
  };

  const toggleStudent = (id: string) => {
    setSelectedStudentIds((prev) =>
      prev.includes(id) ? prev.filter((s) => s !== id) : [...prev, id]
    );
  };

  const handleSave = async () => {
    if (!title || !url || !user) {
      Alert.alert('Errore', 'Inserisci titolo e URL');
      return;
    }

    setSaving(true);
    try {
      await addContent({
        title,
        description,
        type: contentType,
        url,
        createdBy: user.id,
        createdAt: new Date(),
        assignedTo: selectedStudentIds,
        tags: tags.split(',').map((t) => t.trim()).filter(Boolean),
      });
      Alert.alert('Successo', 'Contenuto pubblicato!');
      resetForm();
      setShowModal(false);
      loadData();
    } catch {
      Alert.alert('Errore', 'Impossibile salvare il contenuto');
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = (id: string) => {
    Alert.alert(
      'Conferma',
      'Sei sicuro di voler eliminare questo contenuto?',
      [
        { text: 'Annulla', style: 'cancel' },
        {
          text: 'Elimina',
          style: 'destructive',
          onPress: async () => {
            try {
              await deleteContent(id);
              await loadData();
            } catch {
              Alert.alert('Errore', 'Impossibile eliminare');
            }
          },
        },
      ]
    );
  };

  const resetForm = () => {
    setTitle('');
    setDescription('');
    setUrl('');
    setContentType('video');
    setTags('');
    setSelectedStudentIds([]);
  };

  const getTypeInfo = (type: ContentType) =>
    CONTENT_TYPES.find((t) => t.value === type)!;

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Gestione Contenuti</Text>
        <Text style={styles.subtitle}>
          {contents.length} contenuti pubblicati
        </Text>
      </View>

      <View style={styles.addButtonContainer}>
        <Button
          title="+ Nuovo Contenuto"
          onPress={() => setShowModal(true)}
        />
      </View>

      <FlatList
        data={contents}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
        }
        renderItem={({ item }) => {
          const typeInfo = getTypeInfo(item.type);
          return (
            <Card variant="elevated">
              <View style={styles.contentRow}>
                <View
                  style={[
                    styles.typeBadge,
                    { backgroundColor: typeInfo.color + '20' },
                  ]}
                >
                  <Text style={[styles.typeBadgeText, { color: typeInfo.color }]}>
                    {typeInfo.label}
                  </Text>
                </View>
                <View style={styles.contentInfo}>
                  <Text style={styles.contentTitle}>{item.title}</Text>
                  {item.description ? (
                    <Text style={styles.contentDesc} numberOfLines={2}>
                      {item.description}
                    </Text>
                  ) : null}
                  <Text style={styles.contentMeta}>
                    {item.assignedTo.length === 0
                      ? 'Tutti gli allievi'
                      : `${item.assignedTo.length} allievi`}
                    {item.tags.length > 0 && ` · ${item.tags.join(', ')}`}
                  </Text>
                </View>
                <TouchableOpacity
                  style={styles.deleteBtn}
                  onPress={() => handleDelete(item.id)}
                >
                  <Text style={styles.deleteBtnText}>X</Text>
                </TouchableOpacity>
              </View>
            </Card>
          );
        }}
        ListEmptyComponent={
          <Card>
            <Text style={styles.emptyText}>
              Nessun contenuto pubblicato.{'\n'}Aggiungi podcast, video, articoli e risorse per i tuoi allievi.
            </Text>
          </Card>
        }
      />

      {/* Modale nuovo contenuto */}
      <Modal visible={showModal} animationType="slide" transparent>
        <View style={styles.modalOverlay}>
          <ScrollView style={styles.modalContent}>
            <ModalHeader title="Nuovo Contenuto" onClose={() => { setShowModal(false); resetForm(); }} />

            {/* Tipo */}
            <Text style={styles.fieldLabel}>Tipo</Text>
            <View style={styles.typeRow}>
              {CONTENT_TYPES.map((t) => (
                <TouchableOpacity
                  key={t.value}
                  style={[
                    styles.typeChip,
                    contentType === t.value && { backgroundColor: t.color + '20', borderColor: t.color },
                  ]}
                  onPress={() => setContentType(t.value)}
                >
                  <Text
                    style={[
                      styles.typeChipText,
                      contentType === t.value && { color: t.color },
                    ]}
                  >
                    {t.label}
                  </Text>
                </TouchableOpacity>
              ))}
            </View>

            <InputField
              label="Titolo"
              value={title}
              onChangeText={setTitle}
              placeholder="Es: Tecnica di respirazione diaframmatica"
            />

            <InputField
              label="Descrizione"
              value={description}
              onChangeText={setDescription}
              placeholder="Breve descrizione del contenuto..."
              multiline
              numberOfLines={3}
            />

            <InputField
              label="URL"
              value={url}
              onChangeText={setUrl}
              placeholder="Link al contenuto"
              autoCapitalize="none"
            />

            <InputField
              label="Tag (separati da virgola)"
              value={tags}
              onChangeText={setTags}
              placeholder="respirazione, benessere, tecnica"
            />

            {/* Assegnazione allievi */}
            <Text style={styles.fieldLabel}>
              Assegna a (vuoto = tutti gli allievi)
            </Text>
            <ScrollView horizontal showsHorizontalScrollIndicator={false}>
              <View style={styles.studentRow}>
                {students.map((s) => (
                  <TouchableOpacity
                    key={s.id}
                    style={[
                      styles.studentChip,
                      selectedStudentIds.includes(s.id) && styles.studentChipActive,
                    ]}
                    onPress={() => toggleStudent(s.id)}
                  >
                    <Text
                      style={[
                        styles.studentChipText,
                        selectedStudentIds.includes(s.id) && styles.studentChipTextActive,
                      ]}
                    >
                      {s.name} {s.surname}
                    </Text>
                  </TouchableOpacity>
                ))}
              </View>
            </ScrollView>

            <View style={styles.modalButtons}>
              <Button
                title="Annulla"
                onPress={() => { setShowModal(false); resetForm(); }}
                variant="outline"
                style={styles.modalButton}
              />
              <Button
                title={saving ? 'Salvataggio...' : 'Pubblica'}
                onPress={handleSave}
                style={styles.modalButton}
                loading={saving}
              />
            </View>

            <View style={styles.bottomSpacer} />
          </ScrollView>
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
  addButtonContainer: {
    padding: spacing.md,
  },
  list: {
    paddingHorizontal: spacing.md,
    paddingBottom: spacing.xxl,
  },
  contentRow: {
    flexDirection: 'row',
    alignItems: 'flex-start',
    gap: spacing.md,
  },
  typeBadge: {
    paddingHorizontal: spacing.sm,
    paddingVertical: spacing.xs,
    borderRadius: borderRadius.md,
  },
  typeBadgeText: {
    fontSize: fontSize.xs,
    fontWeight: '700',
  },
  contentInfo: {
    flex: 1,
  },
  contentTitle: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
  },
  contentDesc: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 2,
  },
  contentMeta: {
    fontSize: fontSize.xs,
    color: colors.textLight,
    marginTop: 4,
  },
  deleteBtn: {
    padding: spacing.xs,
  },
  deleteBtnText: {
    color: colors.error,
    fontWeight: '700',
    fontSize: fontSize.lg,
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
    marginTop: spacing.sm,
  },
  typeRow: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginBottom: spacing.md,
  },
  typeChip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    borderWidth: 1,
    borderColor: colors.border,
  },
  typeChipText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    fontWeight: '600',
  },
  studentRow: {
    flexDirection: 'row',
    gap: spacing.sm,
    paddingBottom: spacing.sm,
  },
  studentChip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    borderWidth: 1,
    borderColor: colors.border,
  },
  studentChipActive: {
    backgroundColor: colors.accent,
    borderColor: colors.accent,
  },
  studentChipText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
  },
  studentChipTextActive: {
    color: colors.textOnAccent,
  },
  modalButtons: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginTop: spacing.lg,
  },
  modalButton: {
    flex: 1,
  },
  bottomSpacer: {
    height: spacing.xxl * 2,
  },
});
