package com.github.ozanaaslan.librespeak.logic.user;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chat {
    @Getter private final UUID uuid;
    @Getter private List<Message> messages = new ArrayList<>();
    private static final int MAX_MESSAGES = 3000;

    public Chat(UUID uuid) {
        this.uuid = uuid;
    }

    public void addMessage(Message message) {
        if (messages.size() < MAX_MESSAGES) {
            messages.add(message);
        }
    }

    /**
     * Pagination logic. Returns a sublist of messages.
     */
    public List<Message> getMessagesBetween(int start, int end) {
        int actualStart = Math.max(0, start);
        int actualEnd = Math.min(messages.size(), end);

        if (actualStart >= actualEnd) return new ArrayList<>();
        return new ArrayList<>(messages.subList(actualStart, actualEnd));
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("uuid", uuid.toString());

        JSONArray msgArray = new JSONArray();
        for (Message m : messages) {
            msgArray.put(m.toJSON());
        }
        json.put("messages", msgArray);
        return json;
    }

    /**
     * @param json The chat JSON object
     * @param lazyLoad If true, we only initialize the UUID and leave messages empty.
     */
    public static Chat fromJSON(JSONObject json, boolean lazyLoad) {
        Chat chat = new Chat(UUID.fromString(json.getString("uuid")));

        if (!lazyLoad && json.has("messages")) {
            JSONArray array = json.getJSONArray("messages");
            for (int i = 0; i < array.length(); i++) {
                chat.addMessage(Message.fromJSON(array.getJSONObject(i)));
            }
        }
        return chat;
    }
}