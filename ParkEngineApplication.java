import java.time.Duration;
import java.time.LocalDateTime;

// Explicit java.util imports
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

// Explicit AWT imports (do NOT use import java.awt.*)
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

// Swing imports
import javax.swing.*;
import javax.swing.border.EmptyBorder;


enum VehicleType {
    BIKE,
    CAR,
    TRUCK
}

enum SpotType {
    BIKE,
    CAR,
    TRUCK
}

enum TicketStatus {
    ACTIVE,
    CLOSED
}



abstract class Vehicle {

    private String vehicleNumber;
    private VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;

    }

    public VehicleType getVehicleType() {
        return this.vehicleType;
    }

    public String getVehicleNumber() {
        return this.vehicleNumber;

    }

    public abstract void display();

}

class Bike extends Vehicle {
    public Bike(String vehicleNumber) {
        // calls vehicle class constructor
        super(vehicleNumber, VehicleType.BIKE);
    }

    @Override
    public void display() {
        System.out.println("Bike : " + getVehicleNumber());
    }
}

class Car extends Vehicle {
    public Car(String vehicleNumber) {
        // calls vehicle class constructor
        super(vehicleNumber, VehicleType.CAR);
    }

    @Override
    public void display() {
        System.out.println("Car : " + getVehicleNumber());
    }
}

class Truck extends Vehicle {
    public Truck(String vehicleNumber) {
        // calls vehicle class constructor
        super(vehicleNumber, VehicleType.TRUCK);
    }

    @Override
    public void display() {
        System.out.println("Truck : " + getVehicleNumber());
    }
}



class VehicleFactory {
    // creates and returns the desired class object
    public static Vehicle createVehicle(VehicleType type, String number) {
        switch (type) {
            case BIKE:
                return new Bike(number);

            case CAR:
                return new Car(number);

            case TRUCK:
                return new Truck(number);

            default:
                throw new IllegalArgumentException("Invalid Vehicle type");
        }
    }
}


abstract class ParkingSpot {
    private int spotNumber;

    private SpotType spotType;

    private boolean occupied;

    private Vehicle vehicle;

    public ParkingSpot(int spotNumber, SpotType spotType) {
        this.spotNumber = spotNumber;
        this.spotType = spotType;

        this.occupied = false;
        this.vehicle = null;
    }

    public int getSpotNumber() {
        return this.spotNumber;
    }

    public SpotType getSpotType() {
        return this.spotType;
    }

    public boolean isOccupied() {
        return this.occupied;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    // it is used to park the vehicle
    public void parkVehicle(Vehicle vehicle) {
        if (this.occupied == true) {
            throw new RuntimeException("Parking spot is already occupied");
        } else {
            this.vehicle = vehicle;
            this.occupied = true;
        }
    }

    public Vehicle removeVehicle() {
        if (this.occupied == true) {
            Vehicle temp = vehicle;
            this.vehicle = null;
            this.occupied = false;

            return temp;
        }

        else {
            throw new RuntimeException("Parking spot is already empty");
        }
    }

    public abstract boolean canFitVehicle(Vehicle vehicle);

    public void display() {
        System.out.println("Spot : " + spotNumber + "[ " + spotType + " ]");

        if (this.occupied == true) {
            System.out.println("Occupied by : " + vehicle.getVehicleNumber());

        } else {
            System.out.println("Spot is available");
        }
    }

} // end of parking spot class

class BikeSpot extends ParkingSpot {
    public BikeSpot(int spotNumber) {
        super(spotNumber, SpotType.BIKE);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle) {
        if (vehicle.getVehicleType() == VehicleType.BIKE) {
            return true;
        } else {
            return false;
        }
    }
}

class CarSpot extends ParkingSpot {
    public CarSpot(int spotNumber) {
        super(spotNumber, SpotType.CAR);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle) {
        if (vehicle.getVehicleType() == VehicleType.CAR) {
            return true;
        } else {
            return false;
        }
    }
}

class TruckSpot extends ParkingSpot {
    public TruckSpot(int spotNumber) {
        super(spotNumber, SpotType.TRUCK);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle) {
        if (vehicle.getVehicleType() == VehicleType.TRUCK) {
            return true;
        } else {
            return false;
        }
    }
}



interface ParkingObserver {
    void update();
}



class ParkingFloor {
    private int floorNumber;


    private List<ParkingSpot> parkingSpots;

    private List<ParkingObserver> observers;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;

        this.parkingSpots = new ArrayList<>();

        this.observers = new ArrayList<>();
    }

    public int getFloorNumber() {
        return this.floorNumber;
    }

    public void addParkingSpot(ParkingSpot spot) {
        parkingSpots.add(spot);
    }

    public void addObserver(ParkingObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (ParkingObserver observer : observers) {

            observer.update();
        }
    }

    public ParkingSpot findAvailableSpot(Vehicle vehicle) {
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isOccupied() && spot.canFitVehicle(vehicle)) {
                return spot;
            }
        }
        return null;
    }

    public void occupySpot(ParkingSpot spot, Vehicle vehicle) {
        spot.parkVehicle(vehicle);

        notifyObservers();
    }

    public void releaseSpot(ParkingSpot spot) {
        spot.removeVehicle();

        notifyObservers();

    }

    public int getAvailableCount(SpotType type) {
        int count = 0;
        for (ParkingSpot spot : parkingSpots) {
            if (spot.getSpotType() == type && !spot.isOccupied()) {
                count++;
            }
        }

        return count;
    }

    public void displayFloor() {
        System.out.println("Floor : " + floorNumber);

        for (ParkingSpot spot : parkingSpots) {
            spot.display();
        }
    }
}



class ParkingDisplayBoard implements ParkingObserver {

    private ParkingFloor floor;

    public ParkingDisplayBoard(ParkingFloor floor) {
        this.floor = floor;

    }

    @Override
    public void update() {
        System.out.println();
        System.out.println("----------- Display Board ------------");
        System.out.println("Floor : " + floor.getFloorNumber());
        System.out.println("Available Bike Spots : " + floor.getAvailableCount(SpotType.BIKE));
        System.out.println("Available Bike Spots : " + floor.getAvailableCount(SpotType.CAR));
        System.out.println("Available Bike Spots : " + floor.getAvailableCount(SpotType.TRUCK));
        System.out.println("--------------------------------------");
        System.out.println();

    }
}


interface ParkingStrategy {
    ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}


class FirstAvailableStrategy implements ParkingStrategy {
    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        // itterate over all available floors
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.findAvailableSpot(vehicle);

            if (spot != null) {
                return spot;
            }

        }
        return null;
    }
}


interface PricingStrategy {
    double calculatePrice(Vehicle vehicle, long hours);
}

class NormalPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Vehicle vehicle, long hours) {
        if (hours <= 0) {
            hours = 1;
        }

        switch (vehicle.getVehicleType()) {
            case BIKE:
                return hours * 20;

            case CAR:
                return hours * 50;

            case TRUCK:
                return hours * 100;

            default:
                return 0;

        }
    }
}

class WeekendPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(Vehicle vehicle, long hours) {
        if (hours <= 0) {
            hours = 1;
        }

        switch (vehicle.getVehicleType()) {
            case BIKE:
                return hours * 40;

            case CAR:
                return hours * 100;

            case TRUCK:
                return hours * 200;

            default:
                return 0;
        }
    }
}



interface PaymentStrategy {
    void pay(double amount);
}

class UPIPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("UPI Payment successfull : Rs. " + amount);
    }
}

class CardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Card Payment successfull : Rs. " + amount);
    }
}

class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Cash Payment successfull : Rs. " + amount);
    }
}



class ParkingTicket {
    private static int counter = 1000;

    private int ticketNumber;

    private Vehicle vehicle;

    private ParkingFloor floor;

    private ParkingSpot spot;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private TicketStatus status;

    public ParkingTicket(
            Vehicle vehicle,
            ParkingFloor floor,
            ParkingSpot spot) {
        this.ticketNumber = ++counter;
        this.vehicle = vehicle;
        this.floor = floor;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.status = TicketStatus.ACTIVE;

    }

    public int getTicketNumber() {
        return this.ticketNumber;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public ParkingFloor getFloor() {
        return this.floor;
    }

    public ParkingSpot getSpot() {
        return this.spot;
    }

    public LocalDateTime getEntryTime() {
        return this.entryTime;
    }

    public LocalDateTime getExitTime() {
        return this.exitTime;
    }

    public TicketStatus getStatus() {
        return this.status;
    }

    public void closeTicket() {
        this.exitTime = LocalDateTime.now();
        this.status = TicketStatus.CLOSED;
    }

    public long calculateHours() {
        LocalDateTime endtime;

        if (exitTime == null) {
            endtime = LocalDateTime.now();
        }

        else {
            endtime = exitTime;
        }

        // calculates actual time

        long minutes = Duration.between(entryTime, endtime).toMinutes();

        // converts minutes to hours
        long hours = minutes / 60;

        if ((minutes % 60) != 0) {
            hours++;
        }

        if (hours == 0) {
            hours = 0;
        }

        return hours;

    }

    // it will display complete ticket on screen
    public void displayTicket() {

        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("----------- Parking Ticket -----------");
        System.out.println("--------------------------------------");

        System.out.println("Ticket Number : " + this.ticketNumber);

        System.out.println("Vehicle Number : " + this.vehicle.getVehicleNumber());

        System.out.println("Vehicle type : " + this.vehicle.getVehicleType());

        System.out.println("Floor Number : " + this.floor.getFloorNumber());

        System.out.println("Spot Number : " + this.spot.getSpotNumber());

        System.out.println("Entry Time : " + this.entryTime);

        System.out.println("Exit Time : " + this.exitTime);

        System.out.println("Status  : " + this.status);
        System.out.println("--------------------------------------");

        System.out.println();
    }

}



class EntryGate {
    private int gateNumber;

    public EntryGate(int gateNumber) {
        this.gateNumber = gateNumber;
    }

    public int getGateNumber() {
        return this.gateNumber;
    }


    public ParkingTicket generateTicket(Vehicle vehicle, ParkingFloor floor, ParkingSpot spot) {
        System.out.println("Vehicle entering from Gate : " + this.gateNumber);

        return new ParkingTicket(vehicle, floor, spot);
    }

}


class ExitGate {
    private int gateNumber;

    public ExitGate(int gateNumber) {
        this.gateNumber = gateNumber;
    }

    public void processExit(
            ParkingTicket ticket,
            PricingStrategy pricingStrategy,
            PaymentStrategy paymentStrategy) {

                ticket.closeTicket();

        long hours = ticket.calculateHours();

        double amount = pricingStrategy.calculatePrice(ticket.getVehicle(), hours);

        System.out.println("Vehicle exiting from gate : " + this.gateNumber);

        System.out.println("Parking Duration : " + hours);

        System.out.println("Parking charges : " + amount);


        paymentStrategy.pay(amount);
    }

}



class ParkingLot {
    // instance of class
    private static ParkingLot instance;

    // store the parking lot name
    private String parkingLotName;

    // stores all floors of the parking lot
    private List<ParkingFloor> floors;

    // maps the ticket number with active parking slot
    private Map<Integer, ParkingTicket> activeTickets;



    private Map<String, ParkingTicket> vehicleTicketMap;

    private ParkingStrategy parkingStrategy;

    private PricingStrategy pricingStrategy;

    // private constructor for singleton class
    private ParkingLot() {
        this.floors = new ArrayList<>();
        activeTickets = new HashMap<>();
        vehicleTicketMap = new HashMap<>();

        // default parking strategy

        parkingStrategy = new FirstAvailableStrategy();

        // default pricing strategy

        pricingStrategy = new NormalPricingStrategy();
    }


    // Method to return the singleton class object
    public static synchronized ParkingLot getInstance()
    {
        if(instance == null)
        {
            instance = new ParkingLot();
        }

        return instance;
    }


    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    // used to add new parking floor

    public void addFloor(ParkingFloor floor) {
        // insert in arrayList

        floors.add(floor);
    }

    // this method returns list of all floors
    public List<ParkingFloor> getFloors() {
        return floors;
    }

    public void setParkingStrategy(ParkingStrategy strategy) {

        this.parkingStrategy = strategy;
    }

    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
    }

    

    public ParkingTicket parkVehicle(
            Vehicle vehicle,
            EntryGate entryGate) {

        if (vehicleTicketMap.containsKey(vehicle.getVehicleNumber())) {
            System.out.println("This vehicle is already parked");

            throw new RuntimeException("This vehicle is already parked");
        }

        ParkingSpot spot = parkingStrategy.findSpot(floors, vehicle);


        if (spot == null) {
            throw new RuntimeException("Parking is FULL");
        }


        ParkingFloor selectedFloor = null;

        for (ParkingFloor floor : floors) {
            ParkingSpot temp = floor.findAvailableSpot(vehicle);

            if (temp == spot) {
                selectedFloor = floor;
                break;
            }
        }
        if (selectedFloor == null) {
                throw new RuntimeException("Enable to identify floor");
            }


        selectedFloor.occupySpot(spot, vehicle);


        ParkingTicket ticket = entryGate.generateTicket(vehicle, selectedFloor, spot);

        activeTickets.put(ticket.getTicketNumber(),ticket);

        vehicleTicketMap.put(vehicle.getVehicleNumber(), ticket);

        return ticket;
    }
    

    public void removeVehicle(
            int ticketNumber,
            ExitGate exitGate,
            PaymentStrategy paymentStrategy) 
            {

                ParkingTicket ticket = activeTickets.get(ticketNumber);

        if (ticket == null) {
            throw new RuntimeException("There is no such ticket");
        }

        exitGate.processExit(ticket, pricingStrategy, paymentStrategy);

        ticket.getFloor().releaseSpot(ticket.getSpot());

        activeTickets.remove(ticketNumber);

        vehicleTicketMap.remove(ticket.getVehicle().getVehicleNumber());

        System.out.println("Vehicle removed successfully");
    }

    // search the specified method

    public ParkingTicket searchVehicle(String vehicleNumber) {
        return vehicleTicketMap.get(vehicleNumber);
    }

    // display complete parking lot information

    public void displayParkingLot() {
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println("-------- Parking Lot Details --------");
        System.out.println("-------------------------------------");

        for (ParkingFloor floor : floors) {
            floor.displayFloor();
        }
    }
}// end of parking lot class


class ParkingLotGUI extends JFrame implements ParkingObserver {

    private ParkingLot parkingLot;
    private EntryGate entryGate;
    private ExitGate exitGate;

    // GUI Form Controls
    private JComboBox<VehicleType> comboVehicleType;
    private JTextField txtParkVehicleNum;

    private JTextField txtExitTicketNum;
    private JComboBox<String> comboPaymentType;

    private JTextField txtSearchVehicleNum;

    private JTextArea txtDisplayBoard;

    public ParkingLotGUI() {
        initBackendSystem();
        initUI();
        refreshDisplayBoard();
    }

    private void initBackendSystem() {
        parkingLot = ParkingLot.getInstance();
        parkingLot.setParkingLotName("Marvellous ParkEngine");

        // Initialize Floor 1
        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addParkingSpot(new BikeSpot(101));
        floor1.addParkingSpot(new BikeSpot(102));
        floor1.addParkingSpot(new CarSpot(103));
        floor1.addParkingSpot(new CarSpot(104));
        floor1.addParkingSpot(new TruckSpot(105));
        floor1.addParkingSpot(new TruckSpot(106));
        floor1.addObserver(this);

        // Initialize Floor 2
        ParkingFloor floor2 = new ParkingFloor(2);
        floor2.addParkingSpot(new BikeSpot(201));
        floor2.addParkingSpot(new BikeSpot(202));
        floor2.addParkingSpot(new CarSpot(203));
        floor2.addParkingSpot(new CarSpot(204));
        floor2.addParkingSpot(new TruckSpot(205));
        floor2.addParkingSpot(new TruckSpot(206));
        floor2.addObserver(this);

        parkingLot.addFloor(floor1);
        parkingLot.addFloor(floor2);

        entryGate = new EntryGate(1);
        exitGate = new ExitGate(1);
    }

    private void initUI() {
        setTitle("Marvellous ParkEngine - Management System");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 41, 59));
        JLabel lblTitle = new JLabel("MARVELLOUS PARKENGINE CONTROL CENTER");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // Center Split
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplit.setDividerLocation(480);
        mainSplit.setLeftComponent(createControlPanel());
        mainSplit.setRightComponent(createDisplayBoardPanel());

        add(mainSplit, BorderLayout.CENTER);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. Park Vehicle Section
        JPanel parkPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        parkPanel.setBorder(BorderFactory.createTitledBorder("1. Park Vehicle (Entry Gate)"));

        parkPanel.add(new JLabel("Vehicle Type:"));
        comboVehicleType = new JComboBox<>(VehicleType.values());
        parkPanel.add(comboVehicleType);

        parkPanel.add(new JLabel("Vehicle Number:"));
        txtParkVehicleNum = new JTextField();
        parkPanel.add(txtParkVehicleNum);

        JButton btnPark = new JButton("Park Vehicle");
        btnPark.addActionListener(this::handleParkVehicle);
        parkPanel.add(new JLabel());
        parkPanel.add(btnPark);

        panel.add(parkPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 2. Exit Vehicle Section
        JPanel exitPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        exitPanel.setBorder(BorderFactory.createTitledBorder("2. Exit Vehicle (Exit Gate)"));

        exitPanel.add(new JLabel("Ticket Number:"));
        txtExitTicketNum = new JTextField();
        exitPanel.add(txtExitTicketNum);

        exitPanel.add(new JLabel("Payment Method:"));
        comboPaymentType = new JComboBox<>(new String[]{"Cash", "UPI", "Card"});
        exitPanel.add(comboPaymentType);

        JButton btnExit = new JButton("Process Exit");
        btnExit.addActionListener(this::handleExitVehicle);
        exitPanel.add(new JLabel());
        exitPanel.add(btnExit);

        panel.add(exitPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // 3. Search Vehicle Section
        JPanel searchPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("3. Search Vehicle"));

        searchPanel.add(new JLabel("Vehicle Number:"));
        txtSearchVehicleNum = new JTextField();
        searchPanel.add(txtSearchVehicleNum);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(this::handleSearchVehicle);
        searchPanel.add(new JLabel());
        searchPanel.add(btnSearch);

        panel.add(searchPanel);

        return panel;
    }

    private JPanel createDisplayBoardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("----- Live Display Board ----"));

        txtDisplayBoard = new JTextArea();
        txtDisplayBoard.setEditable(false);
        txtDisplayBoard.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtDisplayBoard.setBackground(new Color(248, 250, 252));

        panel.add(new JScrollPane(txtDisplayBoard), BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void update() {
        refreshDisplayBoard();
    }

    private void refreshDisplayBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("         PARKING LOT LIVE STATUS         \n");
        sb.append("=========================================\n\n");

        for (ParkingFloor floor : parkingLot.getFloors()) {
            sb.append("Floor ").append(floor.getFloorNumber()).append(" Availability:\n");
            sb.append(" - Bike Spots Free  : ").append(floor.getAvailableCount(SpotType.BIKE)).append("\n");
            sb.append(" - Car Spots Free   : ").append(floor.getAvailableCount(SpotType.CAR)).append("\n");
            sb.append(" - Truck Spots Free : ").append(floor.getAvailableCount(SpotType.TRUCK)).append("\n");
            sb.append("-----------------------------------------\n");
        }

        txtDisplayBoard.setText(sb.toString());
    }

    private void handleParkVehicle(ActionEvent e) {
        String number = txtParkVehicleNum.getText().trim();
        if (number.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a vehicle number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        VehicleType type = (VehicleType) comboVehicleType.getSelectedItem();

        try {
            Vehicle vehicle = VehicleFactory.createVehicle(type, number);
            ParkingTicket ticket = parkingLot.parkVehicle(vehicle, entryGate);

            JOptionPane.showMessageDialog(this,
                    "Vehicle Parked Successfully!\n" +
                            "Ticket Number: " + ticket.getTicketNumber() + "\n" +
                            "Floor: " + ticket.getFloor().getFloorNumber() + "\n" +
                            "Spot: " + ticket.getSpot().getSpotNumber(),
                    "Ticket Issued", JOptionPane.INFORMATION_MESSAGE);

            txtParkVehicleNum.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Parking Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleExitVehicle(ActionEvent e) {
        String ticketStr = txtExitTicketNum.getText().trim();
        if (ticketStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a ticket number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int ticketNumber = Integer.parseInt(ticketStr);
            String payOption = (String) comboPaymentType.getSelectedItem();

            PaymentStrategy strategy;
            if ("UPI".equalsIgnoreCase(payOption)) {
                strategy = new UPIPayment();
            } else if ("Card".equalsIgnoreCase(payOption)) {
                strategy = new CardPayment();
            } else {
                strategy = new CashPayment();
            }

            parkingLot.removeVehicle(ticketNumber, exitGate, strategy);

            JOptionPane.showMessageDialog(this, "Vehicle removed successfully. Payment processed.", "Exit Success", JOptionPane.INFORMATION_MESSAGE);
            txtExitTicketNum.setText("");

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Ticket number must be integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Exit Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSearchVehicle(ActionEvent e) {
        String number = txtSearchVehicleNum.getText().trim();
        if (number.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a vehicle number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ParkingTicket ticket = parkingLot.searchVehicle(number);
        if (ticket == null) {
            JOptionPane.showMessageDialog(this, "Vehicle not found in active parking lot records.", "Search Result", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Vehicle Details:\n" +
                            "Vehicle Number: " + ticket.getVehicle().getVehicleNumber() + "\n" +
                            "Ticket Number: " + ticket.getTicketNumber() + "\n" +
                            "Floor: " + ticket.getFloor().getFloorNumber() + "\n" +
                            "Spot: " + ticket.getSpot().getSpotNumber() + "\n" +
                            "Entry Time: " + ticket.getEntryTime() + "\n" +
                            "Status: " + ticket.getStatus(),
                    "Vehicle Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

//////////////////////////////////////////////////////////////////////////
// 
// Controller of the project
//
///////////////////////////////////////////////////////////////////////////


public class ParkEngineApplication {
    public static void main(String[] A)  
    {
       try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fallback to default Swing L&F if system L&F fails
        }

        SwingUtilities.invokeLater(() -> {
            ParkingLotGUI gui = new ParkingLotGUI();
            gui.setVisible(true);
        });
    }
}
     