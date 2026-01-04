package com.github.ozanaaslan.librespeak.logic.user;

import com.github.ozanaaslan.librespeak.util.E2EE;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Identity {
    @Getter private UUID uuid;
    @Getter @Setter private String username;
    @Getter private String publicKey;
    @Getter private transient String privateKey; // transient prevents it from auto-serializing in some libs

    // Constructor for loading existing
    public Identity(UUID uuid, String username, String publicKey, String privateKey) {
        this.uuid = uuid;
        this.username = username;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    // Static factory to create an ENTIRELY new identity
    public static Identity createNew(String username) throws NoSuchAlgorithmException {
        E2EE crypto = new E2EE(); // Your existing class handles KeyPair generation
        return new Identity(
                UUID.randomUUID(),
                username,
                crypto.getPublicKey(),
                crypto.getPrivateKey()
        );
    }

    public static Identity fromJSON(JSONObject json) {
        return new Identity(
                UUID.fromString(json.getString("uuid")),
                json.getString("username"),
                json.getString("publicKey"),
                json.optString("privateKey", null) // returns null if not present
        );
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("uuid", uuid.toString());
        json.put("username", username);
        json.put("publicKey", publicKey);
        // ONLY include privateKey if it exists (for local user)
        if (privateKey != null) json.put("privateKey", privateKey);
        return json;
    }
}
