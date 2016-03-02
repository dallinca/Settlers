package client.proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import shared.communication.params.move.AcceptTrade_Params;
import shared.communication.params.move.BuildCity_Params;
import shared.communication.params.move.BuildRoad_Params;
import shared.communication.params.move.BuildSettlement_Params;
import shared.communication.params.move.BuyDevCard_Params;
import shared.communication.params.move.DiscardCards_Params;
import shared.communication.params.move.FinishTurn_Params;
import shared.communication.params.move.MaritimeTrade_Params;
import shared.communication.params.move.OfferTrade_Params;
import shared.communication.params.move.RobPlayer_Params;
import shared.communication.params.move.RollNumber_Params;
import shared.communication.params.move.SendChat_Params;
import shared.communication.params.move.devcard.PlayMonopoly_Params;
import shared.communication.params.move.devcard.PlayMonument_Params;
import shared.communication.params.move.devcard.PlayRoadBuilding_Params;
import shared.communication.params.move.devcard.PlaySoldier_Params;
import shared.communication.params.move.devcard.PlayYearOfPlenty_Params;
import shared.communication.params.nonmove.AddAI_Params;
import shared.communication.params.nonmove.Create_Params;
import shared.communication.params.nonmove.GetVersion_Params;
import shared.communication.params.nonmove.Join_Params;
import shared.communication.params.nonmove.ListAI_Params;
import shared.communication.params.nonmove.List_Params;
import shared.communication.params.nonmove.Login_Params;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.move.AcceptTrade_Result;
import shared.communication.results.move.BuildCity_Result;
import shared.communication.results.move.BuildRoad_Result;
import shared.communication.results.move.BuildSettlement_Result;
import shared.communication.results.move.BuyDevCard_Result;
import shared.communication.results.move.DiscardCards_Result;
import shared.communication.results.move.FinishTurn_Result;
import shared.communication.results.move.MaritimeTrade_Result;
import shared.communication.results.move.OfferTrade_Result;
import shared.communication.results.move.RobPlayer_Result;
import shared.communication.results.move.RollNumber_Result;
import shared.communication.results.move.SendChat_Result;
import shared.communication.results.move.devcard.PlayMonopoly_Result;
import shared.communication.results.move.devcard.PlayMonument_Result;
import shared.communication.results.move.devcard.PlayRoadBuilding_Result;
import shared.communication.results.move.devcard.PlaySoldier_Result;
import shared.communication.results.move.devcard.PlayYearOfPlenty_Result;
import shared.communication.results.nonmove.AddAI_Result;
import shared.communication.results.nonmove.Create_Result;
import shared.communication.results.nonmove.GetVersion_Result;
import shared.communication.results.nonmove.Join_Result;
import shared.communication.results.nonmove.ListAI_Result;
import shared.communication.results.nonmove.List_Result;
import shared.communication.results.nonmove.Login_Result;
import shared.communication.results.nonmove.Register_Result;
import client.ClientException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 
 * Temporary model of server and server functions.
 * 
 */

public class MockServerProxy implements IServerProxy {


	private static String SERVER_HOST = "localhost";
	private static int SERVER_PORT = 8081;
	private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	
	Gson gson = new Gson();
	//private static final String HTTP_GET = "GET";
	//private static final String HTTP_POST = "POST";

	@Override
	public AddAI_Result addAI(AddAI_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

/*	@Override
	public ChangeLogLevel_Result changeLogLevel(ChangeLogLevel_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public Create_Result createGame(Create_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		
		JsonElement json = gson.toJsonTree("{\n			  \"title\": \"myveryfirstgame\",\n			  \"id\": 3,\n			  \"players\": [\n			    {},\n			    {},\n			    {},\n			    {}\n			  ]\n			}");
		
		return new Create_Result();
		//#2
		/*
		{
			  "title": "myveryfirstgame",
			  "id": 3,
			  "players": [
			    {},
			    {},
			    {},
			    {}
			  ]
			}
		 */
		//200
		//{"Date":"Fri, 05 Feb 2016 22:30:45 GMT",
		//"Content-Type":"application/json",
		//"Content-Length":"58"}
	}

/*	@Override
	public GetCommands_Result getCommands(GetCommands_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public GetVersion_Result getVersion(GetVersion_Params request)
			throws ClientException {
		
		//System.out.println("Getting fake version.");

		File file = new File("jsonTestGame.txt");
		String testVersion = "";

		try {

			Scanner sc = new Scanner(file);
			StringBuilder sb = new StringBuilder();

			while (sc.hasNextLine()) {
				sb.append(sc.nextLine());				
			}
			testVersion = sb.toString();
			sc.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		JsonElement json = gson.toJsonTree(testVersion);
		
		GetVersion_Result result = new GetVersion_Result();
		
		//http://localhost:8081/game/model?version=1


		//response code: 200
		// TODO Auto-generated method stub

		//{"Date":"Fri, 05 Feb 2016 22:37:48 GMT",
		//"Content-Type":"application/json",
		//"Content-Length":"2489"}
		return result;
	}

	@Override
	public Join_Result joinGame(Join_Params request) throws ClientException {
		// TODO Auto-generated method stub

		//#3

		/*{
			  "id": "3",
			  "color": "blue"
		}*/
		//Success
		//200
		//{"Date":"Fri, 05 Feb 2016 22:32:31 GMT",
		//"Content-Type":"text/html",
		//"Content-Length":"7"}


		return null;
	}

	@Override
	public List_Result listGames(List_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListAI_Result listAI(ListAI_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
/*	public Load_Result loadGame(Load_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public Login_Result login(Login_Params request) throws ClientException {
		// TODO Auto-generated method stub

		//#1
		//username Sam
		//password sam

		//response body --- 
		//Success

		//response code ---
		//200		

		//response headers ---
		//"Date":"Fri, 05 Feb 2016 22:25:41 GMT",
		//"Content-Type":"text/html",
		//"Access-Control-Allow-Origin":"*",
		//"Content-Length":"7"
		return null;
	}

//	@Override
/*	public PostCommands_Result postCommands(PostCommands_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public Register_Result register(Register_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

/*	@Override
	public Save_Result saveGame(Save_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public AcceptTrade_Result acceptTrade(AcceptTrade_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildCity_Result buildCity(BuildCity_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildRoad_Result buildRoad(BuildRoad_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildSettlement_Result buildSettlement(BuildSettlement_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuyDevCard_Result buyDevCard(BuyDevCard_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscardCards_Result discardCards(DiscardCards_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FinishTurn_Result finishTurn(FinishTurn_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MaritimeTrade_Result maritimeTrade(MaritimeTrade_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OfferTrade_Result offerTrade(OfferTrade_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RobPlayer_Result robPlayer(RobPlayer_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RollNumber_Result rollNumber(RollNumber_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SendChat_Result sendChat(SendChat_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayMonopoly_Result playMonopoly(PlayMonopoly_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayMonument_Result playMonument(PlayMonument_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayRoadBuilding_Result playRoadBuilding(
			PlayRoadBuilding_Params request) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlaySoldier_Result playSoldier(PlaySoldier_Params request)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayYearOfPlenty_Result playYearOfPlenty(
			PlayYearOfPlenty_Params result) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object doPost(String urlString, Object request)
			throws ClientException {

		try {
			Gson gson = new Gson();

			URL url = new URL(URL_PREFIX + urlString);
			HttpClient httpClient = HttpClientBuilder.create().build();

			HttpPost post = new HttpPost(url.toString());
			StringEntity params = new StringEntity(gson.toJson(request));
			//post.addHeader("clientcookie", clientCookie);
			post.setEntity(params);
			HttpResponse response = httpClient.execute(post);

			HttpEntity e = response.getEntity();					
			ObjectInputStream in = new ObjectInputStream(e.getContent()); //Java			
			JsonObject jsonResult = (JsonObject)in.readObject();			
			Object result = gson.fromJson(jsonResult, request.getClass());

			return result;			

		}	 
		catch (Exception ex) {
			// handle exception here
		} finally {
			//finally stuff
		}
		return null;

	}


}
