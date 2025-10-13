package bus_problem.domain;

import bus_problem.domain.interfaces.StudentStatus;

public class Student {
    public final String name;
    public StudentStatus status;

    public Student(String name) {
        this.name = name;
        this.status = null;
    }

    public void syncNotifyBusHasArrived() {
        this.status = StudentStatus.TRYING_TO_ENTER_BUS;
    }

    public void moveToBusStopAndWaitForBus(BusStop busStop) {
        this.status = StudentStatus.WAITING_FOR_NEXT_BUS;
        System.out.printf("Student %s is waiting for a bus to arrive\n", this.name);

        while (true) {
            synchronized (busStop) {
                try {
                    busStop.studentLine.add(this);
                    busStop.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Bus availableBus = busStop.getFirstBusWithAvailableSeat();

                if (availableBus == null) {
                    this.status = StudentStatus.WAITING_FOR_NEXT_BUS;
                    System.out.printf("No available bus for student %s\n", this.name);
                    try {
                        busStop.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    this.status = StudentStatus.IN_BUS;
                    System.out.printf("Available bus for student %s, bus code:%s\n", this.name, availableBus.getCode());

                    busStop.studentLine.remove(this);
                    availableBus.addStudent(this);
                    break;
                }
            }
        }
    }
}
