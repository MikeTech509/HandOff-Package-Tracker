# Handoff — Package Tracker

A Java package tracker for front-desk mailrooms. Log arrivals from any carrier, search by name or tracking number, and mark packages as picked up.

Built as a self-taught learning project — one concept, one commit at a time.

---

## About

Front desks handle packages from every direction — Amazon, UPS, FedEx, USPS, and more. When someone asks *"did my package arrive?"*, staff shouldn't be digging through boxes or a paper log.

Handoff is a lightweight console app that keeps everything in one place: who the package is for, where it's stored, when it arrived, and whether it's been picked up. Built for a real small-business need (under 20 packages/day). No database, no internet, no setup.

## Features

- Log packages with recipient, tracking number, carrier, date, and location
- Search by recipient name (partial, case-insensitive — `"rob"` finds `"Roberto"`)
- Search by tracking number
- Mark packages as picked up
- View pending pickups only
- Persistent storage — packages survive across sessions
- Input validation — the app refuses empty or malformed data
- Cross-platform — runs on Mac and Windows too

## Getting Started

Requires Java 17 or newer.

    git clone https://github.com/MikeTech509/HandOff-Package-Tracker.git
    cd HandOff-Package-Tracker
    javac HandoffPacketTracker.java
    java HandoffPacketTracker

## Roadmap

**Done**
- [x] Full workflow (add, search, mark, view)
- [x] File persistence (CSV save + load)
- [x] Cross-platform file paths
- [x] Encapsulation with getters, setters, and validation

## Project Structure

```
src/
├── HandoffPacketTracker.java   Main entry point + menu loop
├── PackageService.java         Package operations (add, search, mark, filter)
├── FileStorage.java            CSV save/load logic
└── Parcel.java                 The Parcel data class
```

**Next**
- [ ] Input-layer re-prompts on invalid data
- [ ] Constructor for cleaner Parcel creation
- [ ] Proper date handling
- [ ] Delete a package
- [ ] Inheritance (special package types)
- [ ] SQLite backend


## About the Author

Built by Miketchly-zar Francois — self-taught developer learning by building real projects. Handoff is written and understood line by line, with guidance from Claude (Anthropic's AI) as a tutor. Concepts explained, code shipped.

---

*Small tool. Real problem. Built to be used.*
Last updated: July 24, 2026
Now works on Windows too!