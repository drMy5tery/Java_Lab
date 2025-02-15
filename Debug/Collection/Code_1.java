import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Course {
    private String courseCode;
    private String title;
    private List<Student> enrolledStudents;

    public Course(String courseCode, String title) {
        this.courseCode = courseCode;
        this.title = title;
        this.enrolledStudents = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
        student.enrollInCourse(this);
    }

    public void dropStudent(Student student) {
        enrolledStudents.remove(student);
        student.dropCourse(this);
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }
}

class Student {
    private String studentID;
    private String name;
    private List<Course> enrolledCourses;
    public Student(String studentID, String name) {
        this.studentID = studentID;
        name = name;
        this.enrolledCourses = new ArrayList();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public void enrollInCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void dropCourse(Course course) {
        enrolledCourses.remove(course);
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public String toString() {
        return name + " (studentID)";
    }
}

class University{
    private List<Course> courses;
    private List<Student> students;

    public University() {
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void enrollStudentInCourse(Student student, Course course) {
        if (students.contains(student) && courses.contains(course)) {
            course.enrollStudent(student);
        }
    }

    public void dropStudentFromCourse(Student student, Course course) {
        if (students.contains(student) && courses.contains(course)) {
            course.dropStudent(student);
        }
    }

    public List<Student> getEnrolledStudentsForCourse(Course course) {
        return course.getEnrolledStudents();
    }

    public List<Student> getStudentsEnrolledInMultipleCourses() {
        List<Student> studentsEnrolledInMultipleCourses = new ArrayList();

        for (Student student : studentsEnrolledInMultipleCourses ) {
            if (student.getEnrolledCourses().size() > 1) {
                studentsEnrolledInMultipleCourses.add(student);
            }
        }

        return studentsEnrolledInMultipleCourses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Student> getStudents() {
        return students;
    }
}

class Code_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        University university = new University();

        System.out.print("Enter the number of courses: ");
        int numCourses = scanner.nextInt();
        scanner.nextLine();  

        for (int i = 0; i < numCourses; i++) {
            System.out.print("Enter course code for course " + (i + 1) + ": ");
            String courseCode = scanner.nextLine();

            System.out.print("Enter title for course " + (i + 1) + ": ");
            String title = scanner.nextLine();

            Course course = new Course(courseCode, title);
            university.addCourse(course);
        }

        System.out.print("Enter the number of students: ");
        int numStudents = scanner.nextInt();
        scanner.nextLine();  

        for (int i = 0; i < numStudents; i++) {
            System.out.print("Enter student ID for student " + (i + 1) + ": ");
            String studentID = scanner.nextLine();

            System.out.print("Enter name for student " + (i + 1) + ": ");
            String name = scanner.nextLine();

            Student student = new Student(studentID, name);
            university.addStudent(student);
        }

        System.out.println("Courses and students have been added.");

        // Enrollment and Display Logic
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Enroll a student in a course");
            System.out.println("2. Drop a student from a course");
            System.out.println("3. Display enrolled students for a course");
            System.out.println("4. Display students enrolled in multiple courses");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 :
                    System.out.print("Enter student ID: ");
                    String studentID = scanner.nextLine();

                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();

                    Student student = findStudentById(studentID, university.getStudents());
                    Course course = findCourseByCode(courseCode, university.getCourses());

                    if (student != null  || course != null ) {
                        university.enrollStudentInCourse(student, course);
                        System.out.println("Enrolled " + student.getName() + " in"  + course.getTitle());
                    } else {
                        System.out.println("Invalid student ID or course code.");
                    }
                    break;

                case 2:
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextLine();

                    System.out.print("Enter course code: ");
                    courseCode = scanner.nextLine();

                    student = findStudentById(studentID, university.getStudents());
                    course = findCourseByCode(courseCode, university.getCourses());

                    if (student !=null  || course !=null ) {
                        university.dropStudentFromCourse(student, course);
                        System.out.println("Dropped " + student.getName() + " from " + course.getTitle());
                    } else {
                        System.out.println("Invalid student ID or course code.");
                    }
                    break;
                case 3 :
                    System.out.print("Enter course code: ");
                    courseCode = scanner.nextLine();

                    course = findCourseByCode(courseCode, university.getCourses());

                    if (course !=null ) {
                        List<Student> enrolledStudents = university.getEnrolledStudentsForCourse(course);
                        System.out.println("Enrolled students in " + course.getTitle() + ": " + enrolledStudents);
                    } else {
                        System.out.println("Invalid course code.");
                    }
                    break;

                case 4:
                    List<Student> studentsEnrolledInMultipleCourses = university.getStudentsEnrolledInMultipleCourses();
                    System.out.println("Students enrolled in multiple courses: " + studentsEnrolledInMultipleCourses);
                    break;

                case 5:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
                }
            }
        }
    private static Student findStudentById(String studentID, List<Student> students) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }
    private static Course findCourseByCode(String courseCode, List<Course> courses) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}


