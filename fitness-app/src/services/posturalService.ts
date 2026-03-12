import {
  collection,
  doc,
  addDoc,
  getDocs,
  deleteDoc,
  query,
  where,
  orderBy,
  Timestamp,
} from 'firebase/firestore';
import { ref, uploadBytes, getDownloadURL } from 'firebase/storage';
import { db, storage } from '../config/firebase';
import { PosturalAssessment, PosturalFinding, PosturalArea } from '../types';

const ASSESSMENTS_COLLECTION = 'posturalAssessments';

export const createAssessment = async (
  assessment: Omit<PosturalAssessment, 'id'>
): Promise<string> => {
  const docRef = await addDoc(collection(db, ASSESSMENTS_COLLECTION), {
    ...assessment,
    date: Timestamp.fromDate(assessment.date),
  });
  return docRef.id;
};

export const getStudentAssessments = async (
  studentId: string
): Promise<PosturalAssessment[]> => {
  const q = query(
    collection(db, ASSESSMENTS_COLLECTION),
    where('studentId', '==', studentId),
    orderBy('date', 'desc')
  );
  const snapshot = await getDocs(q);
  return snapshot.docs.map(
    (d) => ({ id: d.id, ...d.data() } as PosturalAssessment)
  );
};

export const uploadPosturalImage = async (
  studentId: string,
  imageUri: string,
  view: 'front' | 'side' | 'back'
): Promise<string> => {
  const timestamp = Date.now();
  const imageRef = ref(
    storage,
    `postural/${studentId}/${view}_${timestamp}.jpg`
  );

  const response = await fetch(imageUri);
  const blob = await response.blob();
  await uploadBytes(imageRef, blob);

  return getDownloadURL(imageRef);
};

export const deleteAssessment = async (assessmentId: string): Promise<void> => {
  await deleteDoc(doc(db, ASSESSMENTS_COLLECTION, assessmentId));
};

// Analisi posturale base tramite punti di riferimento
export const analyzePosture = (
  findings: PosturalFinding[]
): {
  summary: string;
  riskAreas: PosturalArea[];
  recommendations: string[];
} => {
  const riskAreas: PosturalArea[] = [];
  const recommendations: string[] = [];

  for (const finding of findings) {
    if (finding.severity === 'moderate' || finding.severity === 'severe') {
      riskAreas.push(finding.area);
    }
  }

  // Raccomandazioni base per area
  const areaRecommendations: Record<PosturalArea, string> = {
    head_neck: 'Esercizi di retrazione cervicale e stretching del trapezio superiore',
    shoulders: 'Rinforzo dei muscoli scapolari e stretching dei pettorali',
    upper_back: 'Esercizi di estensione toracica e mobilità',
    lower_back: 'Core stability e stretching dei flessori dell\'anca',
    pelvis: 'Esercizi di allineamento pelvico e rinforzo glutei',
    knees: 'Rinforzo del quadricipite e propriocezione',
    ankles_feet: 'Esercizi di mobilità della caviglia e rinforzo intrinseci del piede',
    spine_alignment: 'Programma posturale globale con focus sull\'allineamento',
  };

  for (const area of riskAreas) {
    recommendations.push(areaRecommendations[area]);
  }

  const severeCount = findings.filter((f) => f.severity === 'severe').length;
  const moderateCount = findings.filter((f) => f.severity === 'moderate').length;

  let summary = 'Valutazione posturale: ';
  if (severeCount > 0) {
    summary += `${severeCount} area/e con problematiche importanti. `;
  }
  if (moderateCount > 0) {
    summary += `${moderateCount} area/e con problematiche moderate. `;
  }
  if (severeCount === 0 && moderateCount === 0) {
    summary += 'Postura nella norma con eventuali lievi compensi.';
  } else {
    summary += 'Si consiglia un programma correttivo mirato.';
  }

  return { summary, riskAreas, recommendations };
};
