package jaxb;

import jaxb.models.Placeholder;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.UUID;

public class XMLActions {
    public static boolean objectToXML(Object object, String xmlFilename) {
        boolean success = false;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            File file = new File(xmlFilename);
            jaxbMarshaller.marshal(object, file);
            success = true;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static Object xmlToObject(Class ClassToCast, String xmlFilename) {
        File file = new File(xmlFilename);
        JAXBContext jaxbContext;
        Object object = null;
        try {
            jaxbContext = JAXBContext.newInstance(ClassToCast);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            object = jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static boolean isValid(Class ClassToCast, String schemaFilename, String xmlFilename) {
        boolean valid;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ClassToCast);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(schemaFilename));
            jaxbUnmarshaller.setSchema(schema);
            jaxbUnmarshaller.unmarshal(new File(xmlFilename));
            valid = true;
        } catch (JAXBException | SAXException e) {
            valid = false;
        }
        return valid;
    }

    public static void main(String[] args) {
        // Object to XML
        Placeholder placeholder = new Placeholder(UUID.randomUUID(), "Tiago", 21);
        System.out.println("> [object to xml] " + objectToXML(placeholder, "placeholder.xml"));

        // XML to Object
        Placeholder otherPlaceholder = (Placeholder) xmlToObject(Placeholder.class, "placeholder.xml");
        System.out.println("> [xml to object] " + otherPlaceholder);

        // XML Validator
        System.out.println("> [xml validator] " + isValid(Placeholder.class, "placeholder.xsd", "placeholder.xml"));
    }
}
