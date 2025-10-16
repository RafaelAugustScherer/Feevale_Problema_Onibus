package bus_problem.domain;

import java.util.ArrayList;

/**
 * Sala de aula que mantém a lista de alunos atualmente em aula.
 *
 * A classe em si não gerencia threads; a concorrência ocorre quando, ao término das aulas,
 * as threads de alunos são iniciadas em {@link University#finishClassesAndMoveStudentsToBusStop()}.
 */
public class ClassRoom {
    private final String name;
    private final ArrayList<Student> students;

    /**
     * Cria uma sala identificada por nome.
     *
     * @param name identificador da sala (ex.: "101")
     */
    public ClassRoom(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    /**
     * @return lista mutável de alunos presentes em aula
     */
    public ArrayList<Student> getStudents() {
        return this.students;
    }

    /**
     * Inclui um aluno na sala.
     * @param student aluno a ser adicionado
     */
    public void includeStudent(Student student) {
        this.students.add(student);
    }

    /**
     * Remove todos os alunos da sala.
     */
    public void removeAllStudents() {
        this.students.clear();
    }
}
