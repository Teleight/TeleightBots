import React, { useState } from 'react';
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
import { registerCollaborator } from '../../services/authService';

interface Props {
  onBack: () => void;
}

export const AddCollaboratorScreen: React.FC<Props> = ({ onBack }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [phone, setPhone] = useState('');
  const [commission, setCommission] = useState('60');
  const [specializations, setSpecializations] = useState('');
  const [loading, setLoading] = useState(false);

  const handleRegister = async () => {
    if (!email.trim() || !password.trim() || !name.trim() || !surname.trim()) {
      Alert.alert('Errore', 'Compila tutti i campi obbligatori');
      return;
    }
    if (password.length < 6) {
      Alert.alert('Errore', 'La password deve essere di almeno 6 caratteri');
      return;
    }
    const commissionNum = parseInt(commission, 10);
    if (isNaN(commissionNum) || commissionNum < 0 || commissionNum > 100) {
      Alert.alert('Errore', 'La commissione deve essere un numero tra 0 e 100');
      return;
    }

    setLoading(true);
    try {
      const specs = specializations
        .split(',')
        .map((s) => s.trim())
        .filter((s) => s.length > 0);

      await registerCollaborator(
        email.trim(),
        password,
        name.trim(),
        surname.trim(),
        phone.trim(),
        commissionNum,
        specs
      );
      Alert.alert('Successo', `Collaboratore ${name} ${surname} registrato!`, [
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
          <Text style={styles.title}>Nuovo Collaboratore</Text>
          <Text style={styles.subtitle}>
            Crea le credenziali di accesso per il collaboratore
          </Text>
        </View>

        <View style={styles.form}>
          <InputField
            label="Nome *"
            value={name}
            onChangeText={setName}
            placeholder="Nome del collaboratore"
          />
          <InputField
            label="Cognome *"
            value={surname}
            onChangeText={setSurname}
            placeholder="Cognome del collaboratore"
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
          <InputField
            label="Commissione %"
            value={commission}
            onChangeText={setCommission}
            keyboardType="numeric"
            placeholder="Es: 60 (il collaboratore tiene il 60%)"
          />
          <InputField
            label="Specializzazioni"
            value={specializations}
            onChangeText={setSpecializations}
            placeholder="Posturale, Funzionale, Cardio (separati da virgola)"
          />

          <Button
            title="Registra Collaboratore"
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
  submitButton: {
    marginTop: spacing.lg,
  },
});
