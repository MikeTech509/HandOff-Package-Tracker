# Handoff — Package Tracker

A Java package tracker for front-desk mailrooms. Log arrivals from any carrier, search by name or tracking number, mark packages as picked up, and handle special package types like fragile items.

Built as a self-taught learning project — one concept, one commit at a time.

---

## About

Front desks handle packages from every direction — Amazon, UPS, FedEx, USPS, and more. When someone asks *"did my package arrive?"*, staff shouldn't be digging through boxes or a paper log.

Handoff is a lightweight console app that keeps everything in one place: who the package is for, where it's stored, when it arrived, whether it's been picked up, and whether it needs special handling. Built for a real small-business need (under 20 packages/day). No database, no internet, no setup.

## Features

- Log packages with recipient, tracking number, carrier, date, and location
- Search by recipient name (partial, case-insensitive — `"rob"` finds `"Roberto"`)
- Search by tracking number
- Mark packages as picked up
- View pending pickups only
- **Handle fragile packages** — automatically warn "⚠️ HANDLE WITH CARE" when displayed
- Persistent storage — packages and their types survive across sessions
- Input validation — the app refuses empty or malformed data
- Cross-platform — runs on Mac and Windows


## How It Works

Launch Handoff and a menu appears with 7 options: add, search by recipient, search by tracking, show all, mark picked up, show pending, or quit.

When adding a package, you're asked whether it's fragile. Fragile packages display with a warning wherever they appear — in search results, in the full listing, everywhere.

All packages are automatically saved to a local CSV file. Close the app, come back tomorrow — everything's still there, including package types.

## Project Structure
src/
├── HandoffPacketTracker.java Main entry point + menu loop
├── PackageService.java Package operations (add, search, mark, filter)
├── FileStorage.java CSV save/load logic
├── Parcel.java The base Parcel class
└── FragilePackage.java Extends Parcel with fragile-handling behavior

## Getting Started

Requires Java 17 or newer.
git clone https://github.com/MikeTech509/HandOff-Package-Tracker.git
cd HandOff-Package-Tracker
javac src/*.java -d out
java -cp out HandoffPacketTracker

## Concepts Applied

Handoff is a working exercise in real Java concepts I first learned in school and now put into practice:

- Classes, objects, and methods
- **Encapsulation** with private fields, getters, and setters
- Validation at the model layer (setters reject bad data)
- **Inheritance** — FragilePackage extends Parcel
- Method overriding with `@Override` and `super`
- **Polymorphism** — same method call, different behavior based on actual type
- ArrayList and generic collections
- `while` loops and `switch` statements
- Interactive Scanner input
- Case-insensitive partial-match search
- File I/O with try/catch exception handling
- CSV serialization/deserialization
- Type-aware persistence (using `instanceof`)
- Cross-platform file paths

## Roadmap

**Done**
- [x] Full workflow (add, search, mark, view)
- [x] File persistence (CSV save + load)
- [x] Cross-platform file paths
- [x] Encapsulation with getters, setters, and validation
- [x] Inheritance with FragilePackage
- [x] Type-aware persistence

**In Progress**
- [ ] LargePackage class (second subclass)
- [ ] Input-layer re-prompts on invalid data
- [ ] Constructor for Parcel

**Planned**
- [ ] Abstract Parcel class + interfaces
- [ ] Proper date handling (LocalDate)
- [ ] Delete a package
- [ ] Menu input validation (no more crashes)
- [ ] Auto-timestamp arrivals
- [ ] Unit tests with JUnit
- [ ] SQLite database backend
- [ ] Spring Boot REST API
- [ ] Web UI

## About the Author

Built by Miketchly-Zar  Jean-Francois. Handoff started as a real need at my company and became my first serious programming project. I designed the features, wrote and debugged every line, and shipped it — treating AI as a tutor to accelerate my learning, the way earlier generations used books and Stack Overflow.

---

*Small tool. Real problem. Built to be used.*
