# Module 5 — QR Pickup & Counter Scanner
Build here:
- QrTokenService: generate unique secure token on order confirm (UUID or HMAC); render PNG via ZXing
- PickupController:
    GET  /api/pickup/qr/{orderId}      -> QR image/string for the employee
    GET  /api/pickup/queue             -> pending/active orders; the counter screen POLLS this
                                          every few seconds (replaces the old WebSocket board)
    POST /api/pickup/verify            -> COUNTER scans token, validates state, marks COLLECTED
    POST /api/pickup/conflict/{orderId}-> COUNTER flags conflict -> WalletService.refund (Module 4)
                                          and sets order status CONFLICT
Frontend counter screen: poll /queue on a timer (e.g. RxJS interval + switchMap).
