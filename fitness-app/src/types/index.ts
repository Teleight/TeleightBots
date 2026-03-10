// ============================================================
// FITNESS COACHING APP - DATA MODELS
// ============================================================

// --- Ruoli utente ---
export type UserRole = 'owner' | 'manager' | 'collaborator' | 'student';

// --- Utente base ---
export interface User {
  id: string;
  email: string;
  name: string;
  surname: string;
  phone: string;
  role: UserRole;
  avatarUrl?: string;
  createdAt: Date;
  isActive: boolean;
}

// --- Titolare (Owner) ---
export interface Owner extends User {
  role: 'owner';
}

// --- Manager ---
export interface Manager extends User {
  role: 'manager';
  assignedCollaborators: string[]; // collaborator IDs gestiti dal manager
  assignedStudents: string[]; // student IDs
}

// --- Collaboratore ---
export interface Collaborator extends User {
  role: 'collaborator';
  commissionPercentage: number; // Es: 60 = il collaboratore tiene il 60%
  specializations: string[];
  assignedStudents: string[]; // student IDs
}

// --- Allievo ---
export interface Student extends User {
  role: 'student';
  assignedCollaboratorId: string;
  startDate: Date;
  goals: string;
  medicalNotes?: string;
  nutritionalConsultations: number;
}

// --- Sessione di allenamento ---
export type SessionStatus = 'scheduled' | 'completed' | 'cancelled_by_student' | 'cancelled_late' | 'no_show';

export interface TrainingSession {
  id: string;
  studentId: string;
  collaboratorId: string;
  date: Date;
  startTime: string; // "09:00"
  endTime: string;   // "10:00"
  status: SessionStatus;
  program?: TrainingProgram;
  notes: string;
  cancelledAt?: Date;
  // Se cancellato < 10 ore prima => considerato eseguito
  isCountedAsCompleted: boolean;
}

// --- Programma di allenamento ---
export interface TrainingProgram {
  id: string;
  studentId: string;
  collaboratorId: string;
  createdAt: Date;
  title: string;
  description: string;
  exercises: Exercise[];
  sessionNumber: number;
  progressNotes: string;
}

// --- Esercizio ---
export interface Exercise {
  id: string;
  name: string;
  description: string;
  sets: number;
  reps: string; // "12" o "8-12" o "AMRAP"
  restSeconds: number;
  videoUrl?: string;
  imageUrl?: string;
  notes: string;
  category: ExerciseCategory;
}

export type ExerciseCategory =
  | 'forza'
  | 'cardio'
  | 'mobilita'
  | 'stretching'
  | 'funzionale'
  | 'posturale'
  | 'altro';

// --- Programmazione (piano settimanale/mensile) ---
export interface WorkoutPlan {
  id: string;
  studentId: string;
  collaboratorId: string;
  title: string;
  startDate: Date;
  endDate: Date;
  weeklySchedule: WeeklyDay[];
  createdAt: Date;
  isActive: boolean;
}

export interface WeeklyDay {
  dayOfWeek: number; // 0=Lunedì, 6=Domenica
  exercises: Exercise[];
  notes: string;
}

// --- Pagamenti e rate ---
export type PaymentType = 'full' | 'installment';
export type PaymentStatus = 'pending' | 'paid' | 'overdue';

export interface PaymentPlan {
  id: string;
  studentId: string;
  collaboratorId: string;
  totalAmount: number;
  paymentType: PaymentType;
  installments: Installment[];
  createdAt: Date;
}

export interface Installment {
  id: string;
  amount: number;
  dueDate: Date;
  paidDate?: Date;
  status: PaymentStatus;
}

// --- Calcolo commissione collaboratore ---
export interface CollaboratorEarning {
  collaboratorId: string;
  paymentPlanId: string;
  studentId: string;
  totalPaid: number;
  collaboratorShare: number; // Percentuale del collaboratore
  ownerShare: number;        // Da versare al titolare
  period: string;            // "2026-03"
}

// --- Sezione economica del titolare ---
export type TransactionType = 'income' | 'expense';
export type TransactionCategory =
  | 'collaborator_payment'
  | 'student_payment'
  | 'rent'
  | 'equipment'
  | 'marketing'
  | 'insurance'
  | 'utilities'
  | 'other';

export interface FinancialTransaction {
  id: string;
  type: TransactionType;
  category: TransactionCategory;
  amount: number;
  description: string;
  date: Date;
  collaboratorId?: string;
  studentId?: string;
  receiptUrl?: string;
}

// --- Chat ---
export interface ChatRoom {
  id: string;
  participants: string[]; // user IDs
  type: 'direct' | 'group';
  createdAt: Date;
  lastMessage?: ChatMessage;
  studentId: string;
  collaboratorId: string;
}

export interface ChatMessage {
  id: string;
  chatRoomId: string;
  senderId: string;
  senderName: string;
  text: string;
  timestamp: Date;
  isAnonymousOwner: boolean; // Se il titolare sta leggendo in anonimo
  readBy: string[];
  attachmentUrl?: string;
}

// --- Contenuti speciali ---
export type ContentType = 'podcast' | 'video' | 'article' | 'resource';

export interface SpecialContent {
  id: string;
  title: string;
  description: string;
  type: ContentType;
  url: string;
  thumbnailUrl?: string;
  createdBy: string; // user ID
  createdAt: Date;
  assignedTo: string[]; // student IDs (vuoto = tutti)
  tags: string[];
}

// --- Diario allievo ---
export interface DiaryEntry {
  id: string;
  studentId: string;
  date: Date;
  content: string;
  mood?: 'great' | 'good' | 'ok' | 'tired' | 'bad';
  painLevel?: number; // 0-10
  createdAt: Date;
}

// --- Test posturale ---
export interface PosturalAssessment {
  id: string;
  studentId: string;
  assessorId: string; // chi fa la valutazione
  date: Date;
  frontImageUrl: string;
  sideImageUrl: string;
  backImageUrl: string;
  findings: PosturalFinding[];
  overallNotes: string;
  recommendations: string;
}

export interface PosturalFinding {
  area: PosturalArea;
  observation: string;
  severity: 'normal' | 'mild' | 'moderate' | 'severe';
  imageAnnotations?: ImageAnnotation[];
}

export type PosturalArea =
  | 'head_neck'
  | 'shoulders'
  | 'upper_back'
  | 'lower_back'
  | 'pelvis'
  | 'knees'
  | 'ankles_feet'
  | 'spine_alignment';

export interface ImageAnnotation {
  x: number;
  y: number;
  label: string;
  color: string;
}

// --- Consulenza nutrizionale ---
export interface NutritionalConsultation {
  id: string;
  studentId: string;
  collaboratorId: string;
  date: Date;
  notes: string;
  recommendations: string;
  nextAppointment?: Date;
}

// --- Notifiche ---
export type NotificationType =
  | 'payment_due'
  | 'session_reminder'
  | 'new_program'
  | 'new_message'
  | 'session_cancelled'
  | 'new_content';

export interface AppNotification {
  id: string;
  userId: string;
  type: NotificationType;
  title: string;
  body: string;
  data?: Record<string, string>;
  read: boolean;
  createdAt: Date;
}

// --- Navigation types ---
export type RootStackParamList = {
  Login: undefined;
  Register: undefined;
  OwnerTabs: undefined;
  ManagerTabs: undefined;
  CollaboratorTabs: undefined;
  StudentTabs: undefined;
};

export type OwnerTabParamList = {
  Dashboard: undefined;
  Team: undefined;
  Sessions: undefined;
  Financial: undefined;
  Content: undefined;
  Chat: undefined;
};

export type ManagerTabParamList = {
  Dashboard: undefined;
  Team: undefined;
  Sessions: undefined;
  Content: undefined;
  Chat: undefined;
};

export type CollaboratorTabParamList = {
  MyStudents: undefined;
  Schedule: undefined;
  Programs: undefined;
  Postura: undefined;
  Earnings: undefined;
  Chat: undefined;
};

export type StudentTabParamList = {
  MyProgram: undefined;
  Sessions: undefined;
  Diary: undefined;
  Payments: undefined;
  Content: undefined;
  Chat: undefined;
};
