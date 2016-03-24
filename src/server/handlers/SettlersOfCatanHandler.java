package server.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import server.facade.ServerFacade;
import shared.communication.User;
import shared.communication.results.nonmove.GetVersion_Result;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class SettlersOfCatanHandler implements HttpHandler {
	protected Gson gson;
	protected ServerFacade facade = ServerFacade.getInstance();

	public SettlersOfCatanHandler(){
		gson = new Gson();
	}

	public void handle(HttpExchange arg0)  throws IOException {
		//Overrride
	}

	public String getExchangeBody(HttpExchange exchange){

		InputStream in = exchange.getRequestBody();

		int len = 0;
		byte[] buffer = new byte[1024];		
		StringBuilder sb = new StringBuilder();		

		try {

			while (-1 != (len = in.read(buffer))){
				sb.append(new String(buffer, 0, len));
			}

			in.close();

		} catch (IOException e) {
			System.out.println("Error in getExchangeBody() of catan handler.");
			e.printStackTrace();
		}		

		return sb.toString();	
	}

	/**
	 * 
	 * First item is the user cookie, second is the game cookie. There will be no second item
	 * if there is no game cookie.
	 * @param exchange
	 * @return
	 */

	public LinkedList<String> extractCookies(HttpExchange exchange){
		//System.out.println("Extracting cookies");
		//Cookie cacher----------------------------------

		String gameCookie, userCookie;
		List<String> rawCookies = new LinkedList<String>();
		LinkedList<String> cookies = new LinkedList<String>();

		Map<String, List<String>> headers = exchange.getRequestHeaders();

		if (headers.size()==0){
			//System.out.println("No headers!");
			return null;
		}

		rawCookies = headers.get("Cookie");

		//Get user cookie first.
		//System.out.println("Retrieving user cookie");

		userCookie = rawCookies.get(0);
		//System.out.println(userCookie);

		userCookie = userCookie.substring(11, userCookie.length());//Cut off path from end of string		
		//System.out.println(userCookie);

		userCookie = URLDecoder.decode(userCookie);
		//System.out.println(userCookie);



		//Get game cookie, if there is one.

		if (userCookie.contains("=")){

			//System.out.println("Retrieving game cookie");

			gameCookie = userCookie.substring(userCookie.indexOf(';') + 2);			
			gameCookie = gameCookie.substring(gameCookie.lastIndexOf('=')+1);			



			userCookie = userCookie.substring(0, userCookie.indexOf(';'));
			cookies.add(userCookie);
			cookies.add(gameCookie);
			//System.out.println("Final user cookie: "+userCookie);
			//System.out.println("Final game cookie: " + gameCookie);
		}	else{
			cookies.add(userCookie);
			//System.out.println("Final single user cookie: "+userCookie);
		}
		
		//System.out.println("Returning cookies");





		return cookies;
	}

	public String validateCookies(LinkedList<String> cookies){

		if (cookies.size()<2){
			return "MISSING COOKIES";
		}

		User user = gson.fromJson(cookies.getFirst(), User.class);			
		if(!facade.validateUser(user)){
			return "INVALID USER";
		}
		int gameID = Integer.parseInt(cookies.get(1));	
		if (!facade.validateGame(user, gameID)){
			return "INVALID GAME";
		}

		return "VALID";

	}

	public void writeResult(HttpExchange exchange, Object result){

		try {

			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

			String job = gson.toJson(result);	//serialize result to json

			OutputStreamWriter sw = new OutputStreamWriter(exchange.getResponseBody());
			sw.write(job);//Write result to stream.
			sw.flush();

		} catch (IOException e) {
			System.out.println("Error writing result.");
			e.printStackTrace();
		} //Everything's okay

	}



}
