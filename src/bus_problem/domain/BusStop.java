package src.bus_problem.domain;

import java.util.ArrayList;

class BusStop {
    private final ArrayList<Bus> busLine;

    public BusStop() {
        this.busLine = new ArrayList<>();
    }

    public void addBus(Bus bus) {
        this.busLine.add(bus);
    }

    public void departBus(int busPosition) {
        this.busLine.remove(busPosition);
    }

    public Bus getFirstBusWithAvailableSeat() {
        for (int i = 0; i < this.busLine.size(); i++) {
            Bus bus = this.busLine.get(i);

            if (!bus.isFull()) {
                return bus;
            }
        }

        return null;
    }
}
