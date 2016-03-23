package shared.communication.results.nonmove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import shared.communication.results.JsonConverter;
import shared.definitions.CatanColor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import client.data.GameInfo;
import client.data.PlayerInfo;

/**
 * Result object from list result command.
 *
 */
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
		listedGames = new LinkedList<Game>();
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
		
		if (games!=null){
			return games;			
		}
		if (listedGames==null){
			return new GameInfo[0];
		}else if(listedGames.size()==0){
			return new GameInfo[0];
		}
		games = new GameInfo[listedGames.size()];
		for (int i =0;i<listedGames.size(); i++){
			games[i]=new GameInfo();
			games[i].setId(listedGames.get(i).getID());
			games[i].setTitle(listedGames.get(i).getTitle().replace("\"",""));
			
			LinkedList<Player> llp = new LinkedList<Player>();
			llp = listedGames.get(i).getPlayers();
			
			for (int j = 0; j < llp.size(); j++){
				PlayerInfo pi = new PlayerInfo();
				pi.setColor(getCatanColorFromString(llp.get(j).color.replace("\"","")));
				pi.setId(llp.get(j).id);
				pi.setName(llp.get(j).name.replace("\"",""));
				pi.setPlayerIndex(j);
				games[i].addPlayer(pi);
			}
		}
		
		return games;
	}

	public void setGames(GameInfo[] games) {
		this.games = games;
	}
	
	/*public String toString(){
		StringBuilder sb = new StringBuilder();
		
		for (Game g: listedGames){
			sb.append(g.toString());
		}
		return sb.toString();		
	}*/
	
	
	
	
	
	public boolean isValid() {
		return valid;
	}

	@Override
	public String toString() {
		return "List_Result [valid=" + valid + ", games="
				+ Arrays.toString(games) + ", listedGames=" + listedGames + "]";
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	private CatanColor getCatanColorFromString(String color) {
		if(color.equals("blue")) {
			return CatanColor.BLUE;
		} else if(color.equals("brown")) {
			return CatanColor.BROWN;
		} else if(color.equals("green")) {
			return CatanColor.GREEN;
		} else if(color.equals("orange")) {
			return CatanColor.ORANGE;
		} else if(color.equals("puce")) {
			return CatanColor.PUCE;
		} else if(color.equals("purple")) {
			return CatanColor.PURPLE;
		} else if(color.equals("red")) {
			return CatanColor.RED;
		} else if(color.equals("white")) {
			return CatanColor.WHITE;
		} else if(color.equals("yellow")) {
			return CatanColor.YELLOW;
		}
		return null;
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
		public LinkedList<Player> getPlayers(){
			return players;
		}
	}


}
