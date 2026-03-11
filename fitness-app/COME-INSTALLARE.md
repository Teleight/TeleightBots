# ESSERE - Come installare l'app su iPhone (senza PC)

## Cosa ti serve

1. **Account Expo** (gratuito): vai su https://expo.dev e registrati
2. **Account Apple Developer** (99$/anno): necessario solo per pubblicare sull'App Store
3. **App "Expo Go"**: scaricala dall'App Store sul tuo iPhone

## Metodo 1: Build automatica con GitHub (consigliato)

### Setup iniziale (una sola volta)

1. Vai su https://expo.dev e crea un account
2. Vai su **Account Settings > Access Tokens** e crea un token
3. Vai nelle **Settings** del tuo repository GitHub
4. Vai su **Secrets and variables > Actions**
5. Aggiungi un nuovo secret chiamato `EXPO_TOKEN` con il token creato al punto 2

### Lanciare una build

1. Vai su GitHub > il tuo repository > tab **Actions**
2. Clicca su **"EAS Build iOS"** nella lista a sinistra
3. Clicca **"Run workflow"**
4. Scegli:
   - **Profile**: `preview` (per testare) o `production` (per l'App Store)
   - **Platform**: `ios`
5. Clicca **"Run workflow"**
6. Aspetta che la build finisca (circa 15-20 minuti)
7. Vai su https://expo.dev > il tuo progetto > **Builds**
8. Scarica e installa l'app dal link fornito

### Build automatica

Ogni volta che fai push sul branch `main` o `master` con modifiche nella cartella `fitness-app/`, la build parte automaticamente.

## Metodo 2: Preview con Expo Go (per testare velocemente)

Se qualcuno con un PC lancia `npx expo start`, puoi:
1. Aprire **Expo Go** sull'iPhone
2. Scansionare il QR code
3. L'app si apre direttamente

## Profili di build

| Profilo | Uso | Distribuzione |
|---------|-----|---------------|
| `development` | Per sviluppare con hot reload | Solo dispositivi registrati |
| `preview` | Per testare la versione completa | Solo dispositivi registrati |
| `production` | Per pubblicare sull'App Store | App Store |

## Registrare il tuo iPhone per le build "internal"

Per i profili `development` e `preview`, devi registrare il tuo dispositivo:

1. Vai su https://expo.dev > il tuo progetto
2. Vai su **Devices**
3. Segui le istruzioni per registrare il tuo iPhone (ti mandano un profilo da installare)

## Pubblicare sull'App Store

1. Modifica `eas.json` e inserisci i tuoi dati Apple:
   - `appleId`: la tua email Apple
   - `ascAppId`: l'ID dell'app su App Store Connect
   - `appleTeamId`: il tuo Team ID Apple Developer
2. Lancia una build con profilo `production`
3. Dopo la build, lancia il submit: `eas submit --platform ios`

## Supporto

Per problemi con le build, controlla i log su https://expo.dev > Builds.
