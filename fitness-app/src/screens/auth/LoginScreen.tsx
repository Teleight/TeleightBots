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
  Modal,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { InputField } from '../../components/common/InputField';
import { Button } from '../../components/common/Button';
import { useAuth } from '../../hooks/useAuth';
import { resetPassword } from '../../services/authService';
import { RegisterStudentScreen } from './RegisterStudentScreen';
import { EnsoLogo } from '../../components/common/EnsōLogo';

export const LoginScreen: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showRegister, setShowRegister] = useState(false);
  const [showForgotPassword, setShowForgotPassword] = useState(false);
  const [resetEmail, setResetEmail] = useState('');
  const [resetLoading, setResetLoading] = useState(false);
  const { login, loading, error } = useAuth();

  const handleLogin = async () => {
    if (!email.trim() || !password.trim()) {
      Alert.alert('Errore', 'Inserisci email e password');
      return;
    }
    try {
      await login(email.trim(), password);
    } catch {
      Alert.alert('Errore di accesso', 'Credenziali non valide. Riprova.');
    }
  };

  const handleResetPassword = async () => {
    const target = resetEmail.trim() || email.trim();
    if (!target) {
      Alert.alert('Errore', 'Inserisci la tua email per recuperare la password');
      return;
    }
    setResetLoading(true);
    try {
      await resetPassword(target);
      Alert.alert(
        'Email inviata',
        `Abbiamo inviato un link per reimpostare la password a ${target}. Controlla la tua casella di posta.`
      );
      setShowForgotPassword(false);
      setResetEmail('');
    } catch {
      Alert.alert(
        'Errore',
        'Impossibile inviare l\'email di recupero. Verifica che l\'indirizzo sia corretto.'
      );
    } finally {
      setResetLoading(false);
    }
  };

  if (showRegister) {
    return <RegisterStudentScreen onBack={() => setShowRegister(false)} />;
  }

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
          <EnsoLogo size={150} />
          <Text style={styles.title}>Essère</Text>
          <Text style={styles.subtitle}>Il tuo percorso di benessere</Text>
        </View>

        <View style={styles.form}>
          <InputField
            label="Email"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
            autoCapitalize="none"
            placeholder="la.tua@email.com"
          />

          <InputField
            label="Password"
            value={password}
            onChangeText={setPassword}
            secureTextEntry
            placeholder="La tua password"
          />

          {error && <Text style={styles.error}>{error}</Text>}

          <Button
            title="Accedi"
            onPress={handleLogin}
            loading={loading}
            style={styles.loginButton}
          />

          <TouchableOpacity
            onPress={() => {
              setResetEmail(email);
              setShowForgotPassword(true);
            }}
            style={styles.forgotLink}
          >
            <Text style={styles.forgotText}>Password dimenticata?</Text>
          </TouchableOpacity>
        </View>

        <TouchableOpacity
          onPress={() => setShowRegister(true)}
          style={styles.registerLink}
        >
          <Text style={styles.registerText}>Sei un nuovo allievo? Registrati</Text>
        </TouchableOpacity>

        <Text style={styles.footer}>
          Coach e Manager: contattare l'amministratore per le credenziali
        </Text>
      </ScrollView>

      {/* Modal Password Dimenticata */}
      <Modal
        visible={showForgotPassword}
        transparent
        animationType="fade"
        onRequestClose={() => setShowForgotPassword(false)}
      >
        <View style={styles.modalOverlay}>
          <View style={styles.modalContent}>
            <Text style={styles.modalTitle}>Recupera Password</Text>
            <Text style={styles.modalDescription}>
              Inserisci la tua email e ti invieremo un link per reimpostare la password.
            </Text>

            <InputField
              label="Email"
              value={resetEmail}
              onChangeText={setResetEmail}
              keyboardType="email-address"
              autoCapitalize="none"
              placeholder="la.tua@email.com"
            />

            <Button
              title="Invia link di recupero"
              onPress={handleResetPassword}
              loading={resetLoading}
              style={styles.resetButton}
            />

            <TouchableOpacity
              onPress={() => setShowForgotPassword(false)}
              style={styles.cancelLink}
            >
              <Text style={styles.cancelText}>Annulla</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
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
    marginBottom: spacing.xxl,
  },
  title: {
    fontSize: 32,
    fontWeight: '300',
    color: colors.textOnPrimary,
    letterSpacing: 6,
    marginTop: spacing.md,
    fontStyle: 'italic',
  },
  subtitle: {
    fontSize: fontSize.md,
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
  loginButton: {
    marginTop: spacing.md,
  },
  error: {
    color: colors.error,
    fontSize: fontSize.sm,
    textAlign: 'center',
    marginBottom: spacing.sm,
  },
  forgotLink: {
    alignItems: 'center',
    marginTop: spacing.md,
    padding: spacing.xs,
  },
  forgotText: {
    color: colors.textSecondary,
    fontSize: fontSize.sm,
    textDecorationLine: 'underline',
  },
  registerLink: {
    alignItems: 'center',
    marginTop: spacing.lg,
    padding: spacing.sm,
  },
  registerText: {
    color: colors.accent,
    fontSize: fontSize.md,
    fontWeight: '600',
  },
  footer: {
    color: colors.textLight,
    fontSize: fontSize.sm,
    textAlign: 'center',
    marginTop: spacing.sm,
  },
  // Modal styles
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.7)',
    justifyContent: 'center',
    alignItems: 'center',
    padding: spacing.xl,
  },
  modalContent: {
    backgroundColor: colors.surface,
    borderRadius: borderRadius.xl,
    padding: spacing.xl,
    width: '100%',
    maxWidth: 400,
    borderWidth: 1,
    borderColor: colors.border,
  },
  modalTitle: {
    fontSize: fontSize.xl,
    fontWeight: '700',
    color: colors.text,
    textAlign: 'center',
    marginBottom: spacing.sm,
  },
  modalDescription: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    textAlign: 'center',
    marginBottom: spacing.lg,
    lineHeight: 18,
  },
  resetButton: {
    marginTop: spacing.md,
  },
  cancelLink: {
    alignItems: 'center',
    marginTop: spacing.md,
    padding: spacing.sm,
  },
  cancelText: {
    color: colors.textSecondary,
    fontSize: fontSize.md,
  },
});
