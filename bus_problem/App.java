package bus_problem;

import bus_problem.domain.*;
import bus_problem.domain.generator.StudentGenerator;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Ponto de entrada da simulação do Problema do Ônibus.
 *
 * Esta aplicação modela:
 * - Threads de geração de ônibus (chegam a cada 2–3 minutos, aleatório).
 * - Threads de ciclo de aulas (2–10 minutos, aleatório), que ao fim criam uma thread por aluno
 *   para ir ao ponto de ônibus e aguardar/embarcar.
 *
 * A sincronização entre alunos e ônibus ocorre via o monitor do {@link bus_problem.domain.BusStop}
 * utilizando synchronized/wait/notifyAll. O embarque respeita a regra: apenas alunos já no ponto
 * no momento da chegada do ônibus embarcam; quem chega durante o embarque espera o próximo.
 */
public class App {
    /**
     * Inicializa a universidade e inicia as threads de geração de aulas e deb ônius.
     */
    public static void main(String[] args) {
        University university = App.setupUniversity();
        App.startClassGenerationThread(university);
        App.startBusGenerationThread(university);
    }

    /**
     * Cria todas as salas de aula e o ponto de ônibus da simulação.
     *
     * @return instância de {@link University} contendo salas e ponto de ônibus
     */
    public static University setupUniversity() {
        ArrayList<ClassRoom> classRooms = new ArrayList<>();
        String[] classRoomNames = { "101", "102", "201", "202" };

        for (String name : classRoomNames) {
            classRooms.add(new ClassRoom(name));
        }

        BusStop busStop = new BusStop();

        return new University(classRooms, busStop);
    }

    /**
     * Inicia uma thread que gera ônibus periodicamente.
     *
     * Responsabilidades da thread:
     * - Dormir um intervalo aleatório entre 2 e 3 minutos entre ônibus.
     * - Criar um novo {@link Bus} e informar a {@link University} para posicioná-lo no ponto.
     *
     * Observação: a sincronização do embarque não ocorre aqui, mas sim no {@link bus_problem.domain.BusStop}.
     */
    public static void startBusGenerationThread(University university) {
        new Thread(() -> {
            int busCode = 1;

            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(new Random().nextLong(2, 3));
                } catch (InterruptedException e) {
                    break;
                }

                university.moveBusToBusStop(new Bus(String.format("%0" + 3 + "d", busCode)));
                busCode += 1;
            }
        }).start();
    }

    /**
     * Inicia uma thread que representa o ciclo de aulas.
     *
     * Comportamento:
     * - Para cada sala, gera aleatoriamente alguns alunos e os coloca em aula.
     * - Aguarda a duração aleatória da aula (2 a 10 minutos). Todas as salas terminam simultaneamente.
     * - Ao término, dispara uma thread por aluno para ir ao ponto de ônibus e aguardar embarque.
     *
     * Ênfase em threads: a criação de uma thread por aluno permite concorrência no ponto, onde a
     * sincronização é orquestrada pelo {@link bus_problem.domain.BusStop} via monitor intrínseco.
     */
    public static void startClassGenerationThread(University university) {
        new Thread(() -> {
            System.out.println("class started");
            while (true) {
                for (int classRoomIndex = 0; classRoomIndex < university.getClassRooms().size(); classRoomIndex++) {
                    int numberOfStudentsInClassRoom = new Random().nextInt(3);

                    ArrayList<Student> students = StudentGenerator.generate(numberOfStudentsInClassRoom);

                    university.moveStudentsToClassRoom(students, classRoomIndex);
                }

                try {
                    TimeUnit.SECONDS.sleep(new Random().nextLong(2, 10));
                } catch (InterruptedException e) {
                    break;
                }

                university.finishClassesAndMoveStudentsToBusStop();
            }
        }).start();
    }
}
