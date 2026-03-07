import {
  collection,
  doc,
  addDoc,
  updateDoc,
  getDocs,
  getDoc,
  query,
  where,
  orderBy,
  Timestamp,
} from 'firebase/firestore';
import { db } from '../config/firebase';
import { TrainingProgram, WorkoutPlan, Exercise } from '../types';

const PROGRAMS_COLLECTION = 'trainingPrograms';
const PLANS_COLLECTION = 'workoutPlans';
const EXERCISES_COLLECTION = 'exerciseLibrary';

// --- Programmi sessione singola ---

export const createProgram = async (
  program: Omit<TrainingProgram, 'id'>
): Promise<string> => {
  const docRef = await addDoc(collection(db, PROGRAMS_COLLECTION), {
    ...program,
    createdAt: Timestamp.now(),
  });
  return docRef.id;
};

export const getStudentPrograms = async (
  studentId: string
): Promise<TrainingProgram[]> => {
  const q = query(
    collection(db, PROGRAMS_COLLECTION),
    where('studentId', '==', studentId),
    orderBy('createdAt', 'desc')
  );
  const snapshot = await getDocs(q);
  return snapshot.docs.map((d) => ({ id: d.id, ...d.data() } as TrainingProgram));
};

export const updateProgram = async (
  programId: string,
  data: Partial<TrainingProgram>
): Promise<void> => {
  await updateDoc(doc(db, PROGRAMS_COLLECTION, programId), data);
};

// --- Piani di allenamento (programmazioni settimanali/mensili) ---

export const createWorkoutPlan = async (
  plan: Omit<WorkoutPlan, 'id'>
): Promise<string> => {
  const docRef = await addDoc(collection(db, PLANS_COLLECTION), {
    ...plan,
    startDate: Timestamp.fromDate(plan.startDate),
    endDate: Timestamp.fromDate(plan.endDate),
    createdAt: Timestamp.now(),
  });
  return docRef.id;
};

export const getStudentWorkoutPlans = async (
  studentId: string
): Promise<WorkoutPlan[]> => {
  const q = query(
    collection(db, PLANS_COLLECTION),
    where('studentId', '==', studentId),
    orderBy('createdAt', 'desc')
  );
  const snapshot = await getDocs(q);
  return snapshot.docs.map((d) => ({ id: d.id, ...d.data() } as WorkoutPlan));
};

export const getActiveWorkoutPlan = async (
  studentId: string
): Promise<WorkoutPlan | null> => {
  const q = query(
    collection(db, PLANS_COLLECTION),
    where('studentId', '==', studentId),
    where('isActive', '==', true)
  );
  const snapshot = await getDocs(q);
  if (snapshot.empty) return null;
  const d = snapshot.docs[0];
  return { id: d.id, ...d.data() } as WorkoutPlan;
};

// --- Libreria esercizi ---

export const getExerciseLibrary = async (): Promise<Exercise[]> => {
  const snapshot = await getDocs(collection(db, EXERCISES_COLLECTION));
  return snapshot.docs.map((d) => ({ id: d.id, ...d.data() } as Exercise));
};

export const addExerciseToLibrary = async (
  exercise: Omit<Exercise, 'id'>
): Promise<string> => {
  const docRef = await addDoc(collection(db, EXERCISES_COLLECTION), exercise);
  return docRef.id;
};

export const getExercise = async (exerciseId: string): Promise<Exercise | null> => {
  const docSnap = await getDoc(doc(db, EXERCISES_COLLECTION, exerciseId));
  if (!docSnap.exists()) return null;
  return { id: docSnap.id, ...docSnap.data() } as Exercise;
};
