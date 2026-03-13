// ============================================================
// ESSĒRE - AI SERVICE (Claude Anthropic API)
// Analisi posturale con visione + Progressioni allenamento
// ============================================================

import { PosturalFinding, Exercise, WorkoutPlan } from '../types';

// La chiave API va impostata in config. In produzione usare un backend proxy.
// MAI esporre la chiave in un'app client in produzione.
const API_URL = 'https://api.anthropic.com/v1/messages';

let API_KEY = '';

export const setAIApiKey = (key: string) => {
  API_KEY = key;
};

export const getAIApiKey = () => API_KEY;

// --- Helper per chiamata Claude ---
const callClaude = async (
  messages: Array<{ role: string; content: any }>,
  systemPrompt: string,
  maxTokens: number = 2000
): Promise<string> => {
  if (!API_KEY) {
    throw new Error('API key Anthropic non configurata. Vai in Impostazioni AI per inserirla.');
  }

  if (!API_KEY.startsWith('sk-ant-') && !API_KEY.startsWith('sk-')) {
    throw new Error('Chiave API non valida. Deve iniziare con "sk-ant-" o "sk-". Controlla le impostazioni.');
  }

  let response: Response;
  try {
    response = await fetch(API_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'x-api-key': API_KEY,
        'anthropic-version': '2023-06-01',
        'anthropic-dangerous-direct-browser-access': 'true',
      },
      body: JSON.stringify({
        model: 'claude-sonnet-4-6',
        max_tokens: maxTokens,
        system: systemPrompt,
        messages,
      }),
    });
  } catch (networkError) {
    throw new Error(
      'Impossibile connettersi al server AI. Controlla la connessione internet e riprova.'
    );
  }

  if (!response.ok) {
    let errorBody = '';
    try {
      errorBody = await response.text();
    } catch {
      // ignore
    }

    if (response.status === 401) {
      throw new Error('Chiave API non valida o scaduta. Aggiorna la chiave nelle impostazioni AI.');
    }
    if (response.status === 429) {
      throw new Error('Troppe richieste. Attendi qualche secondo e riprova.');
    }
    if (response.status === 400) {
      // Check for common issues
      if (errorBody.includes('model')) {
        throw new Error('Modello AI non disponibile. Riprova più tardi.');
      }
      if (errorBody.includes('image') || errorBody.includes('base64')) {
        throw new Error('Errore nell\'invio delle immagini. Prova con foto più piccole o in formato JPEG.');
      }
      throw new Error(`Richiesta non valida: ${errorBody.substring(0, 200)}`);
    }
    if (response.status >= 500) {
      throw new Error('Il server AI è temporaneamente non disponibile. Riprova tra qualche minuto.');
    }
    throw new Error(`Errore API (${response.status}): ${errorBody.substring(0, 200)}`);
  }

  let data: any;
  try {
    data = await response.json();
  } catch {
    throw new Error('Risposta non valida dal server AI. Riprova.');
  }

  const text = data?.content?.[0]?.text;
  if (!text) {
    throw new Error('Il server AI ha restituito una risposta vuota. Riprova.');
  }

  return text;
};

// --- Converte immagine URI in base64 ---
const imageUriToBase64 = async (uri: string): Promise<string> => {
  // Se l'URI è già base64, estrarre i dati
  if (uri.startsWith('data:')) {
    const base64Part = uri.split(',')[1];
    if (base64Part) return base64Part;
    throw new Error('Formato immagine base64 non valido');
  }

  try {
    const response = await fetch(uri);
    if (!response.ok) {
      throw new Error(`Impossibile caricare l'immagine (${response.status})`);
    }
    const blob = await response.blob();

    // Controlla dimensione (max 20MB per l'API)
    if (blob.size > 20 * 1024 * 1024) {
      throw new Error('Immagine troppo grande. Usa foto con dimensioni inferiori a 20MB.');
    }

    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        const result = reader.result as string;
        const base64 = result.split(',')[1];
        if (!base64) {
          reject(new Error('Conversione immagine fallita'));
          return;
        }
        resolve(base64);
      };
      reader.onerror = () => reject(new Error('Errore nella lettura dell\'immagine'));
      reader.readAsDataURL(blob);
    });
  } catch (err) {
    if (err instanceof Error && err.message.includes('Immagine')) {
      throw err;
    }
    throw new Error('Impossibile elaborare l\'immagine. Riprova con un\'altra foto.');
  }
};

// ============================================================
// 1. ANALISI POSTURALE CON AI VISION
// ============================================================

export interface AIPosturalAnalysis {
  findings: Array<{
    area: string;
    observation: string;
    severity: 'normal' | 'mild' | 'moderate' | 'severe';
  }>;
  summary: string;
  recommendations: string[];
  exerciseProgram: string[];
}

export const analyzePostureWithAI = async (
  images: {
    front?: string;
    side?: string;
    back?: string;
  },
  manualFindings?: PosturalFinding[],
  studentInfo?: { name: string; goals: string; medicalNotes?: string }
): Promise<AIPosturalAnalysis> => {
  const systemPrompt = `Sei un esperto fisioterapista e posturologo italiano. Analizza le immagini posturali del paziente e fornisci una valutazione dettagliata.

RISPONDI SEMPRE in formato JSON valido con questa struttura:
{
  "findings": [
    {
      "area": "head_neck|shoulders|upper_back|lower_back|pelvis|knees|ankles_feet|spine_alignment",
      "observation": "descrizione dettagliata dell'osservazione in italiano",
      "severity": "normal|mild|moderate|severe"
    }
  ],
  "summary": "riassunto generale della valutazione posturale in italiano",
  "recommendations": ["raccomandazione 1", "raccomandazione 2", ...],
  "exerciseProgram": ["esercizio correttivo 1 con serie/reps", "esercizio 2", ...]
}

Analizza attentamente:
- Allineamento della testa e del collo
- Simmetria delle spalle
- Cifosi/lordosi toracica
- Lordosi/rettilineizzazione lombare
- Inclinazione del bacino (antiversione/retroversione)
- Valgismo/varismo delle ginocchia
- Appoggio dei piedi (pronazione/supinazione)
- Allineamento generale della colonna

Sii specifico e professionale. Suggerisci esercizi correttivi concreti con serie e ripetizioni.`;

  const content: any[] = [];

  // Aggiungi le immagini
  const imageEntries = Object.entries(images).filter(([_, uri]) => uri);
  for (const [view, uri] of imageEntries) {
    if (!uri) continue;
    try {
      const base64 = await imageUriToBase64(uri);
      const labels: Record<string, string> = {
        front: 'Vista frontale',
        side: 'Vista laterale',
        back: 'Vista posteriore',
      };
      content.push({
        type: 'text',
        text: `${labels[view]}:`,
      });
      content.push({
        type: 'image',
        source: {
          type: 'base64',
          media_type: 'image/jpeg',
          data: base64,
        },
      });
    } catch {
      // Skip image if conversion fails
    }
  }

  // Aggiungi contesto testuale
  let contextText = 'Analizza la postura di questo paziente basandoti sulle immagini fornite.';

  if (studentInfo) {
    contextText += `\n\nInformazioni paziente:
- Nome: ${studentInfo.name}
- Obiettivi: ${studentInfo.goals}`;
    if (studentInfo.medicalNotes) {
      contextText += `\n- Note mediche: ${studentInfo.medicalNotes}`;
    }
  }

  if (manualFindings && manualFindings.length > 0) {
    contextText += '\n\nOsservazioni manuali del valutatore:';
    for (const f of manualFindings) {
      contextText += `\n- ${f.area}: ${f.observation} (${f.severity})`;
    }
    contextText += '\n\nConferma o integra queste osservazioni con la tua analisi visiva.';
  }

  content.push({ type: 'text', text: contextText });

  if (content.length === 0) {
    throw new Error('Fornisci almeno un\'immagine per l\'analisi AI');
  }

  const responseText = await callClaude(
    [{ role: 'user', content }],
    systemPrompt,
    3000
  );

  // Parse JSON dalla risposta
  try {
    const jsonMatch = responseText.match(/\{[\s\S]*\}/);
    if (!jsonMatch) throw new Error('Risposta non valida');
    return JSON.parse(jsonMatch[0]);
  } catch {
    return {
      findings: [],
      summary: responseText,
      recommendations: ['Analisi completata - leggi il riassunto sopra'],
      exerciseProgram: [],
    };
  }
};

// ============================================================
// 2. AI PROGRESSIONI ALLENAMENTO
// ============================================================

export interface AIProgressionSuggestion {
  title: string;
  reasoning: string;
  weeklySchedule: Array<{
    day: string;
    exercises: Array<{
      name: string;
      sets: number;
      reps: string;
      restSeconds: number;
      notes: string;
      category: string;
    }>;
  }>;
  generalNotes: string;
}

export const suggestWorkoutProgression = async (
  currentPlan: {
    title: string;
    weeklySchedule: Array<{
      dayOfWeek: number;
      exercises: Exercise[];
    }>;
  },
  studentInfo: {
    name: string;
    goals: string;
    medicalNotes?: string;
  },
  weekNumber: number,
  posturalNotes?: string
): Promise<AIProgressionSuggestion> => {
  const days = ['Lunedì', 'Martedì', 'Mercoledì', 'Giovedì', 'Venerdì', 'Sabato', 'Domenica'];

  const systemPrompt = `Sei un preparatore atletico e personal trainer esperto italiano. Crea la progressione della scheda di allenamento.

RISPONDI SEMPRE in formato JSON valido con questa struttura:
{
  "title": "titolo della nuova scheda",
  "reasoning": "spiegazione delle modifiche apportate e perché",
  "weeklySchedule": [
    {
      "day": "Lunedì",
      "exercises": [
        {
          "name": "nome esercizio",
          "sets": 4,
          "reps": "8-10",
          "restSeconds": 90,
          "notes": "note sull'esecuzione",
          "category": "forza|cardio|mobilita|stretching|funzionale|posturale|altro"
        }
      ]
    }
  ],
  "generalNotes": "note generali sulla progressione e consigli"
}

Principi di progressione:
- Sovraccarico progressivo (aumento volume o intensità ogni 2-3 settimane)
- Periodizzazione (variare stimoli per evitare plateau)
- Considerare eventuali problemi posturali
- Inserire esercizi correttivi se necessario
- Mantenere equilibrio muscolare (agonisti/antagonisti)
- Rispettare i tempi di recupero`;

  let currentPlanDescription = `Scheda attuale: "${currentPlan.title}" (settimana ${weekNumber})\n\n`;
  for (const day of currentPlan.weeklySchedule) {
    if (day.exercises.length === 0) continue;
    currentPlanDescription += `${days[day.dayOfWeek]}:\n`;
    for (const ex of day.exercises) {
      currentPlanDescription += `  - ${ex.name}: ${ex.sets}x${ex.reps} (rec ${ex.restSeconds}s) [${ex.category}]`;
      if (ex.notes) currentPlanDescription += ` | Note: ${ex.notes}`;
      currentPlanDescription += '\n';
    }
    currentPlanDescription += '\n';
  }

  let prompt = `Crea la progressione per la settimana ${weekNumber + 1} di questo allievo.

Informazioni allievo:
- Nome: ${studentInfo.name}
- Obiettivi: ${studentInfo.goals}`;

  if (studentInfo.medicalNotes) {
    prompt += `\n- Note mediche: ${studentInfo.medicalNotes}`;
  }
  if (posturalNotes) {
    prompt += `\n- Note posturali: ${posturalNotes}`;
  }

  prompt += `\n\n${currentPlanDescription}`;
  prompt += `\nCrea la nuova scheda progressiva mantenendo la stessa struttura settimanale ma con progressioni appropriate.`;

  const responseText = await callClaude(
    [{ role: 'user', content: prompt }],
    systemPrompt,
    4000
  );

  try {
    const jsonMatch = responseText.match(/\{[\s\S]*\}/);
    if (!jsonMatch) throw new Error('Risposta non valida');
    return JSON.parse(jsonMatch[0]);
  } catch {
    return {
      title: `Progressione Settimana ${weekNumber + 1}`,
      reasoning: responseText,
      weeklySchedule: [],
      generalNotes: 'Generazione automatica non riuscita. Leggi il ragionamento sopra.',
    };
  }
};

// ============================================================
// 3. AI SUGGERIMENTO ESERCIZI
// ============================================================

export const suggestExercises = async (
  muscle: string,
  goal: string,
  equipment: string = 'palestra completa'
): Promise<Array<{
  name: string;
  sets: number;
  reps: string;
  restSeconds: number;
  description: string;
  category: string;
}>> => {
  const systemPrompt = `Sei un personal trainer esperto italiano. Suggerisci esercizi specifici.
RISPONDI in JSON array con questa struttura:
[{"name":"nome","sets":4,"reps":"8-12","restSeconds":90,"description":"come eseguirlo","category":"forza|cardio|mobilita|stretching|funzionale|posturale|altro"}]`;

  const responseText = await callClaude(
    [{ role: 'user', content: `Suggerisci 5 esercizi per ${muscle} con obiettivo ${goal}. Attrezzatura: ${equipment}.` }],
    systemPrompt,
    1500
  );

  try {
    const jsonMatch = responseText.match(/\[[\s\S]*\]/);
    if (!jsonMatch) throw new Error('Risposta non valida');
    return JSON.parse(jsonMatch[0]);
  } catch {
    return [];
  }
};
