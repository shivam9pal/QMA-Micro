# Annotations Used in Quantity Measurement App

## Spring Framework Annotations

### Core Spring Annotations
- **@SpringBootApplication** - Main application entry point (QuantitymeasurementappApplication.java)
- **@Configuration** - Configuration class (WebSecurityConfig.java)
- **@Service** - Service layer component (AuthService.java, QuantityMeasurementServiceImpl.java, CustomUserDetailsService.java)
- **@Component** - Generic Spring component (JwtAuthenticationFilter.java, AuthUtil.java)
- **@Repository** - Data access layer (IQuantityMeasurementRepository.java)

### REST Controller Annotations
- **@RestController** - REST API controller (AuthController.java, QuantityMeasurementController.java)
- **@RequestMapping** - Base URL mapping (AuthController.java - "/auth/", QuantityMeasurementController.java - "/api/measurements")
- **@PostMapping** - POST request handler (login, signup, compare, convert, add, subtract, divide)
- **@GetMapping** - GET request handler (history endpoints)
- **@RequestBody** - Bind request body to method parameter (CompareRequestDTO, ConvertRequestDTO, ArithmeticRequestDTO, LoginRequest)
- **@PathVariable** - Extract path variable from URL

### Exception Handling Annotations
- **@RestControllerAdvice** - Global exception handler (GlobalExceptionHandler.java)
- **@ExceptionHandler** - Handle specific exceptions:
  - QuantityMeasurementException
  - DatabaseException
  - UnsupportedOperationException
  - BadCredentialsException
  - UsernameNotFoundException
  - DisabledException
  - AuthenticationException (custom)
  - TokenExpiredException (custom)
  - InvalidTokenException (custom)
  - IllegalArgumentException
  - Exception (generic catch-all)

### Dependency Injection Annotations
- **@Autowired** - Automatic dependency injection (AuthController, JwtAuthenticationFilter, CustomUserDetailsService, WebSecurityConfig)
- **@Bean** - Spring bean factory method (WebSecurityConfig.java - passwordEncoder, authenticationManager, securityFilterChain)
- **@RequiredArgsConstructor** - Lombok constructor generation (AuthController.java, CustomUserDetailsService.java)

---

## JPA/Hibernate Annotations

### Entity Annotations
- **@Entity** - Mark class as JPA entity (User.java, QuantityMeasurementEntity.java)
- **@Table** - Specify table name and properties (User.java, QuantityMeasurementEntity.java)

### Field/Column Annotations
- **@Id** - Primary key field (User.java, QuantityMeasurementEntity.java)
- **@GeneratedValue** - Auto-generated ID strategy (IDENTITY) (User.java, QuantityMeasurementEntity.java)
- **@Column** - Customize column properties:
  - name - custom column name
  - nullable - NOT NULL constraint
  - unique - UNIQUE constraint
  - length - VARCHAR length
  - updatable - prevent updates

### Timestamp Annotations
- **@CreationTimestamp** - Auto-set creation timestamp (QuantityMeasurementEntity.java)
- **@UpdateTimestamp** - Auto-set update timestamp (QuantityMeasurementEntity.java)

---

## Lombok Annotations

### Data/Getter/Setter Annotations
- **@Data** - Generate getters, setters, toString, equals, hashCode (LoginRequest.java, LoginResponse.java)
- **@Getter** - Generate getter methods (SignupResponse.java)

### Constructor Annotations
- **@NoArgsConstructor** - Generate no-arg constructor (User.java, SignupResponse.java)
- **@AllArgsConstructor** - Generate all-arg constructor (User.java)

---

## Java Built-in Annotations

### Override Annotation
- **@Override** - Indicates method overrides superclass method (Used extensively in):
  - User.java (getAuthorities, getPassword, getUsername, isAccountNonExpired, isAccountNonLocked, isCredentialsNonExpired, isEnabled)
  - CustomUserDetailsService.java (loadUserByUsername)
  - JwtAuthenticationFilter.java (doFilterInternal)
  - All unit classes (LengthUnit, WeightUnit, VolumeUnit, TemperatureUnit, QuantityModel)
  - Service implementations (QuantityMeasurementServiceImpl)
  - Domain classes (Quantity)
  - QuantityMeasurementEntity.java (toString)
  - QuantityDTO.java (toString)

### Suppress Warnings Annotation
- **@SuppressWarnings** - Suppress compiler warnings:
  - "unchecked" (Quantity.java, QuantityMeasurementServiceImpl.java) - Used for generic type casting

---

## Custom/Annotation Interfaces
- **@FunctionalInterface** - Mark interface as functional (IMeasurable.java)

---

## Summary Statistics

| Category | Count |
|----------|-------|
| Spring Framework | 18 |
| JPA/Hibernate | 13 |
| Lombok | 5 |
| Java Built-in | Multiple @Override + 2 others |
| Custom Interfaces | 1 |
| Exception Handlers | 10 |

---

## Annotations by File

### Controller Layer
- **AuthController.java**: @RestController, @RequestMapping, @RequiredArgsConstructor, @Autowired, @PostMapping, @RequestBody
- **QuantityMeasurementController.java**: @RestController, @RequestMapping, @PostMapping, @GetMapping, @RequestBody, @PathVariable

### Service Layer
- **AuthService.java**: @Service
- **QuantityMeasurementServiceImpl.java**: @Service, @Override, @SuppressWarnings

### Repository Layer
- **IQuantityMeasurementRepository.java**: @Repository

### Security Layer
- **WebSecurityConfig.java**: @Configuration, @Autowired, @Bean
- **JwtAuthenticationFilter.java**: @Component, @Autowired, @Override
- **AuthUtil.java**: @Component
- **CustomUserDetailsService.java**: @Service, @RequiredArgsConstructor, @Autowired, @Override

### Exception Handling
- **GlobalExceptionHandler.java**: @RestControllerAdvice, @ExceptionHandler (10 times)

### Entity/DTO Layer
- **User.java**: @Entity, @Table, @NoArgsConstructor, @AllArgsConstructor, @Id, @GeneratedValue, @Column, @Override (5 times)
- **QuantityMeasurementEntity.java**: @Entity, @Table, @Id, @GeneratedValue, @Column (multiple), @CreationTimestamp, @UpdateTimestamp, @Override
- **LoginRequest.java**: @Data
- **LoginResponse.java**: @Data, @Getter, @NoArgsConstructor
- **SignupResponse.java**: @Getter, @NoArgsConstructor
- **QuantityDTO.java**: @Override (multiple)

### Domain/Unit Layer
- **Quantity.java**: @Override (multiple), @SuppressWarnings
- **LengthUnit.java**: @Override (multiple)
- **WeightUnit.java**: @Override (multiple)
- **VolumeUnit.java**: @Override (multiple)
- **TemperatureUnit.java**: @Override (multiple)
- **IMeasurable.java**: @FunctionalInterface
