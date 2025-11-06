# 🧰 BACKEND_SETUP_CHECKLIST.md
*(Spring Boot 3.x + Java 17 Template)*

---

## 🪴 1. Prerequisites

- **JDK:** Install Java **17** (LTS).
- **IDE:** IntelliJ IDEA (Community Edition is fine).
- **Build:** Use **Maven Wrapper** (`mvnw` or `mvnw.cmd`) – no global Maven required.
- **Database:** PostgreSQL.
- **API Client:** Postman.

---

## ⚙️ 2. IntelliJ JDK vs System JDK

| Action | Uses Which JDK? |
|--------|------------------|
| 🟢 Run app via IntelliJ (green play button) | IntelliJ Project SDK |
| 💻 Run app via Terminal / PowerShell | System JDK (from environment variables) |

**Temporary switch in PowerShell:**
```powershell
$env:JAVA_HOME="C:\Users\<you>\.jdks\temurin-17.0.16"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
java -version
```

**Permanent:**  
Set in Windows → “Environment Variables” → add `JAVA_HOME` and `Path`.

---

## 🏗️ 3. Create Spring Boot Project

- Parent:
  ```xml
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>3.4.x</version>
  </parent>
  ```
- Java version:
  ```xml
  <properties>
      <java.version>17</java.version>
  </properties>
  ```

---

## 🧩 4. Dependencies (pom.xml)

```xml
<dependencies>
    <!-- Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Database (JPA + PostgreSQL) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.32</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

---

## ⚡ 5. application.properties

```properties
server.port=8080
spring.application.name=your-app

spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# JWT
app.jwt.secret=change-me-dev-secret-change-me-dev-secret
app.jwt.expiration=36000000
```

---

## 🔐 6. JWT Utility Class (JJWT 0.11.5)

```java
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private final String secret = "...";
    private final long expirationMillis = 36000000;

    private Key key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMillis);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
```

---

## 🧱 7. Security Config

```java
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/actuator/health").permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic();
    return http.build();
}
```

---

## 🌐 8. CORS Setup

```java
@Bean
CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:4200"));
    config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}
```

---

## 🧠 9. Quick Reminders

| Topic | Key Points |
|-------|-------------|
| 🧩 Lombok | Use `@Getter`, `@Setter`, `@Builder`, `@Data` |
| 🧭 Jakarta Migration | `javax.*` → `jakarta.*` |
| ☕ JDK Version | Use Java 17 or 21 (not 24 yet) |
| 🚀 Run App | IntelliJ green button (JDK 17) or `mvnw spring-boot:run` |
| 🔍 Check Build | `.\mvnw clean package -DskipTests` |

---

## ✅ 10. Summary

All setups verified and build success confirmed.  
**Backend stack:**

| Component | Version |
|------------|----------|
| Spring Boot | 3.4.3 |
| JDK | 17 |
| JJWT | 0.11.5 |
| Lombok | 1.18.32 |

---

✨ *Now you’re ready to clone, build, and deploy any Spring Boot backend smoothly!* 🚀
