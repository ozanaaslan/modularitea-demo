package com.github.ozanaaslan.librespeak.logic.user;

import com.github.ozanaaslan.librespeak.util.E2EE;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

public class Message {
    @Getter private String content;
    @Getter private User sender;
    @Getter private long timestamp;
    @Getter private String signature; // The cryptographic proof

    // Constructor for creating a NEW message (Signing it)
    public Message(String content, User sender, E2EE crypto) throws Exception {
        this.content = content;
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
        // Sign the data: "Content + Timestamp"
        this.signature = crypto.sign(content + timestamp);
    }

    // Constructor for loading an EXISTING message
    private Message() {}

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("content", content);
        json.put("sender", sender.toJSON());
        json.put("timestamp", timestamp);
        json.put("signature", signature); // Save the signature to JSON
        return json;
    }

    public static Message fromJSON(JSONObject json) {
        Message msg = new Message();
        msg.content = json.getString("content");
        msg.sender = User.fromJSON(json.getJSONObject("sender"));
        msg.timestamp = json.getLong("timestamp");
        msg.signature = json.getString("signature"); // Load the signature back
        return msg;
    }

    public boolean isAuthentic(E2EE crypto) {
        try {
            String dataToVerify = this.content + this.timestamp;
            String publicKey = this.sender.getIdentity().getPublicKey();
            return crypto.verify(dataToVerify, this.signature, publicKey);
        } catch (Exception e) {
            return false;
        }
    }
}