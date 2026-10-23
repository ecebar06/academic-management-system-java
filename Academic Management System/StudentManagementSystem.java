import java.util.*;
import java.io.*;
/**
 * Central class that manages all the entities in the student management system,
 * as in students, academic staff, departments, programs, and courses.
 * Provides means to add, retrieve, and assign relationships between them.
 */
public class StudentManagementSystem {

    private Map<String, Student> students;
    private Map<String, AcademicMember> academicMembers;
    private Map<String, Department> departments;
    private Map<String, Program> programs;
    private Map<String, Course> courses;

    public StudentManagementSystem() {
        students = new LinkedHashMap<>();
        academicMembers = new LinkedHashMap<>();
        departments = new LinkedHashMap<>();
        programs = new LinkedHashMap<>();
        courses = new LinkedHashMap<>();
    }

    // Add methods
    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public void addAcademicMember(AcademicMember member) {
        academicMembers.put(member.getId(), member);
    }

    public void addDepartment(Department department) {
        departments.put(department.getCode(), department);
    }

    public void addProgram(Program program) {
        programs.put(program.getCode(), program);
    }
    public Student getStudent(String id) {
        return students.get(id);
    }

    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }
    public AcademicMember getAcademicMember(String id) {
        return academicMembers.get(id);
    }

    public Course getCourse(String code) {
        return courses.get(code);
    }


    /**
     * Assigns a department head if both department and academic member exist.
     * @throws DepartmentNotFoundException if department is missing
     * @throws AcademicMemberNotFoundException if academic member is missing
     */
    public void assignDepartmentHead(String departmentCode, String academicMemberId) throws Exception {
        Department dept = departments.get(departmentCode);
        AcademicMember head = academicMembers.get(academicMemberId);
        if (dept == null) throw new DepartmentNotFoundException(departmentCode);
        if (head == null) throw new AcademicMemberNotFoundException(academicMemberId);
        dept.setHead(head);
    }
    /**
     * Assigns a grade to a student for a specific course.
     * Validates student, course, and grade before assignment.
     *
     * @throws StudentNotFoundException if student is missing
     * @throws CourseNotFoundException if course is missing
     * @throws InvalidGradeException if grade is invalid
     */
    public void assignGradeToStudent(String studentId, String courseCode, String grade)
            throws StudentNotFoundException, CourseNotFoundException, InvalidGradeException {

        Student s = students.get(studentId);
        if (s == null) throw new StudentNotFoundException(studentId);

        Course c = courses.get(courseCode);
        if (c == null) throw new CourseNotFoundException(courseCode);

        if (!Main.isValidGrade(grade)) throw new InvalidGradeException(grade);

        s.assignGrade(c, grade);
        c.assignGrade(s, grade);
    }

    // Getter methods
    public Map<String, Student> getStudents() {
        return students;
    }

    public Map<String, AcademicMember> getAcademicMembers() {
        return academicMembers;
    }

    public Map<String, Department> getDepartments() {
        return departments;
    }

    public Map<String, Program> getPrograms() {
        return programs;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }
}

