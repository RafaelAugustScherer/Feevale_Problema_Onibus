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

    public static University setupUniversity() {
        ArrayList<ClassRoom> classRooms = new ArrayList<>();
        String[] classRoomNames = { "101", "102", "201", "202" };

        for (String name : classRoomNames) {
            classRooms.add(new ClassRoom(name));
        }

        BusStop busStop = new BusStop();

        return new University(classRooms, busStop);
    }

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

    public static void startClassGenerationThread(University university) {
        new Thread(() -> {
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
