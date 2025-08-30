package proyecto.data;

import proyecto.data.entities.*;
import proyecto.data.lists.*;

public class Data {
    private ListaMedicos medicos;
    private ListaPacientes pacientes;
    private ListaFarmaceutas farmaceutas;
    private ListaMedicamentos medicamentos;

    public Data() {
        medicos = new ListaMedicos();
        medicos.agregar(new Medico("MED001", "Sebastián González", "Cardiología"));
        medicos.agregar(new Medico("MED002", "María López", "Pediatría"));
        medicos.agregar(new Medico("MED003", "Carlos Ramírez", "Neurología"));
        medicos.agregar(new Medico("MED004", "Juan Sánchez", "Dermatología"));
        medicos.agregar(new Medico("MED005", "Pedro Sánchez", "Oftalmología"));

        pacientes = new ListaPacientes();
        farmaceutas = new ListaFarmaceutas();
        medicamentos = new ListaMedicamentos();
    }

    public ListaMedicos getMedicos() {
        return medicos;
    }

    public ListaPacientes getPacientes() {
        return pacientes;
    }

    public ListaFarmaceutas getFarmaceutas() {
        return farmaceutas;
    }

    public ListaMedicamentos getMedicamentos() {
        return medicamentos;
    }
}