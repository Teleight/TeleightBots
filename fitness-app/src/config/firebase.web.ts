import { initializeApp, getApps } from 'firebase/app';
import { initializeAuth, browserLocalPersistence } from 'firebase/auth';
import { getFirestore } from 'firebase/firestore';
import { getStorage } from 'firebase/storage';

// Configurazione Firebase
const firebaseConfig = {
  apiKey: 'AIzaSyDAuKlToc-_GRILEcMzwoD4ysuBYRtPzxE',
  authDomain: 'essere-3fe6f.firebaseapp.com',
  projectId: 'essere-3fe6f',
  storageBucket: 'essere-3fe6f.firebasestorage.app',
  messagingSenderId: '9504654070',
  appId: '1:9504654070:web:7b8c7d09645b113b0ea2d7',
  measurementId: 'G-QLVE494VJR',
};

const app = getApps().length === 0 ? initializeApp(firebaseConfig) : getApps()[0];

const auth = initializeAuth(app, {
  persistence: browserLocalPersistence,
});

const db = getFirestore(app);
const storage = getStorage(app);

export { auth, db, storage };
export default app;
