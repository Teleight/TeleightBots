import React, { useState, useEffect, useRef } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
  KeyboardAvoidingView,
  Platform,
  TextInput,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { ScreenHeader } from '../../components/common/ScreenHeader';
import { ChatRoom, ChatMessage, User } from '../../types';
import { useAuth } from '../../hooks/useAuth';
import {
  sendMessage,
  subscribeToMessages,
  markMessagesAsRead,
} from '../../services/chatService';

interface Props {
  room: ChatRoom;
  isAnonymous: boolean;
  onBack: () => void;
  participants: Record<string, User>;
}

export const ChatConversationScreen: React.FC<Props> = ({
  room,
  isAnonymous,
  onBack,
  participants,
}) => {
  const { user, isOwner } = useAuth();
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [newMessage, setNewMessage] = useState('');
  const flatListRef = useRef<FlatList>(null);

  useEffect(() => {
    if (!user) return;

    const unsubscribe = subscribeToMessages(room.id, (msgs) => {
      setMessages(msgs);
      // Segna come letti
      if (!isAnonymous) {
        markMessagesAsRead(room.id, user.id);
      }
    });

    return () => unsubscribe();
  }, [room.id, user, isAnonymous]);

  const handleSend = async () => {
    if (!newMessage.trim() || !user || isAnonymous) return;

    try {
      await sendMessage(
        room.id,
        user.id,
        `${user.name} ${user.surname}`,
        newMessage.trim(),
        false
      );
      setNewMessage('');
    } catch {
      // Silently handle
    }
  };

  const getSenderName = (msg: ChatMessage): string => {
    if (msg.senderId === user?.id) return 'Tu';
    const sender = participants[msg.senderId];
    return sender ? `${sender.name}` : msg.senderName;
  };

  const renderMessage = ({ item }: { item: ChatMessage }) => {
    const isMe = item.senderId === user?.id;

    return (
      <View
        style={[
          styles.messageBubble,
          isMe ? styles.myMessage : styles.theirMessage,
        ]}
      >
        {!isMe && (
          <Text style={styles.senderName}>{getSenderName(item)}</Text>
        )}
        <Text style={[styles.messageText, isMe && styles.myMessageText]}>
          {item.text}
        </Text>
        <Text style={[styles.messageTime, isMe && styles.myMessageTime]}>
          {new Date(item.timestamp as unknown as string).toLocaleTimeString('it-IT', {
            hour: '2-digit',
            minute: '2-digit',
          })}
        </Text>
      </View>
    );
  };

  // Titolo della conversazione
  const getTitle = (): string => {
    if (isOwner) {
      const student = participants[room.studentId];
      const collab = participants[room.collaboratorId];
      const sName = student ? `${student.name} ${student.surname}` : 'Allievo';
      const cName = collab ? `${collab.name} ${collab.surname}` : 'Collaboratore';
      return `${sName} ↔ ${cName}`;
    }
    const otherId = room.participants.find((id) => id !== user?.id);
    if (otherId && participants[otherId]) {
      const p = participants[otherId];
      return `${p.name} ${p.surname}`;
    }
    return 'Chat';
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
      keyboardVerticalOffset={90}
    >
      {/* Header */}
      <ScreenHeader
        title={getTitle()}
        subtitle={isAnonymous ? 'Modalita anonima - solo lettura' : undefined}
        onBack={onBack}
      />

      {/* Messaggi */}
      <FlatList
        ref={flatListRef}
        data={messages}
        renderItem={renderMessage}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.messageList}
        onContentSizeChange={() =>
          flatListRef.current?.scrollToEnd({ animated: true })
        }
        ListEmptyComponent={
          <View style={styles.emptyContainer}>
            <Text style={styles.emptyText}>
              Nessun messaggio. Inizia la conversazione!
            </Text>
          </View>
        }
      />

      {/* Input messaggio (nascosto in modalita anonima) */}
      {!isAnonymous && (
        <View style={styles.inputContainer}>
          <TextInput
            style={styles.textInput}
            value={newMessage}
            onChangeText={setNewMessage}
            placeholder="Scrivi un messaggio..."
            placeholderTextColor={colors.textLight}
            multiline
            maxLength={1000}
          />
          <TouchableOpacity
            style={[
              styles.sendButton,
              !newMessage.trim() && styles.sendButtonDisabled,
            ]}
            onPress={handleSend}
            disabled={!newMessage.trim()}
          >
            <Text style={styles.sendButtonText}>Invia</Text>
          </TouchableOpacity>
        </View>
      )}

      {isAnonymous && (
        <View style={styles.anonBar}>
          <Text style={styles.anonBarText}>
            Stai osservando in modalita anonima
          </Text>
        </View>
      )}
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background,
  },
  header: {
    backgroundColor: colors.primary,
    padding: spacing.md,
    paddingTop: spacing.xxl,
    flexDirection: 'row',
    alignItems: 'center',
  },
  backButton: {
    marginRight: spacing.md,
  },
  backText: {
    color: colors.accent,
    fontSize: fontSize.md,
    fontWeight: '600',
  },
  headerInfo: {
    flex: 1,
  },
  headerTitle: {
    fontSize: fontSize.lg,
    fontWeight: '700',
    color: colors.textOnPrimary,
  },
  anonLabel: {
    fontSize: fontSize.xs,
    color: colors.warning,
    marginTop: 2,
  },
  messageList: {
    padding: spacing.md,
    paddingBottom: spacing.lg,
    flexGrow: 1,
  },
  messageBubble: {
    maxWidth: '80%',
    padding: spacing.md,
    borderRadius: borderRadius.lg,
    marginBottom: spacing.sm,
  },
  myMessage: {
    alignSelf: 'flex-end',
    backgroundColor: colors.accent,
    borderBottomRightRadius: spacing.xs,
  },
  theirMessage: {
    alignSelf: 'flex-start',
    backgroundColor: colors.surface,
    borderBottomLeftRadius: spacing.xs,
  },
  senderName: {
    fontSize: fontSize.xs,
    fontWeight: '700',
    color: colors.collaboratorBadge,
    marginBottom: spacing.xs,
  },
  messageText: {
    fontSize: fontSize.md,
    color: colors.text,
    lineHeight: 20,
  },
  myMessageText: {
    color: colors.textOnAccent,
  },
  messageTime: {
    fontSize: fontSize.xs,
    color: colors.textLight,
    marginTop: spacing.xs,
    alignSelf: 'flex-end',
  },
  myMessageTime: {
    color: 'rgba(255,255,255,0.7)',
  },
  inputContainer: {
    flexDirection: 'row',
    padding: spacing.md,
    paddingBottom: spacing.lg,
    backgroundColor: colors.surface,
    borderTopWidth: 1,
    borderTopColor: colors.divider,
    alignItems: 'flex-end',
  },
  textInput: {
    flex: 1,
    backgroundColor: colors.background,
    borderRadius: borderRadius.lg,
    padding: spacing.md,
    paddingTop: spacing.md,
    color: colors.text,
    fontSize: fontSize.md,
    maxHeight: 120,
    borderWidth: 1,
    borderColor: colors.border,
  },
  sendButton: {
    marginLeft: spacing.sm,
    backgroundColor: colors.accent,
    paddingHorizontal: spacing.lg,
    paddingVertical: spacing.md,
    borderRadius: borderRadius.lg,
  },
  sendButtonDisabled: {
    opacity: 0.5,
  },
  sendButtonText: {
    color: colors.textOnAccent,
    fontWeight: '700',
    fontSize: fontSize.md,
  },
  anonBar: {
    backgroundColor: colors.warning + '20',
    padding: spacing.md,
    alignItems: 'center',
  },
  anonBarText: {
    color: colors.warning,
    fontSize: fontSize.sm,
    fontWeight: '600',
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingTop: spacing.xxl * 2,
  },
  emptyText: {
    color: colors.textSecondary,
    fontSize: fontSize.md,
    textAlign: 'center',
  },
});
