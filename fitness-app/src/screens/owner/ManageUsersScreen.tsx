import React, { useState, useEffect, useCallback } from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  RefreshControl,
  TouchableOpacity,
} from 'react-native';
import { colors, spacing, fontSize, borderRadius, shadows } from '../../config/theme';
import { Card } from '../../components/common/Card';
import { Button } from '../../components/common/Button';
import { getCollaborators, getStudents, getManagers } from '../../services/authService';
import { Collaborator, Student, Manager } from '../../types';
import { AddCollaboratorScreen } from './AddCollaboratorScreen';
import { AddStudentScreen } from './AddStudentScreen';
import { AddManagerScreen } from './AddManagerScreen';

type ViewMode = 'list' | 'addManager' | 'addCollaborator' | 'addStudent';

export const ManageUsersScreen: React.FC = () => {
  const [viewMode, setViewMode] = useState<ViewMode>('list');
  const [managers, setManagers] = useState<Manager[]>([]);
  const [collaborators, setCollaborators] = useState<Collaborator[]>([]);
  const [students, setStudents] = useState<Student[]>([]);
  const [refreshing, setRefreshing] = useState(false);
  const [activeTab, setActiveTab] = useState<'managers' | 'collaborators' | 'students'>('collaborators');

  const loadData = useCallback(async () => {
    try {
      const [mgrs, collabs, studs] = await Promise.all([getManagers(), getCollaborators(), getStudents()]);
      setManagers(mgrs);
      setCollaborators(collabs);
      setStudents(studs);
    } catch {
      // Silently handle - data will show empty
    }
  }, []);

  useEffect(() => {
    loadData();
  }, [loadData]);

  const onRefresh = async () => {
    setRefreshing(true);
    await loadData();
    setRefreshing(false);
  };

  const handleBack = () => {
    setViewMode('list');
    loadData(); // Ricarica dati dopo aggiunta
  };

  if (viewMode === 'addManager') {
    return <AddManagerScreen onBack={handleBack} />;
  }

  if (viewMode === 'addCollaborator') {
    return <AddCollaboratorScreen onBack={handleBack} />;
  }

  if (viewMode === 'addStudent') {
    return <AddStudentScreen onBack={handleBack} />;
  }

  return (
    <ScrollView
      style={styles.container}
      refreshControl={<RefreshControl refreshing={refreshing} onRefresh={onRefresh} />}
    >
      <View style={styles.header}>
        <Text style={styles.title}>Gestione Utenti</Text>
        <Text style={styles.subtitle}>
          {managers.length} manager · {collaborators.length} coach · {students.length} allievi
        </Text>
      </View>

      {/* Pulsanti azione */}
      <View style={styles.actions}>
        <Button
          title="+ Manager"
          onPress={() => setViewMode('addManager')}
          style={styles.actionButton}
        />
        <Button
          title="+ Coach"
          onPress={() => setViewMode('addCollaborator')}
          style={styles.actionButton}
        />
        <Button
          title="+ Allievo"
          onPress={() => setViewMode('addStudent')}
          style={styles.actionButton}
        />
      </View>

      {/* Tab switch */}
      <View style={styles.tabSwitch}>
        <TouchableOpacity
          style={[styles.tab, activeTab === 'managers' && styles.tabActive]}
          onPress={() => setActiveTab('managers')}
        >
          <Text style={[styles.tabText, activeTab === 'managers' && styles.tabTextActive]}>
            Manager ({managers.length})
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[styles.tab, activeTab === 'collaborators' && styles.tabActive]}
          onPress={() => setActiveTab('collaborators')}
        >
          <Text style={[styles.tabText, activeTab === 'collaborators' && styles.tabTextActive]}>
            Coach ({collaborators.length})
          </Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={[styles.tab, activeTab === 'students' && styles.tabActive]}
          onPress={() => setActiveTab('students')}
        >
          <Text style={[styles.tabText, activeTab === 'students' && styles.tabTextActive]}>
            Allievi ({students.length})
          </Text>
        </TouchableOpacity>
      </View>

      {/* Lista manager */}
      {activeTab === 'managers' && (
        <>
          {managers.length === 0 ? (
            <Card>
              <Text style={styles.emptyText}>
                Nessun manager registrato. Premi "+ Manager" per aggiungerne uno.
              </Text>
            </Card>
          ) : (
            managers.map((mgr) => (
              <Card key={mgr.id} variant="elevated">
                <View style={styles.userRow}>
                  <View style={[styles.avatar, styles.avatarManager]}>
                    <Text style={styles.avatarText}>
                      {mgr.name[0]}{mgr.surname[0]}
                    </Text>
                  </View>
                  <View style={styles.userInfo}>
                    <Text style={styles.userName}>
                      {mgr.name} {mgr.surname}
                    </Text>
                    <Text style={styles.userEmail}>{mgr.email}</Text>
                    <Text style={styles.userDetail}>
                      {mgr.assignedCollaborators.length} coach · {mgr.assignedStudents.length} allievi
                    </Text>
                  </View>
                  <View style={[styles.statusDot, mgr.isActive ? styles.statusActive : styles.statusInactive]} />
                </View>
              </Card>
            ))
          )}
        </>
      )}

      {/* Lista collaboratori */}
      {activeTab === 'collaborators' && (
        <>
          {collaborators.length === 0 ? (
            <Card>
              <Text style={styles.emptyText}>
                Nessun collaboratore registrato. Premi "+ Collaboratore" per aggiungerne uno.
              </Text>
            </Card>
          ) : (
            collaborators.map((collab) => (
              <Card key={collab.id} variant="elevated">
                <View style={styles.userRow}>
                  <View style={styles.avatar}>
                    <Text style={styles.avatarText}>
                      {collab.name[0]}{collab.surname[0]}
                    </Text>
                  </View>
                  <View style={styles.userInfo}>
                    <Text style={styles.userName}>
                      {collab.name} {collab.surname}
                    </Text>
                    <Text style={styles.userEmail}>{collab.email}</Text>
                    <Text style={styles.userDetail}>
                      {collab.specializations.join(', ')} · {collab.commissionPercentage}% commissione
                    </Text>
                    <Text style={styles.userStudents}>
                      {collab.assignedStudents.length} allievi assegnati
                    </Text>
                  </View>
                  <View style={[styles.statusDot, collab.isActive ? styles.statusActive : styles.statusInactive]} />
                </View>
              </Card>
            ))
          )}
        </>
      )}

      {/* Lista allievi */}
      {activeTab === 'students' && (
        <>
          {students.length === 0 ? (
            <Card>
              <Text style={styles.emptyText}>
                Nessun allievo registrato. Premi "+ Allievo" per aggiungerne uno.
              </Text>
            </Card>
          ) : (
            students.map((student) => {
              const collab = collaborators.find((c) => c.id === student.assignedCollaboratorId);
              return (
                <Card key={student.id} variant="elevated">
                  <View style={styles.userRow}>
                    <View style={[styles.avatar, styles.avatarStudent]}>
                      <Text style={styles.avatarText}>
                        {student.name[0]}{student.surname[0]}
                      </Text>
                    </View>
                    <View style={styles.userInfo}>
                      <Text style={styles.userName}>
                        {student.name} {student.surname}
                      </Text>
                      <Text style={styles.userEmail}>{student.email}</Text>
                      {student.goals && (
                        <Text style={styles.userDetail}>Obiettivi: {student.goals}</Text>
                      )}
                      {collab && (
                        <Text style={styles.userCollab}>
                          Seguito da: {collab.name} {collab.surname}
                        </Text>
                      )}
                    </View>
                    <View style={[styles.statusDot, student.isActive ? styles.statusActive : styles.statusInactive]} />
                  </View>
                </Card>
              );
            })
          )}
        </>
      )}

      <View style={styles.bottomSpacer} />
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
    borderBottomLeftRadius: borderRadius.xl,
    borderBottomRightRadius: borderRadius.xl,
  },
  title: {
    fontSize: fontSize.title,
    fontWeight: '700',
    color: colors.textOnPrimary,
  },
  subtitle: {
    fontSize: fontSize.md,
    color: colors.textLight,
    marginTop: spacing.xs,
  },
  actions: {
    flexDirection: 'row',
    padding: spacing.md,
    gap: spacing.sm,
  },
  actionButton: {
    flex: 1,
  },
  tabSwitch: {
    flexDirection: 'row',
    marginHorizontal: spacing.md,
    backgroundColor: colors.surface,
    borderRadius: borderRadius.lg,
    padding: spacing.xs,
    ...shadows.small,
  },
  tab: {
    flex: 1,
    paddingVertical: spacing.sm,
    alignItems: 'center',
    borderRadius: borderRadius.md,
  },
  tabActive: {
    backgroundColor: colors.accent,
  },
  tabText: {
    fontSize: fontSize.md,
    color: colors.textSecondary,
    fontWeight: '500',
  },
  tabTextActive: {
    color: colors.textOnAccent,
    fontWeight: '700',
  },
  userRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  avatar: {
    width: 44,
    height: 44,
    borderRadius: 22,
    backgroundColor: colors.collaboratorBadge,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: spacing.md,
  },
  avatarManager: {
    backgroundColor: colors.managerBadge,
  },
  avatarStudent: {
    backgroundColor: colors.studentBadge,
  },
  avatarText: {
    color: '#FFFFFF',
    fontSize: fontSize.md,
    fontWeight: '700',
  },
  userInfo: {
    flex: 1,
  },
  userName: {
    fontSize: fontSize.lg,
    fontWeight: '600',
    color: colors.text,
  },
  userEmail: {
    fontSize: fontSize.sm,
    color: colors.textSecondary,
    marginTop: 1,
  },
  userDetail: {
    fontSize: fontSize.xs,
    color: colors.textLight,
    marginTop: 2,
  },
  userStudents: {
    fontSize: fontSize.xs,
    color: colors.collaboratorBadge,
    marginTop: 2,
    fontWeight: '500',
  },
  userCollab: {
    fontSize: fontSize.xs,
    color: colors.collaboratorBadge,
    marginTop: 2,
    fontWeight: '500',
  },
  statusDot: {
    width: 10,
    height: 10,
    borderRadius: 5,
  },
  statusActive: {
    backgroundColor: colors.success,
  },
  statusInactive: {
    backgroundColor: colors.error,
  },
  emptyText: {
    color: colors.textSecondary,
    textAlign: 'center',
    padding: spacing.md,
  },
  bottomSpacer: {
    height: spacing.xxl,
  },
});
