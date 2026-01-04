package com.github.ozanaaslan.librespeak.gui.components;

import com.github.ozanaaslan.librespeak.logic.user.Chat;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class ChatPane extends JPanel {

    @Getter private final JTextArea chatArea = new JTextArea();
    @Getter private final JScrollPane scrollPane = new JScrollPane(chatArea);
    @Getter private Chat chat;

    public ChatPane() {
        setPreferredSize(new Dimension(100, 130));
        setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        setBackground(Color.WHITE);

    }

    private void init() {

    }

    public void setChat(Chat chat) {
/*        this.chat = chat;
        chatArea.setText(chat.toString());*/
    }



}
