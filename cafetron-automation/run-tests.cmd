@echo off
setlocal

set PROJECT_DIR=%~dp0
cd /d "%PROJECT_DIR%"

if "%~1"=="" (
  call "%PROJECT_DIR%mvnw.cmd" -o "-Dmaven.repo.local=%USERPROFILE%\.m2\repository" test
) else (
  call "%PROJECT_DIR%mvnw.cmd" -o "-Dmaven.repo.local=%USERPROFILE%\.m2\repository" %*
)

endlocal

