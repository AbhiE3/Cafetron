# Cafetron Automation

Hybrid Selenium Java TestNG automation framework for the Cafetron Angular UI.

## Stack

- Java 17
- Selenium WebDriver 4
- TestNG
- Maven
- Page Object Model
- Apache POI for Excel test data
- Extent Reports
- Selenium Manager for browser driver handling

## IDE

Use IntelliJ IDEA Community Edition. Open this `cafetron-automation` folder directly as its own Maven project, not the full Cafetron root folder and not the backend folder.

Recommended IntelliJ setup:

- File > Open > select `cafetron-automation`
- Use Maven Wrapper if IntelliJ asks which Maven to use
- Enable Maven offline mode if your corporate certificate blocks Maven Central
- Run `testng.xml`, a suite XML under `src/test/resources/suites`, or `run-tests.cmd`

## Local App URLs

The default configuration expects:

- Frontend: `http://localhost:4200`
- Backend API: `http://localhost:8081/api`

Change these in `src/test/resources/config.properties` or override with Maven properties.

## Run

This project has its own Maven Wrapper. From this folder, run:

```powershell
.\mvnw.cmd -o "-Dmaven.repo.local=$env:USERPROFILE\.m2\repository" test
```

Or use the helper script:

```powershell
.\run-tests.cmd
```

Run a specific suite:

```powershell
.\mvnw.cmd -o "-Dmaven.repo.local=$env:USERPROFILE\.m2\repository" test -DsuiteXmlFile=src/test/resources/suites/smoke.xml
```

Override browser or base URL:

```powershell
.\mvnw.cmd -o "-Dmaven.repo.local=$env:USERPROFILE\.m2\repository" test -Dbrowser=edge -DbaseUrl=http://localhost:4200
```

Run by TestNG group:

```powershell
.\mvnw.cmd -o "-Dmaven.repo.local=$env:USERPROFILE\.m2\repository" test -Dgroups=smoke
```

## Notes

- No Cafetron business test cases are included yet.
- Add future test classes under `src/test/java/tests/<type>`.
- Add locators and actions to page classes as test cases are written.
- Put Excel workbooks under `src/test/resources/testdata/`.
