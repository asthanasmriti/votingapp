package dao;

import java.sql.*;
import java.util.*;
import db.DBConnector;

public class VoteDAO {
    public static List<String> getPartyList() {
        List<String> parties = new ArrayList<>();
        try (Connection conn = DBConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM parties")) {
            while (rs.next()) parties.add(rs.getString("name"));
        } catch (Exception e) { }
        return parties;
    }

    public static void incrementVote(String partyName) {
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE parties SET vote_count = vote_count + 1 WHERE name = ?")) {
            ps.setString(1, partyName);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
