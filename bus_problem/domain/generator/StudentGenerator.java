package bus_problem.domain.generator;

import bus_problem.domain.Student;
import java.util.ArrayList;
import java.util.Random;

public class StudentGenerator {

    private static final String[] FIRST_NAMES = {
            "João", "Maria", "Pedro", "Ana", "Carlos", "Lucia", "Rafael", "Fernanda",
            "Marcos", "Julia", "André", "Camila", "Ricardo", "Beatriz", "Fernando",
            "Larissa", "Gabriel", "Carla", "Diego", "Mariana", "Bruno", "Sophia",
            "Lucas", "Isabella", "Thiago", "Valentina", "Mateus", "Helena", "Paulo",
            "Amanda", "Victor", "Leticia", "Rodrigo", "Gabriela", "Leonardo", "Natalia"
    };

    private static final String[] LAST_NAMES = {
            "Silva", "Santos", "Oliveira", "Souza", "Rodrigues", "Ferreira", "Alves",
            "Pereira", "Lima", "Gomes", "Ribeiro", "Carvalho", "Almeida", "Lopes",
            "Soares", "Fernandes", "Vieira", "Barbosa", "Rocha", "Dias", "Nunes",
            "Mendes", "Ramos", "Moreira", "Araújo", "Cardoso", "Nascimento", "Correia",
            "Martins", "Costa", "Pinto", "Moura", "Monteiro", "Freitas", "Campos"
    };

    public static ArrayList<Student> generate(int howMany) {
        ArrayList<Student> students = new ArrayList<>();

        for (int i = 0; i < howMany; i++) {
            Random random = new Random();

            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String fullName = firstName + " " + lastName;

            students.add(new Student(fullName));
        }

        return students;
    }
}
