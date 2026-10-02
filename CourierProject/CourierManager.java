import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CourierManager {
    // Collections Framework to manage records dynamically
    private static ArrayList<Parcel> parcelList = new ArrayList<>();
    private static final String FILE_NAME = "parcels_log.csv";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadData(); // Automatically load saved data on startup
        
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n=== Courier & Parcel Delivery Tracker ===");
            System.out.println("1. Add New Parcel");
            System.out.println("2. View All Parcels");
            System.out.println("3. Search Parcels by Status (e.g., Transit/Delivered)");
            System.out.println("4. Delete a Parcel Record");
            System.out.println("5. Exit & Save");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1: addParcel(); break;
                    case 2: viewAll(); break;
                    case 3: searchByStatus(); break;
                    case 4: deleteParcel(); break;
                    case 5: 
                        saveData(); // Automatically save data before exiting
                        isRunning = false; 
                        System.out.println("System exited safely. Have a great day!");
                        break;
                    default:
                        System.out.println("Invalid option! Please select between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                // Handling runtime errors gracefully
                System.out.println("Error: Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear the bad input to prevent infinite loop
            }
        }
        scanner.close();
    }

    private static void addParcel() {
        System.out.print("Enter Tracking ID (e.g., P101): ");
        String id = scanner.nextLine();
        System.out.print("Enter Recipient Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Destination City: ");
        String destination = scanner.nextLine();
        System.out.print("Enter Status (e.g., Transit, Delivered, Pending): ");
        String status = scanner.nextLine();

        parcelList.add(new Parcel(id, name, destination, status));
        System.out.println("Parcel record added successfully!");
    }

    private static void viewAll() {
        if (parcelList.isEmpty()) {
            System.out.println("No parcels in the system.");
            return;
        }
        System.out.println("\n--- All Parcel Records ---");
        for (Parcel p : parcelList) {
            System.out.println(p.toString());
        }
    }

    private static void searchByStatus() {
        System.out.print("Enter Status to search (e.g., Transit, Delivered): ");
        String searchStatus = scanner.nextLine();
        boolean found = false;
        
        System.out.println("\n--- Search Results ---");
        for (Parcel p : parcelList) {
            if (p.getStatus().equalsIgnoreCase(searchStatus)) {
                System.out.println(p.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No parcels found with status: " + searchStatus);
        }
    }

    private static void deleteParcel() {
        System.out.print("Enter Tracking ID to delete: ");
        String id = scanner.nextLine();
        
        boolean removed = parcelList.removeIf(p -> p.getTrackingID().equalsIgnoreCase(id));
        
        if (removed) {
            System.out.println("Parcel record deleted successfully.");
        } else {
            System.out.println("Error: Tracking ID not found.");
        }
    }

    // --- Data Persistence via File I/O ---
    private static void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    parcelList.add(new Parcel(data[0], data[1], data[2], data[3]));
                }
            }
            System.out.println("Previous records loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous records found. A new local file will be created on exit.");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private static void saveData() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Parcel p : parcelList) {
                pw.println(p.toCSV());
            }
            System.out.println("All data saved automatically to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}