import React, { useState } from 'react';
import {
  View,
  StyleSheet,
  ScrollView,
  Alert,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';
import { colors, spacing, borderRadius } from '../../config/theme';
import { InputField } from '../../components/common/InputField';
import { Button } from '../../components/common/Button';
import { ScreenHeader } from '../../components/common/ScreenHeader';
import { registerManager } from '../../services/authService';

interface Props {
  onBack: () => void;
}

export const AddManagerScreen: React.FC<Props> = ({ onBack }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [phone, setPhone] = useState('');
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

    setLoading(true);
    try {
      await registerManager(
        email.trim(),
        password,
        name.trim(),
        surname.trim(),
        phone.trim()
      );
      Alert.alert('Successo', `Manager ${name} ${surname} registrato!`, [
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
      <ScreenHeader
        title="Nuovo Manager"
        subtitle="Crea le credenziali di accesso per il manager"
        onBack={onBack}
      />
      <ScrollView contentContainerStyle={styles.scrollContent} keyboardShouldPersistTaps="handled">
        <View style={styles.form}>
          <InputField
            label="Nome *"
            value={name}
            onChangeText={setName}
            placeholder="Nome del manager"
          />
          <InputField
            label="Cognome *"
            value={surname}
            onChangeText={setSurname}
            placeholder="Cognome del manager"
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

          <Button
            title="Registra Manager"
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
