package src.bus_problem.domain;

import java.util.ArrayList;
import src.bus_problem.domain.errors.BusinessError;

public class Bus extends Thread {
    private final String code;
    private final int maxCapacity = 50;

    private final ArrayList<Student> passengers;

    Bus(String code) {
        this.code = code;
        this.passengers = new ArrayList<>();
    }

    public boolean isFull() {
        return passengers.size() == this.maxCapacity;
    }

    public void addStudent(Student student) throws BusinessError {
        if (this.passengers.size() == maxCapacity) {
            throw new BusinessError(
                    String.format("Bus %s is full", this.code));
        }

        this.passengers.add(student);
    }
}
