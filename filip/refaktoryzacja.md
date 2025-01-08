## Refaktoryzacja za pomocą AI

## Podejście z AI:

#### Zalety:

1. **Szybkość**: Automatyczne propozycje refaktoryzacji
2. **Spójność**: Jednolite podejście w całym projekcie
3. **Kompleksowość**: Wykrywanie wielu miejsc do poprawy
4. **Wzorce**: Sugestie zgodne z najlepszymi praktykami
5. **Testowanie**: Automatyczne generowanie testów dla zmian
6. **Dokumentacja**: Aktualizacja dokumentacji wraz ze zmianami

---

#### Wady:

1. **Nadgorliwość**: Czasem sugeruje zbędne zmiany
2. **Kontekst**: Może nie uwzględniać specyfiki projektu
3. **Weryfikacja**: Wymaga sprawdzenia proponowanych zmian
4. **Złożoność**: Trudności z bardzo złożonymi refaktoryzacjami

---

## Przykładowe zadania

### 1. Zamiana długiej metody na Chain of Responsibility

Wyobraźmy sobie, że mamy długą metodę `validateOrder`, która sprawdza wiele warunków. AI może zaproponować refaktoryzację do wzorca Chain of Responsibility. Który z kolei pozwoli na podział walidacji na mniejsze kroki i ułatwi dodawanie nowych warunków.

---

Za pomocą przykładowego prompta AI możemy otrzymać propozycję refaktoryzacji:

```plaintext
Przekształć długą metodę `validateOrder` na wzorzec Chain of Responsibility.
```

---

**Kod przed refaktoryzacją:**

```java
public class OrderValidator {
    public boolean validateOrder(Order order) {
        if (order.getItems().isEmpty()) {
            return false;
        }
        if (order.getCustomer() == null) {
            return false;
        }
        if (order.getTotal() <= 0) {
            return false;
        }
        if (!order.getCustomer().hasValidAddress()) {
            return false;
        }
        return true;
    }
}
```

---

**Kod po refaktoryzacji:**

```java
public interface OrderValidationStep {
    boolean validate(Order order);
    OrderValidationStep setNext(OrderValidationStep next);
}

public class ItemsValidator implements OrderValidationStep {
    private OrderValidationStep next;

    public boolean validate(Order order) {
        if (order.getItems().isEmpty()) return false;
        return next == null || next.validate(order);
    }

    public OrderValidationStep setNext(OrderValidationStep next) {
        this.next = next;
        return next;
    }
}

// Użycie
OrderValidationStep validator = new ItemsValidator();
validator.setNext(new CustomerValidator())
        .setNext(new TotalValidator())
        .setNext(new AddressValidator());
```

---

Dodatkowo AI może zaproponować generację testów jednostkowych dla nowych klas:

```java
public class ItemsValidatorTest {
    @Test
    public void testValidate_EmptyItems_ReturnsFalse() {
        ItemsValidator validator = new ItemsValidator();
        Order order = new Order();
        assertFalse(validator.validate(order));
    }

    @Test
    public void testValidate_NonEmptyItems_ReturnsTrue() {
        ItemsValidator validator = new ItemsValidator();
        Order order = new Order();
        order.addItem(new Item("Product", 1));
        assertTrue(validator.validate(order));
    }
}
```

### 2. Refaktoryzacja klasy z dużą iloscia parametrów

Wyobraźmy sobie, że mamy klasę `UserRegistration` z wieloma parametrami w konstruktorze. AI może zaproponować refaktoryzację do wzorca Builder, co ułatwi tworzenie obiektów tej klasy i zwiększy czytelność kodu.

**Kod przed refaktoryzacją:**

```java
public class UserRegistration {
    public User register(String name, String email, String password,
                        String address, String phone, String country,
                        boolean newsletter) {
        // długi kod rejestracji
    }
}
```

---

**Kod po refaktoryzacji:**

```java

public class UserRegistrationBuilder {
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String country;
    private boolean newsletter;

    public UserRegistrationBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserRegistrationBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    // pozostałe settery

    public User register() {
        // kod rejestracji
    }
}

// Użycie
User user = new UserRegistrationBuilder()
    .withName("John")
    .withEmail("john@example.com")
    .withPassword("secret")
    .register();
```

---

### 3. Refaktoryzacja duplikacji kodu

Wyobraźmy sobie, że mamy dwie metody `generatePDFReport` i `generateExcelReport`, które mają wspólny kod. AI może zaproponować refaktoryzację do wzorca Template Method, co pozwoli na wydzielenie wspólnego kodu do jednej metody bazowej.

---

**Kod przed refaktoryzacją:**

```java
// Przed refaktoryzacją
public class ReportGenerator {
    public void generatePDFReport() {
        // wspólny kod
        System.out.println("Przygotowanie danych");
        System.out.println("Formatowanie");
        // specyficzny kod PDF
        System.out.println("Generowanie PDF");
    }

    public void generateExcelReport() {
        // wspólny kod
        System.out.println("Przygotowanie danych");
        System.out.println("Formatowanie");
        // specyficzny kod Excel
        System.out.println("Generowanie Excel");
    }
}
```

---

**Kod po refaktoryzacji:**

```java

// Po refaktoryzacji (Template Method)
public abstract class ReportGenerator {
    public final void generateReport() {
        prepareData();
        format();
        generate();
    }

    private void prepareData() {
        System.out.println("Przygotowanie danych");
    }

    private void format() {
        System.out.println("Formatowanie");
    }

    protected abstract void generate();
}

public class PDFReportGenerator extends ReportGenerator {
    protected void generate() {
        System.out.println("Generowanie PDF");
    }
}

public class ExcelReportGenerator extends ReportGenerator {
    protected void generate() {
        System.out.println("Generowanie Excel");
    }
}
```

## Podsumowanie

Refaktoryzacja za pomocą AI może przyspieszyć proces poprawy kodu, zapewniając jednocześnie spójność i kompleksowość zmian. Warto jednak pamiętać o weryfikacji proponowanych zmian oraz dostosowaniu ich do specyfiki projektu. AI może być cennym narzędziem w codziennym procesie rozwoju oprogramowania, ale nie zastąpi w pełni ludzkiej wiedzy i doświadczenia.
