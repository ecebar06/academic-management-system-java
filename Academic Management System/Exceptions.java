
/**
 * Custom exception classes to throw particular validation
 * and lookup exceptions in the student management system.
 *
 * Contains:
 * - Invalid type of person
 * - Missing student, academic member, department, program, or course
 * - Invalid entry of grade
 */
class InvalidPersonTypeException extends Exception {
    public InvalidPersonTypeException(String type) {
        super("Invalid Person Type");
    }
}

class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String studentId) {
        super("Student Not Found with ID " + studentId);
    }
}

class AcademicMemberNotFoundException extends Exception {
    public AcademicMemberNotFoundException(String facultyId) {
        super("Academic Member Not Found with ID " + facultyId);
    }
}

class DepartmentNotFoundException extends Exception {
    public DepartmentNotFoundException(String deptCodeOrName) {
        super("Department "  + deptCodeOrName +" Not Found");
    }
}

class ProgramNotFoundException extends Exception {
    public ProgramNotFoundException(String programCode) {
        super("Program "+ programCode + " Not Found" );
    }
}

class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String courseCode) {
        super("Course " + courseCode + " Not Found");
    }
}

class InvalidGradeException extends Exception {
    public InvalidGradeException(String grade) {
        super("The grade " + grade + " is not valid");
    }
}
