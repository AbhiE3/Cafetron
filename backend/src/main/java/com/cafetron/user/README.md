# Module 1 — User (same owner as auth)
Build here:
- User entity: id, email(unique), passwordHash, fullName, role (com.cafetron.common.Role), enabled
- UserRepository: findByEmail
- UserService implementing UserDetailsService (loadUserByUsername)
- UserController: GET /api/users/me (profile), PATCH password
Other modules reference the authenticated user id via SecurityContext — expose a tiny helper for that.
