package src.bus_problem.domain;

public class Student extends Thread {
    public final String name;

    public Student(String name) {
        this.name = name;
    }

    public void moveToBusStopAndWaitForBus(BusStop busStop) {
        while (true) {
            synchronized (busStop) {
                Bus availableBus = busStop.getFirstBusWithAvailableSeat();

                if (availableBus == null) {
                    try {
                        busStop.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    availableBus.addStudent(this);
                    break;
                }
            }
        }
    }
}
