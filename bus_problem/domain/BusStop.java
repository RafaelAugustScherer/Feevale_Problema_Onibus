package bus_problem.domain;

import bus_problem.domain.interfaces.StudentStatus;
import java.util.ArrayList;

/**
 * Ponto de ônibus da simulação: mantém filas de alunos e ônibus.
 *
 * Responsável por toda sincronização entre threads de alunos e de chegada de ônibus, usando o
 * monitor intrínseco (synchronized) desta instância, além de wait/notifyAll para coordenação.
 *
 * Regras implementadas:
 * - Quando um ônibus chega e não há alunos, ele parte imediatamente.
 * - Quando um ônibus chega e existem alunos, apenas os que já estavam esperando no momento da chegada
 *   recebem status de tentativa de embarque e podem embarcar neste ônibus.
 * - Alunos que chegam durante o embarque aguardam o próximo ônibus.
 */
public class BusStop {
    public ArrayList<Student> studentLine;
    private final ArrayList<Bus> busLine;

    /**
     * Constrói um ponto de ônibus vazio (sem alunos/ônibus).
     */
    public BusStop() {
        this.studentLine = new ArrayList<>();
        this.busLine = new ArrayList<>();
    }

    /**
     * Registra a chegada de um ônibus ao ponto.
     *
     * Efeitos de sincronização (sempre dentro de synchronized(this)):
     * - Se não há alunos, remove o ônibus imediatamente (partida imediata).
     * - Caso contrário, registra um observador no ônibus para detectar lotação máxima e/ou fim de embarque
     *   (quando nenhum aluno permanece com status TRYING_TO_ENTER_BUS).
     * - Sinaliza os alunos já presentes no ponto sobre a chegada (definindo o status) e chama notifyAll()
     *   para acordar as threads que estão em wait().
     *
     * @param bus ônibus que acaba de chegar
     */
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

                synchronized (this) {
                    if (this.studentLine.stream()
                            .noneMatch(student -> student.status == StudentStatus.TRYING_TO_ENTER_BUS)) {
                        this.departBus(updatedBus);
                    }
                }
            });

            this.studentLine.forEach((student) -> {
                student.syncNotifyBusHasArrived();
            });
            this.notifyAll();
        }
    }

    /**
     * Remove o ônibus pela posição na fila e registra a partida.
     *
     * Deve ser chamado dentro da região crítica (synchronized).
     *
     * @param busPosition índice do ônibus na fila
     */
    public void departBus(int busPosition) {
        synchronized (this) {
            this.busLine.remove(busPosition);
            System.out.println("Bus departed\n");
        }
    }

    /**
     * Remove o ônibus específico da fila e registra a partida.
     *
     * Deve ser chamado dentro da região crítica (synchronized).
     *
     * @param bus ônibus a ser removido
     */
    public void departBus(Bus bus) {
        synchronized (this) {
            this.busLine.remove(bus);
            System.out.printf("Bus %s departed\n", bus.getCode());
        }
    }

    /**
     * Busca o primeiro ônibus com assentos disponíveis.
     *
     * Deve ser chamado dentro de synchronized(this) para leitura consistente da fila.
     *
     * @return uma instância de ônibus com vaga ou null se nenhum disponível
     */
    public Bus getFirstBusWithAvailableSeat() {
        synchronized (this) {
            //System.out.printf("Bus line size: %d\n", this.busLine.size());
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
