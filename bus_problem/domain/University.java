package bus_problem.domain;

import java.util.ArrayList;

public class University {
    private final ArrayList<ClassRoom> classRooms;
    private final BusStop busStop;

    public University(ArrayList<ClassRoom> classRooms, BusStop busStop) {
        this.classRooms = classRooms;
        this.busStop = busStop;
    }

    public ArrayList<ClassRoom> getClassRooms() {
        return this.classRooms;
    }

    public void moveBusToBusStop(Bus bus) {
        this.busStop.addBus(bus);
    }

    public void moveStudentsToClassRoom(ArrayList<Student> students, int classRoomIndex) {
        ClassRoom classRoom = this.classRooms.get(classRoomIndex);

        students.forEach((student) -> {
            classRoom.includeStudent(student);
        });
    }

    public void finishClassesAndMoveStudentsToBusStop() {
        this.classRooms.forEach((classRoom) -> {
            classRoom.getStudents().forEach((student) -> {
                new Thread(() -> {
                    student.moveToBusStopAndWaitForBus(this.busStop);
                }).start();
            });

            classRoom.removeAllStudents();
        });
    }
}
