# 🧠 Developer Notes – Library Management System (Spring Boot + PostgreSQL)

**Project Type:** Full Stack (Spring Boot backend + Angular frontend)  
**Backend Framework:** Spring Boot 3.4.3  
**Database:** PostgreSQL  
**Build Tool:** Maven  
**Language:** Java  

---

## ☕ Java Compatibility

| JDK Version | Support | Recommendation | Notes |
|--------------|----------|----------------|-------|
| **17 (LTS)** | ✅ Fully Supported | ✅ **Use this version** | Stable, LTS release officially supported by Spring Boot 3.x |
| **21 (LTS)** | ✅ Supported | ⚙️ Optional | Safe for new builds, backward compatible |
| **24 (non-LTS)** | ⚠️ *Not officially supported* | ❌ Avoid | Some libraries and annotation processors (like Lombok, JJWT, etc.) may fail or show unknown errors |

### ✅ Always Ensure:
- `JAVA_HOME` → points to your **JDK 17** path  
  Example: `C:\Users\Hiren\.jdks\temurin-17.0.16`
- `Path` → includes `%JAVA_HOME%\bin` **above** any other Java entries  
- Your `pom.xml` includes:
  ```xml
  <properties>
      <java.version>17</java.version>
  </properties>

## ☕ JDK Path and IntelliJ vs System Configuration

### 🧩 1. IntelliJ's Managed JDK
- When you **download or set a JDK from IntelliJ**, it installs it under your user profile path:
  ```
  C:\Users\<YourName>\.jdks\<jdk-version>
  ```
- IntelliJ manages its own JDKs per project — this avoids admin permissions and allows each project to use a different Java version if needed.

### ⚙️ 2. System-Level JDK
- The **system-level JDK** (the one used by PowerShell, Command Prompt, or Git Bash) is the one defined in your **environment variables**:
  ```
  JAVA_HOME = C:\Program Files\Java\<jdk-version>
  Path = %JAVA_HOME%\bin
  ```
- You can check which one is active by running:
  ```
  java -version
  ```

### 🚀 3. IntelliJ Run (Green Button) vs Terminal Run
- **Running with the green ▶️ button** in IntelliJ → uses IntelliJ’s own configured JDK (from Project Settings → SDK).
- **Running in PowerShell / Git Bash / CMD** → uses the **system JDK** defined in environment variables.

### 🔁 4. Why This Matters
- If IntelliJ uses JDK 17 but your system uses JDK 24, Maven builds from terminal might fail due to version incompatibility.
- That’s why you can temporarily switch the system JDK in PowerShell using:
  ```powershell
  $env:JAVA_HOME = "C:\Users\Hiren\.jdks\temurin-17.0.16"
  $env:Path = "$env:JAVA_HOME\bin;$env:Path"
  java -version
  ```
  *(Note: this change resets when you close PowerShell — it’s temporary.)*

### 🧠 5. Key Takeaway
> IntelliJ JDK = Project-specific (safe & isolated)  
> System JDK = Global (used by terminals, Maven CLI, etc.)

Keep both aligned (JDK 17 for now) to avoid build or runtime conflicts.
