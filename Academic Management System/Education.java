import java.util.*;
import java.io.*;
/**
 * Represents a university course with code, name, credits, semester, and department.
 * Oversees enrolled students, graded assignments, and the instructor of the course.
 * Can produce brief and summary reports like average grade and grade distribution.
 */
class Course implements Reportable {

    private String code, name, department, semester;
    private int credits;
    private Program program;
    private AcademicMember instructor;
    private List<Student> enrolledStudents;
    private Map<Student, String> grades;
    private int reportOrder;
    private int totalCourseCount;
    //to determine whether a course is the last course in the list and to set up a formatting scheme accordingly
    public void setReportOrder(int order) {
        this.reportOrder = order;
    }

    public void setTotalCourseCount(int total) {
        this.totalCourseCount = total;
    }


    public Course(String code, String name, String department, int credits, String semester, Program program) {
        this.code = code;
        this.name = name;
        this.department = department;
        this.credits = credits;
        this.semester = semester;
        this.program = program;
        this.enrolledStudents = new ArrayList<>();
        this.grades = new LinkedHashMap<>();
    }

    public void setInstructor(AcademicMember instructor) {
        this.instructor = instructor;
    }

    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
    }

    public void assignGrade(Student student, String grade) {
        grades.put(student, grade);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Program getProgram() {
        return program;
    }

    public int getCredits() {
        return credits;
    }

    public String getSemester() {
        return semester;
    }

    public AcademicMember getInstructor() {
        return instructor;
    }

    public void generateShortReport(FileWriter writer) throws IOException {
        writer.write("Course Code: " + code + "\n");
        writer.write("Name: " + name + "\n");
        writer.write("Department: " + program.getDepartment().getName() + "\n");
        writer.write("Credits: " + credits + "\n");
        writer.write("Semester: " + semester + "\n\n");
    }


    /**
     * Writes course report for the course, with:
     * - course basic information (code, name, department, credits, semester)
     * - instructor name
     * - student list of enrolled
     * - grade distribution and average grade
     *
     * Writes additional newline after average grade only for last course,
     * depending on reportOrder and totalCourseCount.
     *
     * @param writer FileWriter to write the report into
     * @throws IOException if writing to the file is unsuccessful
     */
    @Override
    public void generateReport(FileWriter writer) throws IOException {
        generateShortReport(writer);

        writer.write("Instructor: " + (instructor != null ? instructor.getName() : "Not assigned") + "\n\n");
        writer.write("Enrolled Students:\n");
        for (Student s : enrolledStudents) {
            writer.write("- " + s.getName() + " (ID: " + s.getId() + ")\n");
        }

        writer.write("\nGrade Distribution:\n");

        Map<String, Integer> count = new LinkedHashMap<>();
        double total = 0;
        int num = 0;

        for (Map.Entry<Student, String> entry : grades.entrySet()) {
            String g = entry.getValue();
            count.put(g, count.getOrDefault(g, 0) + 1);
            total += Main.convertGradeToPoint(g);
            num++;
        }

        List<String> order = Arrays.asList("A1", "A2", "B1", "B2", "C1", "C2", "D1", "D2", "F3");

        boolean wroteGrade = false;
        for (String g : order) {
            if (count.containsKey(g)) {
                writer.write(g + ": " + count.get(g) + "\n");
                wroteGrade = true;
            }
        }

        if (!wroteGrade) {
            ;
        }
        writer.write("\n");
        writer.write("Average Grade: " + String.format("%.2f", num == 0 ? 0.0 : total / num) );
        if (reportOrder == totalCourseCount - 1) {
            writer.write("\n\n");
        } else {
            writer.write("\n");
        }
        writer.write("\n----------------------------------------\n\n");

    }
}
/**
 * Represents a university department with a code, name, and description.
 * Optionally has a department head (an AcademicMember).
 * Can generate a simple report showing department info.
 */
class Department implements Reportable {

    private String code, name, description;
    private AcademicMember head;

    public Department(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public void setHead(AcademicMember head) {
        this.head = head;
    }

    public AcademicMember getHead() {
        return head;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    /**
     * Writes the department's code, name, and head (if assigned) to the output.
     */
    @Override
    public void generateReport(FileWriter writer) throws IOException {
        writer.write("Department Code: " + code + "\n");
        writer.write("Name: " + name + "\n");
        writer.write("Head: " + (head != null ? head.getName() : "Not assigned") + "\n\n");
    }
}

/**
 * Models an academic program in a department.
 * Holds program information such as code, name, degree level, total credits,
 * and a list of courses.
 */
class Program implements Reportable {

    private String code;
    private String name;
    private String description;
    private Department department;
    private String degreeLevel;
    private int totalCredits;
    private List<Course> courses;

    public Program(String code, String name, String description,
                   Department department, String degreeLevel, int totalCredits) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.department = department;
        this.degreeLevel = degreeLevel;
        this.totalCredits = totalCredits;
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public String getCode() {
        return code;
    }

    public Department getDepartment() {
        return department;
    }

    public List<Course> getCourses() {
        return courses;
    }
    /**
     * Prints program information such as code, name, department, degree level,
     * required credits, and course list to the output.
     */
    @Override
    public void generateReport(FileWriter writer) throws IOException {
        writer.write("Program Code: " + code + "\n");
        writer.write("Name: " + name + "\n");
        writer.write("Department: " + department.getName() + "\n");
        writer.write("Degree Level: " + degreeLevel + "\n");
        writer.write("Required Credits: " + totalCredits + "\n");

        if (courses.isEmpty()) {
            writer.write("Courses: - \n");
        } else {
            StringJoiner joiner = new StringJoiner(",");
            for (Course c : courses) {
                joiner.add(c.getCode());
            }
            writer.write("Courses: {" + joiner.toString() + "}\n");
        }

        writer.write("\n");
    }

}
