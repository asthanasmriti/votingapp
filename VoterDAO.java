package dao;

import java.sql.*;
import db.DBConnector;

public class VoterDAO {
    public static boolean isValidVoter(String id, String name) {
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM voters WHERE voter_id=? AND name=? AND has_voted=FALSE")) {
            ps.setString(1, id);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public static void markVoted(String id) {
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE voters SET has_voted=TRUE WHERE voter_id=?")) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
