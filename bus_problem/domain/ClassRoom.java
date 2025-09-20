package bus_problem.domain;

import java.util.ArrayList;

public class ClassRoom {
    private final String name;
    private final ArrayList<Student> students;

    public ClassRoom(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public void includeStudent(Student student) {
        this.students.add(student);
    }

    public void removeAllStudents() {
        this.students.clear();
    }
}
