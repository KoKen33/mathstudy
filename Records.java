package officialInternalAssessment;

import java.io.*;
import java.util.*;

public class Records {

    private static final String DATA_FILE = "/Users/binus/Desktop/users.txt";

    public static List<Student> students = new LinkedList<>();
    public static List<Teacher> teachers = new LinkedList<>();

    public static void addUser(User u) {
        if (u instanceof Student s) students.add(s);
        else if (u instanceof Teacher t) teachers.add(t);

        saveUser(u);
    }

    public static User checkLogin(String username, String password) {
        for (User u : students)
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;

        for (User u : teachers)
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return u;

        return null;
    }

    // Append mode saving (no history)
    private static void saveUser(User user) {
        try (RandomAccessFile raf = new RandomAccessFile(DATA_FILE, "rw")) {
            raf.seek(raf.length());
            raf.writeBytes(user.getUsername() + "," + user.getPassword() + "," + user.getRole() + "\n");
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Rewrite file when necessary
    private static void rewriteFile() {
        try (RandomAccessFile raf = new RandomAccessFile(DATA_FILE, "rw")) {
            raf.setLength(0); // clear file

            recurseWriteStudents(raf, students, 0);
            recurseWriteTeachers(raf, teachers, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Recursive methods

    // Recursively write students
    private static void recurseWriteStudents(RandomAccessFile raf, List<Student> list, int index) throws IOException {
        if (index >= list.size()) return;

        Student s = list.get(index);

        // Write the student header
        raf.writeBytes(s.getUsername() + "," + s.getPassword() + ",student\n");

        // Recursively write their attempts
        writeAttemptsRecursively(raf, s.getHistory(), 0);

        // Move to next student
        recurseWriteStudents(raf, list, index + 1);
    }

    // Recursively write teachers
    private static void recurseWriteTeachers(RandomAccessFile raf, List<Teacher> list, int index) throws IOException {
        if (index >= list.size()) return;

        Teacher t = list.get(index);
        raf.writeBytes(t.getUsername() + "," + t.getPassword() + ",teacher\n");

        recurseWriteTeachers(raf, list, index + 1);
    }

    // Recursively write attempts
    private static void writeAttemptsRecursively(RandomAccessFile raf, List<Student.PracticeAttempt> attempts, int index)
            throws IOException {

        if (index >= attempts.size()) return;

        raf.writeBytes("ATTEMPT:" + attempts.get(index).toString() + "\n");

        writeAttemptsRecursively(raf, attempts, index + 1);
    }



    // Load all data (polymorphic)
    public static void loadData() {
        students.clear();
        teachers.clear();

        try (RandomAccessFile raf = new RandomAccessFile(DATA_FILE, "rw")) {

            String line;
            Student currentStudent = null;

            while ((line = raf.readLine()) != null) {

                if (line.startsWith("ATTEMPT:")) {
                    if (currentStudent != null) {
                        Student.PracticeAttempt pa = Student.PracticeAttempt.fromString(line.substring(8));
                        currentStudent.getHistory().add(pa);
                    }
                }

                else {
                    String[] p = line.split(",");

                    if (p.length == 3) {
                        String name = p[0];
                        String pass = p[1];
                        String role = p[2];

                        if (role.equalsIgnoreCase("student")) {
                            currentStudent = new Student(name, pass);
                            students.add(currentStudent);
                        } else {
                            teachers.add(new Teacher(name, pass));
                            currentStudent = null;
                        }
                    }
                }
            }

        } catch (IOException ignored) {}
    }

    public static void recordAttempt(User user, String topic, int score, int time) {
        if (user instanceof Student s) {
            s.addAttempt(topic, score, time);
            rewriteFile();
        }
    }
}
