# Academic Management System

An Object-Oriented Java system designed to model and manage complex university academic structures. The application parses external datasets to manage students, faculty members, departments, academic programs, and course enrollments, while generating formatted academic reports and calculating GPAs.

## Features

- **Domain Entity Management:** Models real-world academic hierarchy including Department, Program, Course, AcademicMember, and Student.
- **Data Structures & Optimization:** Leverages LinkedHashMap and ArrayList to maintain insertion order and ensure O(1) / O(n) optimized data retrieval.
- **Grade & GPA Calculation:** Computes weighted GPA metrics based on course credits and letter grades (A1 to F3).
- **Custom Exception Handling:** Implements specialized exceptions (StudentNotFoundException, InvalidGradeException, etc.) to enforce strict validation during file parsing.
- **Report Generation & File I/O:** Generates comprehensive summary and transcript reports exported to text files using Java FileWriter.

## Architecture & Core Concepts

- **Object-Oriented Programming (OOP):** Uses abstraction (AcademicEntity), interfaces (Person, Reportable), and clean separation of concerns.
- **Data Parsing:** Processes CSV-formatted text inputs (persons.txt, departments.txt, programs.txt, courses.txt, assignments.txt, grades.txt).
- **Polymorphism:** Standardized reporting engine using the Reportable interface across different domain entities.

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
