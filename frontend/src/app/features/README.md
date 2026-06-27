# features/ — one folder per module owner

Generate each feature with the Angular CLI (run from frontend/):

  ng generate component features/auth/login --standalone
  ng generate component features/menu/menu-browse --standalone        # Module 2 employee view
  ng generate component features/menu/menu-manage --standalone        # Module 2 counter/admin view
  ng generate component features/cart-order/cart --standalone          # Module 3
  ng generate component features/cart-order/checkout --standalone
  ng generate component features/cart-order/order-history --standalone
  ng generate component features/wallet/wallet --standalone            # Module 4
  ng generate component features/pickup-scanner/qr-view --standalone   # Module 5 employee
  ng generate component features/pickup-scanner/scanner --standalone   # Module 5 counter
  ng generate component features/admin/dashboard --standalone          # Module 6 reports + charts
  ng generate component features/admin/operations --standalone         # Module 6 window toggle + cutoff

Each owner also generates a feature service, e.g.:
  ng generate service features/menu/menu

Folder ownership:
  auth/            -> Member 1
  menu/            -> Member 2
  cart-order/      -> Member 3
  wallet/          -> Member 4
  pickup-scanner/  -> Member 5
  admin/           -> Member 6
