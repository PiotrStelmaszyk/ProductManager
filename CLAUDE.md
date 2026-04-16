# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Setup

- IntelliJ IDEA Java module project (no Maven/Gradle). Configuration lives in `ProductManager.iml` and `.idea/`.
- JDK: `adopt-openjdk-15` / language level `JDK_15` (see `.idea/misc.xml`). Uses features like diamond operators, `var` is not used but enums and `final` subclasses are.
- Sources: `src/` (the only source root). Compiler output: `out/` (gitignored).
- Entry point: `org.raindog.shop.Shop#main`.
- There are no tests, no build scripts, no linter config. Build/run is done through IntelliJ, or manually:

```bash
# Compile
javac -d out $(find src -name '*.java')
# Copy the i18n bundle into the classpath (javac does not copy resources)
mkdir -p out/org/raindog/shop/data && cp src/org/raindog/shop/data/resources.properties out/org/raindog/shop/data/
# Run
java -cp out org.raindog.shop.Shop
```

The `resources.properties` step matters: `ProductManager` loads `ResourceBundle.getBundle("org.raindog.shop.data.resources", locale)` — if the file is not on the classpath in that package, the app throws `MissingResourceException`.

## Architecture

Small learning-exercise domain model for a shop that tracks products and reviews. Three ideas are load-bearing and span multiple files:

### Products are immutable; `ProductManager` owns the mutable state

- `Product` (abstract), with `final` subclasses `Food` and `Drink`, holds `id`, `name`, `price`, `rating` as `final` fields. There is no setter for rating.
- `Rateable<T>#applyRating(Rating)` returns a *new* instance with the updated rating (see `Food#applyRating`, `Drink#applyRating`).
- `ProductManager` stores state in `Map<Product, List<Review>> products`. When a review is added, `reviewProduct` removes the old key, builds a new `Product` via `applyRating`, and re-inserts under the new key with the same review list. Any code that mutates product state must follow this remove-and-reinsert pattern, otherwise the map key goes stale.
- `Product#equals` and `Product#hashCode` are both keyed on `id` only. If you add a field that should participate in identity, update both together — skewing them will silently break `HashMap` lookups after `applyRating` re-inserts.

### Construction goes through `ProductManager`, not `new`

- `Food` and `Drink` constructors are package-private. External callers (e.g. `Shop`) cannot `new Food(...)` from outside `org.raindog.shop.data`; they must use `ProductManager#createProduct`, which also registers the product in the internal map. When adding a new `Product` subtype, expose it via a new `createProduct` overload on `ProductManager` rather than making the constructor public.

### Discount and freshness rules live on the subclass

- `Product#getDiscount` returns `price * DISCOUNT_RATE` (10%, `HALF_UP`, scale 2).
- `Drink#getDiscount` only applies the parent discount between 17:30 and 18:30 local time (inclusive on both ends, using `!isBefore`/`!isAfter`); otherwise `BigDecimal.ZERO`.
- `Food#getDiscount` only applies it when `bestBefore` equals today. Note: `Product#getBestBefore` returns `LocalDate.now()` as a default, so non-`Food` products implicitly report "today" for best-before — reports rely on subclasses to override this.
- Monetary values are `BigDecimal` throughout. Do not reintroduce `double`/`float` for price or discount arithmetic.

### Reports are locale-aware

- `ProductManager` is constructed with a `Locale` and caches a `ResourceBundle`, `DateTimeFormatter` (short, localized), and `NumberFormat` (currency) for that locale.
- Report strings are `MessageFormat` patterns in `src/org/raindog/shop/data/resources.properties` (keys: `product`, `review`, `no.reviews`). Adding a new localized string means adding a key here and referencing it via `resourceBundle.getString(...)` — do not inline user-facing text.
- Reviews are sorted highest-rating-first before rendering: `Review#compareTo` returns `other.ordinal - this.ordinal` (reverse natural order). Preserve this direction if you touch it — `printProductReport` relies on the sort order being descending.

## Conventions

- Package-private constructors on domain classes are intentional (see above) — do not widen visibility to "fix" a compilation error from outside the package; add a factory method on `ProductManager` instead.
- `Rateable#convert(int)` maps integers to `Rating` via `Rating.values()[rating]`, so the declaration order in the `Rating` enum (`NOT_RATED, ONE_STAR, ..., FIVE_STAR`) doubles as the numeric scale. Do not reorder enum constants.
- Git branch for Claude-driven changes in this repo: `claude/add-claude-documentation-RwKqI` (per repo instructions).
