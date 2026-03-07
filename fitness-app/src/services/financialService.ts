import {
  collection,
  addDoc,
  getDocs,
  query,
  where,
  orderBy,
  Timestamp,
  deleteDoc,
  doc,
  updateDoc,
} from 'firebase/firestore';
import { db } from '../config/firebase';
import { FinancialTransaction, TransactionType } from '../types';

const TRANSACTIONS_COLLECTION = 'financialTransactions';

export const addTransaction = async (
  transaction: Omit<FinancialTransaction, 'id'>
): Promise<string> => {
  const docRef = await addDoc(collection(db, TRANSACTIONS_COLLECTION), {
    ...transaction,
    date: Timestamp.fromDate(transaction.date),
  });
  return docRef.id;
};

export const updateTransaction = async (
  id: string,
  data: Partial<FinancialTransaction>
): Promise<void> => {
  await updateDoc(doc(db, TRANSACTIONS_COLLECTION, id), data);
};

export const deleteTransaction = async (id: string): Promise<void> => {
  await deleteDoc(doc(db, TRANSACTIONS_COLLECTION, id));
};

export const getTransactions = async (
  filters?: {
    type?: TransactionType;
    startDate?: Date;
    endDate?: Date;
    category?: string;
  }
): Promise<FinancialTransaction[]> => {
  let q = query(
    collection(db, TRANSACTIONS_COLLECTION),
    orderBy('date', 'desc')
  );

  if (filters?.type) {
    q = query(q, where('type', '==', filters.type));
  }

  const snapshot = await getDocs(q);
  let transactions = snapshot.docs.map(
    (d) => ({ id: d.id, ...d.data() } as FinancialTransaction)
  );

  // Filtraggio date lato client (Firestore non supporta range + orderBy su campi diversi)
  if (filters?.startDate) {
    transactions = transactions.filter(
      (t) => new Date(t.date as unknown as string) >= filters.startDate!
    );
  }
  if (filters?.endDate) {
    transactions = transactions.filter(
      (t) => new Date(t.date as unknown as string) <= filters.endDate!
    );
  }
  if (filters?.category) {
    transactions = transactions.filter((t) => t.category === filters.category);
  }

  return transactions;
};

export const getFinancialSummary = async (
  period?: { startDate: Date; endDate: Date }
): Promise<{
  totalIncome: number;
  totalExpenses: number;
  netProfit: number;
  byCategory: Record<string, number>;
}> => {
  const transactions = await getTransactions(
    period ? { startDate: period.startDate, endDate: period.endDate } : undefined
  );

  let totalIncome = 0;
  let totalExpenses = 0;
  const byCategory: Record<string, number> = {};

  for (const t of transactions) {
    if (t.type === 'income') {
      totalIncome += t.amount;
    } else {
      totalExpenses += t.amount;
    }
    byCategory[t.category] = (byCategory[t.category] || 0) + t.amount;
  }

  return {
    totalIncome,
    totalExpenses,
    netProfit: totalIncome - totalExpenses,
    byCategory,
  };
};
