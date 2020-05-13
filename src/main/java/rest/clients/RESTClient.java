package rest.clients;

import com.google.gson.Gson;
import org.glassfish.jersey.client.ClientConfig;
import rest.models.Placeholder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class RESTClient {
    private ClientConfig config;
    private Client client;
    private WebTarget target;

    public RESTClient(String uri) {
        this.config = new ClientConfig();
        this.client = ClientBuilder.newClient();
        target = client.target(UriBuilder.fromUri(uri));
    }

    public Placeholder[] index() {
        Response response = target.request().accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        return new Gson().fromJson(response.readEntity(String.class), Placeholder[].class);
    }

    public Placeholder create(Placeholder placeholder) {
        Response response = target.request().post(Entity.entity(new Gson().toJson(placeholder), MediaType.APPLICATION_JSON_TYPE));
        return new Gson().fromJson(response.readEntity(String.class), Placeholder.class);
    }

    public Placeholder show(UUID id) {
        Response response = target.path("/" + id.toString()).request().accept(MediaType.APPLICATION_JSON_TYPE).get(Response.class);
        return new Gson().fromJson(response.readEntity(String.class), Placeholder.class);
    }

    public Placeholder update(Placeholder placeholder) {
        Response response = target.path("/" + placeholder.getId().toString()).request().put(Entity.entity(new Gson().toJson(placeholder), MediaType.APPLICATION_JSON_TYPE));
        return new Gson().fromJson(response.readEntity(String.class), Placeholder.class);
    }

    public Placeholder delete(UUID id) {
        Response response = target.path("/" + id.toString()).request().delete();
        return new Gson().fromJson(response.readEntity(String.class), Placeholder.class);
    }

    public static void main(String[] args) {
        RESTClient client = new RESTClient("http://localhost:9999/crud/");
        Placeholder newPlaceholder = client.create(new Placeholder(null, "Tiago", 21));
        System.out.println("> [ create ] " + newPlaceholder.getId() + " | " + newPlaceholder.getFirstname() + " | " + newPlaceholder.getAge());
        Placeholder placeholder = client.show(newPlaceholder.getId());
        System.out.println("> [ show ] " + placeholder.getId() + " | " + placeholder.getFirstname() + " | " + placeholder.getAge());
        placeholder.setFirstname("Tiago Silva");
        Placeholder updatedPlaceholder = client.update(placeholder);
        System.out.println("> [ update ] " + updatedPlaceholder.getId() + " | " + updatedPlaceholder.getFirstname() + " | " + updatedPlaceholder.getAge());
        System.out.println("> [ index ] " + Arrays.toString(client.index()));
        Placeholder deletedPlaceholder = client.delete(placeholder.getId());
        System.out.println("> [ delete ] " + deletedPlaceholder.getId() + " | " + deletedPlaceholder.getFirstname() + " | " + deletedPlaceholder.getAge());
    }
}
