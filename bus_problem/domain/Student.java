package bus_problem.domain;

import bus_problem.domain.interfaces.StudentStatus;

/**
 * Representa um aluno na simulação.
 *
 * Cada aluno, ao término das aulas, é executado em sua própria thread para ir até o
 * {@link BusStop} e aguardar/efetuar o embarque conforme as regras de sincronização.
 */
public class Student {
    public final String name;
    public StudentStatus status;

    /**
     * Cria um aluno identificado por nome.
     * @param name nome completo do aluno
     */
    public Student(String name) {
        this.name = name;
        this.status = null;
    }

    /**
     * Notificação síncrona, chamada pelo {@link BusStop} quando um ônibus chega.
     *
     * Efeito: marca o aluno como "tentando embarcar" para que apenas os alunos já presentes
     * no momento da chegada tentem embarcar no ônibus atual.
     */
    public void syncNotifyBusHasArrived() {
        this.status = StudentStatus.TRYING_TO_ENTER_BUS;
    }

    /**
     * Move o aluno até o ponto de ônibus e aguarda o embarque.
     *
     * Detalhes de sincronização:
     * - Usa synchronized(busStop) para entrar na seção crítica compartilhada com demais alunos e ônibus.
     * - Chama wait() para aguardar a notificação de chegada de ônibus (notifyAll() em {@link BusStop}).
     * - Ao acordar, verifica se há ônibus com vaga; se não houver, volta a esperar.
     * - Se houver vaga, remove-se da fila de alunos e chama {@link Bus#addStudent(Student)}.
     *
     * A lógica de status garante a regra: alunos que chegam durante o embarque não tentam
     * embarcar neste ônibus (permanecem como WAITING_FOR_NEXT_BUS) e só serão notificados novamente
     * na chegada do próximo ônibus.
     *
     * Este método bloqueia até que o aluno consiga embarcar em algum ônibus.
     *
     * @param busStop ponto de ônibus compartilhado
     */
    public void moveToBusStopAndWaitForBus(BusStop busStop) {
        this.status = StudentStatus.WAITING_FOR_NEXT_BUS;
        System.out.printf("Student %s is waiting for a bus to arrive\n", this.name);

        while (true) {
            synchronized (busStop) {
                try {
                    busStop.studentLine.add(this);
                    busStop.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Bus availableBus = busStop.getFirstBusWithAvailableSeat();

                if (availableBus == null) {
                    this.status = StudentStatus.WAITING_FOR_NEXT_BUS;
                    System.out.printf("No available bus for student %s\n", this.name);
                    try {
                        busStop.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    this.status = StudentStatus.IN_BUS;
                    System.out.printf("Available bus for student %s, bus code:%s\n", this.name, availableBus.getCode());

                    busStop.studentLine.remove(this);
                    availableBus.addStudent(this);
                    break;
                }
            }
        }
    }
}
