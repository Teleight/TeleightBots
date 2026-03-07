import {
  collection,
  addDoc,
  getDocs,
  query,
  where,
  orderBy,
  deleteDoc,
  doc,
  Timestamp,
} from 'firebase/firestore';
import { db } from '../config/firebase';
import { SpecialContent, DiaryEntry, NutritionalConsultation } from '../types';

// --- Contenuti speciali (podcast, risorse, ecc.) ---

const CONTENT_COLLECTION = 'specialContent';
const DIARY_COLLECTION = 'diaryEntries';
const NUTRITION_COLLECTION = 'nutritionalConsultations';

export const addContent = async (
  content: Omit<SpecialContent, 'id'>
): Promise<string> => {
  const docRef = await addDoc(collection(db, CONTENT_COLLECTION), {
    ...content,
    createdAt: Timestamp.now(),
  });
  return docRef.id;
};

export const getContentForStudent = async (
  studentId: string
): Promise<SpecialContent[]> => {
  const snapshot = await getDocs(
    query(collection(db, CONTENT_COLLECTION), orderBy('createdAt', 'desc'))
  );

  return snapshot.docs
    .map((d) => ({ id: d.id, ...d.data() } as SpecialContent))
    .filter(
      (c) => c.assignedTo.length === 0 || c.assignedTo.includes(studentId)
    );
};

export const getAllContent = async (): Promise<SpecialContent[]> => {
  const snapshot = await getDocs(
    query(collection(db, CONTENT_COLLECTION), orderBy('createdAt', 'desc'))
  );
  return snapshot.docs.map((d) => ({ id: d.id, ...d.data() } as SpecialContent));
};

export const deleteContent = async (contentId: string): Promise<void> => {
  await deleteDoc(doc(db, CONTENT_COLLECTION, contentId));
};

// --- Diario allievo ---

export const addDiaryEntry = async (
  entry: Omit<DiaryEntry, 'id'>
): Promise<string> => {
  const docRef = await addDoc(collection(db, DIARY_COLLECTION), {
    ...entry,
    date: Timestamp.fromDate(entry.date),
    createdAt: Timestamp.now(),
  });
  return docRef.id;
};

export const getStudentDiary = async (
  studentId: string
): Promise<DiaryEntry[]> => {
  const q = query(
    collection(db, DIARY_COLLECTION),
    where('studentId', '==', studentId),
    orderBy('date', 'desc')
  );
  const snapshot = await getDocs(q);
  return snapshot.docs.map((d) => ({ id: d.id, ...d.data() } as DiaryEntry));
};

// --- Consulenze nutrizionali ---

export const addNutritionalConsultation = async (
  consultation: Omit<NutritionalConsultation, 'id'>
): Promise<string> => {
  const docRef = await addDoc(collection(db, NUTRITION_COLLECTION), {
    ...consultation,
    date: Timestamp.fromDate(consultation.date),
  });
  return docRef.id;
};

export const getStudentNutritionalConsultations = async (
  studentId: string
): Promise<NutritionalConsultation[]> => {
  const q = query(
    collection(db, NUTRITION_COLLECTION),
    where('studentId', '==', studentId),
    orderBy('date', 'desc')
  );
  const snapshot = await getDocs(q);
  return snapshot.docs.map(
    (d) => ({ id: d.id, ...d.data() } as NutritionalConsultation)
  );
};
