package Xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLToJavaObject {
    public static void main(String[] args) {
        try {
            File file = new File("htmlDto-jaxb.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(patientDTO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            patientDTO patientDTO = (patientDTO) jaxbUnmarshaller.unmarshal(file);
            System.out.println("Cpr: " + patientDTO.getCpr());
            System.out.println("First Name: " + patientDTO.getFirstName());
            System.out.println("Last Name: " + patientDTO.getLastName());
            System.out.println("Email: " + patientDTO.getEmail());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
