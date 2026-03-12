import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  Image,
  Alert,
  ActivityIndicator,
  Platform,
} from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { InputField } from '../../components/common/InputField';
import {
  PosturalAssessment,
  PosturalFinding,
  PosturalArea,
  Student,
} from '../../types';
import { uploadPosturalImage, analyzePosture, createAssessment, getStudentAssessments } from '../../services/posturalService';
import { analyzePostureWithAI, AIPosturalAnalysis, getAIApiKey } from '../../services/aiService';
import { useAuth } from '../../hooks/useAuth';
import { getStudents } from '../../services/authService';

const POSTURAL_AREAS: { value: PosturalArea; label: string }[] = [
  { value: 'head_neck', label: 'Testa/Collo' },
  { value: 'shoulders', label: 'Spalle' },
  { value: 'upper_back', label: 'Dorso' },
  { value: 'lower_back', label: 'Lombare' },
  { value: 'pelvis', label: 'Bacino' },
  { value: 'knees', label: 'Ginocchia' },
  { value: 'ankles_feet', label: 'Caviglie/Piedi' },
  { value: 'spine_alignment', label: 'Allineamento colonna' },
];

const SEVERITY_OPTIONS = [
  { value: 'normal' as const, label: 'Normale', color: colors.success },
  { value: 'mild' as const, label: 'Lieve', color: colors.info },
  { value: 'moderate' as const, label: 'Moderato', color: colors.warning },
  { value: 'severe' as const, label: 'Severo', color: colors.error },
];

export const PosturalAssessmentScreen: React.FC = () => {
  const { user, isOwner, isCollaborator } = useAuth();
  const [students, setStudents] = useState<Student[]>([]);
  const [selectedStudentId, setSelectedStudentId] = useState<string>('');
  const [frontImage, setFrontImage] = useState<string | null>(null);
  const [sideImage, setSideImage] = useState<string | null>(null);
  const [backImage, setBackImage] = useState<string | null>(null);
  const [findings, setFindings] = useState<PosturalFinding[]>([]);
  const [overallNotes, setOverallNotes] = useState('');
  const [selectedArea, setSelectedArea] = useState<PosturalArea | null>(null);
  const [currentObservation, setCurrentObservation] = useState('');
  const [currentSeverity, setCurrentSeverity] =
    useState<PosturalFinding['severity']>('normal');
  const [saving, setSaving] = useState(false);
  const [aiAnalyzing, setAiAnalyzing] = useState(false);
  const [aiResult, setAiResult] = useState<AIPosturalAnalysis | null>(null);
  const [previousAssessments, setPreviousAssessments] = useState<PosturalAssessment[]>([]);
  const [showComparison, setShowComparison] = useState(false);

  const loadStudents = useCallback(async () => {
    if (!user) return;
    try {
      const allStudents = await getStudents();
      if (isOwner) {
        setStudents(allStudents);
      } else if (isCollaborator) {
        setStudents(allStudents.filter((s) => s.assignedCollaboratorId === user.id));
      }
    } catch {
      // Silently handle
    }
  }, [user, isOwner, isCollaborator]);

  useEffect(() => {
    loadStudents();
  }, [loadStudents]);

  const pickImage = async (view: 'front' | 'side' | 'back') => {
    if (Platform.OS !== 'web') {
      const { status } = await ImagePicker.requestMediaLibraryPermissionsAsync();
      if (status !== 'granted') {
        Alert.alert('Permesso negato', 'Serve il permesso per accedere alla galleria');
        return;
      }
    }

    // On web, directly open image library (camera and Alert buttons don't work on web)
    if (Platform.OS === 'web') {
      const result = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ['images'],
        allowsEditing: true,
        quality: 0.8,
        base64: true,
      });
      if (!result.canceled && result.assets[0]) {
        setImageForView(view, result.assets[0].uri);
      }
      return;
    }

    Alert.alert(
      'Seleziona immagine',
      'Scegli da dove caricare l\'immagine',
      [
        {
          text: 'Fotocamera',
          onPress: async () => {
            const camStatus = await ImagePicker.requestCameraPermissionsAsync();
            if (camStatus.status !== 'granted') {
              Alert.alert('Permesso negato', 'Serve il permesso per usare la fotocamera');
              return;
            }
            const result = await ImagePicker.launchCameraAsync({
              mediaTypes: ['images'],
              allowsEditing: true,
              quality: 0.8,
            });
            if (!result.canceled && result.assets[0]) {
              setImageForView(view, result.assets[0].uri);
            }
          },
        },
        {
          text: 'Galleria',
          onPress: async () => {
            const result = await ImagePicker.launchImageLibraryAsync({
              mediaTypes: ['images'],
              allowsEditing: true,
              quality: 0.8,
            });
            if (!result.canceled && result.assets[0]) {
              setImageForView(view, result.assets[0].uri);
            }
          },
        },
        { text: 'Annulla', style: 'cancel' },
      ]
    );
  };

  const setImageForView = (view: 'front' | 'side' | 'back', uri: string) => {
    switch (view) {
      case 'front': setFrontImage(uri); break;
      case 'side': setSideImage(uri); break;
      case 'back': setBackImage(uri); break;
    }
  };

  // Carica valutazioni precedenti quando si seleziona un allievo
  useEffect(() => {
    if (selectedStudentId) {
      getStudentAssessments(selectedStudentId)
        .then(setPreviousAssessments)
        .catch(() => setPreviousAssessments([]));
    }
  }, [selectedStudentId]);

  // Analisi AI con visione
  const handleAIAnalysis = async () => {
    if (!frontImage && !sideImage && !backImage) {
      Alert.alert('Errore', 'Carica almeno una foto per l\'analisi AI');
      return;
    }
    if (!getAIApiKey()) {
      Alert.alert(
        'API Key mancante',
        'Inserisci la chiave API Anthropic nelle impostazioni per usare l\'analisi AI.'
      );
      return;
    }

    setAiAnalyzing(true);
    try {
      const student = students.find((s) => s.id === selectedStudentId);
      const result = await analyzePostureWithAI(
        {
          front: frontImage || undefined,
          side: sideImage || undefined,
          back: backImage || undefined,
        },
        findings.length > 0 ? findings : undefined,
        student ? { name: `${student.name} ${student.surname}`, goals: student.goals, medicalNotes: student.medicalNotes } : undefined
      );

      setAiResult(result);

      // Aggiungi automaticamente i findings dell'AI se non ci sono findings manuali
      if (findings.length === 0 && result.findings.length > 0) {
        const newFindings: PosturalFinding[] = result.findings.map((f) => ({
          area: f.area as PosturalArea,
          observation: f.observation,
          severity: f.severity,
        }));
        setFindings(newFindings);
      }

      Alert.alert('Analisi AI completata', result.summary);
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Errore durante l\'analisi AI';
      Alert.alert('Errore AI', message);
    } finally {
      setAiAnalyzing(false);
    }
  };

  const addFinding = () => {
    if (!selectedArea || !currentObservation.trim()) {
      Alert.alert('Errore', 'Seleziona un\'area e aggiungi un\'osservazione');
      return;
    }

    const newFinding: PosturalFinding = {
      area: selectedArea,
      observation: currentObservation,
      severity: currentSeverity,
    };

    setFindings([...findings, newFinding]);
    setSelectedArea(null);
    setCurrentObservation('');
    setCurrentSeverity('normal');
  };

  const removeFinding = (index: number) => {
    setFindings(findings.filter((_, i) => i !== index));
  };

  const handleAnalyze = () => {
    if (findings.length === 0) {
      Alert.alert('Errore', 'Aggiungi almeno un\'osservazione');
      return;
    }

    const analysis = analyzePosture(findings);
    Alert.alert(
      'Analisi Posturale',
      `${analysis.summary}\n\nRaccomandazioni:\n- ${analysis.recommendations.join('\n- ')}`,
      [{ text: 'OK' }]
    );
  };

  const handleSave = async () => {
    if (!user || findings.length === 0) {
      Alert.alert('Errore', 'Aggiungi almeno un\'osservazione');
      return;
    }
    if (!selectedStudentId) {
      Alert.alert('Errore', 'Seleziona un allievo');
      return;
    }

    setSaving(true);
    try {
      let frontUrl = frontImage || '';
      let sideUrl = sideImage || '';
      let backUrl = backImage || '';

      // Upload images if they are local/blob URIs (file:// on native, blob:/data: on web)
      const isLocalUri = (uri: string) =>
        uri.startsWith('file://') || uri.startsWith('blob:') || uri.startsWith('data:');

      if (frontImage && isLocalUri(frontImage)) {
        frontUrl = await uploadPosturalImage(selectedStudentId, frontImage, 'front');
      }
      if (sideImage && isLocalUri(sideImage)) {
        sideUrl = await uploadPosturalImage(selectedStudentId, sideImage, 'side');
      }
      if (backImage && isLocalUri(backImage)) {
        backUrl = await uploadPosturalImage(selectedStudentId, backImage, 'back');
      }

      await createAssessment({
        studentId: selectedStudentId,
        assessorId: user.id,
        date: new Date(),
        frontImageUrl: frontUrl,
        sideImageUrl: sideUrl,
        backImageUrl: backUrl,
        findings,
        overallNotes,
        recommendations: analyzePosture(findings).recommendations.join('\n'),
      });

      Alert.alert('Successo', 'Valutazione posturale salvata!');
      // Reset form
      setSelectedStudentId('');
      setFrontImage(null);
      setSideImage(null);
      setBackImage(null);
      setFindings([]);
      setOverallNotes('');
    } catch {
      Alert.alert('Errore', 'Impossibile salvare la valutazione');
    } finally {
      setSaving(false);
    }
  };

  const selectedStudent = students.find((s) => s.id === selectedStudentId);

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Test Posturale</Text>
        <Text style={styles.subtitle}>Analisi posturale con immagini</Text>
      </View>

      {/* Selezione allievo */}
      <Text style={styles.sectionTitle}>Allievo</Text>
      {students.length === 0 ? (
        <Card>
          <Text style={styles.emptyText}>Nessun allievo disponibile</Text>
        </Card>
      ) : (
        <ScrollView horizontal showsHorizontalScrollIndicator={false}>
          <View style={styles.studentRow}>
            {students.map((s) => (
              <TouchableOpacity
                key={s.id}
                style={[
                  styles.studentChip,
                  selectedStudentId === s.id && styles.studentChipActive,
                ]}
                onPress={() => setSelectedStudentId(s.id)}
              >
                <Text
                  style={[
                    styles.studentChipText,
                    selectedStudentId === s.id && styles.studentChipTextActive,
                  ]}
                >
                  {s.name} {s.surname}
                </Text>
              </TouchableOpacity>
            ))}
          </View>
        </ScrollView>
      )}

      {/* Sezione foto */}
      <Text style={styles.sectionTitle}>Fotografie</Text>
      <View style={styles.imageRow}>
        {(['front', 'side', 'back'] as const).map((view) => {
          const labels = { front: 'Frontale', side: 'Laterale', back: 'Posteriore' };
          const images = { front: frontImage, side: sideImage, back: backImage };
          return (
            <TouchableOpacity
              key={view}
              style={styles.imageBox}
              onPress={() => pickImage(view)}
            >
              {images[view] ? (
                <Image source={{ uri: images[view]! }} style={styles.image} />
              ) : (
                <View style={styles.imagePlaceholder}>
                  <Text style={styles.imagePlaceholderIcon}>+</Text>
                  <Text style={styles.imagePlaceholderText}>{labels[view]}</Text>
                  <Text style={styles.imagePlaceholderHint}>Tocca per caricare</Text>
                </View>
              )}
            </TouchableOpacity>
          );
        })}
      </View>

      {/* Osservazioni per area */}
      <Text style={styles.sectionTitle}>Osservazioni</Text>

      <Card variant="outlined">
        <Text style={styles.fieldLabel}>Area corporea</Text>
        <ScrollView horizontal showsHorizontalScrollIndicator={false}>
          <View style={styles.areaRow}>
            {POSTURAL_AREAS.map((area) => (
              <TouchableOpacity
                key={area.value}
                style={[
                  styles.areaChip,
                  selectedArea === area.value && styles.areaChipActive,
                ]}
                onPress={() => setSelectedArea(area.value)}
              >
                <Text
                  style={[
                    styles.areaChipText,
                    selectedArea === area.value && styles.areaChipTextActive,
                  ]}
                >
                  {area.label}
                </Text>
              </TouchableOpacity>
            ))}
          </View>
        </ScrollView>

        <Text style={styles.fieldLabel}>Severita</Text>
        <View style={styles.severityRow}>
          {SEVERITY_OPTIONS.map((opt) => (
            <TouchableOpacity
              key={opt.value}
              style={[
                styles.severityChip,
                currentSeverity === opt.value && {
                  backgroundColor: opt.color + '20',
                  borderColor: opt.color,
                },
              ]}
              onPress={() => setCurrentSeverity(opt.value)}
            >
              <View
                style={[styles.severityDot, { backgroundColor: opt.color }]}
              />
              <Text
                style={[
                  styles.severityText,
                  currentSeverity === opt.value && { color: opt.color },
                ]}
              >
                {opt.label}
              </Text>
            </TouchableOpacity>
          ))}
        </View>

        <InputField
          label="Osservazione"
          value={currentObservation}
          onChangeText={setCurrentObservation}
          placeholder="Descrivi cio che osservi..."
          multiline
          numberOfLines={3}
        />

        <Button
          title="Aggiungi Osservazione"
          onPress={addFinding}
          variant="secondary"
        />
      </Card>

      {/* Lista osservazioni */}
      {findings.length > 0 && (
        <>
          <Text style={styles.sectionTitle}>
            Osservazioni Registrate ({findings.length})
          </Text>
          {findings.map((finding, index) => {
            const areaLabel =
              POSTURAL_AREAS.find((a) => a.value === finding.area)?.label ?? '';
            const severityInfo =
              SEVERITY_OPTIONS.find((s) => s.value === finding.severity)!;

            return (
              <Card key={index} variant="outlined">
                <View style={styles.findingHeader}>
                  <View style={styles.findingInfo}>
                    <Text style={styles.findingArea}>{areaLabel}</Text>
                    <View
                      style={[
                        styles.severityBadge,
                        { backgroundColor: severityInfo.color + '20' },
                      ]}
                    >
                      <Text
                        style={[
                          styles.severityBadgeText,
                          { color: severityInfo.color },
                        ]}
                      >
                        {severityInfo.label}
                      </Text>
                    </View>
                  </View>
                  <TouchableOpacity onPress={() => removeFinding(index)}>
                    <Text style={styles.removeButton}>X</Text>
                  </TouchableOpacity>
                </View>
                <Text style={styles.findingObservation}>
                  {finding.observation}
                </Text>
              </Card>
            );
          })}
        </>
      )}

      {/* Note generali */}
      <InputField
        label="Note generali"
        value={overallNotes}
        onChangeText={setOverallNotes}
        placeholder="Note aggiuntive sulla valutazione..."
        multiline
        numberOfLines={4}
      />

      {/* Azioni */}
      <View style={styles.actionButtons}>
        <Button
          title="Analizza Postura"
          onPress={handleAnalyze}
          variant="secondary"
          style={styles.actionButton}
        />
        <Button
          title={aiAnalyzing ? 'Analisi AI...' : 'Analisi AI'}
          onPress={handleAIAnalysis}
          variant="primary"
          style={styles.actionButton}
          loading={aiAnalyzing}
          disabled={(!frontImage && !sideImage && !backImage) || aiAnalyzing}
        />
      </View>

      {aiAnalyzing && (
        <Card variant="outlined">
          <Text style={styles.aiLoadingText}>
            L'AI sta analizzando le immagini... Potrebbe richiedere fino a 30 secondi.
          </Text>
        </Card>
      )}

      <Button
        title={saving ? 'Salvataggio...' : 'Salva Valutazione'}
        onPress={handleSave}
        style={{ marginTop: spacing.sm }}
        loading={saving}
      />

      {/* Risultato AI */}
      {aiResult && (
        <>
          <Text style={styles.sectionTitle}>Risultato Analisi AI</Text>
          <Card variant="elevated">
            <Text style={styles.aiSummary}>{aiResult.summary}</Text>
          </Card>

          {aiResult.recommendations.length > 0 && (
            <Card variant="outlined">
              <Text style={styles.aiSubtitle}>Raccomandazioni</Text>
              {aiResult.recommendations.map((rec, i) => (
                <Text key={i} style={styles.aiListItem}>
                  {'\u2022'} {rec}
                </Text>
              ))}
            </Card>
          )}

          {aiResult.exerciseProgram.length > 0 && (
            <Card variant="outlined">
              <Text style={styles.aiSubtitle}>Programma Esercizi Correttivi</Text>
              {aiResult.exerciseProgram.map((ex, i) => (
                <Text key={i} style={styles.aiListItem}>
                  {i + 1}. {ex}
                </Text>
              ))}
            </Card>
          )}
        </>
      )}

      {/* Confronto con valutazioni precedenti */}
      {previousAssessments.length > 0 && selectedStudentId && (
        <>
          <TouchableOpacity
            onPress={() => setShowComparison(!showComparison)}
            style={styles.comparisonToggle}
          >
            <Text style={styles.sectionTitle}>
              Storico ({previousAssessments.length}) {showComparison ? '▼' : '▶'}
            </Text>
          </TouchableOpacity>

          {showComparison && previousAssessments.map((assessment, idx) => (
            <Card key={assessment.id || idx} variant="outlined">
              <Text style={styles.comparisonDate}>
                {new Date(assessment.date as unknown as string).toLocaleDateString('it-IT', {
                  day: 'numeric', month: 'long', year: 'numeric',
                })}
              </Text>
              <View style={styles.comparisonImages}>
                {assessment.frontImageUrl ? (
                  <Image source={{ uri: assessment.frontImageUrl }} style={styles.comparisonImage} />
                ) : null}
                {assessment.sideImageUrl ? (
                  <Image source={{ uri: assessment.sideImageUrl }} style={styles.comparisonImage} />
                ) : null}
                {assessment.backImageUrl ? (
                  <Image source={{ uri: assessment.backImageUrl }} style={styles.comparisonImage} />
                ) : null}
              </View>
              {assessment.findings.map((f, fi) => (
                <Text key={fi} style={styles.comparisonFinding}>
                  {'\u2022'} {POSTURAL_AREAS.find(a => a.value === f.area)?.label}: {f.observation} ({f.severity})
                </Text>
              ))}
              {assessment.recommendations && (
                <Text style={styles.comparisonRec}>
                  Note: {assessment.recommendations}
                </Text>
              )}
            </Card>
          ))}
        </>
      )}

      <View style={styles.bottomSpacer} />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background,
    padding: spacing.md,
  },
  header: {
    paddingTop: spacing.lg,
    marginBottom: spacing.lg,
  },
  title: {
    fontSize: fontSize.title,
    fontWeight: '700',
    color: colors.text,
  },
  subtitle: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    marginTop: spacing.xs,
  },
  sectionTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    marginTop: spacing.lg,
    marginBottom: spacing.sm,
  },
  studentRow: {
    flexDirection: 'row',
    gap: spacing.sm,
    paddingBottom: spacing.sm,
  },
  studentChip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    borderWidth: 1,
    borderColor: colors.border,
    backgroundColor: colors.surface,
  },
  studentChipActive: {
    backgroundColor: colors.accent,
    borderColor: colors.accent,
  },
  studentChipText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    fontWeight: '600',
  },
  studentChipTextActive: {
    color: colors.textOnAccent,
  },
  imageRow: {
    flexDirection: 'row',
    gap: spacing.sm,
  },
  imageBox: {
    flex: 1,
    aspectRatio: 0.7,
    borderRadius: borderRadius.lg,
    overflow: 'hidden',
    ...shadows.small,
  },
  image: {
    width: '100%',
    height: '100%',
  },
  imagePlaceholder: {
    flex: 1,
    backgroundColor: colors.surface,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 2,
    borderStyle: 'dashed',
    borderColor: colors.border,
    borderRadius: borderRadius.lg,
  },
  imagePlaceholderIcon: {
    fontSize: fontSize.hero,
    color: colors.accent,
    fontWeight: '300',
  },
  imagePlaceholderText: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.textSecondary,
    marginTop: spacing.xs,
  },
  imagePlaceholderHint: {
    fontSize: fontSize.xs,
    color: colors.textLight,
    marginTop: 4,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.md,
  },
  fieldLabel: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
    marginBottom: spacing.sm,
    marginTop: spacing.sm,
  },
  areaRow: {
    flexDirection: 'row',
    gap: spacing.sm,
    paddingBottom: spacing.sm,
  },
  areaChip: {
    paddingHorizontal: spacing.md,
    paddingVertical: spacing.sm,
    borderRadius: borderRadius.round,
    borderWidth: 1,
    borderColor: colors.border,
  },
  areaChipActive: {
    backgroundColor: colors.accent,
    borderColor: colors.accent,
  },
  areaChipText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
  },
  areaChipTextActive: {
    color: colors.textOnAccent,
    fontWeight: '600',
  },
  severityRow: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginBottom: spacing.md,
    flexWrap: 'wrap',
  },
  severityChip: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 6,
    paddingHorizontal: spacing.sm,
    paddingVertical: spacing.xs,
    borderRadius: borderRadius.round,
    borderWidth: 1,
    borderColor: colors.border,
  },
  severityDot: {
    width: 8,
    height: 8,
    borderRadius: 4,
  },
  severityText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
  },
  findingHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: spacing.xs,
  },
  findingInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: spacing.sm,
  },
  findingArea: {
    fontSize: fontSize.md,
    fontWeight: '600',
    color: colors.text,
  },
  severityBadge: {
    paddingHorizontal: spacing.sm,
    paddingVertical: 2,
    borderRadius: borderRadius.round,
  },
  severityBadgeText: {
    fontSize: fontSize.xs,
    fontWeight: '600',
  },
  removeButton: {
    color: colors.error,
    fontSize: fontSize.lg,
    fontWeight: '700',
    padding: spacing.xs,
  },
  findingObservation: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    lineHeight: 20,
  },
  actionButtons: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginTop: spacing.lg,
  },
  actionButton: {
    flex: 1,
  },
  aiLoadingText: {
    color: colors.accent,
    fontSize: fontSize.sm,
    textAlign: 'center',
    fontStyle: 'italic',
    padding: spacing.sm,
  },
  aiSummary: {
    fontSize: fontSize.md,
    color: colors.text,
    lineHeight: 20,
  },
  aiSubtitle: {
    fontSize: fontSize.lg,
    fontWeight: '700',
    color: colors.accent,
    marginBottom: spacing.sm,
  },
  aiListItem: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    lineHeight: 20,
    marginBottom: spacing.xs,
  },
  comparisonToggle: {
    marginTop: spacing.lg,
  },
  comparisonDate: {
    fontSize: fontSize.md,
    fontWeight: '700',
    color: colors.accent,
    marginBottom: spacing.sm,
  },
  comparisonImages: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginBottom: spacing.sm,
  },
  comparisonImage: {
    flex: 1,
    aspectRatio: 0.7,
    borderRadius: borderRadius.md,
  },
  comparisonFinding: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    lineHeight: 18,
    marginBottom: 2,
  },
  comparisonRec: {
    fontSize: fontSize.sm,
    color: colors.info,
    marginTop: spacing.xs,
    fontStyle: 'italic',
  },
  bottomSpacer: {
    height: spacing.xxl * 2,
  },
});
