package com.example.myproject;

import com.github.ozanaaslan.modularitea.AbstractModulariteaApplication;
import com.github.ozanaaslan.modularitea.components.*;
import javax.swing.*;
import java.awt.*;

public class DashboardApp extends AbstractModulariteaApplication {

    private JTextArea logArea;

    @Override
    public void entrypoint(AbstractModulariteaApplication app) {
        // Run GUI code on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ModulariTea Dashboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLayout(new BorderLayout());

            // 1. Setup Log Area
            logArea = new JTextArea();
            logArea.setEditable(false);
            logArea.setBackground(new Color(30, 30, 30));
            logArea.setForeground(Color.GREEN);
            frame.add(new JScrollPane(logArea), BorderLayout.CENTER);

            // 2. Setup Control Panel
            JPanel panel = new JPanel();
            JButton pingBtn = new JButton("Ping Network");
            
            // Trigger an event when clicked
            pingBtn.addActionListener(e -> {
                log("Button Clicked: Dispatching PingEvent...");
                app.getEventManager().dispatch(new PingEvent("User Ping"));
            });

            panel.add(pingBtn);
            frame.add(panel, BorderLayout.SOUTH);

            frame.setVisible(true);
            log("Dashboard Initialized.");
        });
    }

    // A helper method to write to the GUI log
    public void log(String message) {
        if (logArea != null) {
            SwingUtilities.invokeLater(() -> logArea.append("[" + System.currentTimeMillis() + "] " + message + "\n"));
        }
    }

    // TEST: Service Bean - Let's make the logger available to other modules
    @ServiceManager.ServiceBean
    public DashboardLogger provideLogger() {
        return this::log;
    }

    public interface DashboardLogger { void log(String msg); }
}

class PingEvent extends EventManager.Event {
    private final String source;
    public PingEvent(String source) { this.source = source; }
    public String getSource() { return source; }
}