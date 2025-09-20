package bus_problem.domain;

public class Student {
    public final String name;

    public Student(String name) {
        this.name = name;
    }

    public void moveToBusStopAndWaitForBus(BusStop busStop) {
        System.out.printf("Student %s is going to the bus stop\n", this.name);
        while (true) {
            synchronized (busStop) {
                Bus availableBus = busStop.getFirstBusWithAvailableSeat();

                if (availableBus == null) {
                    System.out.printf("No available bus for student %s\n", this.name);
                    try {
                        busStop.studentLine.add(this);
                        busStop.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.printf("Available bus for student %s %s\n", this.name, availableBus.getCode());
                    busStop.studentLine.remove(this);
                    availableBus.addStudent(this);
                    break;
                }
            }
        }
    }
}
