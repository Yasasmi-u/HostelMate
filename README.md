# HostelMate – University Hostel Management System

HostelMate is a Java CLI-based hostel management system developed for the CMJD Coursework 01 assignment at the Institute of Software Engineering.

The system manages hostel rooms, students, bed allocations, transfers, occupancy tracking, overdue fines, and reports using only 2D String arrays as required by the coursework constraints.

---

## Features

### Authentication
- Login validation system
- Username/password verification

### Room Management
- Add room
- Update room
- Delete room
- Search room
- View all rooms
- Sort rooms by fee
- Sort rooms by available beds

### Student Management
- Add student
- Update student
- Delete student
- Search student
- View all students

### Bed Allocation
- Allocate beds to students
- Automatic lowest available bed selection
- Occupancy tracking

### Vacate Bed
- Student check-out
- Overdue fine calculation
- Bed release and availability update

### Transfers
- Transfer students between rooms
- Preserve allocation details
- Update occupancy records

### Reports
- Occupancy map
- Vacant beds by floor
- Students per room
- Overdue dues report
- Revenue projection

---

## Technologies Used

- Java
- CLI / Console Application
- Java Scanner
- LocalDate API
- 2D String Arrays

---

## Coursework Constraints Followed

- Single Java class only
- Uses only 2D String arrays
- No ArrayLists or collections
- Manual sorting algorithms
- Manual searching algorithms
- String parsing using:
  - `Integer.parseInt()`
  - `Double.parseDouble()`
  - `Integer.toString()`
  - `Double.toString()`

---

## How to Run

### Compile
```bash
javac HostelMate.java
```

### Run
```bash
java HostelMate
```

---

## Default Login Credentials

```text
Username: warden
Password: 1234
```

---

## Sample Functionalities

### Room Allocation
- Allocates the lowest available bed automatically
- Updates room availability dynamically

### Overdue Fine Calculation
- Calculates overdue charges using:
```text
Fine = overdueDays × feePerDay
```

### Manual Sorting
- Bubble sort implementation for:
  - Fee per day
  - Available beds

---

## Validation Implemented

- Unique room IDs
- Unique student IDs
- Email validation
- Contact number validation
- Capacity validation
- Date validation
- Room availability checks
- Active student checks

---

## Academic Information

Course: CMJD Coursework 01  
Institute: Institute of Software Engineering (IJSE)

---

## Author

W.R.U. Yasasmie
