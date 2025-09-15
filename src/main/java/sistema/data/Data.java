package sistema.data;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sistema.logic.entities.*;
import sistema.logic.entities.enums.EstadoReceta;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    @XmlElementWrapper(name = "medicos")
    @XmlElement(name = "medico")
    private List<Medico> medicos;

    @XmlElementWrapper(name = "farmaceutas")
    @XmlElement(name = "farmaceuta")
    private List<Farmaceuta> farmaceutas;

    @XmlElementWrapper(name = "pacientes")
    @XmlElement(name = "paciente")
    private List<Paciente> pacientes;

    @XmlElementWrapper(name = "medicamentos")
    @XmlElement(name = "medicamento")
    private List<Medicamento> medicamentos;

    @XmlElementWrapper(name = "admins")
    @XmlElement(name = "admin")
    private List<Admin> admins;

    @XmlElementWrapper(name = "recetas")
    @XmlElement(name = "receta")
    private List<Receta> recetas;

    public Data() {

        // Datos quemados
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

        //
        medicamentos = new ArrayList<>();

        Medicamento m1 = new Medicamento("1001", "Antibiotico", "Pastilla");
        Medicamento m2 = new Medicamento("2002", "Antihistaminicos", "Pastilla");
        Medicamento m3 = new Medicamento("3003", "Jarabe Tos", "Bebible");
        Medicamento m4 = new Medicamento("4004", "Paracetamol", "Pastilla");

        medicamentos.add(m1);
        medicamentos.add(m2);
        medicamentos.add(m3);
        medicamentos.add(m4);

        //
        admins = new ArrayList<>();

        Admin a1 = new Admin("ADM001", "ADM001");
        Admin a2 = new Admin("ADM002", "ADM002");
        Admin a3 = new Admin("ADM002", "ADM002");

        admins.add(a1);
        admins.add(a2);
        admins.add(a3);

        //
        recetas = new ArrayList<>();

        // Receta 1
        Receta r1 = new Receta("REC001", "MED001", "P001", LocalDate.of(2024, 11, 15));
        r1.setFechaRetiro(LocalDate.of(2024, 11, 20));
        r1.setEstado(EstadoReceta.ENTREGADA);
        r1.agregarDetalle(new RecetaDetalle("1001", 21, "Tomar 1 cada 8 horas después de las comidas", 7));
        r1.agregarDetalle(new RecetaDetalle("4004", 15, "Para el dolor, máximo 1 cada 8 horas", 5));
        recetas.add(r1);

        // Receta 2
        Receta r2 = new Receta("REC002", "MED002", "P002", LocalDate.of(2024, 12, 1));
        r2.setEstado(EstadoReceta.LISTA);
        r2.agregarDetalle(new RecetaDetalle("2002", 20, "Una pastilla cada 12 horas para la alergia", 10));
        r2.agregarDetalle(new RecetaDetalle("3003", 1, "15ml cada 6 horas para la tos", 7));
        recetas.add(r2);

        // Receta 3
        Receta r3 = new Receta("REC003", "MED003", "P003", LocalDate.of(2024, 12, 5));
        r3.setEstado(EstadoReceta.PROCESO);
        r3.agregarDetalle(new RecetaDetalle("4004", 9, "Solo si hay dolor intenso, máximo 3 al día", 3));
        recetas.add(r3);

        // Receta 4
        Receta r4 = new Receta("REC004", "MED004", "P004", LocalDate.of(2024, 12, 8));
        r4.setEstado(EstadoReceta.CONFECCIONADA);
        r4.agregarDetalle(new RecetaDetalle("1001", 28, "Tomar 1 cada 12 horas con abundante agua", 14));
        r4.agregarDetalle(new RecetaDetalle("2002", 14, "Una en la mañana, una en la noche", 7));
        r4.agregarDetalle(new RecetaDetalle("4004", 10, "Solo para fiebre mayor a 38°C, máximo 2 al día", 5));
        recetas.add(r4);

        // Receta 5
        Receta r5 = new Receta("REC005", "MED001", "P002", LocalDate.of(2024, 12, 10));
        r5.setFechaRetiro(LocalDate.of(2024, 12, 12));
        r5.setEstado(EstadoReceta.ENTREGADA);
        r5.agregarDetalle(new RecetaDetalle("3003", 1, "10ml cada 8 horas para la tos seca nocturna", 5));
        recetas.add(r5);

        // Fin de datos quemados
    }

    // Get de Listas

    public List<Medico> getMedicos() { return medicos; }
    public List<Farmaceuta> getFarmaceutas() { return farmaceutas; }
    public List<Paciente> getPacientes() { return pacientes; }
    public List<Medicamento> getMedicamentos() { return medicamentos; }
    public List<Admin> getAdmins() { return admins; }
    public List<Receta> getRecetas() { return recetas; }
}