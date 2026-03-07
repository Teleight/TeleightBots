import { useState, useEffect, useCallback } from 'react';
import { onAuthStateChanged, User as FirebaseUser } from 'firebase/auth';
import { auth } from '../config/firebase';
import { User, UserRole } from '../types';
import { getUserProfile, signIn, signOut } from '../services/authService';

interface AuthState {
  firebaseUser: FirebaseUser | null;
  userProfile: User | null;
  loading: boolean;
  error: string | null;
}

export const useAuth = () => {
  const [state, setState] = useState<AuthState>({
    firebaseUser: null,
    userProfile: null,
    loading: true,
    error: null,
  });

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, async (fbUser) => {
      if (fbUser) {
        const profile = await getUserProfile(fbUser.uid);
        setState({
          firebaseUser: fbUser,
          userProfile: profile,
          loading: false,
          error: null,
        });
      } else {
        setState({
          firebaseUser: null,
          userProfile: null,
          loading: false,
          error: null,
        });
      }
    });

    return unsubscribe;
  }, []);

  const login = useCallback(async (email: string, password: string) => {
    setState((prev) => ({ ...prev, loading: true, error: null }));
    try {
      const profile = await signIn(email, password);
      setState((prev) => ({ ...prev, userProfile: profile, loading: false }));
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Errore di login';
      setState((prev) => ({ ...prev, loading: false, error: message }));
      throw err;
    }
  }, []);

  const logout = useCallback(async () => {
    await signOut();
    setState({
      firebaseUser: null,
      userProfile: null,
      loading: false,
      error: null,
    });
  }, []);

  return {
    user: state.userProfile,
    firebaseUser: state.firebaseUser,
    loading: state.loading,
    error: state.error,
    isAuthenticated: !!state.firebaseUser,
    role: state.userProfile?.role ?? null,
    isOwner: state.userProfile?.role === 'owner',
    isCollaborator: state.userProfile?.role === 'collaborator',
    isStudent: state.userProfile?.role === 'student',
    login,
    logout,
  };
};
