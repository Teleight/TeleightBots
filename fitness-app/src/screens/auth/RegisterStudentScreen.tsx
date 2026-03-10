import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
  Alert,
  TouchableOpacity,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { InputField } from '../../components/common/InputField';
import { Button } from '../../components/common/Button';
import { registerStudentSelf } from '../../services/authService';

interface Props {
  onBack: () => void;
}

export const RegisterStudentScreen: React.FC<Props> = ({ onBack }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [phone, setPhone] = useState('');
  const [goals, setGoals] = useState('');
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
    if (password !== confirmPassword) {
      Alert.alert('Errore', 'Le password non corrispondono');
      return;
    }

    setLoading(true);
    try {
      await registerStudentSelf(
        email.trim(),
        password,
        name.trim(),
        surname.trim(),
        phone.trim(),
        goals.trim()
      );
      // Dopo la registrazione l'utente e' automaticamente loggato via onAuthStateChanged
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
      <ScrollView
        contentContainerStyle={styles.scrollContent}
        keyboardShouldPersistTaps="handled"
      >
        <View style={styles.header}>
          <Text style={styles.title}>ESSĒRE</Text>
          <Text style={styles.subtitle}>Registrazione Allievo</Text>
        </View>

        <View style={styles.form}>
          <InputField
            label="Nome *"
            value={name}
            onChangeText={setName}
            placeholder="Il tuo nome"
          />
          <InputField
            label="Cognome *"
            value={surname}
            onChangeText={setSurname}
            placeholder="Il tuo cognome"
          />
          <InputField
            label="Email *"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
            autoCapitalize="none"
            placeholder="la.tua@email.com"
          />
          <InputField
            label="Password *"
            value={password}
            onChangeText={setPassword}
            secureTextEntry
            placeholder="Minimo 6 caratteri"
          />
          <InputField
            label="Conferma Password *"
            value={confirmPassword}
            onChangeText={setConfirmPassword}
            secureTextEntry
            placeholder="Ripeti la password"
          />
          <InputField
            label="Telefono"
            value={phone}
            onChangeText={setPhone}
            keyboardType="phone-pad"
            placeholder="Il tuo numero"
          />
          <InputField
            label="Obiettivi"
            value={goals}
            onChangeText={setGoals}
            placeholder="Es: Dimagrimento, tonificazione..."
            multiline
          />

          <Button
            title="Registrati"
            onPress={handleRegister}
            loading={loading}
            style={styles.registerButton}
          />
        </View>

        <TouchableOpacity onPress={onBack} style={styles.backLink}>
          <Text style={styles.backText}>Hai gia' un account? Accedi</Text>
        </TouchableOpacity>
      </ScrollView>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.primary,
  },
  scrollContent: {
    flexGrow: 1,
    justifyContent: 'center',
    padding: spacing.xl,
  },
  header: {
    alignItems: 'center',
    marginBottom: spacing.lg,
  },
  title: {
    fontSize: fontSize.hero,
    fontWeight: '300',
    color: colors.textOnPrimary,
    letterSpacing: 8,
  },
  subtitle: {
    fontSize: fontSize.lg,
    color: colors.textSecondary,
    marginTop: spacing.sm,
    letterSpacing: 2,
  },
  form: {
    backgroundColor: colors.surface,
    borderRadius: borderRadius.xl,
    padding: spacing.lg,
    borderWidth: 1,
    borderColor: colors.border,
  },
  registerButton: {
    marginTop: spacing.md,
  },
  backLink: {
    alignItems: 'center',
    marginTop: spacing.lg,
    padding: spacing.sm,
  },
  backText: {
    color: colors.accent,
    fontSize: fontSize.md,
    fontWeight: '600',
  },
});
