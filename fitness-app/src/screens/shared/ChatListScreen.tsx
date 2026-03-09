import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  RefreshControl,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { ChatRoom, User } from '../../types';
import { useAuth } from '../../hooks/useAuth';
import { getUserChatRooms, getAllChatRooms } from '../../services/chatService';
import { getUserProfile } from '../../services/authService';
import { ChatConversationScreen } from './ChatConversationScreen';

export const ChatListScreen: React.FC = () => {
  const { user, isOwner } = useAuth();
  const [rooms, setRooms] = useState<ChatRoom[]>([]);
  const [participants, setParticipants] = useState<Record<string, User>>({});
  const [selectedRoom, setSelectedRoom] = useState<ChatRoom | null>(null);
  const [isAnonymous, setIsAnonymous] = useState(false);
  const [refreshing, setRefreshing] = useState(false);

  const loadRooms = useCallback(async () => {
    if (!user) return;
    try {
      // Il titolare vede TUTTE le chat
      const chatRooms = isOwner
        ? await getAllChatRooms()
        : await getUserChatRooms(user.id);
      setRooms(chatRooms);

      // Carica i profili dei partecipanti
      const userIds = new Set<string>();
      chatRooms.forEach((room) => {
        room.participants.forEach((id) => userIds.add(id));
      });

      const profiles: Record<string, User> = {};
      await Promise.all(
        Array.from(userIds).map(async (id) => {
          const profile = await getUserProfile(id);
          if (profile) profiles[id] = profile;
        })
      );
      setParticipants(profiles);
    } catch {
      // Silently handle
    }
  }, [user, isOwner]);

  useEffect(() => {
    loadRooms();
  }, [loadRooms]);

  const onRefresh = async () => {
    setRefreshing(true);
    await loadRooms();
    setRefreshing(false);
  };

  const getOtherParticipantName = (room: ChatRoom): string => {
    if (!user) return '';
    const otherId = room.participants.find((id) => id !== user.id);
    if (otherId && participants[otherId]) {
      const p = participants[otherId];
      return `${p.name} ${p.surname}`;
    }
    // Per l'owner, mostra entrambi i nomi
    if (isOwner) {
      const student = participants[room.studentId];
      const collab = participants[room.collaboratorId];
      const studentName = student ? `${student.name} ${student.surname}` : 'Allievo';
      const collabName = collab ? `${collab.name} ${collab.surname}` : 'Collaboratore';
      return `${studentName} ↔ ${collabName}`;
    }
    return 'Chat';
  };

  const getParticipantRole = (room: ChatRoom): string => {
    if (isOwner) return 'Allievo ↔ Collaboratore';
    const otherId = room.participants.find((id) => id !== user?.id);
    if (otherId && participants[otherId]) {
      return participants[otherId].role === 'collaborator' ? 'Collaboratore' : 'Allievo';
    }
    return '';
  };

  if (selectedRoom) {
    return (
      <ChatConversationScreen
        room={selectedRoom}
        isAnonymous={isAnonymous}
        onBack={() => {
          setSelectedRoom(null);
          setIsAnonymous(false);
          loadRooms();
        }}
        participants={participants}
      />
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Chat</Text>
        <Text style={styles.subtitle}>
          {isOwner
            ? 'Puoi accedere a tutte le conversazioni'
            : `${rooms.length} conversazioni`}
        </Text>
      </View>

      {isOwner && (
        <View style={styles.ownerModeToggle}>
          <TouchableOpacity
            style={[styles.modeButton, !isAnonymous && styles.modeButtonActive]}
            onPress={() => setIsAnonymous(false)}
          >
            <Text style={[styles.modeText, !isAnonymous && styles.modeTextActive]}>
              Partecipa
            </Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.modeButton, isAnonymous && styles.modeButtonAnon]}
            onPress={() => setIsAnonymous(true)}
          >
            <Text style={[styles.modeText, isAnonymous && styles.modeTextActive]}>
              Anonimo (sola lettura)
            </Text>
          </TouchableOpacity>
        </View>
      )}

      <FlatList
        data={rooms}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        refreshControl={
          <RefreshControl refreshing={refreshing} onRefresh={onRefresh} />
        }
        renderItem={({ item }) => {
          const lastMsg = item.lastMessage;
          return (
            <TouchableOpacity onPress={() => setSelectedRoom(item)}>
              <Card variant="elevated">
                <View style={styles.roomRow}>
                  <View style={styles.roomAvatar}>
                    <Text style={styles.roomAvatarText}>
                      {getOtherParticipantName(item).charAt(0)}
                    </Text>
                  </View>
                  <View style={styles.roomInfo}>
                    <Text style={styles.roomName}>
                      {getOtherParticipantName(item)}
                    </Text>
                    <Text style={styles.roomRole}>
                      {getParticipantRole(item)}
                    </Text>
                    {lastMsg && (
                      <Text style={styles.lastMessage} numberOfLines={1}>
                        {lastMsg.text}
                      </Text>
                    )}
                  </View>
                  {isOwner && isAnonymous && (
                    <View style={styles.anonBadge}>
                      <Text style={styles.anonBadgeText}>Anon</Text>
                    </View>
                  )}
                </View>
              </Card>
            </TouchableOpacity>
          );
        }}
        ListEmptyComponent={
          <Card>
            <Text style={styles.emptyText}>
              Nessuna conversazione attiva
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
  ownerModeToggle: {
    flexDirection: 'row',
    margin: spacing.md,
    backgroundColor: colors.surface,
    borderRadius: borderRadius.lg,
    padding: spacing.xs,
    ...shadows.small,
  },
  modeButton: {
    flex: 1,
    paddingVertical: spacing.sm,
    alignItems: 'center',
    borderRadius: borderRadius.md,
  },
  modeButtonActive: {
    backgroundColor: colors.accent,
  },
  modeButtonAnon: {
    backgroundColor: colors.warning,
  },
  modeText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    fontWeight: '600',
  },
  modeTextActive: {
    color: '#FFFFFF',
  },
  list: {
    padding: spacing.md,
    paddingBottom: spacing.xxl,
  },
  roomRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  roomAvatar: {
    width: 48,
    height: 48,
    borderRadius: 24,
    backgroundColor: colors.collaboratorBadge,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: spacing.md,
  },
  roomAvatarText: {
    color: '#FFFFFF',
    fontSize: fontSize.lg,
    fontWeight: '700',
  },
  roomInfo: {
    flex: 1,
  },
  roomName: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
  },
  roomRole: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
    marginTop: 1,
  },
  lastMessage: {
    fontSize: fontSize.sm,
    color: colors.textLight,
    marginTop: 4,
  },
  anonBadge: {
    backgroundColor: colors.warning + '30',
    paddingHorizontal: spacing.sm,
    paddingVertical: spacing.xs,
    borderRadius: borderRadius.round,
  },
  anonBadgeText: {
    fontSize: fontSize.xs,
    color: colors.warning,
    fontWeight: '700',
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.lg,
  },
});
