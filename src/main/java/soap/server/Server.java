package soap.server;

import javax.xml.ws.Endpoint;

import soap.services.CRUDWebService;

public class Server {
    public static void main(String[] args) {
        String serverURL = "http://127.0.0.1:8888";

        Endpoint endpoint = Endpoint.create(new CRUDWebService());
        endpoint.publish(serverURL + "/crud");

        System.out.println("> [server] started");
    }
}
