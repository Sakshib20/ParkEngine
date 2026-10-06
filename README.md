# 🚗 ParkEngine — Automated Parking Management System

![Java Version](https://img.shields.io/badge/Java-8%2B-orange.svg)
![Architecture](https://img.shields.io/badge/Architecture-LLD%20%26%20Design%20Patterns-blue.svg)
![GUI](https://img.shields.io/badge/UI-Java%20Swing-green.svg)

> An automated multi-floor parking lot management system built in Java. Implements Low-Level Design (LLD) principles, Gang of Four (GoF) design patterns, and an event-driven Swing Graphical User Interface.

---

## 🏛️ System Architecture & Design Patterns

ParkEngine separates presentation, domain models, and core business logic using object-oriented design patterns:

* **Singleton Pattern (`ParkingLot`)**: Guarantees a single centralized controller managing active ticket registries, floor capacity, and vehicle lookup tables across the application runtime.
* **Factory Pattern (`VehicleFactory`)**: Centralizes object creation for `Bike`, `Car`, and `Truck` instances, encapsulating subclass instantiation logic.
* **Observer Pattern (`ParkingObserver`)**: Establishes reactive synchronization between `ParkingFloor` (Subject) and display boards (`ParkingLotGUI`, `ParkingDisplayBoard`). Automatically recalculates and broadcasts available spots when vehicles enter or exit.
* **Strategy Pattern**:
  * **`ParkingStrategy`**: Pluggable spot allocation algorithm (`FirstAvailableStrategy`).
  * **`PricingStrategy`**: Dynamic pricing calculations (`NormalPricingStrategy`, `WeekendPricingStrategy`).
  * **`PaymentStrategy`**: Decoupled transaction handling (`CashPayment`, `UPIPayment`, `CardPayment`).

---

## ✨ Features

* **Multi-Floor Capacity Engine**: Dedicated spot classification (`BIKE`, `CAR`, `TRUCK`) enforcing strict spot-fitting constraints per vehicle type.
* **Real-Time UI Dashboard**: Event-driven Swing interface providing real-time spot availability updates per floor.
* **Automated Gate Operations**: Integrated check-in at `EntryGate` (ticket generation) and check-out at `ExitGate` (fee calculation and spot release).
* **Dynamic Billing Engine**: Hourly duration calculation based on arrival and departure timestamps.
* **Vehicle Search**: Direct lookups for active tickets using vehicle registration numbers.

---

## 📂 Project Structure

```text
.
├── ParkEngineApplication.java   # Complete application driver (Domain Models, Strategies, GUI & Launcher)
└── README.md                    # Project documentation
```
---

## 🧩 Core Component Reference

- **Launcher** (`ParkEngineApplication`): Application entry point configuring System Look & Feel and launching the Swing Event Dispatch Thread (EDT).

- **GUI View** (`ParkingLotGUI`): Reactive Swing dashboard implementing `ParkingObserver` to render live parking capacity.

- **Core Manager** (`ParkingLot`): Thread-safe Singleton managing active tickets, floor registries, and parking/exit workflows.

- **Domain Models** (`Vehicle`, `ParkingSpot`, `ParkingFloor`, `ParkingTicket`): Core domain entities holding vehicle details, spot allocations, and timestamps.

- **Gates** (`EntryGate`, `ExitGate`): Controllers handling ticket issuance, duration calculation, and payment execution.

---

## 💳 Pricing Structure

- **Bike**: ₹20 / hour (`NormalPricingStrategy`) | ₹40 / hour (`WeekendPricingStrategy`)

- **Car**: ₹50 / hour (`NormalPricingStrategy`) | ₹100 / hour (`WeekendPricingStrategy`)

- **Truck**: ₹100 / hour (`NormalPricingStrategy`) | ₹200 / hour (`WeekendPricingStrategy`)

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher.

### Compilation & Execution

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Sakshib20/ParkEngine.git
   cd ParkEngine

2. **Compile the source file**:
   ```bash
   javac ParkEngineApplication.java
   
3.**Run the application**:
   ```bash
   java ParkEngineApplication
