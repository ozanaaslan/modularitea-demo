package com.github.ozanaaslan.librespeak.logic.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.UUID;
@Builder
public class User {
    @Getter @Setter private Identity identity;
    @Getter @Setter private String description;
    @Getter @Setter private String avatar;

    @Getter @Setter private boolean blocked = false;
    @Getter @Setter private boolean befriended = false;
    @Getter @Setter private boolean microphoneMuted = false;
    @Getter @Setter private boolean soundMuted = false;

    public User(Identity identity) {
        this.identity = identity;
    }

    /**
     * Serializes the entire user profile, including the identity, to JSON.
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("identity", identity.toJSON());
        json.put("description", description != null ? description : "");
        json.put("avatar", avatar != null ? avatar : "");
        json.put("blocked", blocked);
        json.put("befriended", befriended);
        json.put("microphoneMuted", microphoneMuted);
        json.put("soundMuted", soundMuted);
        return json;
    }

    /**
     * Creates a User object from a JSONObject.
     */
    public static User fromJSON(JSONObject json) {
        Identity id = Identity.fromJSON(json.getJSONObject("identity"));

        User user = new User(id);
        user.setDescription(json.optString("description", ""));
        user.setAvatar(json.optString("avatar", ""));
        user.setBlocked(json.optBoolean("blocked", false));
        user.setBefriended(json.optBoolean("befriended", false));

        return user;
    }
}