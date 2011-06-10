package com.jj.chess.server;

import java.util.logging.Logger;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.jj.chess.shared.ChessPositionStatistics;

/**
 * Implementation of the data abstraction object (Dao) for Google Datastore using Objectify 3:d party API.
 * @author Acevedo
 *
 */
public class DaoGaeDatastore extends DAOBase implements Dao {

    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(DaoGaeDatastore.class.getName());
    
	// Register entity classes with Objectify
	static {
		ObjectifyService.register(ChessPositionStatistics.class);
	}
	
	// Singleton pattern
	private static DaoGaeDatastore instance;
	public static DaoGaeDatastore getInstance() {
		if(instance == null){
			instance = new DaoGaeDatastore();
		}
		return instance;
	}
	
	private DaoGaeDatastore(){}

	@Override
	public ChessPositionStatistics getOrCreateChessPositionStatistics(String fenId) {
		ChessPositionStatistics fetchedPosition = ofy().get(ChessPositionStatistics.class, fenId);
		if(fetchedPosition == null){
			fetchedPosition = new ChessPositionStatistics(fenId);
			ofy().put(fetchedPosition);
		}
		return fetchedPosition;
	}

	@Override
	public void putChessPositionStatistics(
			ChessPositionStatistics chessPositionStatistics) {
		ofy().put(chessPositionStatistics);
	}
	
}
