
public class Player implements Comparable <Player> {
	
	
	private String playerName;
	private String playerSymbol;
	private int numWins;
	
	
	//Default
	public Player() {
		this("Jane Doe", "*");
	}
	
	private Player(String name, String symbol) {
		this.playerName = name;
		this.playerSymbol = symbol;
		this.numWins = 0;
		
	}

	
	public int getWinNum() {
		return numWins;
	}


	public void setWinNum(int winNum) {
		this.numWins = winNum;
	}
	
	public void addWinNum() {
		this.numWins = numWins + 1;
	}


	public String getSymbol() {
		return playerSymbol;
	}


	public void setSymbol(String symbol) {
		this.playerSymbol = symbol;
	}


	public String getName() {
		return playerName;
	}


	public void setName(String name) {
		this.playerName = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player otherPlayer = (Player) obj;
			if (this.playerName.equals(otherPlayer.getName())) {
				if (this.playerSymbol.equals(otherPlayer.getSymbol())) {
					if (this.numWins == otherPlayer.getWinNum()) {
						return true; 
					}
				}
			}
		}
		
		return false;
	}
	

	@Override
	public String toString() {
		return "Player [name=" + playerName + ", symbol=" + playerSymbol + ", winNum=" + numWins + "]";
	}


	@Override
	public int compareTo(Player o) {
		if (this.numWins > o.getWinNum()) {
			return 1;
		} else if (this.numWins == o.getWinNum()) {
			return 0;
		} else {
			return -1;
		}
	}
	
	

}
