# Module 3 — Order (the heart of the system)
Build here:
- Order entity: id, userId, status(PLACED/LOCKED/COLLECTED/CANCELLED/CONFLICT),
  pickupSlot, qrToken, totalAmount, placedAt
- OrderItem entity: orderId, menuItemId, qty, unitPrice
- OrderService.placeOrder(...) annotated @Transactional:
    1. check window open + before cutoff   (WindowService, Module 6)
    2. lock + decrement stock per item      (MenuRepository.findByIdForUpdate, Module 2)
    3. debit wallet                          (WalletService.debit, Module 4)
    4. save order, generate QR token         (Module 5)
   If any step fails the whole transaction rolls back.
- cancelOrder: only within cafetron.order.cancel-window-minutes -> refund + restock.
SHARED CONTRACTS YOU CONSUME: WalletService.debit/refund (M4), stock decrement (M2),
WindowService.isOpen/isBeforeCutoff (M6). Agree these signatures in sprint 1.
NOTE: no WebSocket broadcast — the counter screen polls (see Module 5).
