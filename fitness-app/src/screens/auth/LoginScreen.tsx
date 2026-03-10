import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
  Alert,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { InputField } from '../../components/common/InputField';
import { Button } from '../../components/common/Button';
import { useAuth } from '../../hooks/useAuth';

export const LoginScreen: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
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
          {/* Cerchio Enso stilizzato */}
          <View style={styles.ensoContainer}>
            <View style={styles.enso} />
            <View style={styles.ensoWave} />
          </View>
          <Text style={styles.title}>ESSĒRE</Text>
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
        </View>

        <Text style={styles.footer}>
          Contatta l'amministratore per ottenere le credenziali
        </Text>
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
    marginBottom: spacing.xxl,
  },
  ensoContainer: {
    width: 120,
    height: 120,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: spacing.lg,
  },
  enso: {
    width: 100,
    height: 100,
    borderRadius: 50,
    borderWidth: 5,
    borderColor: colors.accent,
    backgroundColor: 'transparent',
  },
  ensoWave: {
    position: 'absolute',
    width: 40,
    height: 3,
    backgroundColor: colors.text,
    borderRadius: 2,
  },
  title: {
    fontSize: fontSize.hero,
    fontWeight: '300',
    color: colors.textOnPrimary,
    letterSpacing: 8,
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
  footer: {
    color: colors.textLight,
    fontSize: fontSize.sm,
    textAlign: 'center',
    marginTop: spacing.lg,
  },
});
