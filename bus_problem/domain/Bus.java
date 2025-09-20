package bus_problem.domain;

import bus_problem.domain.errors.BusinessError;
import bus_problem.domain.interfaces.Observer;
import java.util.ArrayList;

public class Bus {
    private final String code;
    private final int maxCapacity = 50;

    private final ArrayList<Observer<Bus>> observers;

    private final ArrayList<Student> passengers;

    public Bus(String code) {
        this.code = code;
        this.passengers = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer<Bus> observer) {
        this.observers.add(observer);
    }

    public String getCode() {
        return this.code;
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

        this.observers.forEach((observer) -> {
            observer.update(this);
        });
    }
}
