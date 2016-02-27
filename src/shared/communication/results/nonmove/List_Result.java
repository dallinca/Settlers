package shared.communication.results.nonmove;

import java.util.ArrayList;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import client.data.GameInfo;

public class List_Result {

	private boolean valid;
	private GameInfo[] games = null;
	private LinkedList<Game> listedGames;

	// CONSTRUCTORS
	//////////////////////
	public List_Result(ArrayList<GameInfo> games) {
		this.games = new GameInfo[games.size()];
		this.games = games.toArray(this.games);
	}

	public List_Result(GameInfo[] games) {
		this.games = games;
	}

	public List_Result(JsonArray jobj) {

		listedGames = new LinkedList<Game>();
		
		for (JsonElement e: jobj){
			
			String title = e.getAsJsonObject().get("title").toString();
			int id = e.getAsJsonObject().get("id").getAsInt();
			JsonArray playerList = e.getAsJsonObject().get("players").getAsJsonArray();
			
			LinkedList<Player> players = new LinkedList<Player>();
			
			for (JsonElement p: playerList){		
				//System.out.println("LOOK HERE:::: "+p.toString());
				
				if (!p.getAsJsonObject().has("color")){
					
				}
				else{				
				String pColor = p.getAsJsonObject().get("color").toString();
				String pName = p.getAsJsonObject().get("name").toString();
				int pID = p.getAsJsonObject().get("id").getAsInt();
				players.add(new Player(pColor, pName, pID));
				}
			}
			
			listedGames.add(new Game(title, id, players));			
		}	
		setValid(true);
	}		

	public List_Result() {
		setValid(false);
	}

	// GETTERS AND SETTERS
	//////////////////////

	public LinkedList<Game> getListedGames(){
		
		return listedGames;
	}

	public GameInfo[] getGames() {
		return games;
	}

	public void setGames(GameInfo[] games) {
		this.games = games;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		for (Game g: listedGames){
			sb.append(g.toString());
		}
		return sb.toString();		
	}
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	//INTERNAL DATA OBJECTS
	public class Player{
		String color;
		String name;
		int id;
		boolean empty;
		
		public Player(String color, String name, int id){
			this.color = color;
			this.name = name;
			this.id = id;
			this.empty = false;
		}
		
		public Player(){
			this.empty = true;
		}
		
		public String toString(){		

			StringBuilder sb = new StringBuilder();

			sb.append("Player {");
			if (empty){
				sb.append("Empty slot}\n");
				return sb.toString();
			}
			sb.append("\nColor: " +color+",\n");
			sb.append("Name: " +name+",\n");
			sb.append("ID: " +id+"\n}\n");
			return sb.toString();			
		}
	}
	
	public class Game{
		String title;
		int id;
		LinkedList<Player> players;
		
		public Game(String title, int id, LinkedList<Player> players){
			this.title = title;
			this.id = id;
			this.players = players;
		}
		
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append("Game {\n");
			sb.append("Title: " +title+",\n");
			sb.append("ID: " +id+",\n");
			
			for(Player p: players){
				sb.append(p.toString());				
			}			
			sb.append("}\n");
			
			return sb.toString();
		}
		
		public String getTitle(){
			return title;
		}
		public int getID(){
			return id;
		}
	}



}
