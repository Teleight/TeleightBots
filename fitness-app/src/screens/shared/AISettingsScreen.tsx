import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { colors, spacing, fontSize, borderRadius } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { InputField } from '../../components/common/InputField';
import { setAIApiKey, getAIApiKey } from '../../services/aiService';

const AI_KEY_STORAGE = '@essère_ai_key';

export const AISettingsScreen: React.FC = () => {
  const [apiKey, setApiKey] = useState('');
  const [saved, setSaved] = useState(false);

  useEffect(() => {
    AsyncStorage.getItem(AI_KEY_STORAGE).then((key) => {
      if (key) {
        setApiKey(key);
        setAIApiKey(key);
        setSaved(true);
      }
    });
  }, []);

  const handleSave = async () => {
    if (!apiKey.startsWith('sk-ant-') && !apiKey.startsWith('sk-')) {
      Alert.alert('Formato non valido', 'La chiave API Anthropic deve iniziare con "sk-ant-" o "sk-"');
      return;
    }
    try {
      await AsyncStorage.setItem(AI_KEY_STORAGE, apiKey);
      setAIApiKey(apiKey);
      setSaved(true);
      Alert.alert('Salvato', 'Chiave API configurata con successo!');
    } catch {
      Alert.alert('Errore', 'Impossibile salvare la chiave');
    }
  };

  const handleRemove = async () => {
    await AsyncStorage.removeItem(AI_KEY_STORAGE);
    setAIApiKey('');
    setApiKey('');
    setSaved(false);
    Alert.alert('Rimossa', 'Chiave API rimossa');
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Impostazioni AI</Text>
        <Text style={styles.subtitle}>
          Configura l'intelligenza artificiale per analisi posturale e progressioni
        </Text>
      </View>

      <View style={styles.content}>
        <Card variant="elevated">
          <Text style={styles.cardTitle}>Claude AI (Anthropic)</Text>
          <Text style={styles.cardDesc}>
            L'AI viene utilizzata per:
          </Text>
          <Text style={styles.featureItem}>
            {'\u2022'} Analisi posturale dalle foto (visione)
          </Text>
          <Text style={styles.featureItem}>
            {'\u2022'} Suggerimenti progressioni allenamento
          </Text>
          <Text style={styles.featureItem}>
            {'\u2022'} Raccomandazioni esercizi personalizzati
          </Text>
        </Card>

        <InputField
          label="Chiave API Anthropic"
          value={apiKey}
          onChangeText={(text) => {
            setApiKey(text);
            setSaved(false);
          }}
          placeholder="sk-ant-..."
          secureTextEntry
          autoCapitalize="none"
          autoCorrect={false}
        />

        <View style={styles.buttonRow}>
          <Button
            title={saved ? 'Aggiorna' : 'Salva Chiave'}
            onPress={handleSave}
            style={styles.btn}
          />
          {saved && (
            <Button
              title="Rimuovi"
              onPress={handleRemove}
              variant="outline"
              style={styles.btn}
            />
          )}
        </View>

        {saved && (
          <Card variant="outlined">
            <Text style={styles.statusOk}>
              Chiave API configurata. Le funzionalita' AI sono attive.
            </Text>
          </Card>
        )}

        <Card>
          <Text style={styles.warningTitle}>Nota sulla sicurezza</Text>
          <Text style={styles.warningText}>
            La chiave API viene salvata localmente sul dispositivo. In ambiente di produzione,
            si consiglia di utilizzare un backend proxy per proteggere la chiave.
          </Text>
        </Card>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.background,
  },
  header: {
    backgroundColor: colors.primary,
    padding: spacing.lg,
    paddingTop: spacing.xxl,
  },
  title: {
    fontSize: fontSize.xxl,
    fontWeight: '700',
    color: colors.textOnPrimary,
  },
  subtitle: {
    fontSize: fontSize.md,
    color: colors.textLight,
    marginTop: spacing.xs,
  },
  content: {
    padding: spacing.md,
  },
  cardTitle: {
    fontSize: fontSize.lg,
    fontWeight: '700',
    color: colors.accent,
    marginBottom: spacing.sm,
  },
  cardDesc: {
    fontSize: fontSize.md,
    color: colors.text,
    marginBottom: spacing.sm,
  },
  featureItem: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    lineHeight: 22,
    marginLeft: spacing.sm,
  },
  buttonRow: {
    flexDirection: 'row',
    gap: spacing.sm,
    marginBottom: spacing.md,
  },
  btn: {
    flex: 1,
  },
  statusOk: {
    color: colors.success,
    fontWeight: '600',
    fontSize: fontSize.md,
    textAlign: 'center',
  },
  warningTitle: {
    fontSize: fontSize.md,
    fontWeight: '700',
    color: colors.warning,
    marginBottom: spacing.xs,
  },
  warningText: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    lineHeight: 18,
  },
});
