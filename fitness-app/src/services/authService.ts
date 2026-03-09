import {
  signInWithEmailAndPassword,
  createUserWithEmailAndPassword,
  signOut as firebaseSignOut,
  onAuthStateChanged,
  User as FirebaseUser,
} from 'firebase/auth';
import { doc, getDoc, setDoc, collection, getDocs, query, where, Timestamp } from 'firebase/firestore';
import { auth, db } from '../config/firebase';
import { User, UserRole } from '../types';

export const signIn = async (email: string, password: string): Promise<User> => {
  const credential = await signInWithEmailAndPassword(auth, email, password);
  const userDoc = await getDoc(doc(db, 'users', credential.user.uid));
  if (!userDoc.exists()) {
    throw new Error('Utente non trovato nel database');
  }
  return { id: userDoc.id, ...userDoc.data() } as User;
};

export const registerOwner = async (
  email: string,
  password: string,
  name: string,
  surname: string,
  phone: string
): Promise<User> => {
  // Controlla se esiste gia' un owner
  const q = query(collection(db, 'users'), where('role', '==', 'owner'));
  const existing = await getDocs(q);
  if (!existing.empty) {
    throw new Error('Esiste già un amministratore registrato');
  }

  const credential = await createUserWithEmailAndPassword(auth, email, password);
  const userData: Omit<User, 'id'> = {
    email,
    name,
    surname,
    phone,
    role: 'owner',
    createdAt: new Date(),
    isActive: true,
  };

  await setDoc(doc(db, 'users', credential.user.uid), {
    ...userData,
    createdAt: Timestamp.now(),
  });

  return { id: credential.user.uid, ...userData };
};

export const signOut = async (): Promise<void> => {
  await firebaseSignOut(auth);
};

export const getCurrentUser = (): Promise<FirebaseUser | null> => {
  return new Promise((resolve) => {
    const unsubscribe = onAuthStateChanged(auth, (user) => {
      unsubscribe();
      resolve(user);
    });
  });
};

export const getUserProfile = async (uid: string): Promise<User | null> => {
  const userDoc = await getDoc(doc(db, 'users', uid));
  if (!userDoc.exists()) return null;
  return { id: userDoc.id, ...userDoc.data() } as User;
};

export const getUserRole = async (uid: string): Promise<UserRole | null> => {
  const user = await getUserProfile(uid);
  return user?.role ?? null;
};
