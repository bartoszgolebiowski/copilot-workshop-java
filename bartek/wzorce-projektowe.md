### Dlaczego korzystać ze wzorców projektowych?

Wzorce projektowe rozwiązują często spotykane problemy w programowaniu, oferując:

1. **Spójność kodu**: Zapewniają przewidywalną strukturę, co ułatwia zrozumienie i utrzymanie kodu (np. **Factory Method** dla dynamicznego tworzenia obiektów).
2. **Skalowalność**: Pozwalają łatwo rozwijać aplikacje (np. **Decorator** dodaje funkcjonalność bez ingerencji w implementację).
3. **Redukcję błędów**: Stosują przetestowane rozwiązania (np. **Singleton** kontroluje liczbę instancji klasy).
4. **Lepszą komunikację**: Wspólne wzorce jak **Observer** czy **Builder** ułatwiają omawianie rozwiązań w zespole.

### Dlaczego korzystać z AI do wzorców projektowych?

1. **Przyspieszenie pracy**: AI generuje szkielet wzorca w kilka sekund, np. **Builder** z komentarza w kodzie.
2. **Edukacja i dobre praktyki**: Kod AI jest zgodny z najlepszymi standardami, co uczy mniej doświadczonych programistów.
3. **Redukcja błędów**: AI unika typowych pomyłek, np. brak synchronizacji w **Singletonie**.
4. **Oszczędność czasu**: Pozwala skupić się na logice biznesowej zamiast na kodzie szkieletowym.
5. **Adaptowalność**: Kod wzorcowy można łatwo dostosować do konkretnych potrzeb (np. **Observer** dla priorytetowych powiadomień).
6. **Wsparcie dla początkujących**: AI dostarcza gotowe rozwiązania, eliminując potrzebę dogłębnej wiedzy o wzorcach.

Przykład kodu bez wzorca:

# Strategia 

### 1. Kod bez zastosowania wzorca projektowego (problem do rozwiązania)
W tym przykładzie mamy system, który oblicza różne podatki (np. VAT, podatek dochodowy, itp.). Kod jest napisany bez użycia wzorca **Strategii**, co prowadzi do trudności w rozwoju i utrzymaniu.

```java
public class TaxCalculator {
    public double calculateTax(String taxType, double amount) {
        if (taxType.equalsIgnoreCase("VAT")) {
            return amount * 0.23; // 23% VAT
        } else if (taxType.equalsIgnoreCase("INCOME_TAX")) {
            return amount * 0.18; // 18% Income Tax
        } else if (taxType.equalsIgnoreCase("CORPORATE_TAX")) {
            return amount * 0.19; // 19% Corporate Tax
        } else {
            throw new IllegalArgumentException("Unknown tax type: " + taxType);
        }
    }

    public static void main(String[] args) {
        TaxCalculator calculator = new TaxCalculator();
        double vat = calculator.calculateTax("VAT", 1000);
        double incomeTax = calculator.calculateTax("INCOME_TAX", 1000);
        double corporateTax = calculator.calculateTax("CORPORATE_TAX", 1000);

        System.out.println("VAT: " + vat);
        System.out.println("Income Tax: " + incomeTax);
        System.out.println("Corporate Tax: " + corporateTax);
    }
}
```

#### Problemy:
1. **Brak elastyczności**: Dodanie nowego rodzaju podatku wymaga modyfikacji metody `calculateTax`, co łamie zasadę otwarte-zamknięte (OCP).
2. **Złożoność kodu**: Kod staje się trudny do utrzymania, gdy dodajemy kolejne typy podatków.
3. **Błędy przy rozwoju**: Możliwość literówek w identyfikatorze podatku (np. "vat" vs "VAT").

---

### 2. Zastosowanie wzorca projektowego Strategii
Przepisujemy kod, aby zastosować wzorzec **Strategii**, który rozdziela logikę obliczania podatków na oddzielne klasy.

#### Implementacja wzorca Strategii:

```java
// Interfejs strategii
public interface TaxStrategy {
    double calculateTax(double amount);
}

// Konkretny algorytm: VAT
public class VatStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double amount) {
        return amount * 0.23; // 23% VAT
    }
}

// Konkretny algorytm: Income Tax
public class IncomeTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double amount) {
        return amount * 0.18; // 18% Income Tax
    }
}

// Konkretny algorytm: Corporate Tax
public class CorporateTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double amount) {
        return amount * 0.19; // 19% Corporate Tax
    }
}

// Klasa kontekstowa
public class TaxCalculator {
    private TaxStrategy strategy;

    public void setStrategy(TaxStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateTax(double amount) {
        if (strategy == null) {
            throw new IllegalStateException("Tax strategy not set");
        }
        return strategy.calculateTax(amount);
    }

    public static void main(String[] args) {
        TaxCalculator calculator = new TaxCalculator();

        // VAT calculation
        calculator.setStrategy(new VatStrategy());
        System.out.println("VAT: " + calculator.calculateTax(1000));

        // Income Tax calculation
        calculator.setStrategy(new IncomeTaxStrategy());
        System.out.println("Income Tax: " + calculator.calculateTax(1000));

        // Corporate Tax calculation
        calculator.setStrategy(new CorporateTaxStrategy());
        System.out.println("Corporate Tax: " + calculator.calculateTax(1000));
    }
}
```

---

### Zastosowanie wzorca Strategii: **Zalety**
1. **Łatwe dodawanie nowych strategii**: Nowy typ podatku można dodać poprzez stworzenie nowej klasy implementującej `TaxStrategy`, bez ingerencji w istniejący kod.
2. **Przejrzystość kodu**: Logika każdego podatku jest wydzielona do osobnych klas, co ułatwia debugowanie i utrzymanie.
3. **Reużywalność**: Kontekstowa klasa `TaxCalculator` jest uniwersalna i może być używana z dowolną strategią, co zwiększa elastyczność systemu.

Przepisując kod w ten sposób, wprowadziliśmy bardziej elastyczną, skalowalną i zgodną z zasadami SOLID architekturę.


# Fabryka

Poniższy przykład przedstawia system zarządzania zamówieniami dla firmy transportowej, obsługujący różne typy transportu: rowery, ciężarówki, statki, samoloty. Logika tworzenia i zarządzania transportem znajduje się w jednej klasie, co prowadzi do problemów ze skalowalnością i utrzymaniem kodu.

```java
import java.util.ArrayList;
import java.util.List;

public class TransportManager {

    public List<Object> activeTransports = new ArrayList<>();

    public Object createTransport(String transportType) {
        if (transportType.equalsIgnoreCase("BICYCLE")) {
            System.out.println("Creating a bicycle for delivery.");
            return new Bicycle();
        } else if (transportType.equalsIgnoreCase("TRUCK")) {
            System.out.println("Creating a truck for delivery.");
            return new Truck();
        } else if (transportType.equalsIgnoreCase("SHIP")) {
            System.out.println("Creating a ship for overseas delivery.");
            return new Ship();
        } else if (transportType.equalsIgnoreCase("AIRPLANE")) {
            System.out.println("Creating an airplane for international delivery.");
            return new Airplane();
        } else {
            throw new IllegalArgumentException("Unknown transport type: " + transportType);
        }
    }

    public void assignDelivery(String transportType, String packageId) {
        Object transport = createTransport(transportType);
        activeTransports.add(transport);
        System.out.println("Assigned delivery for package " + packageId + " using " + transportType);
    }

    public void monitorActiveTransports() {
        System.out.println("Monitoring active transports...");
        for (Object transport : activeTransports) {
            if (transport instanceof Bicycle) {
                ((Bicycle) transport).deliver();
            } else if (transport instanceof Truck) {
                ((Truck) transport).deliver();
            } else if (transport instanceof Ship) {
                ((Ship) transport).deliver();
            } else if (transport instanceof Airplane) {
                ((Airplane) transport).deliver();
            }
        }
    }

    public static void main(String[] args) {
        TransportManager manager = new TransportManager();
        manager.assignDelivery("BICYCLE", "PKG123");
        manager.assignDelivery("TRUCK", "PKG124");
        manager.assignDelivery("SHIP", "PKG125");
        manager.assignDelivery("AIRPLANE", "PKG126");

        manager.monitorActiveTransports();
    }
}

// Transport classes
class Bicycle {
    public void deliver() {
        System.out.println("Delivering by bicycle.");
    }
}

class Truck {
    public void deliver() {
        System.out.println("Delivering by truck.");
    }
}

class Ship {
    public void deliver() {
        System.out.println("Delivering by ship.");
    }
}

class Airplane {
    public void deliver() {
        System.out.println("Delivering by airplane.");
    }
}
```

### Problemy w kodzie:

1. **Brak skalowalności**:
   - Dodanie nowego środka transportu wymaga modyfikacji metod `createTransport` i `monitorActiveTransports`. Narusza to zasadę otwarte-zamknięte (OCP).

2. **Złożoność w obsłudze transportów**:
   - Logika dla każdego środka transportu jest powielana w różnych częściach kodu, co prowadzi do redundancji.

3. **Brak modularności**:
   - Klasa `TransportManager` ma zbyt wiele odpowiedzialności, zajmując się zarówno tworzeniem transportów, jak i ich monitorowaniem. Narusza to zasadę pojedynczej odpowiedzialności (SRP).

4. **Przepełnienie instrukcji warunkowych**:
   - Liczne `if-else` w metodach `createTransport` i `monitorActiveTransports` sprawiają, że kod staje się trudny do utrzymania.

---

### Możliwości refaktoryzacji:
1. **Wprowadzenie wzorca Metody Fabryki**:
   - Utworzenie klasy bazowej dla transportów i wykorzystanie fabryk do tworzenia konkretnych typów transportów.

2. **Zastosowanie polimorfizmu**:
   - Zastąpienie `if-else` przez wywołania metod na obiektach implementujących wspólny interfejs `Transport`.

3. **Rozdzielenie odpowiedzialności**:
   - Wyodrębnienie logiki monitorowania transportów do osobnej klasy lub modułu.

Oto implementacja wzorca **Metody Fabryki** z całą logiką umieszczoną w jednej klasie:

```java
import java.util.ArrayList;
import java.util.List;

public class TransportManager {

    private List<Transport> activeTransports = new ArrayList<>();

    // Interfejs dla transportu
    public interface Transport {
        void deliver();
    }

    // Konkretny produkt: Rower
    public class Bicycle implements Transport {
        @Override
        public void deliver() {
            System.out.println("Delivering by bicycle.");
        }
    }

    // Konkretny produkt: Ciężarówka
    public class Truck implements Transport {
        @Override
        public void deliver() {
            System.out.println("Delivering by truck.");
        }
    }

    // Konkretny produkt: Statek
    public class Ship implements Transport {
        @Override
        public void deliver() {
            System.out.println("Delivering by ship.");
        }
    }

    // Konkretny produkt: Samolot
    public class Airplane implements Transport {
        @Override
        public void deliver() {
            System.out.println("Delivering by airplane.");
        }
    }

    // Metoda fabryki
    public Transport createTransport(String transportType) {
        switch (transportType.toUpperCase()) {
            case "BICYCLE":
                return new Bicycle();
            case "TRUCK":
                return new Truck();
            case "SHIP":
                return new Ship();
            case "AIRPLANE":
                return new Airplane();
            default:
                throw new IllegalArgumentException("Unknown transport type: " + transportType);
        }
    }

    // Przypisanie dostawy
    public void assignDelivery(String transportType, String packageId) {
        Transport transport = createTransport(transportType);
        activeTransports.add(transport);
        System.out.println("Assigned delivery for package " + packageId + " using " + transportType);
        transport.deliver();
    }

    // Monitorowanie aktywnych transportów
    public void monitorActiveTransports() {
        System.out.println("Monitoring active transports...");
        for (Transport transport : activeTransports) {
            transport.deliver();
        }
    }

    public static void main(String[] args) {
        TransportManager manager = new TransportManager();

        manager.assignDelivery("BICYCLE", "PKG123");
        manager.assignDelivery("TRUCK", "PKG124");
        manager.assignDelivery("SHIP", "PKG125");
        manager.assignDelivery("AIRPLANE", "PKG126");

        manager.monitorActiveTransports();
    }
}
```

### Kluczowe punkty:
1. Cała logika związana z transportami, metodą fabryki i obsługą zamówień jest zawarta w jednej klasie `TransportManager`.
2. **Metoda fabryki** (`createTransport`) dynamicznie tworzy obiekty na podstawie typu transportu przekazanego jako parametr.
3. Interfejs `Transport` zapewnia polimorfizm, umożliwiając jednolitą obsługę różnych typów transportu.