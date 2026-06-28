@echo off
setlocal
cd /d "%~dp0"
call "%~dp0mvnw.cmd" -o "-Dmaven.repo.local=%USERPROFILE%\.m2\repository" test "-DsuiteXmlFile=src/test/resources/suites/evaluator.xml"
