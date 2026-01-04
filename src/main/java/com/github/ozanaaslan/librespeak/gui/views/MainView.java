package com.github.ozanaaslan.librespeak.gui.views;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class MainView extends javax.swing.JFrame {

    @Getter private JMenuBar menuBar;
    @Getter private JTabbedPane tabbedPane;

    public MainView() {
        super("LibreSpeak");
        init();
    }

    private void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 580);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildMenuBar(), BorderLayout.NORTH);
        this.tabbedPane = new JTabbedPane();
        add(this.tabbedPane, BorderLayout.CENTER);
    }

    private JMenuBar buildMenuBar() {
        this.menuBar = new JMenuBar();

        this.menuBar.add(new JMenu("Connections"));
        this.menuBar.add(new JMenu("Bookmarks"));
        this.menuBar.add(new JMenu("Self"));
        this.menuBar.add(new JMenu("Permissions"));
        this.menuBar.add(new JMenu("Tools"));
        this.menuBar.add(new JMenu("Plugins"));
        this.menuBar.add(new JMenu("Help"));

        return this.menuBar;
    }

    public static void main(String[] args) {
        new MainView().setVisible(true);
    }

}

