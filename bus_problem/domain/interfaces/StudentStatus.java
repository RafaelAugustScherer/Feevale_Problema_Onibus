package bus_problem.domain.interfaces;

/**
 * Estados possíveis de um aluno no ciclo de espera/embarque.
 *
 * - WAITING_FOR_NEXT_BUS: aluno presente no ponto, aguardando chegada.
 * - TRYING_TO_ENTER_BUS: aluno que estava no ponto quando um ônibus chegou e está apto a embarcar.
 * - IN_BUS: aluno já embarcado.
 */
public enum StudentStatus {
    WAITING_FOR_NEXT_BUS,
    TRYING_TO_ENTER_BUS,
    IN_BUS
}
