# ProductManager

Mała aplikacja Java modelująca sklep, który prowadzi katalog produktów (`Food`, `Drink`) wraz z ocenami i recenzjami klientów. Projekt edukacyjny — bez systemu budowania, bez testów.

## Wymagania

- JDK 15 (`adopt-openjdk-15`, language level `JDK_15`).
- Opcjonalnie: IntelliJ IDEA — projekt jest skonfigurowany jako moduł IDEA (`ProductManager.iml`, `.idea/`).

## Budowanie i uruchamianie

W IntelliJ wystarczy uruchomić klasę `org.raindog.shop.Shop`. Z linii poleceń:

```bash
# Kompilacja
javac -d out $(find src -name '*.java')

# Skopiowanie pliku zasobów na classpath (javac nie kopiuje resources)
mkdir -p out/org/raindog/shop/data \
  && cp src/org/raindog/shop/data/resources.properties out/org/raindog/shop/data/

# Uruchomienie
java -cp out org.raindog.shop.Shop
```

Bez kroku z `resources.properties` aplikacja rzuci `MissingResourceException` — `ProductManager` ładuje bundle przez `ResourceBundle.getBundle("org.raindog.shop.data.resources", locale)`.

## Struktura katalogów

- `src/org/raindog/shop/Shop.java` — punkt wejścia z metodą `main`, prezentuje użycie API.
- `src/org/raindog/shop/data/` — model domenowy:
  - `Product` (abstrakcyjna), `Food`, `Drink` — produkty (niemutowalne).
  - `Rating`, `Rateable<T>` — system ocen.
  - `Review` — recenzja produktu.
  - `ProductManager` — fasada zarządzająca produktami i recenzjami.
  - `resources.properties` — szablony komunikatów (`MessageFormat`).
- `out/` — wynik kompilacji (gitignorowany).

## Architektura — w skrócie

- **Produkty są niemutowalne.** Pola `id`, `name`, `price`, `rating` są `final`. Zmiana oceny (`Rateable#applyRating`) zwraca nową instancję; `ProductManager` wymienia klucz w `Map<Product, List<Review>>`.
- **Tworzenie produktów przez `ProductManager`.** Konstruktory `Food`/`Drink` mają widoczność pakietową — z zewnątrz pakietu trzeba użyć `ProductManager#createProduct`. Dodając nowy podtyp produktu, dodaj odpowiednie przeciążenie `createProduct`.
- **Reguły rabatów po stronie podklasy.** `Product#getDiscount` to 10% (`HALF_UP`, scale 2). `Drink` stosuje rabat tylko między 17:30 a 18:30 (włącznie). `Food` stosuje rabat tylko gdy `bestBefore` to dzisiaj. Wszystkie kwoty są liczone na `BigDecimal`.
- **Raporty zależne od locale.** `ProductManager` jest tworzony z `Locale` i cachuje `ResourceBundle`, `DateTimeFormatter` i `NumberFormat`. Wszystkie teksty użytkownika są w `resources.properties` (klucze: `product`, `review`, `no.reviews`).

Pełniejsza dokumentacja architektury dla narzędzi AI znajduje się w `CLAUDE.md`.
