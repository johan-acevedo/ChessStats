package com.jj.chess.server;

import com.jj.chess.shared.ChessPositionStatistics;

public interface Dao {

	public abstract ChessPositionStatistics getOrCreateChessPositionStatistics(String fenId);
	
	public abstract void putChessPositionStatistics(ChessPositionStatistics chessPositionStatistics);
}
