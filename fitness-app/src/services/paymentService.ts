import {
  collection,
  doc,
  addDoc,
  updateDoc,
  getDocs,
  deleteDoc,
  query,
  where,
  Timestamp,
} from 'firebase/firestore';
import { db } from '../config/firebase';
import { PaymentPlan, Installment, CollaboratorEarning, PaymentStatus } from '../types';

const PAYMENTS_COLLECTION = 'paymentPlans';
const EARNINGS_COLLECTION = 'collaboratorEarnings';

// --- Piani di pagamento ---

export const createPaymentPlan = async (
  plan: Omit<PaymentPlan, 'id'>
): Promise<string> => {
  const docRef = await addDoc(collection(db, PAYMENTS_COLLECTION), {
    ...plan,
    createdAt: Timestamp.now(),
  });
  return docRef.id;
};

export const getStudentPaymentPlans = async (
  studentId: string
): Promise<PaymentPlan[]> => {
  const q = query(
    collection(db, PAYMENTS_COLLECTION),
    where('studentId', '==', studentId)
  );
  const snapshot = await getDocs(q);
  return snapshot.docs.map((d) => ({ id: d.id, ...d.data() } as PaymentPlan));
};

export const markInstallmentPaid = async (
  planId: string,
  installmentId: string,
  installments: Installment[]
): Promise<void> => {
  const updated = installments.map((inst) =>
    inst.id === installmentId
      ? { ...inst, status: 'paid' as PaymentStatus, paidDate: new Date() }
      : inst
  );
  await updateDoc(doc(db, PAYMENTS_COLLECTION, planId), {
    installments: updated,
  });
};

// --- Calcolo commissioni collaboratore ---

export const calculateCollaboratorEarnings = (
  totalPaid: number,
  commissionPercentage: number
): { collaboratorShare: number; ownerShare: number } => {
  const collaboratorShare = (totalPaid * commissionPercentage) / 100;
  const ownerShare = totalPaid - collaboratorShare;
  return { collaboratorShare, ownerShare };
};

export const getCollaboratorEarnings = async (
  collaboratorId: string,
  period?: string
): Promise<CollaboratorEarning[]> => {
  let q = query(
    collection(db, EARNINGS_COLLECTION),
    where('collaboratorId', '==', collaboratorId)
  );
  if (period) {
    q = query(q, where('period', '==', period));
  }
  const snapshot = await getDocs(q);
  return snapshot.docs.map((d) => ({ ...d.data() } as CollaboratorEarning));
};

export const saveEarningRecord = async (
  earning: CollaboratorEarning
): Promise<void> => {
  await addDoc(collection(db, EARNINGS_COLLECTION), earning);
};

// --- Rate e scadenze ---

export const getUpcomingInstallments = async (
  userId: string,
  role: 'student' | 'collaborator'
): Promise<{ plan: PaymentPlan; installment: Installment }[]> => {
  const field = role === 'student' ? 'studentId' : 'collaboratorId';
  const q = query(
    collection(db, PAYMENTS_COLLECTION),
    where(field, '==', userId)
  );
  const snapshot = await getDocs(q);
  const plans = snapshot.docs.map((d) => ({ id: d.id, ...d.data() } as PaymentPlan));

  const upcoming: { plan: PaymentPlan; installment: Installment }[] = [];
  const now = new Date();

  for (const plan of plans) {
    for (const inst of plan.installments) {
      if (inst.status !== 'paid') {
        const dueDate = inst.dueDate instanceof Date ? inst.dueDate : new Date(inst.dueDate as unknown as string);
        if (dueDate >= now) {
          upcoming.push({ plan, installment: inst });
        }
      }
    }
  }

  return upcoming.sort(
    (a, b) => new Date(a.installment.dueDate).getTime() - new Date(b.installment.dueDate).getTime()
  );
};

export const deletePaymentPlan = async (planId: string): Promise<void> => {
  await deleteDoc(doc(db, PAYMENTS_COLLECTION, planId));
};
