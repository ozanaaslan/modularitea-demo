package com.github.ozanaaslan.librespeak.util;

import com.github.ozanaaslan.librespeak.logic.user.Chat;
import com.github.ozanaaslan.librespeak.logic.user.Identity;
import com.github.ozanaaslan.librespeak.logic.user.User;
import lombok.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResourceManager {
    @Getter private static final String BASE_PATH = System.getProperty("user.home") + "/.librespeak/";
    @Getter private ZipFile sfxZipFile;
    @Getter private ZipFile gfxZipFile;
    @Getter private List<Identity> identities = new ArrayList<>();

    public ResourceManager(){
        init();
    }

    @SneakyThrows
    public void init(){

        Files.copy(Objects.requireNonNull(ResourceManager.class.getResourceAsStream("/assets/sfx_default.zip")),
                Path.of(BASE_PATH + "assets/sfx_default.zip"));
        Files.copy(Objects.requireNonNull(ResourceManager.class.getResourceAsStream("/assets/gfx_default.zip")),
                Path.of(BASE_PATH + "assets/gfx_default.zip"));

        this.gfxZipFile = new ZipFile(BASE_PATH + "assets/gfx_default.zip");
        this.sfxZipFile = new ZipFile(BASE_PATH + "assets/sfx_default.zip");


    }

    public void saveChat(Chat chat) throws IOException {
        Path path = Path.of(BASE_PATH + "chats/" + chat.getUuid().toString() + ".json");
        Files.createDirectories(path.getParent());
        Files.writeString(path, chat.toJSON().toString(4)); // 4 is for pretty-printing
    }

    public Chat loadChat(UUID uuid, boolean lazyLoad) throws IOException {
        Path path = Path.of(BASE_PATH + "chats/" + uuid.toString() + ".json");
        if (!Files.exists(path)) return null;

        String content = Files.readString(path);
        return Chat.fromJSON(new JSONObject(content), lazyLoad);
    }

    public UUID generateUniqueChatUUID() {
        UUID newId;
        do {
            newId = UUID.randomUUID();
        } while (Files.exists(Path.of(BASE_PATH + "chats/" + newId + ".json")));
        return newId;
    }

    public void saveUserLocally(User user) throws IOException {
        Path userPath = Path.of(BASE_PATH + "contacts/" + user.getIdentity().getUuid() + ".json");
        Files.createDirectories(userPath.getParent());
        Files.writeString(userPath, user.toJSON().toString(4));
    }

    public List<User> loadSavedUsers() throws IOException {
        List<User> users = new ArrayList<>();
        Path contactsPath = Path.of(BASE_PATH + "contacts/");
        if (!Files.exists(contactsPath)) return users;

        Files.list(contactsPath).forEach(path -> {
            try {
                String content = Files.readString(path);
                users.add(User.fromJSON(new JSONObject(content)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return users;
    }

    @Getter
    @AllArgsConstructor
    public enum EIcons{
        LOGO("kaki"),
        STUFF("kaki");
        private final String name;
    }

}
