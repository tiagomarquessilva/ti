package rest.services;

import rest.models.Placeholder;

import javax.ws.rs.*;

import com.google.gson.Gson;
import rest.models.PlaceholderList;

import java.util.HashMap;
import java.util.UUID;

@Path("crud")
public class CRUDWebService {
    private HashMap<UUID, Placeholder> placeholders;

    public CRUDWebService() {
        placeholders = PlaceholderList.getPlaceholders();
    }

    @GET
    public String index() {
        System.out.println("> [index] " + this.placeholders);
        return new Gson().toJson(this.placeholders.values().toArray());
    }

    @POST
    public String create(String jsonInput) {
        Placeholder placeholder = new Gson().fromJson(jsonInput, Placeholder.class);
        placeholder.setId(UUID.randomUUID());
        PlaceholderList.add(placeholder);
        System.out.println("> [create] " + placeholder);
        return new Gson().toJson(placeholder, Placeholder.class);
    }

    @GET
    @Path("{id}")
    public String show(@PathParam("id") UUID id) {
        Placeholder placeholder = null;
        if (id != null) {
            placeholder = this.placeholders.get(id);
        }
        System.out.println("> [show] " + placeholder);
        return new Gson().toJson(placeholder, Placeholder.class);
    }

    @PUT
    @Path("{id}")
    public String update(@PathParam("id") UUID id, String jsonInput) {
        Placeholder nextPlaceholder = null;
        if (id != null) {
            Placeholder prevPlaceholder = this.placeholders.get(id);
            nextPlaceholder = new Gson().fromJson(jsonInput, Placeholder.class);
            nextPlaceholder.setId(id);
            if (nextPlaceholder.getFirstname() == null) {
                nextPlaceholder.setFirstname(prevPlaceholder.getFirstname());
            }
            if (nextPlaceholder.getAge() == 0) {
                nextPlaceholder.setAge(prevPlaceholder.getAge());
            }
            PlaceholderList.update(nextPlaceholder.getId(), nextPlaceholder);
        }
        System.out.println("> [update] " + nextPlaceholder);
        return new Gson().toJson(nextPlaceholder, Placeholder.class);
    }

    @DELETE
    @Path("{id}")
    public String delete(@PathParam("id") UUID id) {
        Placeholder placeholder = null;
        if (id != null) {
            placeholder = this.placeholders.get(id);
            PlaceholderList.remove(id);
        }
        System.out.println("> [delete] " + placeholder);
        return new Gson().toJson(placeholder, Placeholder.class);
    }
}
