import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  Switch,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { ChatRoom, UserRole } from '../../types';

interface ChatListScreenProps {
  userRole: UserRole;
  userId: string;
  onSelectChat: (room: ChatRoom) => void;
}

export const ChatListScreen: React.FC<ChatListScreenProps> = ({
  userRole,
  userId,
  onSelectChat,
}) => {
  const [chatRooms, setChatRooms] = useState<ChatRoom[]>([]);
  const [anonymousMode, setAnonymousMode] = useState(false);

  const renderChatRoom = ({ item }: { item: ChatRoom }) => (
    <TouchableOpacity
      style={styles.chatItem}
      onPress={() => onSelectChat(item)}
    >
      <View style={styles.avatar}>
        <Text style={styles.avatarText}>
          {item.participants.length > 0 ? 'CH' : '??'}
        </Text>
      </View>
      <View style={styles.chatInfo}>
        <Text style={styles.chatName}>
          Chat: {item.studentId.substring(0, 8)}...
        </Text>
        {item.lastMessage && (
          <Text style={styles.lastMessage} numberOfLines={1}>
            {item.lastMessage.text}
          </Text>
        )}
      </View>
      {item.lastMessage && (
        <Text style={styles.chatTime}>
          {new Date(
            item.lastMessage.timestamp as unknown as string
          ).toLocaleTimeString('it-IT', { hour: '2-digit', minute: '2-digit' })}
        </Text>
      )}
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Chat</Text>

        {/* Modalita anonima solo per il titolare */}
        {userRole === 'owner' && (
          <View style={styles.anonymousToggle}>
            <Text style={styles.anonymousLabel}>
              Modalita Anonima
            </Text>
            <Switch
              value={anonymousMode}
              onValueChange={setAnonymousMode}
              trackColor={{ false: colors.border, true: colors.accent + '60' }}
              thumbColor={anonymousMode ? colors.accent : colors.textLight}
            />
          </View>
        )}

        {userRole === 'owner' && anonymousMode && (
          <View style={styles.anonymousInfo}>
            <Text style={styles.anonymousInfoText}>
              Stai navigando in modalita anonima. Puoi leggere tutte le chat
              senza che i partecipanti lo sappiano.
            </Text>
          </View>
        )}
      </View>

      <FlatList
        data={chatRooms}
        renderItem={renderChatRoom}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        ListEmptyComponent={
          <View style={styles.emptyContainer}>
            <Text style={styles.emptyText}>
              Nessuna conversazione attiva
            </Text>
          </View>
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
  anonymousToggle: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginTop: spacing.md,
    backgroundColor: 'rgba(255,255,255,0.1)',
    borderRadius: borderRadius.md,
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
  },
  anonymousLabel: {
    color: colors.textOnPrimary,
    fontSize: fontSize.md,
    fontWeight: '600',
  },
  anonymousInfo: {
    marginTop: spacing.sm,
    backgroundColor: colors.warning + '20',
    borderRadius: borderRadius.md,
    padding: spacing.sm,
  },
  anonymousInfoText: {
    color: colors.warning,
    fontSize: fontSize.sm,
  },
  list: {
    paddingVertical: spacing.sm,
  },
  chatItem: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: spacing.md,
    backgroundColor: colors.surface,
    borderBottomWidth: 1,
    borderBottomColor: colors.divider,
    gap: spacing.md,
  },
  avatar: {
    width: 48,
    height: 48,
    borderRadius: 24,
    backgroundColor: colors.primaryLight,
    justifyContent: 'center',
    alignItems: 'center',
  },
  avatarText: {
    color: colors.textOnPrimary,
    fontWeight: '700',
    fontSize: fontSize.md,
  },
  chatInfo: {
    flex: 1,
  },
  chatName: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
  },
  lastMessage: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 2,
  },
  chatTime: {
    fontSize: fontSize.xs,
    color: colors.textLight,
  },
  emptyContainer: {
    padding: spacing.xxl,
    alignItems: 'center',
  },
  emptyText: {
    color: colors.textSecondary,
    fontSize: fontSize.md,
  },
});
