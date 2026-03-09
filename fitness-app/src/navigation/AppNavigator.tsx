import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { View, Text, StyleSheet, ActivityIndicator } from 'react-native';
import { colors, fontSize } from '../config/theme';
import { useAuth } from '../hooks/useAuth';
import { RootStackParamList, OwnerTabParamList, CollaboratorTabParamList, StudentTabParamList } from '../types';

// Screens
import { LoginScreen } from '../screens/auth/LoginScreen';
import { DashboardScreen } from '../screens/owner/DashboardScreen';
import { FinancialScreen } from '../screens/owner/FinancialScreen';
import { MyStudentsScreen } from '../screens/collaborator/MyStudentsScreen';
import { EarningsScreen } from '../screens/collaborator/EarningsScreen';
import { MyProgramScreen } from '../screens/student/MyProgramScreen';
import { SessionsScreen } from '../screens/student/SessionsScreen';
import { DiaryScreen } from '../screens/student/DiaryScreen';
import { PaymentsScreen } from '../screens/student/PaymentsScreen';
import { ContentScreen } from '../screens/student/ContentScreen';
import { PosturalAssessmentScreen } from '../screens/shared/PosturalAssessmentScreen';
import { WorkoutPlanScreen } from '../screens/shared/WorkoutPlanScreen';
import { ManageUsersScreen } from '../screens/owner/ManageUsersScreen';

const RootStack = createNativeStackNavigator<RootStackParamList>();
const OwnerTab = createBottomTabNavigator<OwnerTabParamList>();
const CollaboratorTab = createBottomTabNavigator<CollaboratorTabParamList>();
const StudentTab = createBottomTabNavigator<StudentTabParamList>();

// --- Tab icon semplice (testo) ---
const TabIcon = ({ label, focused }: { label: string; focused: boolean }) => (
  <Text
    style={[
      styles.tabIcon,
      { color: focused ? colors.accent : colors.textLight },
    ]}
  >
    {label}
  </Text>
);

// --- Owner Tabs ---
// Il titolare ha accesso a TUTTO
const OwnerTabs = () => (
  <OwnerTab.Navigator
    screenOptions={{
      tabBarActiveTintColor: colors.accent,
      tabBarInactiveTintColor: colors.textLight,
      tabBarStyle: styles.tabBar,
      headerStyle: { backgroundColor: colors.primary },
      headerTintColor: colors.textOnPrimary,
    }}
  >
    <OwnerTab.Screen
      name="Dashboard"
      component={DashboardScreen}
      options={{
        headerShown: false,
        tabBarIcon: ({ focused }) => <TabIcon label="Home" focused={focused} />,
      }}
    />
    <OwnerTab.Screen
      name="Collaborators"
      component={ManageUsersScreen}
      options={{
        title: 'Gestione Utenti',
        headerShown: false,
        tabBarLabel: 'Team',
        tabBarIcon: ({ focused }) => <TabIcon label="Team" focused={focused} />,
      }}
    />
    <OwnerTab.Screen
      name="Students"
      component={PosturalAssessmentScreen}
      options={{
        title: 'Test Posturale',
        headerShown: false,
        tabBarLabel: 'Postura',
        tabBarIcon: ({ focused }) => <TabIcon label="Test" focused={focused} />,
      }}
    />
    <OwnerTab.Screen
      name="Financial"
      component={FinancialScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Economia',
        tabBarIcon: ({ focused }) => <TabIcon label="€" focused={focused} />,
      }}
    />
    <OwnerTab.Screen
      name="Chat"
      component={ChatPlaceholder}
      options={{
        headerShown: false,
        tabBarIcon: ({ focused }) => <TabIcon label="Chat" focused={focused} />,
      }}
    />
  </OwnerTab.Navigator>
);

// --- Collaborator Tabs ---
const CollaboratorTabs = () => (
  <CollaboratorTab.Navigator
    screenOptions={{
      tabBarActiveTintColor: colors.accent,
      tabBarInactiveTintColor: colors.textLight,
      tabBarStyle: styles.tabBar,
      headerStyle: { backgroundColor: colors.primary },
      headerTintColor: colors.textOnPrimary,
    }}
  >
    <CollaboratorTab.Screen
      name="MyStudents"
      component={MyStudentsScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Allievi',
        tabBarIcon: ({ focused }) => <TabIcon label="Allievi" focused={focused} />,
      }}
    />
    <CollaboratorTab.Screen
      name="Schedule"
      component={WorkoutPlanScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Programmi',
        tabBarIcon: ({ focused }) => <TabIcon label="Prog" focused={focused} />,
      }}
    />
    <CollaboratorTab.Screen
      name="Programs"
      component={PosturalAssessmentScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Postura',
        tabBarIcon: ({ focused }) => <TabIcon label="Test" focused={focused} />,
      }}
    />
    <CollaboratorTab.Screen
      name="Earnings"
      component={EarningsScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Guadagni',
        tabBarIcon: ({ focused }) => <TabIcon label="€" focused={focused} />,
      }}
    />
    <CollaboratorTab.Screen
      name="Chat"
      component={ChatPlaceholder}
      options={{
        headerShown: false,
        tabBarIcon: ({ focused }) => <TabIcon label="Chat" focused={focused} />,
      }}
    />
  </CollaboratorTab.Navigator>
);

// --- Student Tabs ---
const StudentTabs = () => (
  <StudentTab.Navigator
    screenOptions={{
      tabBarActiveTintColor: colors.accent,
      tabBarInactiveTintColor: colors.textLight,
      tabBarStyle: styles.tabBar,
      headerStyle: { backgroundColor: colors.primary },
      headerTintColor: colors.textOnPrimary,
    }}
  >
    <StudentTab.Screen
      name="MyProgram"
      component={MyProgramScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Programma',
        tabBarIcon: ({ focused }) => <TabIcon label="Allenamento" focused={focused} />,
      }}
    />
    <StudentTab.Screen
      name="Sessions"
      component={SessionsScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Sessioni',
        tabBarIcon: ({ focused }) => <TabIcon label="Sessioni" focused={focused} />,
      }}
    />
    <StudentTab.Screen
      name="Diary"
      component={DiaryScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Diario',
        tabBarIcon: ({ focused }) => <TabIcon label="Diario" focused={focused} />,
      }}
    />
    <StudentTab.Screen
      name="Payments"
      component={PaymentsScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Pagamenti',
        tabBarIcon: ({ focused }) => <TabIcon label="€" focused={focused} />,
      }}
    />
    <StudentTab.Screen
      name="Content"
      component={ContentScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Contenuti',
        tabBarIcon: ({ focused }) => <TabIcon label="Extra" focused={focused} />,
      }}
    />
    <StudentTab.Screen
      name="Chat"
      component={ChatPlaceholder}
      options={{
        headerShown: false,
        tabBarIcon: ({ focused }) => <TabIcon label="Chat" focused={focused} />,
      }}
    />
  </StudentTab.Navigator>
);

// Placeholder per la chat (integra ChatListScreen)
function ChatPlaceholder() {
  return (
    <View style={styles.placeholder}>
      <Text style={styles.placeholderText}>Chat</Text>
      <Text style={styles.placeholderSubtext}>
        La sezione chat si connette in tempo reale via Firebase
      </Text>
    </View>
  );
}

// --- Loading screen ---
const LoadingScreen = () => (
  <View style={styles.loading}>
    <ActivityIndicator size="large" color={colors.accent} />
    <Text style={styles.loadingText}>Caricamento...</Text>
  </View>
);

// --- Main Navigator ---
export const AppNavigator: React.FC = () => {
  const { isAuthenticated, loading, role } = useAuth();

  if (loading) {
    return <LoadingScreen />;
  }

  return (
    <NavigationContainer>
      <RootStack.Navigator screenOptions={{ headerShown: false }}>
        {!isAuthenticated ? (
          <RootStack.Screen name="Login" component={LoginScreen} />
        ) : role === 'owner' ? (
          <RootStack.Screen name="OwnerTabs" component={OwnerTabs} />
        ) : role === 'collaborator' ? (
          <RootStack.Screen
            name="CollaboratorTabs"
            component={CollaboratorTabs}
          />
        ) : (
          <RootStack.Screen name="StudentTabs" component={StudentTabs} />
        )}
      </RootStack.Navigator>
    </NavigationContainer>
  );
};

const styles = StyleSheet.create({
  tabBar: {
    backgroundColor: colors.surface,
    borderTopColor: colors.divider,
    borderTopWidth: 1,
    paddingTop: 4,
    height: 60,
  },
  tabIcon: {
    fontSize: fontSize.xs,
    fontWeight: '700',
  },
  placeholder: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: colors.background,
    padding: 40,
  },
  placeholderText: {
    fontSize: fontSize.xxl,
    fontWeight: '700',
    color: colors.text,
    marginBottom: 8,
  },
  placeholderSubtext: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    textAlign: 'center',
  },
  loading: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: colors.primary,
  },
  loadingText: {
    color: colors.textOnPrimary,
    fontSize: fontSize.md,
    marginTop: 16,
  },
});
