package ui;

import dao.VoterDAO;
import model.Voter;

import javax.swing.*;
import java.awt.*;

public class VotingApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VotingApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Online Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JButton registerButton = new JButton("Register");
        JButton voteButton = new JButton("Vote");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(registerButton, gbc);
        gbc.gridy = 3;
        panel.add(voteButton, gbc);

        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || email.isEmpty()) {
                showMessage("Error", "Name and Email cannot be empty.");
                return;
            }

            Voter voter = new Voter(name, email);
            if (new VoterDAO().registerVoter(voter)) {
                showMessage("Success", "Registration successful.");
            } else {
                showMessage("Error", "Registration failed. Email might already be registered.");
            }
        });

        voteButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                showMessage("Error", "Email cannot be empty.");
                return;
            }

            if (new VoterDAO().vote(email)) {
                showMessage("Success", "Vote recorded.");
            } else {
                showMessage("Error", "You may have already voted or are not registered.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
