Automated Parking Management System (ParkEngine)
A Low-Level Design (LLD) and Java Swing desktop application implementing an automated multi-floor parking lot system. Built around core Object-Oriented Design (OOD) principles, this project demonstrates clean component decoupling and software design patterns.   
TXT
+ 1

🏛️ System Architecture & Design Patterns
The backend separates domain entities, strategy algorithms, and presentation logic through classic software design patterns:   
TXT

Singleton Pattern: ParkingLot serves as the centralized, thread-safe system controller managing active state across all floors.   
TXT

Factory Pattern: VehicleFactory encapsulates the instantiation logic for various vehicle types (Bike, Car, Truck).   
TXT

Observer Pattern: ParkingObserver provides reactive updates. ParkingLotGUI subscribes to ParkingFloor state changes to update the live display board automatically when vehicles enter or exit.   
TXT
+ 1

Strategy Pattern: Decouples business logic across three configurable modules:

Parking Strategy: FirstAvailableStrategy identifies available spots matching vehicle criteria across floors.   
TXT

Pricing Strategy: NormalPricingStrategy and WeekendPricingStrategy dynamically compute parking fees.   
TXT

Payment Strategy: UPIPayment, CardPayment, and CashPayment isolate transaction handling.   
TXT

✨ Features
Multi-Floor & Spot Type Compatibility: Categorizes parking capacity (BIKE, CAR, TRUCK) and enforces strict spot-fitting logic per vehicle type.   
TXT

Real-time Live Display Board: Event-driven Swing UI updates available spot counts in real time per floor.   
TXT

Automated Gate Operations: Integrated EntryGate for ticket generation and ExitGate for billing and spot release.   
TXT

Dynamic Fee Calculation: Calculates duration in hours from arrival to exit and applies the configured pricing strategy.   
TXT

Vehicle Lookup & Active Records: Indexing by ticket number and vehicle registration number for search operations and duplicate entry prevention.   
TXT

📂 File & Repository Structure
Plaintext
.
├── ParkEngineApplication.java   # Core system implementation (Domain Models, Strategies, GUI & Launcher)
└── README.md                    # System documentation
Key Class Breakdown
Component	Class / Interface	Description
Launcher	ParkEngineApplication	
Application entry point configuring System Look & Feel and Swing EDT. 
TXT

GUI View	ParkingLotGUI	
Swing dashboard implementing ParkingObserver for reactive UI updates. 
TXT

System Core	ParkingLot	
Thread-safe Singleton managing active tickets, floor registries, and operations. 
TXT

Domain Models	Vehicle, ParkingSpot, ParkingFloor, ParkingTicket	
Core domain entities handling state, capacity, and timestamps. 
TXT

Gates	EntryGate, ExitGate	
Controllers for check-in ticket issuing and check-out payment processing. 
TXT

💳 Pricing Strategy Matrix
Vehicle Type	Normal Rate (NormalPricingStrategy)	Weekend Rate (WeekendPricingStrategy)
Bike	
₹20 / hour 
TXT

₹40 / hour 
TXT

Car	
₹50 / hour 
TXT

₹100 / hour 
TXT

Truck	
₹100 / hour 
TXT

₹200 / hour[cite: 3]

🚀 Getting Started
Prerequisites
Java Development Kit (JDK): Version 8 or higher.

Compilation and Execution
Clone the repository:

Bash
git clone https://github.com/YOUR_USERNAME/parking-management-system.git
cd parking-management-system
Compile the source file:

Bash
javac ParkEngineApplication.java
Run the application:

Bash
java ParkEngineApplication
