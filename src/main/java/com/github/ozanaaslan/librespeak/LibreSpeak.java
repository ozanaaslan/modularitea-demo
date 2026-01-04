package com.github.ozanaaslan.librespeak;

import com.github.ozanaaslan.librespeak.logic.user.Identity;
import com.github.ozanaaslan.librespeak.logic.user.User;
import com.github.ozanaaslan.librespeak.util.E2EE;
import com.github.ozanaaslan.librespeak.util.ResourceManager;
import com.github.ozanaaslan.modularitea.AbstractModulariteaApplication;
import lombok.Getter;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class LibreSpeak extends AbstractModulariteaApplication {

    @Getter private static LibreSpeak libreSpeak;
    @Getter private ResourceManager resourceManager;

    // The profile of the person running the app
    @Getter private User localUser;
    @Getter private E2EE crypto;

    @Override
    public void entrypoint(AbstractModulariteaApplication abstractModulariteaApplication) {
        libreSpeak = this;
        this.resourceManager = new ResourceManager();

        try {
            initLocalProfile();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle critical failure (e.g., show error dialog)
        }
    }

    private void initLocalProfile() throws Exception {
        Path profilePath = Path.of(ResourceManager.getBASE_PATH() + "self.json");

        // Loads or creates persisted local user profile
        if (Files.exists(profilePath)) {
            String content = Files.readString(profilePath);
            this.localUser = User.fromJSON(new JSONObject(content));

            this.crypto = new E2EE(
                    localUser.getIdentity().getPublicKey(),
                    localUser.getIdentity().getPrivateKey()
            );
        } else {
            this.crypto = new E2EE();

            Identity newId = new Identity(
                    UUID.randomUUID(),
                    System.getProperty("user.name"), // Default to OS username
                    crypto.getPublicKey(),
                    crypto.getPrivateKey()
            );

            this.localUser = new User(newId);
            this.localUser.setDescription("New LibreSpeak User");

            Files.createDirectories(profilePath.getParent());
            Files.writeString(profilePath, localUser.toJSON().toString(4));
        }
    }
}
