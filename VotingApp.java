package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.VoterDAO;
import dao.VoteDAO;
import chatbot.ChatBot;

public class VotingApp {
    JFrame frame;
    JTextField nameField, idField;
    String voterName;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VotingApp().showMainScreen());
    }

    void showMainScreen() {
        frame = new JFrame("🗳️ Online Voting System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(null);
        frame.setContentPane(panel);

        JLabel heading = new JLabel("Welcome to National E-Voting Portal", JLabel.CENTER);
        heading.setFont(new Font("Verdana", Font.BOLD, 20));
        heading.setForeground(Color.WHITE);
        heading.setBounds(30, 30, 440, 30);
        panel.add(heading);

        JLabel nameLbl = new JLabel("Name:");
        nameLbl.setForeground(Color.WHITE);
        nameLbl.setBounds(100, 100, 80, 25);
        panel.add(nameLbl);

        nameField = new JTextField();
        nameField.setBounds(180, 100, 200, 25);
        panel.add(nameField);

        JLabel idLbl = new JLabel("Voter ID:");
        idLbl.setForeground(Color.WHITE);
        idLbl.setBounds(100, 140, 80, 25);
        panel.add(idLbl);

        idField = new JTextField();
        idField.setBounds(180, 140, 200, 25);
        panel.add(idField);

        JButton voteBtn = new JButton("Vote Now 🗳️");
        voteBtn.setBounds(180, 190, 200, 35);
        voteBtn.setBackground(new Color(0x00cc66));
        voteBtn.setForeground(Color.WHITE);
        voteBtn.setFocusPainted(false);
        panel.add(voteBtn);

        voteBtn.addActionListener(e -> {
            voterName = nameField.getText();
            String voterId = idField.getText();
            if (VoterDAO.isValidVoter(voterId, voterName)) {
                showWelcomeAnimation(voterName, () -> showPartySelection(voterId));
            } else {
                JOptionPane.showMessageDialog(frame,
                        "❌ Sorry, your name isn't in the voters list or you have already voted.",
                        "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    void showWelcomeAnimation(String name, Runnable after) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        JLabel label = new JLabel("", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 30));
        frame.add(label, BorderLayout.CENTER);
        frame.getContentPane().setBackground(new Color(0x003366));

        String[] words = {"Welcome", name};
        Timer timer = new Timer(500, null);
        timer.addActionListener(new ActionListener() {
            int i = 0;
            public void actionPerformed(ActionEvent e) {
                if (i < words.length) {
                    label.setForeground(new Color((int)(Math.random() * 0xFFFFFF)));
                    label.setText(label.getText() + " " + words[i++]);
                } else {
                    timer.stop();
                    after.run();
                }
            }
        });
        timer.start();
    }

    void showPartySelection(String voterId) {
        frame.getContentPane().removeAll();
        frame.setLayout(new GridLayout(0, 1));
        JLabel label = new JLabel("Choose a party:", JLabel.CENTER);
        frame.add(label);

        for (String party : VoteDAO.getPartyList()) {
            JButton btn = new JButton(party);
            btn.setBackground(getPartyColor(party));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.addActionListener(e -> {
                int conf = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to vote for " + party + "? This cannot be changed.",
                        "Confirm Vote", JOptionPane.YES_NO_OPTION);
                if (conf == JOptionPane.YES_OPTION) {
                    VoteDAO.incrementVote(party);
                    VoterDAO.markVoted(voterId);
                    showThankYouAnimation();
                }
            });
            frame.add(btn);
        }

        JButton askBot = new JButton("Ask AI about Parties");
        askBot.setBackground(new Color(0x3399ff));
        askBot.setForeground(Color.WHITE);
        askBot.setFocusPainted(false);
        askBot.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame,
                    "Ask about any party (e.g., Why should I vote BJP?)");
            if (input != null) {
                String response = ChatBot.getPartyInfo(input);
                JOptionPane.showMessageDialog(frame, response);
            }
        });
        frame.add(askBot);
        frame.revalidate();
        frame.repaint();
    }

    void showThankYouAnimation() {
        frame.getContentPane().removeAll();
        JLabel thankYou = new JLabel("", JLabel.CENTER);
        thankYou.setFont(new Font("Serif", Font.BOLD, 30));
        frame.add(thankYou);
        frame.revalidate();

        String message = "Thank You For Voting!";
        Timer timer = new Timer(150, null);
        timer.addActionListener(new ActionListener() {
            int i = 0;
            public void actionPerformed(ActionEvent e) {
                if (i < message.length()) {
                    thankYou.setForeground(new Color((int)(Math.random() * 0xFFFFFF)));
                    thankYou.setText(thankYou.getText() + message.charAt(i++));
                } else timer.stop();
            }
        });
        timer.start();
    }

    private Color getPartyColor(String party) {
        switch (party.toLowerCase()) {
            case "bjp": return new Color(0xFF9933); // Saffron
            case "congress": return new Color(0x0000FF); // Blue
            case "aap": return new Color(0x00FFFF); // Cyan
            case "tmc": return new Color(0x228B22); // Forest Green
            case "shiv sena": return new Color(0xFF4500); // Orange Red
            default: return new Color(0x808080); // Gray
        }
    }
}
