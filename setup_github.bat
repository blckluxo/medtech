@echo off
echo ============================================
echo    MEDTECH - CONFIGURACION DE GITHUB
echo ============================================
echo.

echo [1/6] Verificando Git...
git --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Git no está instalado
    echo.
    echo 📥 INSTALANDO GIT...
    echo Por favor, descarga e instala Git desde:
    echo https://git-scm.com/download/windows
    echo.
    echo Después de instalar Git, ejecuta este script nuevamente.
    pause
    exit /b 1
) else (
    echo ✅ Git está instalado
)

echo.
echo [2/6] Inicializando repositorio Git...
git init
if %errorlevel% neq 0 (
    echo ❌ Error al inicializar repositorio
    pause
    exit /b 1
)

echo.
echo [3/6] Configurando usuario Git (si es necesario)...
set /p nombre="Ingresa tu nombre para Git: "
set /p email="Ingresa tu email para Git: "
git config user.name "%nombre%"
git config user.email "%email%"

echo.
echo [4/6] Agregando archivos al repositorio...
git add .
if %errorlevel% neq 0 (
    echo ❌ Error al agregar archivos
    pause
    exit /b 1
)

echo.
echo [5/6] Creando commit inicial...
git commit -m "🚀 Initial commit: MedTech v1.0 - Sistema de Gestión de Citas Médicas"
if %errorlevel% neq 0 (
    echo ❌ Error al crear commit
    pause
    exit /b 1
)

echo.
echo [6/6] ✅ Repositorio Git configurado exitosamente!
echo.
echo 📋 PRÓXIMOS PASOS:
echo.
echo 1. Ve a GitHub.com y crea un nuevo repositorio llamado 'medtech'
echo 2. Copia la URL del repositorio (ej: https://github.com/tu-usuario/medtech.git)
echo 3. Ejecuta estos comandos en la terminal:
echo.
echo    git remote add origin https://github.com/TU-USUARIO/medtech.git
echo    git branch -M main
echo    git push -u origin main
echo.
echo ¡Tu proyecto estará disponible en GitHub!
echo.
pause