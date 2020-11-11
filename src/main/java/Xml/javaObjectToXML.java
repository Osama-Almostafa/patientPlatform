package Xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class javaObjectToXML {

    private static final String patientDTO_XML = "patientDto.xml";

    public static void main(String[] args) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader bf = new BufferedReader(isr);
        List<patientDTO> patientDTOS = new ArrayList<>();
        patientDTO patientDTO1 = new patientDTO();
        System.out.println("Type a Cpr: ");
        String cpr = bf.readLine();
        System.out.println("Type a First name: ");
        String fName = bf.readLine();
        System.out.println("Type a Last name: ");
        String lName = bf.readLine();
        System.out.println("Type an Email: ");
        String email = bf.readLine();
        patientDTO1.setCpr(cpr);
        patientDTO1.setFirstName(fName);
        patientDTO1.setLastName(lName);
        patientDTO1.setEmail(email);
        patientDTOS.add(patientDTO1);
        convertObjectToXML(patientDTO1);
    }

    private static void convertObjectToXML(patientDTO patientDTO) {
        try {
            // create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(patientDTO.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // Write to System.out
            m.marshal(patientDTO, System.out);
            // Write to File
            m.marshal(patientDTO, new File(patientDTO_XML));
        } catch (JAXBException je) {
            je.printStackTrace();
        }
    }
}