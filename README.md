# Handoff — Package Tracker

A Java console app for front-desk mailrooms. Log arrivals from any carrier, search by name or tracking number, move packages through pickup stages, and flag fragile or oversized packages for special handling.

Built as a self-taught learning project — one concept, one commit at a time.

---

## About

Front desks handle packages from every direction — Amazon, UPS, FedEx, USPS, and more. When someone asks *"did my package arrive?"*, staff shouldn't be digging through boxes or a paper log.

Handoff is a lightweight console app that keeps everything in one place: who the package is for, where it's stored, when it arrived, what stage it's in, and whether it needs special handling. Built for a real small-business need (under 20 packages a day). No database, no internet, no setup.

## Features

- Log packages with recipient, tracking number, carrier, date, and location
- Auto-timestamp arrivals, or enter a date manually (validated as a real calendar date)
- Search by recipient name or tracking number (partial, case-insensitive)
- Track each package through four stages: **Arrived → Notified → Ready for pickup → Released**
- Mark a package as picked up (a shortcut for the Released stage)
- Delete packages by tracking number or recipient name, with a choice when several names match
- View pending pickups (every package not yet released)
- Special package types — fragile, large, or both — with handling warnings wherever the package appears
- Input validation with re-prompts: empty or malformed data is never accepted
- Crash-proof menu: invalid input is rejected, never fatal
- Fast tracking-number lookup using a HashMap index
- Persistent storage — type and status survive between sessions
- Older save files still load (the old true/false pickup values are converted automatically)
- Runs the same on Mac and Windows

## How It Works

Launch Handoff and a menu appears with nine options:

1. Add a package
2. Search by recipient name
3. Search by tracking number
4. Show all packages
5. Mark a package as picked up
6. Show pending pickups
7. Delete a package
8. Update package status
9. Quit

When adding a package, you choose its type — regular, fragile, large, or both — and the matching warnings appear wherever that package is shown.

Packages are saved to a local CSV file after every change. Close the app, come back tomorrow, and everything is still there. The `data/` folder is not committed to Git, so a fresh clone starts empty and creates the file on the first save.

## Project Structure

```
src/
├── HandoffPacketTracker.java   Main entry point and menu loop
├── PackageService.java         Add, search, update, delete, and filter operations
├── FileStorage.java            CSV save and load
├── InputHelper.java            Reusable validation and re-prompt helpers
├── Parcel.java                 The base package class
├── PackageStatus.java          Enum: the four workflow stages
├── FragilePackage.java         Extends Parcel — fragile handling
├── LargePackage.java           Extends Parcel — oversized handling
├── FragileLargePackage.java    Extends Parcel — both behaviors
├── Fragile.java                Interface for the fragile warning
└── Large.java                  Interface for the large warning
```

## Getting Started

Requires Java 17 or newer.

```
git clone https://github.com/MikeTech509/HandOff-Package-Tracker.git
cd HandOff-Package-Tracker
javac -d out src/*.java
java -cp out HandoffPacketTracker
```

## Concepts Applied

- **Encapsulation** — private fields with validated setters
- **Inheritance** — three package types extend `Parcel`
- **Polymorphism** — the same `displayInfo()` call behaves differently by actual type
- **Interfaces with default methods** — `Fragile` and `Large` let one class combine behaviors
- **Enums** — `PackageStatus` replaces a yes/no flag with named stages the compiler can check
- **Constructors** — `super()` chaining, so an object is valid the moment it exists
- **Collections** — an `ArrayList` for ordered iteration and a `HashMap` for instant lookup by tracking number
- **Exception handling and `try-with-resources`** — bad input and file errors are handled, and files always close
- **Defensive loading** — blank or malformed CSV lines are skipped, and old file formats are converted
- Static utility classes, file I/O, and Git workflow across two machines

## Known Limitations

- The CSV format can't store commas inside a field, such as "Smith, John" — a database would remove this limit
- Duplicate tracking numbers are not yet blocked when adding a package

## Roadmap

**Done**
- [x] Full CRUD: add, search, update status, delete
- [x] Four-stage status workflow using an enum
- [x] Type-aware, backward-compatible CSV storage
- [x] Constructors, input validation, and a crash-proof menu
- [x] Auto-timestamps with validated manual dates
- [x] HashMap index for fast lookup

**Next**
- [ ] Duplicate tracking-number check
- [ ] Edit an existing package
- [ ] Email notifications (will move packages to Notified)
- [ ] SQLite database
- [ ] Unit tests with JUnit
- [ ] GUI or web interface

## About the Author

Built by Miketchly-Zar Jean-Francois. Handoff started as a real need at my company and became my first serious programming project. I designed the features, wrote and debugged every line, and shipped it — treating AI as a tutor to accelerate my learning, the way earlier generations used books and Stack Overflow.

---

*Small tool. Real problem. Built to be used.*