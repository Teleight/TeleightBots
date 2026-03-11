import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { View, Text, StyleSheet, ActivityIndicator } from 'react-native';
import { colors, fontSize } from '../config/theme';
import { useAuth } from '../hooks/useAuth';

// Screens
import { LoginScreen } from '../screens/auth/LoginScreen';
import { DashboardScreen } from '../screens/owner/DashboardScreen';
import { FinancialScreen } from '../screens/owner/FinancialScreen';
import { ManageUsersScreen } from '../screens/owner/ManageUsersScreen';
import { ContentManagementScreen } from '../screens/owner/ContentManagementScreen';
import { MyStudentsScreen } from '../screens/collaborator/MyStudentsScreen';
import { EarningsScreen } from '../screens/collaborator/EarningsScreen';
import { MyProgramScreen } from '../screens/student/MyProgramScreen';
import { SessionsScreen } from '../screens/student/SessionsScreen';
import { DiaryScreen } from '../screens/student/DiaryScreen';
import { PaymentsScreen } from '../screens/student/PaymentsScreen';
import { ContentScreen } from '../screens/student/ContentScreen';
import { PosturalAssessmentScreen } from '../screens/shared/PosturalAssessmentScreen';
import { WorkoutPlanScreen } from '../screens/shared/WorkoutPlanScreen';
import { ScheduleSessionScreen } from '../screens/shared/ScheduleSessionScreen';
import { ChatListScreen } from '../screens/shared/ChatListScreen';
import { AISettingsScreen } from '../screens/shared/AISettingsScreen';
import { AnalyticsScreen } from '../screens/owner/AnalyticsScreen';

const RootStack = createStackNavigator();
const OwnerTab = createBottomTabNavigator();
const ManagerTab = createBottomTabNavigator();
const CollaboratorTab = createBottomTabNavigator();
const StudentTab = createBottomTabNavigator();

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
      tabBarLabelStyle: styles.tabLabel,
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
      name="Team"
      component={ManageUsersScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Team',
        tabBarIcon: ({ focused }) => <TabIcon label="Team" focused={focused} />,
      }}
    />
    <OwnerTab.Screen
      name="Sessions"
      component={ScheduleSessionScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Sessioni',
        tabBarIcon: ({ focused }) => <TabIcon label="Cal" focused={focused} />,
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
      name="Analytics"
      component={AnalyticsScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'KPI',
        tabBarIcon: ({ focused }) => <TabIcon label="KPI" focused={focused} />,
      }}
    />
    <OwnerTab.Screen
      name="Content"
      component={ContentManagementScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Contenuti',
        tabBarIcon: ({ focused }) => <TabIcon label="Media" focused={focused} />,
      }}
    />
    <OwnerTab.Screen
      name="AI"
      component={AISettingsScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'AI',
        tabBarIcon: ({ focused }) => <TabIcon label="AI" focused={focused} />,
      }}
    />
    <OwnerTab.Screen
      name="Chat"
      component={ChatListScreen}
      options={{
        headerShown: false,
        tabBarIcon: ({ focused }) => <TabIcon label="Chat" focused={focused} />,
      }}
    />
  </OwnerTab.Navigator>
);

// --- Manager Tabs ---
// Il manager gestisce coach e allievi, senza sezione economia
const ManagerTabs = () => (
  <ManagerTab.Navigator
    screenOptions={{
      tabBarActiveTintColor: colors.accent,
      tabBarInactiveTintColor: colors.textLight,
      tabBarStyle: styles.tabBar,
      tabBarLabelStyle: styles.tabLabel,
      headerStyle: { backgroundColor: colors.primary },
      headerTintColor: colors.textOnPrimary,
    }}
  >
    <ManagerTab.Screen
      name="Dashboard"
      component={DashboardScreen}
      options={{
        headerShown: false,
        tabBarIcon: ({ focused }) => <TabIcon label="Home" focused={focused} />,
      }}
    />
    <ManagerTab.Screen
      name="Team"
      component={ManageUsersScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Team',
        tabBarIcon: ({ focused }) => <TabIcon label="Team" focused={focused} />,
      }}
    />
    <ManagerTab.Screen
      name="Sessions"
      component={ScheduleSessionScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Sessioni',
        tabBarIcon: ({ focused }) => <TabIcon label="Cal" focused={focused} />,
      }}
    />
    <ManagerTab.Screen
      name="Content"
      component={ContentManagementScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Contenuti',
        tabBarIcon: ({ focused }) => <TabIcon label="Media" focused={focused} />,
      }}
    />
    <ManagerTab.Screen
      name="Chat"
      component={ChatListScreen}
      options={{
        headerShown: false,
        tabBarIcon: ({ focused }) => <TabIcon label="Chat" focused={focused} />,
      }}
    />
  </ManagerTab.Navigator>
);

// --- Collaborator Tabs ---
const CollaboratorTabs = () => (
  <CollaboratorTab.Navigator
    screenOptions={{
      tabBarActiveTintColor: colors.accent,
      tabBarInactiveTintColor: colors.textLight,
      tabBarStyle: styles.tabBar,
      tabBarLabelStyle: styles.tabLabel,
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
      component={ScheduleSessionScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Sessioni',
        tabBarIcon: ({ focused }) => <TabIcon label="Cal" focused={focused} />,
      }}
    />
    <CollaboratorTab.Screen
      name="Programs"
      component={WorkoutPlanScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Programmi',
        tabBarIcon: ({ focused }) => <TabIcon label="Prog" focused={focused} />,
      }}
    />
    <CollaboratorTab.Screen
      name="Postura"
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
      name="AI"
      component={AISettingsScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'AI',
        tabBarIcon: ({ focused }) => <TabIcon label="AI" focused={focused} />,
      }}
    />
    <CollaboratorTab.Screen
      name="Chat"
      component={ChatListScreen}
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
      tabBarLabelStyle: styles.tabLabel,
      headerStyle: { backgroundColor: colors.primary },
      headerTintColor: colors.textOnPrimary,
    }}
  >
    <StudentTab.Screen
      name="MyProgram"
      component={MyProgramScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Scheda',
        tabBarIcon: ({ focused }) => <TabIcon label="Scheda" focused={focused} />,
      }}
    />
    <StudentTab.Screen
      name="Sessions"
      component={SessionsScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Sessioni',
        tabBarIcon: ({ focused }) => <TabIcon label="Sess." focused={focused} />,
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
        tabBarLabel: 'Paga',
        tabBarIcon: ({ focused }) => <TabIcon label="€" focused={focused} />,
      }}
    />
    <StudentTab.Screen
      name="Postura"
      component={PosturalAssessmentScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Postura',
        tabBarIcon: ({ focused }) => <TabIcon label="Post." focused={focused} />,
      }}
    />
    <StudentTab.Screen
      name="Content"
      component={ContentScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Extra',
        tabBarIcon: ({ focused }) => <TabIcon label="Extra" focused={focused} />,
      }}
    />
    <StudentTab.Screen
      name="Chat"
      component={ChatListScreen}
      options={{
        headerShown: false,
        tabBarLabel: 'Chat',
        tabBarIcon: ({ focused }) => <TabIcon label="Chat" focused={focused} />,
      }}
    />
  </StudentTab.Navigator>
);

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
        ) : role === 'manager' ? (
          <RootStack.Screen name="ManagerTabs" component={ManagerTabs} />
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
    paddingTop: 3,
    paddingBottom: 4,
    height: 56,
  },
  tabIcon: {
    fontSize: fontSize.sm,
    fontWeight: '700',
  },
  tabLabel: {
    fontSize: 10,
    fontWeight: '600',
    marginTop: 2,
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
