import java.io.*;
import java.util.*;

class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private ArrayList<Student> students = new ArrayList<>();
    private static final String DATA_FILE = "student_data.txt";

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public void saveStudentData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            outputStream.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadStudentData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (ArrayList<Student>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem system = new StudentManagementSystem();

        system.loadStudentData();

        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add a student");
            System.out.println("2. Remove a student");
            System.out.println("3. Search for a student");
            System.out.println("4. Display all students");
            System.out.println("5. Save and Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter student's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student's roll number: ");
                    int rollNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter student's grade: ");
                    String grade = scanner.nextLine();

                    if (rollNumber > 0) {
                        Student newStudent = new Student(name, rollNumber, grade);
                        system.addStudent(newStudent);
                        System.out.println("Student added successfully.");
                    } else {
                        System.out.println("Invalid roll number. Please enter a positive integer.");
                    }
                    break;

                case 2:
                    System.out.print("Enter the roll number of the student to remove: ");
                    int rollToRemove = scanner.nextInt();
                    scanner.nextLine();
                    system.removeStudent(rollToRemove);
                    System.out.println("Student removed successfully.");
                    break;

                case 3:
                    System.out.print("Enter the roll number of the student to search: ");
                    int rollToSearch = scanner.nextInt();
                    scanner.nextLine();
                    Student foundStudent = system.searchStudent(rollToSearch);
                    if (foundStudent != null) {
                        System.out.println("Student Found: " + foundStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    ArrayList<Student> allStudents = system.getAllStudents();
                    if (allStudents.isEmpty()) {
                        System.out.println("No students in the system.");
                    } else {
                        System.out.println("All Students:");
                        for (Student student : allStudents) {
                            System.out.println(student);
                        }
                    }
                    break;

                case 5:
                    system.saveStudentData();
                    System.out.println("Student data saved. Exiting the program.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
