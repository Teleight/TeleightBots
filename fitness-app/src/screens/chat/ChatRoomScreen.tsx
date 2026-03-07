import React, { useState, useEffect, useCallback, useRef } from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TextInput,
  TouchableOpacity,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { ChatMessage } from '../../types';
import {
  sendMessage,
  subscribeToMessages,
  markMessagesAsRead,
} from '../../services/chatService';

interface ChatRoomScreenProps {
  chatRoomId: string;
  userId: string;
  userName: string;
  isAnonymous?: boolean; // Modalita anonima per il titolare
  isReadOnly?: boolean;  // Solo lettura in modalita anonima
}

export const ChatRoomScreen: React.FC<ChatRoomScreenProps> = ({
  chatRoomId,
  userId,
  userName,
  isAnonymous = false,
  isReadOnly = false,
}) => {
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [inputText, setInputText] = useState('');
  const flatListRef = useRef<FlatList>(null);

  useEffect(() => {
    const unsubscribe = subscribeToMessages(chatRoomId, (msgs) => {
      setMessages(msgs);
      // Segna come letti solo se non in modalita anonima
      if (!isAnonymous) {
        markMessagesAsRead(chatRoomId, userId);
      }
    });

    return unsubscribe;
  }, [chatRoomId, userId, isAnonymous]);

  const handleSend = useCallback(async () => {
    if (!inputText.trim() || isReadOnly) return;

    const text = inputText.trim();
    setInputText('');

    await sendMessage(
      chatRoomId,
      userId,
      isAnonymous ? 'Titolare' : userName,
      text,
      false // Non mostra come anonimo quando partecipa
    );
  }, [inputText, chatRoomId, userId, userName, isAnonymous, isReadOnly]);

  const renderMessage = ({ item }: { item: ChatMessage }) => {
    const isOwn = item.senderId === userId;

    return (
      <View
        style={[
          styles.messageContainer,
          isOwn ? styles.messageRight : styles.messageLeft,
        ]}
      >
        {!isOwn && (
          <Text style={styles.senderName}>{item.senderName}</Text>
        )}
        <View
          style={[
            styles.messageBubble,
            isOwn ? styles.bubbleRight : styles.bubbleLeft,
          ]}
        >
          <Text
            style={[
              styles.messageText,
              isOwn ? styles.messageTextRight : styles.messageTextLeft,
            ]}
          >
            {item.text}
          </Text>
          <Text
            style={[
              styles.messageTime,
              isOwn ? styles.timeRight : styles.timeLeft,
            ]}
          >
            {new Date(
              item.timestamp as unknown as string
            ).toLocaleTimeString('it-IT', {
              hour: '2-digit',
              minute: '2-digit',
            })}
          </Text>
        </View>
      </View>
    );
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : undefined}
      keyboardVerticalOffset={90}
    >
      {/* Banner modalita anonima */}
      {isAnonymous && (
        <View style={styles.anonymousBanner}>
          <Text style={styles.anonymousBannerText}>
            {isReadOnly
              ? 'Stai osservando questa chat in modalita anonima'
              : 'Stai partecipando visibilmente a questa chat'}
          </Text>
        </View>
      )}

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

      {/* Input messaggio */}
      {!isReadOnly && (
        <View style={styles.inputContainer}>
          <TextInput
            style={styles.input}
            value={inputText}
            onChangeText={setInputText}
            placeholder="Scrivi un messaggio..."
            placeholderTextColor={colors.textLight}
            multiline
            maxLength={1000}
          />
          <TouchableOpacity
            style={[
              styles.sendButton,
              !inputText.trim() && styles.sendButtonDisabled,
            ]}
            onPress={handleSend}
            disabled={!inputText.trim()}
          >
            <Text style={styles.sendButtonText}>Invia</Text>
          </TouchableOpacity>
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
  anonymousBanner: {
    backgroundColor: colors.warning + '20',
    padding: spacing.sm,
    alignItems: 'center',
  },
  anonymousBannerText: {
    color: colors.warning,
    fontSize: fontSize.sm,
    fontWeight: '600',
  },
  messageList: {
    padding: spacing.md,
    flexGrow: 1,
  },
  messageContainer: {
    marginVertical: spacing.xs,
    maxWidth: '80%',
  },
  messageLeft: {
    alignSelf: 'flex-start',
  },
  messageRight: {
    alignSelf: 'flex-end',
  },
  senderName: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
    marginBottom: 2,
    marginLeft: spacing.sm,
  },
  messageBubble: {
    borderRadius: borderRadius.lg,
    padding: spacing.sm,
    paddingHorizontal: spacing.md,
  },
  bubbleLeft: {
    backgroundColor: colors.surface,
    borderBottomLeftRadius: 4,
  },
  bubbleRight: {
    backgroundColor: colors.accent,
    borderBottomRightRadius: 4,
  },
  messageText: {
    fontSize: fontSize.md,
    lineHeight: 20,
  },
  messageTextLeft: {
    color: colors.text,
  },
  messageTextRight: {
    color: colors.textOnAccent,
  },
  messageTime: {
    fontSize: fontSize.xs,
    marginTop: 4,
    alignSelf: 'flex-end',
  },
  timeLeft: {
    color: colors.textLight,
  },
  timeRight: {
    color: 'rgba(255,255,255,0.7)',
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'flex-end',
    padding: spacing.sm,
    backgroundColor: colors.surface,
    borderTopWidth: 1,
    borderTopColor: colors.divider,
    gap: spacing.sm,
  },
  input: {
    flex: 1,
    backgroundColor: colors.background,
    borderRadius: borderRadius.lg,
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    fontSize: fontSize.md,
    maxHeight: 100,
    color: colors.text,
  },
  sendButton: {
    backgroundColor: colors.accent,
    borderRadius: borderRadius.lg,
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    justifyContent: 'center',
  },
  sendButtonDisabled: {
    opacity: 0.5,
  },
  sendButtonText: {
    color: colors.textOnAccent,
    fontWeight: '700',
    fontSize: fontSize.md,
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: spacing.xxl * 2,
  },
  emptyText: {
    color: colors.textSecondary,
    fontSize: fontSize.md,
  },
});
