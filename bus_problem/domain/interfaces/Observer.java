package bus_problem.domain.interfaces;

public interface Observer<T> {
    void update(T updatedObject);
}
