import { FirebaseApp } from 'firebase/app';
import { Auth } from 'firebase/auth';
import { Firestore } from 'firebase/firestore';
import { FirebaseStorage } from 'firebase/storage';

// Configurazione Firebase - Sostituire con le proprie credenziali
const firebaseConfig = {
  apiKey: 'YOUR_API_KEY',
  authDomain: 'YOUR_PROJECT.firebaseapp.com',
  projectId: 'YOUR_PROJECT_ID',
  storageBucket: 'YOUR_PROJECT.appspot.com',
  messagingSenderId: 'YOUR_SENDER_ID',
  appId: 'YOUR_APP_ID',
};

let app: FirebaseApp | null = null;
let auth: Auth | null = null;
let db: Firestore | null = null;
let storage: FirebaseStorage | null = null;

// Solo se le credenziali sono state configurate
const isConfigured = firebaseConfig.apiKey !== 'YOUR_API_KEY';

if (isConfigured) {
  try {
    const { initializeApp } = require('firebase/app');
    const { initializeAuth, getReactNativePersistence } = require('firebase/auth');
    const { getFirestore } = require('firebase/firestore');
    const { getStorage } = require('firebase/storage');
    const AsyncStorage = require('@react-native-async-storage/async-storage').default;

    app = initializeApp(firebaseConfig);
    auth = initializeAuth(app, {
      persistence: getReactNativePersistence(AsyncStorage),
    });
    db = getFirestore(app);
    storage = getStorage(app);
  } catch (e) {
    console.warn('Firebase initialization failed:', e);
  }
} else {
  console.log('Firebase: credenziali non configurate, modalità offline');
}

export { auth, db, storage };
export default app;
