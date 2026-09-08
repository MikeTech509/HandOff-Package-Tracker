# Handoff — Package Tracker

A Java package tracker for front-desk mailrooms. Log arrivals from any carrier, search by name or tracking number, mark packages as picked up or delete them, and automatically flag fragile or oversized packages for special handling.

Built as a self-taught learning project — one concept, one commit at a time.

---

## About

Front desks handle packages from every direction — Amazon, UPS, FedEx, USPS, and more. When someone asks *"did my package arrive?"*, staff shouldn't be digging through boxes or a paper log.

Handoff is a lightweight console app that keeps everything in one place: who the package is for, where it's stored, when it arrived, whether it's been picked up, and whether it needs special handling. Built for a real small-business need (under 20 packages/day). No database, no internet, no setup.

## Features

- Log packages with recipient, tracking number, carrier, date, and location
- **Auto-timestamp arrivals** with today's date, or enter one manually (validated as a real calendar date)
- Search by recipient name or tracking number (partial, case-insensitive)
- Mark packages as picked up
- **Delete packages** by tracking number or recipient name — with disambiguation when multiple names match
- View pending pickups only
- **Special package types** — fragile and/or oversized packages automatically display handling warnings
- Input validation with re-prompts — the app never accepts empty or malformed data
- Crash-proof menu — invalid input is rejected gracefully, never crashes the app
- Persistent storage — packages and their types survive across sessions
- Cross-platform — runs identically on Mac and Windows

## How It Works

Launch Handoff and a menu appears with 8 options: add, search by recipient, search by tracking, show all, mark picked up, show pending, delete, or quit.

When adding a package, you choose its type — regular, fragile, oversized, or both — and each displays its own warning automatically wherever it appears, in search results or the full listing.

All packages are saved to a local CSV file after every change. Close the app, come back tomorrow — everything's still there, including package type.

## Project Structure
src/
├── HandoffPacketTracker.java Main entry point + menu loop
├── PackageService.java Add, search, mark, delete, and filter operations
├── FileStorage.java CSV save/load logic
├── InputHelper.java Reusable input validation and re-prompt helpers
├── Parcel.java The base package class
├── FragilePackage.java Extends Parcel — fragile handling
├── LargePackage.java Extends Parcel — oversized handling
├── FragileLargePackage.java Extends Parcel — both behaviors combined
├── Fragile.java Interface defining fragile-warning behavior
└── Large.java Interface defining large-warning behavior


## Getting Started

Requires Java 17 or newer.

git clone https://github.com/MikeTech509/HandOff-Package-Tracker.git
cd HandOff-Package-Tracker
javac src/*.java -d out
java -cp out HandoffPacketTracker


## Concepts Applied

Handoff is a working exercise in real Java concepts, applied to solve an actual problem:

- **Encapsulation** — private fields with validated getters/setters
- **Inheritance** — `FragilePackage`, `LargePackage`, and `FragileLargePackage` all extend `Parcel`
- **Polymorphism** — the same method call produces different behavior depending on the actual object type
- **Interfaces with default methods** — `Fragile` and `Large` let a single class combine multiple behaviors without the limits of single inheritance
- **Constructors** — parameterized construction with `super()` chaining, guaranteeing every object is valid the moment it's created
- **Defensive programming** — malformed input, out-of-range menu choices, and corrupted data lines are all caught and handled gracefully instead of crashing
- **Exception handling** — `try/catch` for invalid numbers (`NumberFormatException`) and invalid dates (`DateTimeParseException`)
- File I/O, CSV serialization, and type-aware persistence using `instanceof`
- Cross-platform file handling and Git workflow across two machines

## Roadmap

**Done**
- [x] Full CRUD (add, search, mark picked up, delete, view all/pending)
- [x] File persistence with type-aware CSV serialization
- [x] Encapsulation, inheritance, polymorphism, and interfaces
- [x] Constructors for guaranteed-valid object creation
- [x] Input-layer validation with re-prompts
- [x] Crash-proof menu input
- [x] Auto-timestamp with validated manual date entry
- [x] Delete by tracking number or recipient name

**Next**
- [ ] HashMap for fast tracking-number lookup
- [ ] Unit tests with JUnit
- [ ] SQLite database backend
- [ ] Spring Boot REST API
- [ ] Web or GUI interface

## About the Author

Built by Miketchly-Zar Jean-Francois. Handoff started as a real need at my company and became my first serious programming project. I designed the features, wrote and debugged every line, and shipped it — treating AI as a tutor to accelerate my learning, the way earlier generations used books and Stack Overflow.

---

*Small tool. Real problem. Built to be used.*