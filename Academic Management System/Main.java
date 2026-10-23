import java.io.*;
import java.util.*;
/**
 * Entry point of the University Student Management System.
 * Reads input files
 * initializes data structures, and writes sorted formatted reports to the output file.
 * It also handles exceptions such as not found input files and writes corresponding
 * error messages.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length != 7) {
            System.out.println("Usage: java Main persons.txt departments.txt programs.txt courses.txt assignments.txt grades.txt output.txt");
            return;
        }

        String personFile = args[0];
        String departmentFile = args[1];
        String programFile = args[2];
        String courseFile = args[3];
        String assignmentFile = args[4];
        String gradesFile = args[5];
        String outputFile = args[6];

        StudentManagementSystem system = new StudentManagementSystem();

        try (FileWriter writer = new FileWriter(outputFile)) {
            try {
                readPersons(personFile, system, writer);
            } catch (FileNotFoundException e) {
                writer.write("Error: persons.txt not found.\n");
            }
            try {
                readDepartments(departmentFile, system, writer);
            } catch (FileNotFoundException e) {
                writer.write("Error: departments.txt not found.\n");

            }
            readPrograms(programFile, system, writer);
            readCourses(courseFile, system, writer);
            readAssignments(assignmentFile, system, writer);
            readGrades(gradesFile, system, writer);



            // Academic Members
            writer.write("----------------------------------------\n");
            writer.write("            Academic Members\n");
            writer.write("----------------------------------------\n");
            List<AcademicMember> sortedMembers = new ArrayList<>(system.getAcademicMembers().values());
            sortedMembers.sort(Comparator.comparingInt(m -> Integer.parseInt(m.getId())));

            for (AcademicMember member : sortedMembers) {
                member.generateReport(writer);
            }

            writer.write("----------------------------------------\n\n");

            // Students
            writer.write("----------------------------------------\n");
            writer.write("                STUDENTS\n");
            writer.write("----------------------------------------\n");

            List<Student> sortedStudents = new ArrayList<>(system.getStudents().values());
            sortedStudents.sort(Comparator.comparingInt(s -> Integer.parseInt(s.getId())));

            for (Student student : sortedStudents) {
                student.generateShortReport(writer);
            }
            writer.write("----------------------------------------\n\n");


            // Departments
            writer.write("---------------------------------------\n");
            writer.write("              DEPARTMENTS\n");
            writer.write("---------------------------------------\n");
            for (Department dept : system.getDepartments().values()) {
                dept.generateReport(writer);
            }
            writer.write("----------------------------------------\n\n");

            // Programs
            writer.write("--------------------------------------\n");
            writer.write("                PROGRAMS\n");
            writer.write("---------------------------------------\n");
            List<Program> sortedPrograms = new ArrayList<>(system.getPrograms().values());
            sortedPrograms.sort(Comparator.comparing(Program::getCode));

            for (Program program : sortedPrograms) {
                program.generateReport(writer);
            }

            writer.write("----------------------------------------\n\n");


            writer.write("---------------------------------------\n");
            writer.write("                COURSES\n");
            writer.write("---------------------------------------\n");

            List<Course> sortedCourses = new ArrayList<>(system.getCourses().values());
            sortedCourses.sort(Comparator.comparing(Course::getCode));

            for (Course course : sortedCourses) {
                course.generateShortReport(writer);

            }
            writer.write("----------------------------------------\n\n");


            writer.write("----------------------------------------\n");
            writer.write("             COURSE REPORTS\n");
            writer.write("----------------------------------------\n");

            sortedCourses.sort(Comparator.comparing(Course::getCode));

            for (int i = 0; i < sortedCourses.size(); i++) {
                Course course = sortedCourses.get(i);
                course.setReportOrder(i);
                course.setTotalCourseCount(sortedCourses.size());
                course.generateReport(writer);
            }




            // Student Reports
            writer.write("----------------------------------------\n");
            writer.write("            STUDENT REPORTS\n");
            writer.write("----------------------------------------\n");

            for (int i = 0; i < sortedStudents.size(); i++) {
                Student student = sortedStudents.get(i);
                student.setReportOrder(i);
                student.setTotalStudentCount(sortedStudents.size());
                student.generateReport(writer);
            }


        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found → " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error writing to output file: " + e.getMessage());
        }

    }
    /**
     * Helper reading functions for initializing system data.
     * These methods use <code>throw</code> to signal exceptional conditions
     * (such as missing or invalid data), and handle them using <code>try-catch</code> blocks and throw keyword
     * to ensure proper error logging and program flow.
     */


    private static void readPersons(String filename, StudentManagementSystem system, FileWriter writer) throws IOException {
        writer.write("Reading Person Information \n");
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length != 5) continue;

            String type = parts[0].trim();
            String id = parts[1].trim();
            String name = parts[2].trim();
            String email = parts[3].trim();
            String department = parts[4].trim();

            try {
                if (!type.equals("S") && !type.equals("F")) {
                    throw new InvalidPersonTypeException(type);
                }

                if (type.equals("S")) {
                    system.addStudent(new Student(id, name, email, department));
                } else {
                    system.addAcademicMember(new AcademicMember(id, name, email, department));
                }

            } catch (InvalidPersonTypeException e) {
                writer.write(e.getMessage() + "\n");
            }
        }
        br.close();
    }



    private static void readDepartments(String file, StudentManagementSystem sys, FileWriter w) throws IOException {
        w.write("Reading Departments \n");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split(",");
            if (p.length < 4) continue;
            String code = p[0].trim(), name = p[1].trim(), desc = p[2].trim(), headId = p[3].trim();
            Department d = new Department(code, name, desc);
            sys.addDepartment(d);
            try {
                sys.assignDepartmentHead(code, headId);
            } catch (Exception e) {
                w.write("Academic Member Not Found with ID " + headId + "\n");
            }
        }
        br.close();
    }

    private static void readPrograms(String file, StudentManagementSystem sys, FileWriter w) throws IOException {
        w.write("Reading Programs \n");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split(",");
            if (p.length < 6) continue;
            String code = p[0].trim(), name = p[1].trim(), desc = p[2].trim(), depName = p[3].trim(), level = p[4].trim();
            int credits = Integer.parseInt(p[5].trim());
            Department dept = null;
            for (Department d : sys.getDepartments().values()) {
                if (d.getName().equalsIgnoreCase(depName)) {
                    dept = d; break;
                }
            }
            try {
                if (dept == null) {
                    throw new DepartmentNotFoundException(depName);
                }
            } catch (DepartmentNotFoundException e) {
                w.write(e.getMessage() + "\n");
                continue;
            }

            Program program = new Program(code, name, desc, dept, level, credits);
            sys.addProgram(program);
        }
        br.close();
    }

    private static void readCourses(String filename, StudentManagementSystem system, FileWriter writer) throws IOException {
        writer.write("Reading Courses \n");
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 6) continue;

            String code = parts[0].trim();
            String name = parts[1].trim();
            String departmentName = parts[2].trim();
            int credits = Integer.parseInt(parts[3].trim());
            String semester = parts[4].trim();
            String programCode = parts[5].trim();

            Program program = system.getPrograms().get(programCode);
            try {
                if (program == null) {
                    throw new ProgramNotFoundException(programCode);
                }
            } catch (ProgramNotFoundException e) {
                writer.write(e.getMessage() + "\n");
                continue;
            }


            Course course = new Course(code, name, departmentName, credits, semester, program);
            system.addCourse(course);
            program.addCourse(course);
        }
        br.close();
    }


    private static void readAssignments(String file, StudentManagementSystem sys, FileWriter writer) throws IOException {
        writer.write("Reading Course Assignments \n");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            String[] p = line.split(",");
            if (p.length != 3) continue;

            String type = p[0].trim();
            String id = p[1].trim();
            String courseCode = p[2].trim();

            if (type.equals("F")) {
                try {
                    AcademicMember m = sys.getAcademicMember(id);
                    if (m == null) throw new AcademicMemberNotFoundException(id);

                    try {
                        Course c = sys.getCourse(courseCode);
                        if (c == null) throw new CourseNotFoundException(courseCode);

                        c.setInstructor(m);
                        m.assignCourse(c);
                    } catch (CourseNotFoundException e) {
                        writer.write(e.getMessage() + "\n");
                    }

                } catch (AcademicMemberNotFoundException e) {
                    writer.write(e.getMessage() + "\n");
                }

            } else if (type.equals("S")) {
                try {
                    Student s = sys.getStudent(id);
                    if (s == null) throw new StudentNotFoundException(id);

                    try {
                        Course c = sys.getCourse(courseCode);
                        if (c == null) throw new CourseNotFoundException(courseCode);

                        s.enrollCourse(c);
                        c.enrollStudent(s);
                    } catch (CourseNotFoundException e) {
                        writer.write(e.getMessage() + "\n");
                    }

                } catch (StudentNotFoundException e) {
                    writer.write(e.getMessage() + "\n");
                }

            } else {
                writer.write("Invalid Person Type: " + type + "\n");
            }
        }
        br.close();

    }
    public static boolean isValidGrade(String grade) {
        List<String> validGrades = Arrays.asList("A1", "A2", "B1", "B2", "C1", "C2", "D1", "D2", "F3");
        return validGrades.contains(grade);
    }



    private static void readGrades(String file, StudentManagementSystem sys, FileWriter w) throws IOException {
        w.write("Reading Grades \n");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            String[] p = line.split(",");
            if (p.length != 3) continue;

            String grade = p[0].trim();
            String studentId = p[1].trim();
            String courseCode = p[2].trim();

            try {
                sys.assignGradeToStudent(studentId, courseCode, grade);
            } catch (Exception e) {
                w.write(e.getMessage() + "\n");
            }
        }

        br.close();
    }


    // Convert letter grades to numbers for GPA calculation
    public static double convertGradeToPoint(String grade) {
        switch (grade) {
            case "A1": return 4.0;
            case "A2": return 3.5;
            case "B1": return 3.0;
            case "B2": return 2.5;
            case "C1": return 2.0;
            case "C2": return 1.5;
            case "D1": return 1.0;
            case "D2": return 0.5;
            case "F3": return 0.0;
            default: return 0.0;
        }
    }
}
