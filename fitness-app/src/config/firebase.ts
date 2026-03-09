// Configurazione Firebase - Sostituire con le proprie credenziali
const firebaseConfig = {
  apiKey: 'AIzaSyDAuKlToc-_GRILEcMzwoD4ysuBYRtPzxE',
  authDomain: 'essere-3fe6f.firebaseapp.com',
  projectId: 'essere-3fe6f',
  storageBucket: 'essere-3fe6f.firebasestorage.app',
  messagingSenderId: '9504654070',
  appId: '1:9504654070:web:7b8c7d09645b113b0ea2d7',
  measurementId: 'G-QLVE494VJR',
};

let app: any = null;
let auth: any = null;
let db: any = null;
let storage: any = null;

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
