import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class SQLQuery {

	private static Connection con;
	private static Statement stmt;
	private static String queryStr;
	private static ResultSet rs;
	private static PreparedStatement pstmt;

	// connect
	public static void connect(String username, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EmojiBubblesDB?allowPublicKeyRetrieval=true&useSSL=false", username, password);
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public static boolean insertFeedback(FeedbackData feedobj) {
			
		 //public FeedbackData(boolean enjoyment, int playersNumber, float averageAge, String notes) {
	
			String sql = "INSERT INTO feedback (enjoy, players,averageage, note) VALUES (?, ?, ?, ?)";
	
			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.setBoolean(1, feedobj.isEnjoyment());
				pstmt.setInt(2, feedobj.getPlayersNumber());
				pstmt.setFloat(3, feedobj.getAverageAge());
				pstmt.setString(4, feedobj.getNotes());
	
				int affectedRows = pstmt.executeUpdate();
				return affectedRows > 0;
	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}


	public static boolean insertStatisticData(StatisticData statData) {
	    String sql = "INSERT INTO statisticdata (numberofplayers, שמחה, רגע, עצב, חזקה, פחד, כעס, durationofplay) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	        // Set the number of players
	        pstmt.setInt(1, statData.getPlayerCounter());

	        // Set the emotion columns based on the color usage
	        pstmt.setInt(2, statData.getColorUsageCounter().getOrDefault(Color.ORANGE, 0)); // שמחה (orange)
	        pstmt.setInt(3, statData.getColorUsageCounter().getOrDefault(Color.PINK, 0));   // רגע (pink)
	        pstmt.setInt(4, statData.getColorUsageCounter().getOrDefault(Color.GRAY, 0));   // עצב (grey)
	        pstmt.setInt(5, statData.getColorUsageCounter().getOrDefault(Color.GREEN, 0));  // חזקה (green)
	        pstmt.setInt(6, statData.getColorUsageCounter().getOrDefault(new Color(102, 0, 153), 0)); // פחד (purple)
	        pstmt.setInt(7, statData.getColorUsageCounter().getOrDefault(Color.RED, 0));    // כעס (red)

	        // Set the duration of play
	        pstmt.setInt(8, statData.getDurationOfPlay());

	        // Execute the query
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0; // Returns true if the row was inserted successfully
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Return false if something goes wrong
	    }
	}

	public static ResultSet fetchStatisticData() {
        // SQL query to fetch data from the last month
        String sql = "SELECT numberofplayers, שמחה, רגע, עצב, חזקה, פחד, כעס, durationofplay, date " +
                     "FROM statisticdata " +
                     "WHERE date >= CURDATE() - INTERVAL 1 MONTH"; // Fetch data for the last month

        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(sql); // Return the ResultSet to the caller
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }
	
	// Fetch feedback data from the feedback table
    public static ResultSet fetchFeedback() {
        String sql = "SELECT id, enjoy, players, averageage, note, date FROM feedback";

        try {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(sql); // Return the ResultSet to the caller
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }


	// disconnect
	public static void disconnect() {

		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		SQLQuery.connect("root", "123456");

		// Fetch feedback data
        ResultSet feedbackRs = SQLQuery.fetchFeedback();
        if (feedbackRs != null) {
            try {
                while (feedbackRs.next()) {
                    int id = feedbackRs.getInt("id");
                    boolean enjoy = feedbackRs.getBoolean("enjoy");
                    int players = feedbackRs.getInt("players");
                    float averageAge = feedbackRs.getFloat("averageage");
                    String note = feedbackRs.getString("note");
                    Timestamp date = feedbackRs.getTimestamp("date");

                    // Output the feedback data
                    System.out.println("ID: " + id + ", Enjoy: " + enjoy + ", Players: " + players +
                            ", Average Age: " + averageAge + ", Note: " + note + ", Date: " + date);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to fetch feedback data.");
        }


		SQLQuery.disconnect();

	}

}
