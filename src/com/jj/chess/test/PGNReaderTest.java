package com.jj.chess.test;

import ictk.boardgame.History;
import ictk.boardgame.chess.ChessBoard;
import ictk.boardgame.chess.ChessGame;
import ictk.boardgame.chess.ChessMove;
import ictk.boardgame.chess.ChessResult;
import ictk.boardgame.chess.io.ChessMoveNotation;
import ictk.boardgame.chess.io.FEN;
import ictk.boardgame.chess.io.PGNReader;
import ictk.boardgame.chess.io.SAN;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import com.jj.chess.shared.ChessPositionStatistics;

public class PGNReaderTest {
   public static ChessGame game;
   public static ChessMoveNotation san = new SAN();
	   
	public static void main(String[] args) {
		PGNReader reader = null;

		// check the command line args
		if (args.length != 1) {
			System.err.println("usage: java CLIPGNViewer <pgn file>");
			System.exit(1);
		}
		
		DaoMemoryDatastore dao = DaoMemoryDatastore.getInstance();

		try {
			// establish the reader object
			reader = new PGNReader(new FileReader(new File(args[0])));

			System.out.println("Map size: " + dao.memoryDatabase.size());

			FEN f = new FEN();
			// read in the first game in the file
			try{
				int t = 0;
				for(int i = 0; i < 100; i++) {
					game = (ChessGame) reader.readGame();
					
					ChessBoard board = (ChessBoard)game.getBoard();
					board.setPositionDefault();
					ChessResult result = (ChessResult)game.getResult();
					History moveHistory = game.getHistory();
					// Move
					moveHistory.rewind();
					for(int move = 0; move < moveHistory.size(); move++) {
						t++;
						String s[] = f.boardToString(board).split(" ");
						ChessPositionStatistics cps = dao.getOrCreateChessPositionStatistics(s[0]+s[1]+s[2]);
	
						if(result.isBlackWin())
							cps.incNumberOfBlackWinnings();
						else if(result.isWhiteWin())
							cps.incNumberOfWhiteWinnings();
						else if(result.isDraw())
							cps.incNumberOfTies();
						
						dao.putChessPositionStatistics(cps);
						ChessMove cm = (ChessMove)moveHistory.next();
					}
					if((t&((2^12)+1)) == ((2^12)+1) && i != 0){
						System.out.println("Number of moves: " + t + " Unieque: " + dao.memoryDatabase.size() + " diff: " + 
								(t-dao.memoryDatabase.size()) + " (" + ((t-dao.memoryDatabase.size())/(float)t) + ")");
					}
				}
			}catch (Exception e) {
				System.out.println(e);
			}
			
			ChessPositionStatistics test = dao.getOrCreateChessPositionStatistics("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNRwKQkq");
			//System.out.println(test);
			System.out.println(test.getNumberOfBlackWinnings() + test.getNumberOfTies() + test.getNumberOfWhiteWinnings());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
