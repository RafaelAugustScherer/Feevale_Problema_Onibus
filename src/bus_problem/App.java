package src.bus_problem;

import java.util.ArrayList;
import src.bus_problem.domain.BusStop;
import src.bus_problem.domain.ClassRoom;
import src.bus_problem.domain.University;

public class App {
    public static void main(String[] args) {

        ArrayList<ClassRoom> classRooms = new ArrayList<>();
        String[] classRoomNames = { "101", "102", "201", "202" };

        for (String name : classRoomNames) {
            classRooms.add(new ClassRoom(name));
        }

        BusStop busStop = new BusStop();

        University university = new University(classRooms, busStop);
    }
}
