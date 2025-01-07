# Konfiguracja projektu z GitHub Copilot

## Wprowadzenie

GitHub Copilot wspiera konfigurację projektu poprzez:

- **Generowanie plików konfiguracyjnych**

  - `package.json` dla npm
  - `requirements.txt` dla pip
  - `pom.xml` dla Maven

- **Wsparcie zależności**

  - Sugestie nowych zależności
  - Aktualizacja do najnowszych wersji
  - Rozwiązywanie konfliktów
  - Automatyczna dokumentacja

- **Integracja DevOps**
  - Konfiguracja GitHub Actions
  - Automatyzacja CI/CD
  - Generowanie dokumentacji

## Przykłady użycia

### 1. Migracja Maven → Gradle

### 1. Migracja z Maven do Gradle

Mając pom.xml:

```xml
<project>
    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>3.2.0</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.1</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

Copilot może wygenerować równoważny build.gradle:

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.0'
    id 'io.spring.dependency-management' version '1.1.4'
}

group = 'com.example'
version = '1.0.0'
sourceCompatibility = '17'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.postgresql:postgresql:42.7.1'
}
```

### 2. Zaawansowany pipeline CI/CD

```yaml
name: Java CI/CD Pipeline

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  validate:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Code quality check
        uses: sonarsource/sonarqube-scan-action@master
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}

  test:
    needs: validate
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:13
        env:
          POSTGRES_DB: testdb
          POSTGRES_PASSWORD: test
        ports:
          - 5432:5432
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5

    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: "17"
          distribution: "temurin"
          cache: "gradle"

      - name: Run tests
        run: ./gradlew test
        env:
          DB_URL: jdbc:postgresql://localhost:5432/testdb
          DB_USER: postgres
          DB_PASS: test

      - name: Upload test results
        uses: actions/upload-artifact@v3
        if: always()
        with:
          name: test-results
          path: build/reports/tests/

  build:
    needs: test
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Build
        run: ./gradlew bootJar
      - name: Docker build
        run: |
          docker build -t myapp:${{ github.sha }} .
          docker tag myapp:${{ github.sha }} myapp:latest

  deploy:
    needs: build
    runs-on: ubuntu-latest
    environment: production
    steps:
      - name: Deploy to k8s
        uses: azure/k8s-deploy@v1
        with:
          manifests: |
            k8s/deployment.yaml
            k8s/service.yaml
```

### 3. Dockerfile z wieloetapowym buildem

```Dockerfile
# Build stage
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## Podsumowanie

Wszystkie powyższe pliki konfiguracyjne zostały wygenerowane przy pomocy GitHub Copilot:

### Wygenerowane pliki:

1. **pom.xml/build.gradle**

   - Konfiguracja projektu Java
   - Zarządzanie zależnościami
   - Ustawienia buildu

2. **GitHub Actions workflows**

   - Pipeline CI/CD
   - Automatyzacja testów
   - Proces deploymentu

3. **Dockerfile**
   - Multi-stage build
   - Optymalizacja obrazu
   - Konfiguracja uruchomieniowa

### Korzyści z użycia AI:

- Szybkie generowanie boilerplate code
- Spójne konwencje nazewnicze
- Aktualne wersje zależności
- Best practices w konfiguracji
- Kompleksowa dokumentacja
