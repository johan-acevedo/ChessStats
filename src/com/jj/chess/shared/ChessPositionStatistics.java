package com.jj.chess.shared;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;

/**
 * Stores statistics for a chess position. It is identified by a string
 * containing the FEN notation of the board position.
 * @author Acevedo
 *
 */
public class ChessPositionStatistics implements Serializable{
	/**
	 * Primary Id key for the datastore based on a FEN string
	 */
	@Id
	private String fenId;
	
	@Unindexed
	private int numberOfWhiteWinnings;
	
	@Unindexed
	private int numberOfBlackWinnings;
	
	@Unindexed
	private int numberOfTies;

	// Default constructor
	public ChessPositionStatistics() {	}
	
	public ChessPositionStatistics(String fenId) {
		super();
		this.fenId = fenId;
		this.numberOfWhiteWinnings = 0;
		this.numberOfBlackWinnings = 0;
		this.numberOfTies = 0;
	}

	public void setFenId(String fenId) {
		this.fenId = fenId;
	}

	public String getFenId() {
		return fenId;
	}

	public void setNumberOfWhiteWinnings(int numberOfWhiteWinnings) {
		this.numberOfWhiteWinnings = numberOfWhiteWinnings;
	}

	public int getNumberOfWhiteWinnings() {
		return numberOfWhiteWinnings;
	}
	
	public void incNumberOfWhiteWinnings() {
		numberOfWhiteWinnings++;
	}

	public void setNumberOfBlackWinnings(int numberOfBlackWinnings) {
		this.numberOfBlackWinnings = numberOfBlackWinnings;
	}

	public int getNumberOfBlackWinnings() {
		return numberOfBlackWinnings;
	}
	
	public void incNumberOfBlackWinnings() {
		this.numberOfBlackWinnings++;
	}

	public void setNumberOfTies(int numberOfTies) {
		this.numberOfTies = numberOfTies;
	}

	public int getNumberOfTies() {
		return numberOfTies;
	}
	
	public void incNumberOfTies() {
		numberOfTies++;
	}
}
