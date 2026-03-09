import {
  signInWithEmailAndPassword,
  createUserWithEmailAndPassword,
  signOut as firebaseSignOut,
  onAuthStateChanged,
  User as FirebaseUser,
} from 'firebase/auth';
import { doc, getDoc, setDoc, collection, getDocs, query, where, Timestamp, updateDoc, arrayUnion } from 'firebase/firestore';
import { auth, db } from '../config/firebase';
import { User, UserRole, Collaborator, Student } from '../types';

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

export const registerCollaborator = async (
  email: string,
  password: string,
  name: string,
  surname: string,
  phone: string,
  commissionPercentage: number,
  specializations: string[]
): Promise<Collaborator> => {
  // Crea l'utente in Firebase Auth usando la Firebase Admin REST API secondaria
  // Per evitare il logout dell'owner, usiamo un approccio con seconda istanza auth
  const { createUserWithEmailAndPassword: createUser } = await import('firebase/auth');
  const { initializeApp } = await import('firebase/app');
  const { getAuth } = await import('firebase/auth');

  // Crea un'app secondaria temporanea per non interferire con la sessione corrente
  const secondaryApp = initializeApp(auth.app.options, 'secondary-' + Date.now());
  const secondaryAuth = getAuth(secondaryApp);

  try {
    const credential = await createUser(secondaryAuth, email, password);
    const uid = credential.user.uid;

    const collaboratorData: Omit<Collaborator, 'id'> = {
      email,
      name,
      surname,
      phone,
      role: 'collaborator',
      commissionPercentage,
      specializations,
      assignedStudents: [],
      createdAt: new Date(),
      isActive: true,
    };

    await setDoc(doc(db, 'users', uid), {
      ...collaboratorData,
      createdAt: Timestamp.now(),
    });

    // Esci dall'app secondaria e cancellala
    await secondaryAuth.signOut();
    await (secondaryApp as any).delete();

    return { id: uid, ...collaboratorData };
  } catch (error) {
    // Cleanup in caso di errore
    try {
      await secondaryAuth.signOut();
      await (secondaryApp as any).delete();
    } catch { /* ignore cleanup errors */ }
    throw error;
  }
};

export const registerStudent = async (
  email: string,
  password: string,
  name: string,
  surname: string,
  phone: string,
  assignedCollaboratorId: string,
  goals: string,
  medicalNotes?: string
): Promise<Student> => {
  const { createUserWithEmailAndPassword: createUser } = await import('firebase/auth');
  const { initializeApp } = await import('firebase/app');
  const { getAuth } = await import('firebase/auth');

  const secondaryApp = initializeApp(auth.app.options, 'secondary-' + Date.now());
  const secondaryAuth = getAuth(secondaryApp);

  try {
    const credential = await createUser(secondaryAuth, email, password);
    const uid = credential.user.uid;

    const studentData: Omit<Student, 'id'> = {
      email,
      name,
      surname,
      phone,
      role: 'student',
      assignedCollaboratorId,
      startDate: new Date(),
      goals,
      medicalNotes: medicalNotes || '',
      nutritionalConsultations: 0,
      createdAt: new Date(),
      isActive: true,
    };

    await setDoc(doc(db, 'users', uid), {
      ...studentData,
      createdAt: Timestamp.now(),
      startDate: Timestamp.now(),
    });

    // Aggiorna la lista allievi del collaboratore
    if (assignedCollaboratorId) {
      await updateDoc(doc(db, 'users', assignedCollaboratorId), {
        assignedStudents: arrayUnion(uid),
      });
    }

    await secondaryAuth.signOut();
    await (secondaryApp as any).delete();

    return { id: uid, ...studentData };
  } catch (error) {
    try {
      await secondaryAuth.signOut();
      await (secondaryApp as any).delete();
    } catch { /* ignore cleanup errors */ }
    throw error;
  }
};

export const getCollaborators = async (): Promise<Collaborator[]> => {
  const q = query(collection(db, 'users'), where('role', '==', 'collaborator'));
  const snapshot = await getDocs(q);
  return snapshot.docs.map((d) => ({ id: d.id, ...d.data() } as Collaborator));
};

export const getStudents = async (): Promise<Student[]> => {
  const q = query(collection(db, 'users'), where('role', '==', 'student'));
  const snapshot = await getDocs(q);
  return snapshot.docs.map((d) => ({ id: d.id, ...d.data() } as Student));
};
