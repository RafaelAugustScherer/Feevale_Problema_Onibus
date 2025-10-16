package bus_problem.domain.interfaces;

/**
 * Interface simples de observador para notificação de alterações em objetos.
 *
 * No contexto da simulação, é usada pelo ônibus para notificar o {@link bus_problem.domain.BusStop}
 * a cada embarque, permitindo decidir a partida quando cheio ou quando não houver mais alunos tentando embarcar.
 */
public interface Observer<T> {
    /**
     * Chamado quando o objeto observado sofre uma atualização relevante.
     * @param updatedObject instância atualizada
     */
    void update(T updatedObject);
}
