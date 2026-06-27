# Module 6 — Admin Console: Reporting, Analytics & Operational Controls

## Reporting / Analytics (the substantial part)
Build here:
- ReportController (all @PreAuthorize("hasRole('ADMIN')")):
    GET /api/admin/reports/daily?date=YYYY-MM-DD   -> orders, revenue, items sold that day
    GET /api/admin/reports/top-items?limit=10       -> best sellers
    GET /api/admin/reports/range?from=&to=          -> revenue per day series (for the chart)
    GET /api/admin/reports/status-breakdown?date=   -> counts per order status
- ReportService: aggregation via JPQL GROUP BY using DTO projections (do NOT load full entities).
- DTOs/projections: DailySummary(date, orderCount, revenue, itemsSold),
                    TopItem(menuItemId, name, qtySold), RevenuePoint(date, revenue),
                    StatusCount(status, count)
- Reads from order/order_item/transaction tables (owned by Modules 3 & 4) by querying the DB,
  so you can build in parallel once their entities exist.
- Stretch: CSV export of any report.

## Operational controls (small, but you own the contract)
Build here:
- OrderingConfig entity (single row): windowOpen(boolean), cutoffTime(LocalTime)
- AdminController: POST /api/admin/window/toggle, PUT /api/admin/cutoff, GET /api/admin/config
- WindowService — CONTRACT consumed by Module 3 (no Quartz needed):
    boolean isOpen()           // reads windowOpen flag
    boolean isBeforeCutoff()   // LocalTime.now().isBefore(cutoffTime)
