import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class HostelMate {

    static Scanner input = new Scanner(System.in);

    // 2D String arrays
    static String[][] rooms = new String[100][6];     // roomId, floor, roomNo, capacity, feePerDay, availableBeds
    static String[][] students = new String[100][5];  // studentId, name, contact, email, status
    static String[][] allocations = new String[100][5];
    static String[][] occupancy = new String[100][10];

    public static void main(String[] args) {
        login();
        mainMenu();
    }


    // ========== LOGIN ==========
    static void login() {
        while (true) {
            System.out.println("=== HostelMate Login ===");
            System.out.print("Username: ");
            String user = input.nextLine();
            System.out.print("Password: ");
            String pass = input.nextLine();


            if (user.equals("warden")) {
                if (pass.equals("1234")) {
                    System.out.println("\n✅ Login successful. Welcome, " + user + "!");
                    break;
                } else {
                    System.out.println("❌ Incorrect password. Try again.\n");
                }
            } else {
                System.out.println("❌ Invalid username. Try again.\n");
            }
        }
    }

    // ========== MAIN MENU ==========
    static void mainMenu() {
        while (true) {
            System.out.println("\n=== HostelMate ===");
            System.out.println("1) Manage Rooms");
            System.out.println("2) Manage Students");
            System.out.println("3) Allocate Bed");
            System.out.println("4) Vacate Bed");
            System.out.println("5) Transfers");
            System.out.println("6) View Reports");
            System.out.println("7) Exit");
            System.out.print("Choose: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> manageRooms();
                case "2" -> manageStudents();
                case "3" -> allocateBed();
                case "4" -> vacateBed();
                case "5" -> transferStudent();
                case "6" -> viewReports();
                case "7" -> {
                    System.out.println("Exiting system... Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ========== MANAGE ROOMS ==========
    static void manageRooms() {
        System.out.println("\n=== Manage Rooms ===");
        System.out.println("1) Add Room");
        System.out.println("2) Update Room");
        System.out.println("3) Delete Room");
        System.out.println("4) Search Room");
        System.out.println("5) View All Rooms");
        System.out.println("6) Sort Rooms by Fee");
        System.out.println("7) Sort Rooms by Available Beds");
        System.out.println("8) Back");
        System.out.print("Choose: ");
        String choice = input.nextLine();

        switch (choice) {
            case "1" -> addRoom();
            case "2" -> updateRooms();
            case "3" -> deleteRooms();
            case "4" -> searchRooms();
            case "5" -> viewAllRooms();
            case "6" -> sortRoomsByFee();
            case "7" -> sortRoomsByAvailableBeds();
            case "8" -> { return; }
            default -> System.out.println("Invalid choice!");
        }
    }

    static void addRoom() {
        System.out.println("\n=== Add Room ===");

        System.out.print("Room ID: ");
        String id = input.nextLine();

        // Step 11 → Check for duplicate roomId
        for (String[] r : rooms) {
            if (r[0] != null && r[0].equalsIgnoreCase(id)) {
                System.out.println("❌ Room ID already exists!");
                return;
            }
        }

        // Step 8 → Get room details
        System.out.print("Floor: ");
        String floor = input.nextLine();

        System.out.print("Room No: ");
        String roomNo = input.nextLine();

        System.out.print("Capacity: ");
        int capacity;
        try {
            capacity = Integer.parseInt(input.nextLine());
            if (capacity <= 0) {
                System.out.println("❌ Capacity must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number for capacity.");
            return;
        }

        System.out.print("Fee/Day (LKR): ");
        double fee;
        try {
            fee = Double.parseDouble(input.nextLine());
            if (fee < 0) {
                System.out.println("❌ Fee cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number for fee.");
            return;
        }

        // Step 10 → Initialize availableBeds = capacity (converted to String)
        String availableBeds = Integer.toString(capacity);

        // Step 9 + store into array
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][0] == null) {
                rooms[i][0] = id;
                rooms[i][1] = floor;
                rooms[i][2] = roomNo;
                rooms[i][3] = Integer.toString(capacity);
                rooms[i][4] = Double.toString(fee);
                rooms[i][5] = availableBeds;
                System.out.println("\n✅ Room added successfully!");
                System.out.println("Available beds: " + availableBeds);
                break;
            }
        }
    }

    static void updateRooms() {
        System.out.println("\n=== Update Room ===");
        System.out.print("Room ID to update: ");
        String roomId = input.nextLine();

        // Search for the room
        int index = -1;
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][0] != null && rooms[i][0].equalsIgnoreCase(roomId)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("❌ Room not found!");
            return;
        }

        // Display current details
        System.out.println("Current Details:");
        System.out.printf("Floor: %s | Room No: %s | Capacity: %s | Fee/Day: %s | Avail Beds: %s%n",
                rooms[index][1], rooms[index][2], rooms[index][3], rooms[index][4], rooms[index][5]);

        System.out.print("New Floor (or '-' to skip): ");
        String floor = input.nextLine();
        System.out.print("New Room No (or '-' to skip): ");
        String roomNo = input.nextLine();
        System.out.print("New Capacity (or '-' to skip): ");
        String capInput = input.nextLine();
        System.out.print("New Fee/Day (or '-' to skip): ");
        String feeInput = input.nextLine();

        if (!floor.equals("-")) rooms[index][1] = floor;
        if (!roomNo.equals("-")) rooms[index][2] = roomNo;

        if (!capInput.equals("-")) {
            try {
                int newCap = Integer.parseInt(capInput);
                int availBeds = Integer.parseInt(rooms[index][5]);
                int oldCap = Integer.parseInt(rooms[index][3]);
                int occupied = oldCap - availBeds;
                if (newCap < occupied) {
                    System.out.println("❌ Cannot reduce capacity below occupied beds.");
                    return;
                }
                rooms[index][3] = Integer.toString(newCap);
                rooms[index][5] = Integer.toString(newCap - occupied);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid capacity input.");
                return;
            }
        }

        if (!feeInput.equals("-")) {
            try {
                double newFee = Double.parseDouble(feeInput);
                if (newFee < 0) {
                    System.out.println("❌ Fee cannot be negative.");
                    return;
                }
                rooms[index][4] = Double.toString(newFee);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid fee input.");
                return;
            }
        }

        System.out.printf("✅ Updated: %s | Floor=%s | RoomNo=%s | Capacity=%s | Fee/Day=%s | Avail=%s%n",
                rooms[index][0], rooms[index][1], rooms[index][2],
                rooms[index][3], rooms[index][4], rooms[index][5]);
    }

    static void deleteRooms() {
        System.out.println("\n=== Delete Room ===");
        System.out.print("Enter Room ID: ");
        String roomId = input.nextLine();

        int index = -1;
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][0] != null && rooms[i][0].equalsIgnoreCase(roomId)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("❌ Room not found!");
            return;
        }

        // Compact array (shift rows left)
        for (int i = index; i < rooms.length - 1; i++) {
            rooms[i] = rooms[i + 1];
        }
        rooms[rooms.length - 1] = new String[6]; // empty last row

        System.out.println("✅ Room deleted successfully.");
    }

    static void searchRooms() {
        System.out.println("\n=== Search Room ===");
        System.out.print("Enter Room ID: ");
        String roomId = input.nextLine();

        for (String[] r : rooms) {
            if (r[0] != null && r[0].equalsIgnoreCase(roomId)) {
                System.out.println("Found:");
                System.out.println("ID\tFloor\tNo\tCap\tAvail\tFee/Day");
                System.out.println("------------------------------------");
                System.out.printf("%s\t%s\t%s\t%s\t%s\t%s%n",
                        r[0], r[1], r[2], r[3], r[5], r[4]);
                return;
            }
        }

        System.out.println("❌ Room not found!");
    }




    static void viewAllRooms() {
        System.out.println("\nAll Rooms");
        System.out.println("ID\tFloor\tNo\tCap\tAvail\tFee/Day");
        System.out.println("------------------------------------------");
        for (String[] r : rooms) {
            if (r[0] != null) {
                System.out.printf("%s\t%s\t%s\t%s\t%s\t%s%n", r[0], r[1], r[2], r[3], r[5], r[4]);
            }
        }
    }

    static void sortRoomsByFee() {
        System.out.println("\n=== Sort Rooms by Fee (Ascending) ===");

        // Simple bubble sort
        for (int i = 0; i < rooms.length - 1; i++) {
            if (rooms[i][0] == null) break;
            for (int j = i + 1; j < rooms.length; j++) {
                if (rooms[j][0] == null) break;

                double fee1 = Double.parseDouble(rooms[i][4]);
                double fee2 = Double.parseDouble(rooms[j][4]);

                if (fee1 > fee2) {
                    // Swap the rows
                    String[] temp = rooms[i];
                    rooms[i] = rooms[j];
                    rooms[j] = temp;
                }
            }
        }

        System.out.println("✅ Rooms sorted successfully by Fee/Day!");
        viewAllRooms(); // Show sorted list
    }


    static void sortRoomsByAvailableBeds() {
        System.out.println("\n=== Sort Rooms by Available Beds (Descending) ===");

        for (int i = 0; i < rooms.length - 1; i++) {
            if (rooms[i][0] == null) break;
            for (int j = i + 1; j < rooms.length; j++) {
                if (rooms[j][0] == null) break;

                int avail1 = Integer.parseInt(rooms[i][5]);
                int avail2 = Integer.parseInt(rooms[j][5]);

                if (avail1 < avail2) {
                    String[] temp = rooms[i];
                    rooms[i] = rooms[j];
                    rooms[j] = temp;
                }
            }
        }

        System.out.println("✅ Rooms sorted successfully by Available Beds!");
        viewAllRooms();
    }

    // ========== MANAGE STUDENTS ==========
    static void manageStudents() {
        while (true) {
            System.out.println("\n=== Manage Students ===");
            System.out.println("1) Add Student");
            System.out.println("2) Update Student");
            System.out.println("3) Delete Student");
            System.out.println("4) Search Student");
            System.out.println("5) View All Students");
            System.out.println("6) Back to Main Menu");
            System.out.print("Choose: ");
            String choice = input.nextLine();

            switch (choice) {
                case "1" -> addStudent();
                case "2" -> updateStudent();
                case "3" -> deleteStudent();
                case "4" -> searchStudent();
                case "5" -> viewAllStudents();
                case "6" -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }


    static void addStudent() {
        System.out.println("\n=== Add Student ===");

        System.out.print("Student ID: ");
        String id = input.nextLine();

        // Check duplicate
        for (String[] s : students) {
            if (s[0] != null && s[0].equalsIgnoreCase(id)) {
                System.out.println("❌ Student ID already exists!");
                return;
            }
        }

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Contact: ");
        String contact = input.nextLine();
        if (!contact.matches("\\d{10}")) {
            System.out.println("❌ Invalid contact number. Must have 10 digits.");
            return;
        }

        System.out.print("Email: ");
        String email = input.nextLine();
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("❌ Invalid email format.");
            return;
        }

        // Add to array
        for (int i = 0; i < students.length; i++) {
            if (students[i][0] == null) {
                students[i][0] = id;
                students[i][1] = name;
                students[i][2] = contact;
                students[i][3] = email;
                students[i][4] = "ACTIVE";
                System.out.println("✅ Student added successfully!");
                System.out.println("Status: ACTIVE");
                break;
            }
        }
    }

    static void updateStudent() {
        System.out.println("\n=== Update Student ===");
        System.out.print("Student ID to update: ");
        String id = input.nextLine();

        int index = -1;
        for (int i = 0; i < students.length; i++) {
            if (students[i][0] != null && students[i][0].equalsIgnoreCase(id)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("❌ Student not found!");
            return;
        }

        System.out.print("New Contact (or - to skip): ");
        String contact = input.nextLine();
        System.out.print("New Email (or - to skip): ");
        String email = input.nextLine();

        if (!contact.equals("-")) {
            if (!contact.matches("\\d{10}")) {
                System.out.println("❌ Invalid contact number.");
                return;
            }
            students[index][2] = contact;
        }

        if (!email.equals("-")) {
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("❌ Invalid email format.");
                return;
            }
            students[index][3] = email;
        }

        System.out.printf("✅ Updated: %s | %s | %s | %s | %s%n",
                students[index][0], students[index][1],
                students[index][2], students[index][3], students[index][4]);
    }

    static void deleteStudent() {
        System.out.println("\n=== Delete Student ===");
        System.out.print("Enter Student ID: ");
        String id = input.nextLine();

        int index = -1;
        for (int i = 0; i < students.length; i++) {
            if (students[i][0] != null && students[i][0].equalsIgnoreCase(id)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("❌ Student not found!");
            return;
        }

        // Compact the array
        for (int i = index; i < students.length - 1; i++) {
            students[i] = students[i + 1];
        }
        students[students.length - 1] = new String[5];

        System.out.println("✅ Student deleted successfully.");
    }

    static void searchStudent() {
        System.out.println("\n=== Search Student ===");
        System.out.print("Enter Student ID: ");
        String id = input.nextLine();

        for (String[] s : students) {
            if (s[0] != null && s[0].equalsIgnoreCase(id)) {
                System.out.println("Found:");
                System.out.println("ID\tName\t\tContact\t\tEmail\t\t\tStatus");
                System.out.println("---------------------------------------------------------------");
                System.out.printf("%s\t%s\t%s\t%s\t%s%n",
                        s[0], s[1], s[2], s[3], s[4]);
                return;
            }
        }

        System.out.println("❌ Student not found!");
    }



    static void viewAllStudents() {
        System.out.println("\n=== All Students ===");
        System.out.println("ID\tName\t\tContact\t\tEmail\t\t\tStatus");
        System.out.println("---------------------------------------------------------------");
        for (String[] s : students) {
            if (s[0] != null) {
                System.out.printf("%s\t%s\t%s\t%s\t%s%n",
                        s[0], s[1], s[2], s[3], s[4]);
            }
        }
    }

    static void allocateBed() {
        while (true) {
            System.out.println("\n=== Allocate Bed ===");

            System.out.print("Student ID: ");
            String studentId = input.nextLine();

            int studentIndex = -1;
            for (int i = 0; i < students.length; i++) {
                if (students[i][0] != null && students[i][0].equalsIgnoreCase(studentId)) {
                    if (!students[i][4].equalsIgnoreCase("ACTIVE")) {
                        System.out.println("❌ Student is not ACTIVE. Try again.\n");
                        continue; // back to start
                    }
                    studentIndex = i;
                    break;
                }
            }
            if (studentIndex == -1) {
                System.out.println("❌ Student not found! Try again.\n");
                continue;
            }

            System.out.print("Room ID: ");
            String roomId = input.nextLine();

            int roomIndex = -1;
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i][0] != null && rooms[i][0].equalsIgnoreCase(roomId)) {
                    int available = Integer.parseInt(rooms[i][5]);
                    if (available <= 0) {
                        System.out.println("❌ No available beds in this room! Try again.\n");
                        continue;
                    }
                    roomIndex = i;
                    break;
                }
            }
            if (roomIndex == -1) {
                System.out.println("❌ Room not found! Try again.\n");
                continue;
            }

            System.out.print("Due Date (YYYY-MM-DD): ");
            String dueDateStr = input.nextLine();

            // ✅ Date validation
            LocalDate checkInDate = LocalDate.now();
            LocalDate dueDate;
            try {
                dueDate = LocalDate.parse(dueDateStr);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format! Use YYYY-MM-DD.");
                return;
            }

            // ✅ Allow test mode for past dates
            if (dueDate.isBefore(checkInDate)) {
                System.out.print("❌ Due date is before today. Continue for testing? (y/n): ");
                if (!input.nextLine().equalsIgnoreCase("y")) return;
            }


            // If all inputs are valid → allocate
            int bedIndex = 0;
            for (int i = 0; i < occupancy[roomIndex].length; i++) {
                if (occupancy[roomIndex][i] == null || occupancy[roomIndex][i].equals("EMPTY")) {
                    bedIndex = i;
                    break;
                }
            }

            for (int i = 0; i < allocations.length; i++) {
                if (allocations[i][0] == null) {
                    allocations[i][0] = studentId;
                    allocations[i][1] = roomId;
                    allocations[i][2] = Integer.toString(bedIndex);
                    allocations[i][3] = checkInDate.toString();
                    allocations[i][4] = dueDate.toString();
                    break;
                }
            }

            int avail = Integer.parseInt(rooms[roomIndex][5]) - 1;
            rooms[roomIndex][5] = Integer.toString(avail);
            occupancy[roomIndex][bedIndex] = studentId;

            System.out.printf("✅ Allocated: %s → Room %s Bed %d%n", studentId, roomId, bedIndex);
            System.out.println("Available beds (" + roomId + "): " + avail);
            break; // exit loop after success
        }
    }


    static void vacateBed() {
        System.out.println("\n=== Vacate Bed (Check-Out) ===");

        System.out.print("Student ID: ");
        String studentId = input.nextLine();

        System.out.print("Room ID: ");
        String roomId = input.nextLine();

        // ✅ Find the allocation
        int allocIndex = -1;
        for (int i = 0; i < allocations.length; i++) {
            if (allocations[i][0] != null &&
                allocations[i][0].equalsIgnoreCase(studentId) &&
                allocations[i][1].equalsIgnoreCase(roomId)) {
                allocIndex = i;
                break;
            }
        }

        if (allocIndex == -1) {
            System.out.println("❌ No active allocation found for this student in the given room!");
            return;
        }

        // ✅ Get details
        String bedIndexStr = allocations[allocIndex][2];
        String dueDateStr = allocations[allocIndex][4];
        int bedIndex = Integer.parseInt(bedIndexStr);
        LocalDate dueDate = LocalDate.parse(dueDateStr);
        LocalDate currentDate = LocalDate.now();

        // ✅ Find room fee
        double feePerDay = 0;
        int roomIndex = -1;
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][0] != null && rooms[i][0].equalsIgnoreCase(roomId)) {
                roomIndex = i;
                feePerDay = Double.parseDouble(rooms[i][4]);
                break;
            }
        }

        if (roomIndex == -1) {
            System.out.println("❌ Room not found!");
            return;
        }

        // ✅ Calculate overdue days & fine
        long overdueDays = 0;
        if (currentDate.isAfter(dueDate)) {
            overdueDays = ChronoUnit.DAYS.between(dueDate, currentDate);
        }

        double fine = overdueDays * feePerDay;

        System.out.printf("Overdue days: %d  | Fee/Day: %.2f  | Fine: %.2f%n",
                overdueDays, feePerDay, fine);

        // ✅ Remove allocation
        for (int i = allocIndex; i < allocations.length - 1; i++) {
            allocations[i] = allocations[i + 1];
        }
        allocations[allocations.length - 1] = new String[5]; // clear last row

        // ✅ Free the bed
        occupancy[roomIndex][bedIndex] = "EMPTY";

        // ✅ Increase available beds
        int avail = Integer.parseInt(rooms[roomIndex][5]);
        avail++;
        rooms[roomIndex][5] = Integer.toString(avail);

        System.out.println("✅ Checkout completed. Bed freed. Available beds (" + roomId + "): " + avail);
    }

    static void transferStudent() {
        System.out.println("\n=== Transfer Student ===");

        System.out.print("Student ID: ");
        String studentId = input.nextLine();

        System.out.print("From Room: ");
        String fromRoomId = input.nextLine();

        System.out.print("To Room: ");
        String toRoomId = input.nextLine();

        // ✅ Find current allocation for the student
        int allocIndex = -1;
        for (int i = 0; i < allocations.length; i++) {
            if (allocations[i][0] != null &&
                allocations[i][0].equalsIgnoreCase(studentId) &&
                allocations[i][1].equalsIgnoreCase(fromRoomId)) {
                allocIndex = i;
                break;
            }
        }

        if (allocIndex == -1) {
            System.out.println("❌ Student not allocated in the given 'From Room'.");
            return;
        }

        // ✅ Find both rooms
        int fromRoomIndex = -1, toRoomIndex = -1;
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][0] != null && rooms[i][0].equalsIgnoreCase(fromRoomId)) fromRoomIndex = i;
            if (rooms[i][0] != null && rooms[i][0].equalsIgnoreCase(toRoomId)) toRoomIndex = i;
        }

        if (fromRoomIndex == -1 || toRoomIndex == -1) {
            System.out.println("❌ One of the rooms not found!");
            return;
        }

        // ✅ Check if target room has available bed
        int availTo = Integer.parseInt(rooms[toRoomIndex][5]);
        if (availTo <= 0) {
            System.out.println("❌ Target room has no free beds!");
            return;
        }

        // ✅ Find lowest available bed index in target room
        int newBedIndex = -1;
        for (int i = 0; i < occupancy[toRoomIndex].length; i++) {
            if (occupancy[toRoomIndex][i] == null || occupancy[toRoomIndex][i].equals("EMPTY")) {
                newBedIndex = i;
                break;
            }
        }

        // ✅ Free old bed in fromRoom
        int oldBedIndex = Integer.parseInt(allocations[allocIndex][2]);
        occupancy[fromRoomIndex][oldBedIndex] = "EMPTY";

        // ✅ Update allocation to new room and bed
        allocations[allocIndex][1] = toRoomId; // new room
        allocations[allocIndex][2] = Integer.toString(newBedIndex); // new bed

        // ✅ Update available beds
        int availFrom = Integer.parseInt(rooms[fromRoomIndex][5]);
        availFrom++;  // freed one
        rooms[fromRoomIndex][5] = Integer.toString(availFrom);

        availTo--; // occupied one
        rooms[toRoomIndex][5] = Integer.toString(availTo);

        // ✅ Update occupancy grid
        occupancy[toRoomIndex][newBedIndex] = studentId;

        // ✅ Log transfer date
        String transferDate = LocalDate.now().toString();

        System.out.printf("✅ Transferred to %s Bed %d on %s%n", toRoomId, newBedIndex, transferDate);
        System.out.println("Avail (" + fromRoomId + "): " + availFrom + "  |  Avail (" + toRoomId + "): " + availTo);
    }

    static void viewReports() {
        System.out.println("\n=== View Reports ===");
        System.out.println("1) Occupancy Map");
        System.out.println("2) Vacant Beds by Floor");
        System.out.println("3) Students per Room");
        System.out.println("4) Overdue Dues");
        System.out.println("5) Revenue Projection (Daily)");
        System.out.print("Choose: ");
        String choice = input.nextLine();

        switch (choice) {
            case "1" -> showOccupancyMap();
            case "2" -> showVacantBedsByFloor();
            case "3" -> showStudentsPerRoom();
            case "4" -> showOverdueDues();
            case "5" -> showRevenueProjection();
            default -> System.out.println("❌ Invalid option!");
        }
    }

    static void showOccupancyMap() {
        System.out.println("\n=== Occupancy Grid (Rooms x Beds) ===");
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][0] != null) {
                System.out.print("RoomRow " + i + "  Beds → ");
                System.out.print("[ ");
                for (int j = 0; j < occupancy[i].length; j++) {
                    String val = occupancy[i][j];
                    if (val == null) val = "EMPTY";
                    System.out.print(val);
                    if (j < occupancy[i].length - 1) System.out.print(", ");
                }
                System.out.println(" ]");
            }
        }
    }

    static void showVacantBedsByFloor() {
        System.out.println("\n=== Vacant Beds by Floor ===");
        System.out.println("Floor | TotalRooms | TotalBeds | Occupied | Vacant");
        System.out.println("-----------------------------------------------------");

        // We’ll use small temporary arrays
        String[] floors = new String[50];
        int[] totalRooms = new int[50];
        int[] totalBeds = new int[50];
        int[] occupied = new int[50];
        int floorCount = 0;

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][0] != null) {
                String floor = rooms[i][1];
                int cap = Integer.parseInt(rooms[i][3]);
                int avail = Integer.parseInt(rooms[i][5]);
                int occ = cap - avail;

                // Find or add floor
                int index = -1;
                for (int f = 0; f < floorCount; f++) {
                    if (floors[f].equals(floor)) { index = f; break; }
                }
                if (index == -1) { index = floorCount++; floors[index] = floor; }

                totalRooms[index]++;
                totalBeds[index] += cap;
                occupied[index] += occ;
            }
        }

        for (int i = 0; i < floorCount; i++) {
            int vacant = totalBeds[i] - occupied[i];
            System.out.printf("%-6s | %-11d | %-10d | %-8d | %-7d%n",
                    floors[i], totalRooms[i], totalBeds[i], occupied[i], vacant);
        }
    }

    static void showStudentsPerRoom() {
        System.out.println("\n=== Students per Room ===");
        System.out.println("Room   | Count | Students");
        System.out.println("----------------------------");

        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i][0] != null) {
                String roomId = rooms[i][0];
                StringBuilder sb = new StringBuilder();
                int count = 0;
                for (String[] a : allocations) {
                    if (a[0] != null && a[1].equalsIgnoreCase(roomId)) {
                        if (count > 0) sb.append(", ");
                        sb.append(a[0]);
                        count++;
                    }
                }
                System.out.printf("%-6s | %-5d | %s%n", roomId, count, sb.toString());
            }
        }
    }

    static void showOverdueDues() {
        System.out.println("\n=== Overdue Dues ===");
        System.out.println("Student | Room | DaysOverdue | Fee/Day | EstimatedFine");
        System.out.println("------------------------------------------------------");

        LocalDate today = LocalDate.now();

        for (String[] a : allocations) {
            if (a[0] != null) {
                LocalDate due = LocalDate.parse(a[4]);
                if (today.isAfter(due)) {
                    long daysOver = ChronoUnit.DAYS.between(due, today);
                    String roomId = a[1];
                    double fee = 0;

                    for (String[] r : rooms) {
                        if (r[0] != null && r[0].equalsIgnoreCase(roomId)) {
                            fee = Double.parseDouble(r[4]);
                            break;
                        }
                    }

                    double fine = daysOver * fee;
                    System.out.printf("%-8s | %-5s | %-12d | %-8.2f | %.2f%n",
                            a[0], roomId, daysOver, fee, fine);
                }
            }
        }
    }

    static void showRevenueProjection() {
        System.out.println("\n=== Revenue Projection (Daily) ===");

        double total = 0;
        for (String[] a : allocations) {
            if (a[0] != null) {
                String roomId = a[1];
                for (String[] r : rooms) {
                    if (r[0] != null && r[0].equalsIgnoreCase(roomId)) {
                        total += Double.parseDouble(r[4]);
                        break;
                    }
                }
            }
        }
        System.out.printf("Total Daily Revenue: %.2f LKR%n", total);
    }

}




