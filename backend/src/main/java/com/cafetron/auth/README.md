# Module 1 — Identity & Access (auth + user packages, security/, config/SecurityConfig)
Build here:
- AuthController: POST /api/auth/register, POST /api/auth/login -> returns JWT
- AuthService: register (BCrypt encode), authenticate (AuthenticationManager), issue token via JwtService
- DTOs: RegisterRequest, LoginRequest, AuthResponse(token, role, name)
- Finish JwtAuthenticationFilter (load user, set SecurityContext with role authority)
Shared contract you OWN: how the JWT carries the role claim. Document it for everyone.
