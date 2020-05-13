package soap.clients;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class Client {
    public static void sendRequestWithoutParams() {
        try {
            // create structure
            SOAPFactory soapFactory = SOAPFactory.newInstance();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPHeader soapHeader = soapMessage.getSOAPHeader();
            SOAPBody soapBody = soapMessage.getSOAPBody();
            soapHeader.detachNode();

            // add method tag to xml
            Name bodyName = soapFactory.createName("index", "ser", "http://services.soap/");
            SOAPBodyElement soapBodyElement = soapBody.addBodyElement(bodyName);

            // call
            URL endpoint = new URL("http://localhost:8888/crud");
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            SOAPMessage response = soapConnection.call(soapMessage, endpoint);

            //extract data
            SOAPBody responseSOAPBody = response.getSOAPBody();
            NodeList placeholders = responseSOAPBody.getElementsByTagName("placeholders");
            int numOfPlaceholders = placeholders.getLength();
            for (int i = 0; i < numOfPlaceholders; i++) {
                Element placeholder = (Element) placeholders.item(i);
                String idResponse = placeholder.getElementsByTagName("id").item(0).getTextContent();
                String firstnameResponse = placeholder.getElementsByTagName("firstname").item(0).getTextContent();
                String ageResponse = placeholder.getElementsByTagName("age").item(0).getTextContent();
                System.out.println(idResponse + "\t|\t" + firstnameResponse + "\t|\t" + ageResponse);
            }
        } catch (MalformedURLException | SOAPException e) {
            e.printStackTrace();
        }

    }

    public static void sendRequestWithParams() {
        UUID id = UUID.fromString("d3a30cc9-a7eb-492f-9505-050f4dde64f1");
        try {
            // create structure
            SOAPFactory soapFactory = SOAPFactory.newInstance();
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = messageFactory.createMessage();
            SOAPHeader soapHeader = soapMessage.getSOAPHeader();
            SOAPBody soapBody = soapMessage.getSOAPBody();
            soapHeader.detachNode();

            // add method tag to xml
            Name bodyName = soapFactory.createName("show", "ser", "http://services.soap/");
            SOAPBodyElement soapBodyElement = soapBody.addBodyElement(bodyName);
            // params
            Name placeholderId = soapFactory.createName("id");
            SOAPElement idElement = soapBodyElement.addChildElement(placeholderId);
            idElement.addTextNode(String.valueOf(id));

            // call
            URL endpoint = new URL("http://localhost:8888/crud");
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            SOAPMessage response = soapConnection.call(soapMessage, endpoint);

            //extract data
            SOAPBody responseSOAPBody = response.getSOAPBody();
            String idResponse = responseSOAPBody.getElementsByTagName("id").item(0).getTextContent();
            String firstnameResponse = responseSOAPBody.getElementsByTagName("firstname").item(0).getTextContent();
            String ageResponse = responseSOAPBody.getElementsByTagName("age").item(0).getTextContent();
            System.out.println(idResponse + "\t|\t" + firstnameResponse + "\t|\t" + ageResponse);

        } catch (
                SOAPException | IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendRequestWithoutParams();
        sendRequestWithParams();
    }
}
