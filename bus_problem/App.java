package bus_problem;

import bus_problem.domain.*;
import bus_problem.domain.generator.StudentGenerator;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        University university = App.setupUniversity();
        App.startClassGenerationThread(university);
        App.startBusGenerationThread(university);
    }

    //Cria todas as salas de aula e o ponto de ônibus
    public static University setupUniversity() {
        ArrayList<ClassRoom> classRooms = new ArrayList<>();
        String[] classRoomNames = { "101", "102", "201", "202" };

        for (String name : classRoomNames) {
            classRooms.add(new ClassRoom(name));
        }

        BusStop busStop = new BusStop();

        return new University(classRooms, busStop);
    }

    //Gerador de threads de ônibus (cada ônibus é gerado de maneira aleatória entre 2 a 3 minutos)
    public static void startBusGenerationThread(University university) {
        new Thread(() -> {
            int busCode = 1;

            while (true) {
                try {
                    TimeUnit.MINUTES.sleep(new Random().nextLong(2, 3));
                } catch (InterruptedException e) {
                    break;
                }

                university.moveBusToBusStop(new Bus(String.format("%0" + 3 + "d", busCode)));
                busCode += 1;
            }
        }).start();
    }

    //gerador do thread de aulas (geradas aleatoriamente entre 2 a 10 minutos)
    // (todas as aula iniciam e terminam no mesmo momento)
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
                    TimeUnit.MINUTES.sleep(new Random().nextLong(2, 10));
                } catch (InterruptedException e) {
                    break;
                }

                university.finishClassesAndMoveStudentsToBusStop();
            }
        }).start();
    }
}
