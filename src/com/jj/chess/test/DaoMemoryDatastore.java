package com.jj.chess.test;

import java.util.HashMap;
import java.util.logging.Logger;

import com.googlecode.objectify.util.DAOBase;
import com.jj.chess.server.Dao;
import com.jj.chess.shared.ChessPositionStatistics;

/**
 * Implementation of the data abstraction object (Dao) for Google Datastore using Objectify 3:d party API.
 * @author Acevedo
 *
 */
public class DaoMemoryDatastore extends DAOBase implements Dao {

    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(DaoMemoryDatastore.class.getName());
	
    // Memory storage
    public HashMap<String, ChessPositionStatistics> memoryDatabase;
    
	// Singleton pattern
	private static DaoMemoryDatastore instance;
	public static DaoMemoryDatastore getInstance() {
		if(instance == null){
			instance = new DaoMemoryDatastore();
		}
		return instance;
	}
	
	private DaoMemoryDatastore(){
		memoryDatabase = new HashMap<String, ChessPositionStatistics>(100000);
	}

	@Override
	public ChessPositionStatistics getOrCreateChessPositionStatistics(String fenId) {
		ChessPositionStatistics fetchedPosition = memoryDatabase.get(fenId);
		if(fetchedPosition != null){
			return fetchedPosition;
		} else {
			ChessPositionStatistics newPosition = new ChessPositionStatistics(fenId);
			memoryDatabase.put(newPosition.getFenId(), newPosition);
			return newPosition;
		}
	}

	@Override
	public void putChessPositionStatistics(
			ChessPositionStatistics chessPositionStatistics) {
		memoryDatabase.put(chessPositionStatistics.getFenId(), chessPositionStatistics);
	}
	
}
