# Module 4 — Wallet & Transactions
Build here:
- Wallet entity: id, userId(unique), balance(BigDecimal)  — use @Version for optimistic locking
- Transaction entity: id, walletId, type(TOPUP/DEBIT/REFUND), amount, reference(orderId), createdAt
- WalletController: GET /api/wallet (balance), POST /api/wallet/topup, GET /api/wallet/transactions
- WalletService — THE CONTRACT other modules call:
    void debit(Long userId, BigDecimal amount, String reference)   // throws BusinessRuleException if insufficient
    void refund(Long userId, BigDecimal amount, String reference)
  These run inside Module 3's transaction, so do NOT start a new transaction inside them.
