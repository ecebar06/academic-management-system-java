import java.util.*;
import java.io.*;
/**
 * Represents a student with basic info, enrolled and completed courses,
 * and GPA calculation. Supports generating short and full report outputs.
 */
public class Student implements Person, Reportable {

    private String id;
    private String name;
    private String email;
    private String department;

    private List<Course> enrolledCourses;
    private Map<Course, String> completedCoursesWithGrades;
    private int reportOrder;
    private int totalStudentCount;
    /**
     * Used to determine whether this is the last student (for spacing).
     */
    public void setReportOrder(int order) {
        this.reportOrder = order;
    }

    public void setTotalStudentCount(int total) {
        this.totalStudentCount = total;
    }

    public Student(String id, String name, String email, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.enrolledCourses = new ArrayList<>();
        this.completedCoursesWithGrades = new LinkedHashMap<>();
    }

    // Getter's come from interface Person
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    public void enrollCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void assignGrade(Course course, String grade) {
        completedCoursesWithGrades.put(course, grade);
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Map<Course, String> getCompletedCoursesWithGrades() {
        return completedCoursesWithGrades;
    }
    /**
     * Writes a short version of the student’s info to the report.
     */
    public void generateShortReport(FileWriter writer) throws IOException {
        writer.write("Student ID: " + getId() + "\n");
        writer.write("Name: " + getName() + "\n");
        writer.write("Email: " + getEmail() + "\n");
        writer.write("Major: " + getDepartment() + "\n");
        writer.write("Status: Active\n\n");
    }
    /**
     * Writes the full student report, including:
     * personal info, enrolled/completed courses, GPA,
     * and spacing rules for the last student.
     */
    @Override
    public void generateReport(FileWriter writer) throws IOException {
        writer.write("Student ID: " + getId() + "\n");
        writer.write("Name: " + getName() + "\n");
        writer.write("Email: " + getEmail() + "\n");
        writer.write("Major: " + getDepartment() + "\n");
        writer.write("Status: Active\n\n\n");


        writer.write("Enrolled Courses:\n");

        for (Course c : enrolledCourses) {
            if (!completedCoursesWithGrades.containsKey(c)) {
                writer.write("- " + c.getName() + " (" + c.getCode() + ")\n");
            }
        }

        writer.write("\nCompleted Courses:\n");

        double total = 0;
        int totalCredits = 0;
        int studentCount = 0;
        for (Map.Entry<Course, String> entry : completedCoursesWithGrades.entrySet()) {
            Course c = entry.getKey();
            String grade = entry.getValue();
            writer.write("- " + c.getName() + " (" + c.getCode() + "): " + grade + "\n");
            total += Main.convertGradeToPoint(grade) * c.getCredits();
            totalCredits += c.getCredits();
            studentCount++;
        }

        double gpa = totalCredits == 0 ? 0.0 : total / totalCredits;
        writer.write("\nGPA: " + String.format("%.2f", gpa));

        if (reportOrder == totalStudentCount - 1) {
            writer.write("\n\n");
        } else {
            writer.write("\n");
        }

        writer.write("----------------------------------------\n\n");

    }
}
