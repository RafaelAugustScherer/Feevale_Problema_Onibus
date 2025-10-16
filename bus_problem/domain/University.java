package bus_problem.domain;

import java.util.ArrayList;

/**
 * Agrega as salas de aula e o ponto de ônibus, e orquestra o ciclo de aulas/embarque.
 */
public class University {
    private final ArrayList<ClassRoom> classRooms;
    private final BusStop busStop;

    /**
     * @param classRooms lista de salas de aula
     * @param busStop ponto de ônibus compartilhado
     */
    public University(ArrayList<ClassRoom> classRooms, BusStop busStop) {
        this.classRooms = classRooms;
        this.busStop = busStop;
    }

    /**
     * @return lista de salas de aula
     */
    public ArrayList<ClassRoom> getClassRooms() {
        return this.classRooms;
    }

    /**
     * Move (registra) um ônibus no ponto, delegando ao {@link BusStop} a sincronização com alunos.
     *
     * @param bus ônibus que chegou
     */
    public void moveBusToBusStop(Bus bus) {
        this.busStop.addBus(bus);
        System.out.printf("bus %s has arrived\n", bus.getCode());
    }

    /**
     * Aloca alunos em uma sala específica.
     *
     * @param students alunos a incluir
     * @param classRoomIndex índice da sala de aula
     */
    public void moveStudentsToClassRoom(ArrayList<Student> students, int classRoomIndex) {
        ClassRoom classRoom = this.classRooms.get(classRoomIndex);
        students.forEach((student) -> {
            classRoom.includeStudent(student);
        });
    }

    /**
     * Finaliza as aulas e cria uma thread por aluno para ir ao ponto de ônibus.
     *
     * A sincronização do embarque é realizada pelo {@link BusStop}. Esta operação:
     * - Coleta todos os alunos das salas.
     * - Cria uma thread para cada aluno que executa {@link Student#moveToBusStopAndWaitForBus(BusStop)}.
     * - Limpa as salas para o próximo ciclo.
     */
    public void finishClassesAndMoveStudentsToBusStop() {
        ArrayList<Thread> threadsToStart = new ArrayList<>();
        System.out.println("class ended");
        this.classRooms.forEach((classRoom) -> {
            classRoom.getStudents().forEach((student) -> {
                threadsToStart.add(new Thread(() -> {
                    student.moveToBusStopAndWaitForBus(this.busStop);
                }));
            });

            classRoom.removeAllStudents();
        });

        threadsToStart.forEach((thread) -> thread.start());
    }
}
