package Xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class javaObjectToXML {

    private static final String patientDTO_XML = "patientDto.xml";

    public static void main(String[] args){
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader bf = new BufferedReader(isr);
        patientDTO patientDTO = getPatientDTO(bf);
        convertObjectToXML(patientDTO);
    }

    private static patientDTO getPatientDTO(BufferedReader bf){
        patientDTO patientDTO = new patientDTO();
        try {
            System.out.println("Type a Cpr: ");
            String cpr = bf.readLine();
            System.out.println("Type a First name: ");
            String fName = bf.readLine();
            System.out.println("Type a Last name: ");
            String lName = bf.readLine();
            System.out.println("Type an Email: ");
            String email = bf.readLine();
            patientDTO.setCpr(cpr);
            patientDTO.setFirstName(fName);
            patientDTO.setLastName(lName);
            patientDTO.setEmail(email);
        }catch (IOException e){
            e.printStackTrace();
        }
        return patientDTO;
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