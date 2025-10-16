package bus_problem.domain;

import bus_problem.domain.errors.BusinessError;
import bus_problem.domain.interfaces.Observer;
import java.util.ArrayList;

/**
 * Representa um ônibus com capacidade máxima e lista de passageiros.
 *
 * Notas de concorrência:
 * - Esta classe não sincroniza internamente o acesso à lista de passageiros.
 * - O protocolo de acesso é garantido externamente pelo {@link BusStop}, que mantém a seção crítica
 *   ao chamar {@link #addStudent(Student)} durante o processo de embarque.
 * - Observadores são notificados a cada embarque; o {@link BusStop} usa esse mecanismo para detectar
 *   quando o ônibus está cheio ou quando não há mais alunos tentando embarcar.
 */
public class Bus {
    private final String code;
    private final int maxCapacity = 50;

    private final ArrayList<Observer<Bus>> observers;

    private final ArrayList<Student> passengers;

    /**
     * Cria um ônibus identificado por um código.
     *
     * @param code identificador legível do ônibus (ex.: "001")
     */
    public Bus(String code) {
        this.code = code;
        this.passengers = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    /**
     * Registra um observador para ser notificado a cada embarque.
     *
     * @param observer callback a ser invocado com a instância atualizada do ônibus
     */
    public void addObserver(Observer<Bus> observer) {
        this.observers.add(observer);
    }

    /**
     * @return código identificador do ônibus
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @return verdadeiro se o ônibus atingiu a capacidade máxima
     */
    public boolean isFull() {
        return passengers.size() == this.maxCapacity;
    }

    /**
     * Adiciona um estudante como passageiro e notifica os observadores.
     *
     * Regras:
     * - Lança {@link BusinessError} se o ônibus estiver cheio.
     * - Deve ser chamado dentro da região crítica controlada por {@link BusStop} para garantir consistência.
     *
     * @param student aluno a ser embarcado
     * @throws BusinessError quando a capacidade máxima já foi atingida
     */
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
