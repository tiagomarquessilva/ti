package rest.models;

import java.util.HashMap;
import java.util.UUID;

public class PlaceholderList {
    private static HashMap<UUID, Placeholder> placeholders = new HashMap<UUID, Placeholder>();

    public static HashMap<UUID, Placeholder> getPlaceholders() {
        return placeholders;
    }

    public static void add(Placeholder placeholder) {
        placeholders.put(placeholder.getId(), placeholder);
    }

    public static void update(UUID id, Placeholder placeholder) {
        placeholders.replace(id, placeholder);
    }

    public static void remove(UUID id) {
        placeholders.remove(id);
    }
}
