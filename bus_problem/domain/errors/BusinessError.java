package bus_problem.domain.errors;

/**
 * Erro usado para sinalizar violação de regras de negócio (ex.: ônibus lotado).
 */
public class BusinessError extends Error {

    /**
     * @param message descrição do erro de negócio
     */
    public BusinessError(String message) {
        super(message);
    }
}
