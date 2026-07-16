# Handoff — Package Tracker

A Java package tracker for front-desk mailrooms. Log arrivals from any carrier, search by name or tracking number, and mark packages as picked up.

---

## About the Project

Front desks handle packages from every direction — Amazon, UPS, FedEx, USPS, DHL, and more. When someone asks "did my package arrive?", staff shouldn't be digging through a stack of boxes or a paper log. Handoff is a lightweight console app that keeps everything organized in one place: who the package is for, where it's stored, when it arrived, and whether it's been picked up.

Built as a real tool for a small business (under 20 packages per day), Handoff prioritizes simplicity over complexity. It runs on any computer with Java installed and requires no setup, no database, and no internet connection.

## Features

- **Log packages** with recipient name, tracking number, carrier, date received, and physical location
- **Search by recipient** with partial, case-insensitive matching (typing "rob" finds "Roberto")
- **Search by tracking number** with the same flexible matching
- **Mark packages as picked up** to close out deliveries
- **View pending pickups** — see only what's still waiting
- **View all packages** — full inventory at a glance
- **Interactive menu** — no commands to memorize; pick an option and go

## How It Works
