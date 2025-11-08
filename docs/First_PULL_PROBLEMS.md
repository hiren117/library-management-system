# 🧠 Library Management System – Backend Fixes & Updates Summary

This document summarizes all the fixes, upgrades, and configuration steps performed after pulling the project for the first time.  
It serves as a **revision reference** for understanding why each change was necessary.

---

## 🔒 JWT Upgrade and Migration

**1. Replaced old JJWT (0.9.1) with latest 0.11.5 modules (api, impl, jackson)**  
// We did this because JJWT 0.9.1 used deprecated methods (like `Jwts.parser()` and `signWith(SignatureAlgorithm, secret)`)
// which don’t work with modern Java 17+ and Spring Boot 3.x.  
// The new version splits JJWT into three artifacts: **api**, **impl**, and **jackson**, and requires a different signing key setup.

**1.1 For JJWT 0.11.5, you must replace the whole JwtUtil file.**  
// The older builder style (`signWith(SignatureAlgorithm, SECRET_KEY)`) was replaced with a more secure key method.  
// Now we use `Keys.hmacShaKeyFor(secret.getBytes())` for generating a proper HMAC key.

**1.2 We initially tried `Jwts.SIG.HS256` (from JJWT 0.12+), but that’s not available in 0.11.5.**  
// For 0.11.5, we reverted to using `SignatureAlgorithm.HS256` instead.  
// This ensures compatibility while keeping the HMAC-SHA256 algorithm for token signing.

---

## 🔑 Updated JwtUtil Implementation

**2. Updated JwtUtil to use `Keys.hmacShaKeyFor()` and `parserBuilder()`**  
// These are the correct methods in the new JJWT API.  
// `Keys.hmacShaKeyFor()` ensures the secret key has the correct byte length.  
// `parserBuilder()` replaces the deprecated `parser()` method and allows secure parsing of JWTs.

---

## ☕ Java Compatibility and Version Fix

**3. Downgraded Java version to 17 for Spring Boot 3.4.x compatibility**  
// Spring Boot 3.x officially supports LTS JDKs (17 and 21).  
// Using Java 24 caused classloader and bytecode issues because plugins like Lombok, Surefire, and JJWT aren’t tested on it yet.  
// Setting JAVA_HOME to JDK 17 fixed the “TypeTag UNKNOWN” build errors.

---

## 🧩 Lombok and Maven Build Fixes

**4. Fixed Lombok version and Maven build issues**  
// Lombok provides annotations like `@Getter`, `@Setter`, `@Builder`, `@AllArgsConstructor`, etc., to reduce boilerplate code.  
// The older version wasn’t compatible with Spring Boot 3.x.  
// We updated to **Lombok 1.18.32** and configured the `maven-compiler-plugin` properly.  
// Maven build now succeeds without manual imports.

---

## 🚀 Backend Verification

**5. Verified successful backend startup on port 8080**  
// This confirmed that all configuration files, dependencies, and entity scans are correct.  
// “Tomcat started on port 8080” means Spring Boot initialized successfully with no missing beans or scanning issues.

---

## ⚙️ JWT Configuration in `application.properties`
## ⚙️ 6. Added in `application.properties`

```properties
# ===== JWT =====
app.jwt.secret=change-me-dev-secret-change-me-dev-secret
app.jwt.expiration=36000000   # 10 hours in ms (same as before)
```

We externalized the JWT secret and expiration to `application.properties` for better maintainability and security.  
This avoids hardcoding secrets directly in code and allows environment-based configuration later (dev, prod, etc.).

---

## 🧭 7. javax to jakarta migration

Starting with Spring Boot 3.x and Jakarta EE 9, all `javax.*` packages were renamed to `jakarta.*`.  
Example:  
`javax.persistence.Entity → jakarta.persistence.Entity`  

We ensured our project imports and dependencies align with Jakarta naming (used by Hibernate 6 and Boot 3).

---

## 📦 8. Package scanning and main application setup

The main class (`LibraryManagementSystemApplication`) was already under `com.webapp.lms`,  
which is the root package for your components.  

We added explicit:
- `@ComponentScan`
- `@EntityScan`
- `@EnableJpaRepositories` *(if needed)*  

to make sure Spring auto-detects all services, repositories, and entities properly.  
This solved errors like:
- “Found 0 JPA repositories”
- “ClassNotFoundException: com.webapp.lms.model.Book”

---

## 🧱 9. Lombok overview

Lombok is a Java library that automatically generates boilerplate code at compile-time.  

**Example annotations:**
- `@Getter` / `@Setter` → generates getters and setters  
- `@AllArgsConstructor` / `@NoArgsConstructor` → generates constructors  
- `@Builder` → provides builder pattern support  
- `@Data` → combines getter, setter, equals, hashCode, and toString  

It helps make entity and model classes cleaner and shorter, improving readability.

## ✅ Summary

All fixes completed successfully.  
The backend is now fully compatible with the following stack:

| Component      | Version     |
|----------------|-------------|
| **Spring Boot** | `3.4.3`     |
| **JDK**         | `17`        |
| **JJWT**        | `0.11.5`    |
| **Lombok**      | `1.18.32`   |
