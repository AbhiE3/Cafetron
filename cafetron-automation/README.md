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

## First UI Smoke Test

The first Cafetron UI test is:

```text
src/test/java/tests/smoke/LoginPageSmokeTest.java
```

It opens `/login` and verifies the login page is visible.

Before running it, start the Angular frontend from the main project:

```powershell
cd ..\frontend
npm start
```

Then run the smoke suite from `cafetron-automation`:

```powershell
.\mvnw.cmd -o "-Dmaven.repo.local=$env:USERPROFILE\.m2\repository" test -DsuiteXmlFile=src/test/resources/suites/smoke.xml
```

## Notes

- No Cafetron business test cases are included yet.
- Add future test classes under `src/test/java/tests/<type>`.
- Add locators and actions to page classes as test cases are written.
- Put Excel workbooks under `src/test/resources/testdata/`.


### Test plan

# Cafetron Evaluator Defect-Fix and Reduced Test Suite Plan

## Summary
For the June 29, 2026 evaluator demo, use a **49-test reduced evaluator suite** instead of all 99 tests.

Keep:
- **32 defect retest cases**: covers **100% of reported defects** `DF-003` to `DF-032`.
- **15 smoke passed cases**: proves the core app still works.
- **2 E2E passed cases**: proves full employee-to-vendor accept/decline flows.

This reduces the suite from **99 to 49 test cases** without losing defect coverage or testing-type coverage.

## Selected Test Cases
Keep these **passed confidence cases**:

`TC-001, TC-002, TC-003, TC-012, TC-016, TC-028, TC-031, TC-045, TC-047, TC-057, TC-060, TC-064, TC-067, TC-072, TC-073, TC-075, TC-076`

Keep these **defect retest cases**:

`TC-030, TC-042, TC-044, TC-053, TC-059, TC-062, TC-063, TC-065, TC-069, TC-077, TC-078, TC-079, TC-080, TC-081, TC-082, TC-083, TC-084, TC-085, TC-086, TC-087, TC-088, TC-089, TC-090, TC-091, TC-092, TC-093, TC-094, TC-095, TC-096, TC-097, TC-098, TC-099`

Exclude these **50 lower-priority/non-defect cases**:

`TC-004 to TC-011, TC-013 to TC-015, TC-017 to TC-027, TC-029, TC-032 to TC-041, TC-043, TC-046, TC-048 to TC-052, TC-054 to TC-056, TC-058, TC-061, TC-066, TC-068, TC-070 to TC-071, TC-074`

Reason: these excluded cases do not map to defects, are already covered by smoke/E2E flows, or are detailed validation checks that do not affect tomorrow’s defect evidence. `TC-008` should also stay excluded because public admin registration must be blocked after fixing `DF-018`.

## Implementation Changes
Fix defects by module, in this order:

- **Ordering, cutoff, stock**
    - Block order placement when admin closes ordering or cutoff has passed.
    - Add required validation for blank cutoff update.
    - Enforce stock limits in cart and checkout.
    - Covers `DF-003, DF-021, DF-022, DF-031, DF-032`.

- **Auth and role access**
    - Redirect logged-in users away from `/` and `/login` to their role dashboard.
    - Restrict employee pages to employee users only.
    - Block public admin self-registration.
    - Ensure logout is visible from authenticated shell pages.
    - Covers `DF-006, DF-007, DF-008, DF-009, DF-010, DF-018, DF-028`.

- **Vendor/menu/admin management**
    - Reject duplicate vendor menu item names.
    - Reject zero stock during menu item create/update, and show validation instead of logging out.
    - Hide unusable vendor delete action.
    - Fix admin menu filter, edit, delete, and dropdown readability.
    - Covers `DF-011` to `DF-017`, `DF-029`.

- **Admin vendor, wallet, QR, reports**
    - Add create-only `employeeId` and temporary `password` fields for admin-created vendors so they can log in.
    - Validate vendor phone format and length.
    - Add wallet top-up maximum, default `10000.00`.
    - Standardize app timestamps to `Asia/Kolkata`.
    - Fix QR upload verification result and scanner start feedback.
    - Fix dashboard CSV download.
    - Covers `DF-004, DF-005, DF-019, DF-020, DF-023` to `DF-027`, `DF-030`.

## Test Plan
- Update the Excel deliverable by adding an **Evaluator Suite** flag/sheet instead of deleting original rows.
- In Selenium/TestNG, implement the selected 49 cases only, using groups:
    - `smoke`, `e2e`, `defect`, `sanity`, `regression`, `rbac`, `integration`, `usability`, `uat`.
- Add one evaluator suite XML that runs:
    - all smoke cases,
    - both E2E cases,
    - all 32 defect retest cases.
- Final verification:
    - backend unit/integration tests,
    - frontend build,
    - evaluator TestNG suite,
    - manual spot-check for QR/camera/file-upload cases if browser permissions block automation.

## Assumptions
- “Reduce test cases” means create a selected evaluator suite, not permanently remove traceability from the original 99-case workbook.
- All 30 defects must be shown as fixed; `DF-020` and `DF-022` intentionally keep two retest cases each.
- Public users must not be able to create admin accounts; admin creation should be internal/admin-only.
- Local application time means `Asia/Kolkata`.
