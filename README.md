# Academic Management System

An Object-Oriented Java system designed to model and manage complex university academic structures[cite: 1, 12]. The application parses external datasets to manage students, faculty members, departments, academic programs, and course enrollments, while generating formatted academic reports and calculating GPAs[cite: 1, 12, 18].

## Features

- **Domain Entity Management:** Models real-world academic hierarchy including Department, Program, Course, AcademicMember, and Student[cite: 12, 14, 15, 16, 21].
- **Data Structures & Optimization:** Leverages LinkedHashMap and ArrayList to maintain insertion order and ensure O(1) / O(n) optimized data retrieval[cite: 1, 16, 21, 22].
- **Grade & GPA Calculation:** Computes weighted GPA metrics based on course credits and letter grades (A1 to F3)[cite: 16, 18, 21].
- **Custom Exception Handling:** Implements specialized exceptions (StudentNotFoundException, InvalidGradeException, etc.) to enforce strict validation during file parsing[cite: 1, 17, 22].
- **Report Generation & File I/O:** Generates comprehensive summary and transcript reports exported to text files using Java FileWriter[cite: 1, 15, 16, 18, 20, 21].

## Architecture & Core Concepts

- **Object-Oriented Programming (OOP):** Uses abstraction (AcademicEntity), interfaces (Person, Reportable), and clean separation of concerns[cite: 1, 12, 14, 15, 19, 20, 21].
- **Data Parsing:** Processes CSV-formatted text inputs (persons.txt, departments.txt, programs.txt, courses.txt, assignments.txt, grades.txt)[cite: 18].
- **Polymorphism:** Standardized reporting engine using the Reportable interface across different domain entities[cite: 1, 12, 15, 16, 20, 21].

## Tech Stack

- **Language:** Java 8+
- **Core Libraries:** Java Standard Library (java.util, java.io)

## How to Run

1. Clone the repository:
   git clone https://github.com/your-username/academic-management-system-java.git
   cd academic-management-system-java

2. Compile all .java files:
   javac *.java

3. Execute the system with input files:
   java Main persons.txt departments.txt programs.txt courses.txt assignments.txt grades.txt output.txt
