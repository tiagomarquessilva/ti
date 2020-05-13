package soap.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import soap.models.Placeholder;

import java.util.HashMap;
import java.util.UUID;

@WebService
public class CRUDWebService {
    private HashMap<UUID, Placeholder> placeholders;

    public CRUDWebService() {
        placeholders = new HashMap<UUID, Placeholder>();
    }

    @WebMethod
    @WebResult(name = "placeholders")
    public Object[] index() {
        System.out.println("> [index] " + this.placeholders);
        return this.placeholders.values().toArray();
    }

    @WebMethod
    @WebResult(name = "placeholder")
    public Placeholder create(@WebParam(name = "firstname") String firstname, @WebParam(name = "age") int age) {
        Placeholder placeholder = new Placeholder(UUID.randomUUID(), firstname, age);
        this.placeholders.put(placeholder.getId(), placeholder);
        System.out.println("> [create] " + placeholder);
        return placeholder;
    }

    @WebMethod
    @WebResult(name = "placeholder")
    public Placeholder show(@WebParam(name = "id") UUID id) {
        Placeholder placeholder = null;
        if (id != null) {
            placeholder = this.placeholders.get(id);
        }
        System.out.println("> [show] " + placeholder);
        return placeholder;
    }

    @WebMethod
    @WebResult(name = "placeholder")
    public Placeholder update(@WebParam(name = "id") UUID id, @WebParam(name = "firstname") String firstname, @WebParam(name = "age") int age) {
        Placeholder placeholder = null;
        if (id != null) {
            placeholder = this.placeholders.get(id);
            if (firstname != null && !firstname.isEmpty()) {
                placeholder.setFirstname(firstname);
            }
            if (age != 0) {
                placeholder.setAge(age);
            }
            this.placeholders.replace(placeholder.getId(), placeholder);
        }

        System.out.println("> [update] " + placeholder);
        return placeholder;
    }

    @WebMethod
    @WebResult(name = "placeholder")
    public Placeholder delete(@WebParam(name = "id") UUID id) {
        Placeholder placeholder = null;
        if (id != null) {
            placeholder = this.placeholders.get(id);
            this.placeholders.remove(id);
        }
        System.out.println("> [delete] " + placeholder);
        return placeholder;
    }
}
