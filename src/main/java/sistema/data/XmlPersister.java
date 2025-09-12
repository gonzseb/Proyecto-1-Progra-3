package sistema.data;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;

public class XmlPersister {
    private static XmlPersister theInstance;
    private static final String DATA_FILE = "data.xml";

    public static XmlPersister instance() {
        if (theInstance == null) theInstance = new XmlPersister();
        return theInstance;
    }

    public void store(Data data) {
        try {
            JAXBContext context = JAXBContext.newInstance(Data.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File(DATA_FILE);
            System.out.println("Writing XML to: " + file.getAbsolutePath());

            marshaller.marshal(data, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Data load() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Data.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        FileInputStream in = new FileInputStream(DATA_FILE);
        Data data = (Data) unmarshaller.unmarshal(in);
        in.close();

        return data;
    }
}