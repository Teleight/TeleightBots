import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
  KeyboardAvoidingView,
  Platform,
  TouchableOpacity,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { InputField } from '../../components/common/InputField';
import { Button } from '../../components/common/Button';
import { registerStudent, getCollaborators } from '../../services/authService';
import { Collaborator } from '../../types';

interface Props {
  onBack: () => void;
}

export const AddStudentScreen: React.FC<Props> = ({ onBack }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [phone, setPhone] = useState('');
  const [goals, setGoals] = useState('');
  const [medicalNotes, setMedicalNotes] = useState('');
  const [collaborators, setCollaborators] = useState<Collaborator[]>([]);
  const [selectedCollaboratorId, setSelectedCollaboratorId] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadCollaborators();
  }, []);

  const loadCollaborators = async () => {
    try {
      const collabs = await getCollaborators();
      setCollaborators(collabs);
      if (collabs.length > 0) {
        setSelectedCollaboratorId(collabs[0].id);
      }
    } catch {
      Alert.alert('Errore', 'Impossibile caricare i collaboratori');
    }
  };

  const handleRegister = async () => {
    if (!email.trim() || !password.trim() || !name.trim() || !surname.trim()) {
      Alert.alert('Errore', 'Compila tutti i campi obbligatori');
      return;
    }
    if (password.length < 6) {
      Alert.alert('Errore', 'La password deve essere di almeno 6 caratteri');
      return;
    }
    if (!selectedCollaboratorId) {
      Alert.alert('Errore', 'Seleziona un collaboratore da assegnare');
      return;
    }

    setLoading(true);
    try {
      await registerStudent(
        email.trim(),
        password,
        name.trim(),
        surname.trim(),
        phone.trim(),
        selectedCollaboratorId,
        goals.trim(),
        medicalNotes.trim() || undefined
      );
      Alert.alert('Successo', `Allievo ${name} ${surname} registrato!`, [
        { text: 'OK', onPress: onBack },
      ]);
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Errore durante la registrazione';
      Alert.alert('Errore', message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
    >
      <ScrollView contentContainerStyle={styles.scrollContent} keyboardShouldPersistTaps="handled">
        <View style={styles.header}>
          <TouchableOpacity onPress={onBack} style={styles.backButton}>
            <Text style={styles.backText}>← Indietro</Text>
          </TouchableOpacity>
          <Text style={styles.title}>Nuovo Allievo</Text>
          <Text style={styles.subtitle}>
            Crea le credenziali di accesso per l'allievo
          </Text>
        </View>

        <View style={styles.form}>
          <InputField
            label="Nome *"
            value={name}
            onChangeText={setName}
            placeholder="Nome dell'allievo"
          />
          <InputField
            label="Cognome *"
            value={surname}
            onChangeText={setSurname}
            placeholder="Cognome dell'allievo"
          />
          <InputField
            label="Email *"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
            autoCapitalize="none"
            placeholder="email@esempio.com"
          />
          <InputField
            label="Password *"
            value={password}
            onChangeText={setPassword}
            secureTextEntry
            placeholder="Minimo 6 caratteri"
          />
          <InputField
            label="Telefono"
            value={phone}
            onChangeText={setPhone}
            keyboardType="phone-pad"
            placeholder="Numero di telefono"
          />

          {/* Selezione collaboratore */}
          <Text style={styles.fieldLabel}>Collaboratore assegnato *</Text>
          {collaborators.length === 0 ? (
            <Text style={styles.noCollabText}>
              Nessun collaboratore disponibile. Registra prima un collaboratore.
            </Text>
          ) : (
            <View style={styles.collabList}>
              {collaborators.map((collab) => (
                <TouchableOpacity
                  key={collab.id}
                  style={[
                    styles.collabOption,
                    selectedCollaboratorId === collab.id && styles.collabOptionSelected,
                  ]}
                  onPress={() => setSelectedCollaboratorId(collab.id)}
                >
                  <Text
                    style={[
                      styles.collabOptionText,
                      selectedCollaboratorId === collab.id && styles.collabOptionTextSelected,
                    ]}
                  >
                    {collab.name} {collab.surname}
                  </Text>
                  <Text style={styles.collabSpecText}>
                    {collab.specializations.join(', ')}
                  </Text>
                </TouchableOpacity>
              ))}
            </View>
          )}

          <InputField
            label="Obiettivi"
            value={goals}
            onChangeText={setGoals}
            placeholder="Es: Dimagrimento, tonificazione, postura..."
            multiline
          />
          <InputField
            label="Note mediche"
            value={medicalNotes}
            onChangeText={setMedicalNotes}
            placeholder="Eventuali patologie, infortuni, allergie..."
            multiline
          />

          <Button
            title="Registra Allievo"
            onPress={handleRegister}
            loading={loading}
            style={styles.submitButton}
          />
        </View>
      </ScrollView>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background,
  },
  scrollContent: {
    flexGrow: 1,
    padding: spacing.lg,
  },
  header: {
    paddingTop: spacing.xxl,
    marginBottom: spacing.lg,
  },
  backButton: {
    marginBottom: spacing.md,
  },
  backText: {
    color: colors.accent,
    fontSize: fontSize.md,
    fontWeight: '600',
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
  form: {
    backgroundColor: colors.surface,
    borderRadius: borderRadius.xl,
    padding: spacing.lg,
    borderWidth: 1,
    borderColor: colors.border,
  },
  fieldLabel: {
    fontSize: fontSize.sm,
    fontWeight: '600',
    color: colors.textSecondary,
    marginBottom: spacing.xs,
    marginTop: spacing.sm,
    textTransform: 'uppercase',
    letterSpacing: 1,
  },
  noCollabText: {
    color: colors.warning,
    fontSize: fontSize.sm,
    padding: spacing.sm,
  },
  collabList: {
    gap: spacing.xs,
    marginBottom: spacing.sm,
  },
  collabOption: {
    padding: spacing.sm,
    borderRadius: borderRadius.md,
    borderWidth: 1,
    borderColor: colors.border,
    backgroundColor: colors.surfaceLight,
  },
  collabOptionSelected: {
    borderColor: colors.accent,
    backgroundColor: colors.primaryLight,
  },
  collabOptionText: {
    fontSize: fontSize.md,
    color: colors.text,
    fontWeight: '500',
  },
  collabOptionTextSelected: {
    color: colors.accent,
    fontWeight: '700',
  },
  collabSpecText: {
    fontSize: fontSize.xs,
    color: colors.textSecondary,
    marginTop: 2,
  },
  submitButton: {
    marginTop: spacing.lg,
  },
});
