package src.bus_problem.domain;

import java.util.ArrayList;

public class BusStop {
    private final ArrayList<Bus> busLine;

    public BusStop() {
        this.busLine = new ArrayList<>();
    }

    public void addBus(Bus bus) {
        synchronized (this) {
            this.busLine.add(bus);
            this.notifyAll();
        }
    }

    public void departBus(int busPosition) {
        synchronized (this) {
            this.busLine.remove(busPosition);
        }
    }

    public Bus getFirstBusWithAvailableSeat() {
        synchronized (this) {
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
