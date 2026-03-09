import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  Linking,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { SpecialContent, ContentType } from '../../types';
import { useAuth } from '../../hooks/useAuth';
import { getContentForStudent } from '../../services/contentService';

const CONTENT_ICONS: Record<ContentType, string> = {
  podcast: 'Podcast',
  video: 'Video',
  article: 'Articolo',
  resource: 'Risorsa',
};

const CONTENT_COLORS: Record<ContentType, string> = {
  podcast: '#9C27B0',
  video: '#F44336',
  article: '#2196F3',
  resource: '#4CAF50',
};

export const ContentScreen: React.FC = () => {
  const { user } = useAuth();
  const [content, setContent] = useState<SpecialContent[]>([]);
  const [selectedType, setSelectedType] = useState<ContentType | 'all'>('all');

  const loadContent = useCallback(async () => {
    if (!user) return;
    try {
      const data = await getContentForStudent(user.id);
      setContent(data);
    } catch {
      // Silently handle
    }
  }, [user]);

  useEffect(() => {
    loadContent();
  }, [loadContent]);

  const filtered =
    selectedType === 'all'
      ? content
      : content.filter((c) => c.type === selectedType);

  const handleOpenContent = (item: SpecialContent) => {
    if (item.url) {
      Linking.openURL(item.url);
    }
  };

  const renderContent = ({ item }: { item: SpecialContent }) => (
    <TouchableOpacity onPress={() => handleOpenContent(item)}>
      <Card variant="elevated">
        <View style={styles.contentHeader}>
          <View
            style={[
              styles.typeBadge,
              { backgroundColor: CONTENT_COLORS[item.type] + '20' },
            ]}
          >
            <Text
              style={[
                styles.typeBadgeText,
                { color: CONTENT_COLORS[item.type] },
              ]}
            >
              {CONTENT_ICONS[item.type]}
            </Text>
          </View>
          <View style={styles.contentInfo}>
            <Text style={styles.contentTitle}>{item.title}</Text>
            <Text style={styles.contentDate}>
              {new Date(item.createdAt as unknown as string).toLocaleDateString('it-IT')}
            </Text>
          </View>
        </View>
        <Text style={styles.contentDescription}>{item.description}</Text>
        {item.tags.length > 0 && (
          <View style={styles.tagRow}>
            {item.tags.map((tag) => (
              <View key={tag} style={styles.tag}>
                <Text style={styles.tagText}>#{tag}</Text>
              </View>
            ))}
          </View>
        )}
      </Card>
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Contenuti Speciali</Text>
        <Text style={styles.subtitle}>
          Podcast, video e risorse per te
        </Text>
      </View>

      {/* Filtri tipo */}
      <View style={styles.filterRow}>
        <TouchableOpacity
          style={[
            styles.filterChip,
            selectedType === 'all' && styles.filterChipActive,
          ]}
          onPress={() => setSelectedType('all')}
        >
          <Text
            style={[
              styles.filterText,
              selectedType === 'all' && styles.filterTextActive,
            ]}
          >
            Tutti
          </Text>
        </TouchableOpacity>
        {(Object.keys(CONTENT_ICONS) as ContentType[]).map((type) => (
          <TouchableOpacity
            key={type}
            style={[
              styles.filterChip,
              selectedType === type && styles.filterChipActive,
            ]}
            onPress={() => setSelectedType(type)}
          >
            <Text
              style={[
                styles.filterText,
                selectedType === type && styles.filterTextActive,
              ]}
            >
              {CONTENT_ICONS[type]}
            </Text>
          </TouchableOpacity>
        ))}
      </View>

      <FlatList
        data={filtered}
        renderItem={renderContent}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        ListEmptyComponent={
          <Card>
            <Text style={styles.emptyText}>
              Nessun contenuto disponibile al momento
            </Text>
          </Card>
        }
      />
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
  filterRow: {
    flexDirection: 'row',
    padding: spacing.md,
    gap: spacing.sm,
    flexWrap: 'wrap',
  },
  filterChip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    backgroundColor: colors.surface,
    ...shadows.small,
  },
  filterChipActive: {
    backgroundColor: colors.accent,
  },
  filterText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    fontWeight: '600',
  },
  filterTextActive: {
    color: colors.textOnAccent,
  },
  list: {
    paddingHorizontal: spacing.md,
    paddingBottom: spacing.xxl,
  },
  contentHeader: {
    flexDirection: 'row',
    gap: spacing.md,
    marginBottom: spacing.sm,
  },
  typeBadge: {
    width: 48,
    height: 48,
    borderRadius: borderRadius.md,
    justifyContent: 'center',
    alignItems: 'center',
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
  contentDate: {
    fontSize: fontSize.xs,
    color: colors.textLight,
    marginTop: 2,
  },
  contentDescription: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    lineHeight: 20,
  },
  tagRow: {
    flexDirection: 'row',
    gap: spacing.xs,
    marginTop: spacing.sm,
    flexWrap: 'wrap',
  },
  tag: {
    paddingHorizontal: spacing.sm,
    paddingVertical: 2,
    backgroundColor: colors.background,
    borderRadius: borderRadius.round,
  },
  tagText: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.lg,
  },
});
