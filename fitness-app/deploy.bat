@echo off
echo ========================================
echo   ESSERE - Deploy Automatico
echo ========================================
echo.

echo [1/5] Aggiornamento codice...
cd /d "%~dp0"
cd ..
git pull origin claude/fitness-coaching-app-TAwci
cd fitness-app

echo.
echo [2/5] Installazione dipendenze...
call npm install --legacy-peer-deps

echo.
echo [3/5] Build web...
call npx expo export --platform web

echo.
echo [4/5] Login Firebase (se necessario)...
call npx firebase login --no-localhost

echo.
echo [5/5] Deploy su Firebase Hosting...
call npx firebase deploy --only hosting

echo.
echo ========================================
echo   DEPLOY COMPLETATO!
echo   App online su: https://essere-3fe6f.web.app
echo ========================================
echo.
pause
