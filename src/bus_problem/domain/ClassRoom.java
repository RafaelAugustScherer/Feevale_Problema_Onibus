package src.bus_problem.domain;

import java.util.ArrayList;

public class ClassRoom {
    private final ArrayList<Student> students;

    public ClassRoom() {
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
