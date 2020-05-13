package rest.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import rest.services.CRUDWebService;

public class RESTServer {
    public static void main(String[] args) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyRESTServer = new Server(9999);
        jettyRESTServer.setHandler(context);

        //endpoint
        ServletHolder crudEndpoint = context.addServlet(ServletContainer.class, "/*");
        crudEndpoint.setInitParameter("jersey.config.server.provider.classnames", CRUDWebService.class.getCanonicalName());

        try {
            jettyRESTServer.start();
            System.out.println("> [server] started");
            jettyRESTServer.join();
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
