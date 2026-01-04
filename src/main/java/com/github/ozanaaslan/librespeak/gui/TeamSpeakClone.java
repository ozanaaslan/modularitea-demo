package com.github.ozanaaslan.librespeak.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class TeamSpeakClone {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            new TeamSpeakClone().createAndShow();
        });
    }

    private void createAndShow() {
        JFrame frame = new JFrame("TeamSpeak 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 580);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.setJMenuBar(createMenuBar());
        frame.add(createToolBar(), BorderLayout.NORTH);
        frame.add(createMainContent(), BorderLayout.CENTER);
        frame.add(createStatusBar(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // -----------------------------
    // Menu Bar
    // -----------------------------
    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        bar.add(new JMenu("Connections"));
        bar.add(new JMenu("Bookmarks"));
        bar.add(new JMenu("Self"));
        bar.add(new JMenu("Permissions"));
        bar.add(new JMenu("Tools"));
        bar.add(new JMenu("Plugins"));
        bar.add(new JMenu("Help"));

        return bar;
    }

    // -----------------------------
    // Toolbar
    // -----------------------------
    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        toolBar.add(createToolButton());
        toolBar.add(createToolButton());
        toolBar.add(createToolButton());
        toolBar.addSeparator();
        toolBar.add(createToolButton());
        toolBar.add(createToolButton());

        return toolBar;
    }

    private JButton createToolButton() {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(24, 24));
        btn.setFocusable(false);
        return btn;
    }

    // -----------------------------
    // Main Content
    // -----------------------------
    private Component createMainContent() {
        JPanel container = new JPanel(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                createLeftPanel(),
                createRightPanel()
        );
        splitPane.setDividerLocation(350);
        splitPane.setDividerSize(4);
        splitPane.setBorder(null);

        container.add(splitPane, BorderLayout.CENTER);
        container.add(createChatPanel(), BorderLayout.SOUTH);

        return container;
    }

    private Component createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new MatteBorder(1, 1, 1, 0, Color.LIGHT_GRAY));
        panel.setLayout(new BorderLayout());

        return panel;
    }

    private Component createRightPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new MatteBorder(1, 0, 1, 1, Color.LIGHT_GRAY));

        JLabel watermark = new JLabel("teamspeak");
        watermark.setForeground(new Color(200, 200, 200));
        watermark.setFont(watermark.getFont().deriveFont(Font.BOLD, 28f));

        panel.add(watermark);

        return panel;
    }

    // -----------------------------
    // Chat Panel
    // -----------------------------
    private Component createChatPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(100, 130));
        panel.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        panel.setBackground(Color.WHITE);

        return panel;
    }

    // -----------------------------
    // Status Bar
    // -----------------------------
    private Component createStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBorder(new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        bar.setPreferredSize(new Dimension(100, 26));

        JLabel left = new JLabel("  No server");
        JLabel right = new JLabel("Disconnected  ");
        right.setHorizontalAlignment(SwingConstants.RIGHT);

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);

        return bar;
    }
}
