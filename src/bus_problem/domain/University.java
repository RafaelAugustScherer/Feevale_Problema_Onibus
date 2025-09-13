package src.bus_problem.domain;

import java.util.ArrayList;

public class University {
    private final ArrayList<ClassRoom> classRooms;
    private final BusStop busStop;

    public University(ArrayList<ClassRoom> classRooms, BusStop busStop) {
        this.classRooms = classRooms;
        this.busStop = busStop;
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
                student.moveToBusStopAndWaitForBus(this.busStop);
            });

            classRoom.removeAllStudents();
        });
    }
}
