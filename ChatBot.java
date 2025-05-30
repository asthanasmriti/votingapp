package chatbot;

import java.sql.*;
import db.DBConnector;

public class ChatBot {
    public static String getPartyInfo(String partyName) {
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM parties WHERE name LIKE ?")) {
            ps.setString(1, "%" + partyName + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "<html><b>" + rs.getString("name") + "</b><br>" +
                        "Leader: " + rs.getString("leader") + "<br>" +
                        "Motto: " + rs.getString("motto") + "<br>" +
                        "Achievements: " + rs.getString("achievements") + "</html>";
            } else {
                return "Party details not found.";
            }
        } catch (Exception e) {
            return "Error fetching info.";
        }
    }
}
