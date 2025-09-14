package sistema.data;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;


//En detalle receta, la fecha de confeccion y fecha de retiro se nos presento el error a la hora de guardar, por lo que nos vimos en la obligacion de hacer esta clase

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String v) { return LocalDate.parse(v); }

    @Override
    public String marshal(LocalDate v) { return v.toString(); }
}
