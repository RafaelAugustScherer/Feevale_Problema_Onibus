package bus_problem.domain;

import java.util.ArrayList;

public class BusStop {
    public ArrayList<Student> studentLine;
    private final ArrayList<Bus> busLine;

    public BusStop() {
        this.studentLine = new ArrayList<>();
        this.busLine = new ArrayList<>();
    }

    public void addBus(Bus bus) {
        synchronized (this) {
            this.busLine.add(bus);

            if (this.studentLine.isEmpty()) {
                this.departBus(this.busLine.size() - 1);
                return;
            }

            bus.addObserver((Bus updatedBus) -> {
                if (updatedBus.isFull()) {
                    this.departBus(updatedBus);
                }
            });

            this.notifyAll();
        }
    }

    public void departBus(int busPosition) {
        synchronized (this) {
            this.busLine.remove(busPosition);
            System.out.println("Bus departed");
        }
    }

    public void departBus(Bus bus) {
        synchronized (this) {
            this.busLine.remove(bus);
            System.out.println("Bus departed");
        }
    }

    public Bus getFirstBusWithAvailableSeat() {
        synchronized (this) {
            System.out.printf("Bus line size: %d\n", this.busLine.size());
            for (int i = 0; i < this.busLine.size(); i++) {
                Bus bus = this.busLine.get(i);

                if (!bus.isFull()) {
                    return bus;
                }
            }

            return null;
        }
    }
}
