package co.kc.CountryFinder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private int wins;
	private int losses;
	private int gamesPlayed;

	public User() {
		super();
	}

	public User(int userId) {
		super();
		this.userId = userId;
	}

	public User(int userId, int wins, int losses, int gamesPlayed) {
		super();
		this.userId = userId;
		this.wins = wins;
		this.losses = losses;
		this.gamesPlayed = gamesPlayed;
	}

	public int getUserId() {
		return userId;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", wins=" + wins + ", losses=" + losses + ", gamesPlayed=" + gamesPlayed
				+ "]";
	}

}
