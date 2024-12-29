### Tytuł: **Tworzenie DTO za pomocą GitHub Copilot**

#### Dlaczego to ważne:
Data Transfer Object (DTO) to wzorzec projektowy używany do transportowania danych między różnymi warstwami aplikacji lub usługami. Pozwala on ograniczyć liczbę zależności i skupić się na niezbędnych danych, co przekłada się na lepszą organizację kodu oraz bezpieczeństwo (np. nie ujawniamy zbędnych pól z encji bazodanowych).

---

#### Wady bez AI:
- **Czasochłonne tworzenie klas**: Każdy DTO trzeba napisać ręcznie, co może wydłużać czas trwania projektu.
- **Podatność na błędy**: Literówki w polach lub brakujące gettery/settery utrudniają utrzymanie kodu.
- **Brak spójności**: Różni programiści mogą tworzyć DTO w odmienny sposób, przez co kod staje się niejednolity.

---

#### Zalety z AI:
- **Automatyzacja tworzenia DTO**: GitHub Copilot generuje szablony klas na podstawie opisu lub istniejącej struktury bazy danych.
- **Oszczędność czasu**: Wygenerowane klasy wymagają tylko niewielkich zmian zamiast pisania od zera.
- **Jednolitość i spójność kodu**: Narzędzie podpowiada zdefiniowane konwencje nazewnicze i standardy projektowe.
- **Mniejsze ryzyko błędów**: Copilot automatycznie dodaje kluczowe elementy, takie jak gettery, settery czy dokumentację.

---

# Przykład

### Scenariusz:
Mamy aplikację, która pobiera dane o użytkownikach z bazy danych. Tabela `User` zawiera 6 pól: `id`, `firstName`, `lastName`, `email`, `phoneNumber`, oraz `address`. Jednak w kontrakcie API, który wymaga DTO, wystarczą tylko dwa pola: `id` i `email`.

GitHub Copilot może ułatwić proces tworzenia DTO oraz klasy mappera, bazując na dostarczonym JSON-ie kontraktu API.

---

#### JSON z kontraktu:
```json
{
  "id": 123,
  "email": "user@example.com"
}
```

---

#### Wygenerowany kod:

##### Model encji (User):
```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
}
```

---

##### DTO (UserDto):
```java
import lombok.Data;

/**
 * DTO dla informacji użytkownika zgodny z kontraktem API
 */
@Data
public class UserDto {
    private Long id;
    private String email;
}
```

---

##### Mapper (UserMapper):
```java
public class UserMapper {

    /**
     * Mapuje encję User na DTO UserDto
     * @param user obiekt encji User
     * @return UserDto obiekt DTO z wybranymi polami
     */
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
```

---

#### Jak to działa z GitHub Copilot:
1. **Generowanie DTO**: Na podstawie JSON-a Copilot może automatycznie wygenerować klasę `UserDto` zawierającą tylko `id` i `email`.
2. **Automatyzacja mapowania**: Klasa `UserMapper` również może zostać szybko wygenerowana, upraszczając proces transformacji modelu encji do DTO.

---

#### Zalety podejścia z AI:
- **Szybsze wdrażanie kontraktów API**: DTO zgodne z JSON-em można wygenerować w kilka sekund.
- **Spójność kodu**: Dzięki adnotacji `@Data` od Lombok kod jest czytelny i zwięzły.
- **Unikanie błędów manualnych**: Automatyzacja ogranicza literówki oraz pomyłki w mapowaniu pól.