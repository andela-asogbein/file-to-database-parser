package checkpoint.andela.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import checkpoint.andela.parser.Reaction;

public class DBOperations {
	Connection connection;
	
	public DBOperations(){
		connectToDatabase();
	}
	
	public void saveReactionToDatabase(Reaction singleReaction) throws SQLException{
					
		String uniqueId = singleReaction.get("UNIQUE-ID");
		String types = singleReaction.get("TYPES");
		String commonName = singleReaction.get("COMMON-NAME");
		String atomMappings = singleReaction.get("ATOM-MAPPINGS");
		String cannotBalance = singleReaction.get("CANNOT-BALANCE?");
		String enzymaticReaction = singleReaction.get("ENZYMATIC-REACTION");
		String orphan = singleReaction.get("ORPHAN?");
		
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reactions values (?,?,?,?,?,?,?,?)");
		
		preparedStatement.setString(1, uniqueId);
		preparedStatement.setString(2, types);
		preparedStatement.setString(3, commonName);
		preparedStatement.setString(4, atomMappings);
		preparedStatement.setString(5, cannotBalance);
		preparedStatement.setString(6, enzymaticReaction);
		preparedStatement.setString(7, orphan);
		preparedStatement.setInt(8, 0);
		
		preparedStatement.executeUpdate();
	}

	public void connectToDatabase(){
		try {
			connection = DriverManager.getConnection(Config.DATABASE_URL, Config.DATABASE_USER_NAME, Config.PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}