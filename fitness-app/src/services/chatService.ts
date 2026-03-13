import {
  collection,
  doc,
  addDoc,
  getDocs,
  query,
  where,
  orderBy,
  onSnapshot,
  updateDoc,
  arrayUnion,
  Timestamp,
  Unsubscribe,
} from 'firebase/firestore';
import { db } from '../config/firebase';
import { ChatRoom, ChatMessage } from '../types';

const ROOMS_COLLECTION = 'chatRooms';
const MESSAGES_COLLECTION = 'chatMessages';

// --- Chat Rooms ---

export const createChatRoom = async (
  studentId: string,
  collaboratorId: string
): Promise<string> => {
  // Controlla se esiste già
  const existing = await findChatRoom(studentId, collaboratorId);
  if (existing) return existing.id;

  const docRef = await addDoc(collection(db, ROOMS_COLLECTION), {
    participants: [studentId, collaboratorId],
    type: 'direct',
    studentId,
    collaboratorId,
    createdAt: Timestamp.now(),
  });
  return docRef.id;
};

export const findChatRoom = async (
  studentId: string,
  collaboratorId: string
): Promise<ChatRoom | null> => {
  const q = query(
    collection(db, ROOMS_COLLECTION),
    where('studentId', '==', studentId),
    where('collaboratorId', '==', collaboratorId)
  );
  const snapshot = await getDocs(q);
  if (snapshot.empty) return null;
  const d = snapshot.docs[0];
  return { ...d.data(), id: d.id } as ChatRoom;
};

export const getUserChatRooms = async (userId: string): Promise<ChatRoom[]> => {
  const q = query(
    collection(db, ROOMS_COLLECTION),
    where('participants', 'array-contains', userId)
  );
  const snapshot = await getDocs(q);
  return snapshot.docs.map((d) => ({ ...d.data(), id: d.id } as ChatRoom));
};

// Il titolare può vedere TUTTE le chat
export const getAllChatRooms = async (): Promise<ChatRoom[]> => {
  const snapshot = await getDocs(collection(db, ROOMS_COLLECTION));
  return snapshot.docs.map((d) => ({ ...d.data(), id: d.id } as ChatRoom));
};

// --- Messaggi ---

export const sendMessage = async (
  chatRoomId: string,
  senderId: string,
  senderName: string,
  text: string,
  isAnonymousOwner: boolean = false
): Promise<string> => {
  const docRef = await addDoc(collection(db, MESSAGES_COLLECTION), {
    chatRoomId,
    senderId,
    senderName: isAnonymousOwner ? senderName : senderName,
    text,
    timestamp: Timestamp.now(),
    isAnonymousOwner,
    readBy: [senderId],
  });

  // Aggiorna lastMessage nella room
  const roomRef = doc(db, ROOMS_COLLECTION, chatRoomId);
  await updateDoc(roomRef, {
    lastMessage: {
      text,
      timestamp: Timestamp.now(),
      senderId,
    },
  });

  return docRef.id;
};

export const subscribeToMessages = (
  chatRoomId: string,
  callback: (messages: ChatMessage[]) => void
): Unsubscribe => {
  const q = query(
    collection(db, MESSAGES_COLLECTION),
    where('chatRoomId', '==', chatRoomId),
    orderBy('timestamp', 'asc')
  );

  return onSnapshot(q, (snapshot) => {
    const messages = snapshot.docs.map(
      (d) => ({ ...d.data(), id: d.id } as ChatMessage)
    );
    callback(messages);
  });
};

export const markMessagesAsRead = async (
  chatRoomId: string,
  userId: string
): Promise<void> => {
  const q = query(
    collection(db, MESSAGES_COLLECTION),
    where('chatRoomId', '==', chatRoomId)
  );
  const snapshot = await getDocs(q);
  const batch: Promise<void>[] = [];
  snapshot.docs.forEach((d) => {
    const data = d.data();
    if (!data.readBy?.includes(userId)) {
      batch.push(
        updateDoc(doc(db, MESSAGES_COLLECTION, d.id), {
          readBy: arrayUnion(userId),
        })
      );
    }
  });
  await Promise.all(batch);
};
