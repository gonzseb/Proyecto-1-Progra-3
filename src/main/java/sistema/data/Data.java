package sistema.data;

import java.util.ArrayList;
import java.util.List;

import sistema.logic.entities.Medico;
import sistema.logic.entities.Farmaceuta;
import sistema.logic.entities.Paciente;
import sistema.logic.entities.Medicamento;

public class Data {
    private List<Medico> medicos;
    private List<Farmaceuta> farmaceutas;
    private List<Paciente> pacientes;
    private List<Medicamento> medicamentos;

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

        //
        farmaceutas = new ArrayList<>();

        Farmaceuta farma1 = new Farmaceuta("F001", "Pepe Figueres");
        Farmaceuta farma2 = new Farmaceuta("F002", "Laura Chinchilla");
        Farmaceuta farma3 = new Farmaceuta("F003", "Carlos Alvarado");
        Farmaceuta farma4 = new Farmaceuta("F004", "Rodrigo Chavez");

        farmaceutas.add(farma1);
        farmaceutas.add(farma2);
        farmaceutas.add(farma3);
        farmaceutas.add(farma4);

        //
        pacientes = new ArrayList<>();

        Paciente p1 = new Paciente("P001", "Karen Alvares", 40, "86875128", "21-01-1978");
        Paciente p2 = new Paciente("P002", "Eduardo Mora", 32, "85462317", "15-03-1991");
        Paciente p3 = new Paciente("P003", "Alejandra Lazo", 27, "89741236", "09-07-1996");
        Paciente p4 = new Paciente("P004", "Daniel Oviedo", 35, "81234567", "30-11-1988");

        pacientes.add(p1);
        pacientes.add(p2);
        pacientes.add(p3);
        pacientes.add(p4);

        medicamentos = new ArrayList<>();

        Medicamento m1 = new Medicamento("1001", "Antibiotico", "Pastilla");
        Medicamento m2 = new Medicamento("2002", "Antihistaminicos", "Pastilla");
        Medicamento m3 = new Medicamento("3003", "Jarabe Tos", "Bebible");
        Medicamento m4 = new Medicamento("4004", "Paracetamol", "Pastilla");

        medicamentos.add(m1);
        medicamentos.add(m2);
        medicamentos.add(m3);
        medicamentos.add(m4);

    }

    public List<Medico> getMedicos() { return medicos; }
    public List<Farmaceuta> getFarmaceutas() { return farmaceutas; }
    public List<Paciente> getPacientes() { return pacientes; }
    public List<Medicamento> getMedicamentos() { return medicamentos; }
}