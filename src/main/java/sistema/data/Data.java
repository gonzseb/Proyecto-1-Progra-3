package sistema.data;

import java.util.ArrayList;
import java.util.List;

import sistema.logic.entities.Medico;

public class Data {
    private List<Medico> medicos;

    public Data() {
        medicos = new ArrayList<>();

        Medico med1 = new Medico("MED001", "Sebastián González", "Odontología");
        Medico med2 = new Medico("MED002", "David Masis", "Otorrinolaringología");
        Medico med3 = new Medico("MED003", "Juan Sánchez", "Oftalmología");
        Medico med4 = new Medico("MED004", "Pablo Bermúdez", "Geriatría");

        medicos.add(med1);
        medicos.add(med2);
        medicos.add(med3);
        medicos.add(med4);
    }

    public List<Medico> getMedicos() { return medicos; }
}