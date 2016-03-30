package server.handlers.nonmove;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import server.handlers.SettlersOfCatanHandler;
import shared.communication.User;
import shared.communication.params.nonmove.List_Params;
import shared.communication.params.nonmove.Login_Params;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.nonmove.List_Result;
import shared.communication.results.nonmove.Register_Result;
import shared.communication.results.nonmove.List_Result.Game;
import shared.communication.results.nonmove.List_Result.Player;
import client.data.GameInfo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Handles calls from a client to list the games
 */
public class List_Handler extends SettlersOfCatanHandler {
	//private Logger logger = Logger.getLogger("settlers-of-catan"); 	
	/**
	 * Receives the List games Params and constructs the appropriate command and passes it to the Server Facade after decoding the object.
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//System.out.println("Handling list");

		//logger.entering("server.handlers.ListHandler", "handle");
		//Handling Login http exchange.

		String job;	
		List_Params request;
		List_Result result;


		LinkedList<String> cookies = extractCookies(exchange);
		
		swaggerize(exchange);

		if (cookies==null){
			System.out.println("Critical failure");
			job = "Failed";
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //User invalid	
		}
		else{


			User user = gson.fromJson(cookies.getFirst(), User.class);		

			if (facade.validateUser(user)){
				//System.out.println("Valid list request.");

				job = getExchangeBody(exchange); //get json string from exchange.
				request = gson.fromJson(job, List_Params.class); //deserialize request from json

				result = facade.list(request);//Call facade to perform operation with request

				//System.out.println("List okay");

				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Everything's okay	
				//System.out.println("Getting jArray");
				JsonArray jArray = new JsonArray();
				//System.out.println("Getting game info list");
				GameInfo[] games = result.getGames();

				//System.out.println("Turning to json");

				for (GameInfo gi : games) {
					String json;	

					json = gson.toJson(gi);		

					JsonParser parser = new JsonParser();
					JsonObject jo = parser.parse(json).getAsJsonObject();
					jArray.add(jo);
				}

				for (JsonElement e: jArray){

					//String title = e.getAsJsonObject().get("title").toString();
					//int id = e.getAsJsonObject().get("id").getAsInt();
					JsonArray playerList = e.getAsJsonObject().get("players").getAsJsonArray();

					for (JsonElement p: playerList){		
						//System.out.println("LOOK HERE:::: "+p.toString());

						if (!p.getAsJsonObject().has("color")){
							//System.out.println("Player does not have color!!!!!!!!!!!!!!!!!!!!!!!!!!");
						}
						else{				
							String pColor = p.getAsJsonObject().get("color").toString();

							pColor = pColor.toLowerCase();
							pColor= pColor.substring(1, pColor.length()-1);
							
							//System.out.println("PCOLORRRRRRRRRRRRRRRRRRRRR: "+pColor);
//I am checking how many unplayed cards I have! Which is:
							p.getAsJsonObject().addProperty("color", pColor);								
						}
					}	
				}	

				job = jArray.toString(); //gson.toJson(result.getListedGames());	//serialize result to json	

				//System.out.println("To json Okay: "+job);

			}else{
				//System.out.println("Bad list request, invalid user");
				//System.out.println(user.toString());
				job = "Failed";			
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); //User invalid			
			}	
		}

		//System.out.println("Writing output");
		OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
		sw.write(job);//Write result to stream.
		sw.flush();	

		exchange.getResponseBody().close();		
		//logger.exiting("server.handlers.ListHandler", "handle");			
	}

}
