import java.util.*;
import java.io.*;
/**
 * Represents an academic staff member with ID, name, email, and department.
 * Can be assigned to teach courses.
 */
public class AcademicMember implements Person, Reportable {

    private String id;
    private String name;
    private String email;
    private String department;

    private List<Course> coursesTaught;

    public AcademicMember(String id, String name, String email, String department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.coursesTaught = new ArrayList<>();
    }

    // Interface Person's getter's
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

    public void assignCourse(Course course) {
        coursesTaught.add(course);
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }


    //Interface Reportable's function
    @Override
    public void generateReport(FileWriter writer) throws IOException {
        writer.write("Faculty ID: " + getId() + "\n");
        writer.write("Name: " + getName() + "\n");
        writer.write("Email: " + getEmail() + "\n");
        writer.write("Department: " + getDepartment() + "\n\n");
    }
}
