package server.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
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
	protected ServerFacade facade;

	public SettlersOfCatanHandler(){
		gson = new Gson();
		facade = new ServerFacade();
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
		//Cookie cacher----------------------------------

		String gameCookie, userCookie;
		LinkedList<String> cookies = new LinkedList<String>();

		Map<String, List<String>> headers = exchange.getRequestHeaders();

		if (headers.size()==0){
			return null;
		}

		//Get user cookie first.
		userCookie = headers.get("Cookie").get(0);		
		userCookie = userCookie.substring(11, userCookie.length());//Cut off path from end of string		
		cookies.add(userCookie);	

		//Get game cookie, if there is one.
		if (headers.size()>1){

			gameCookie = headers.get("Cookie").get(1);			 
			gameCookie = gameCookie.substring(11, gameCookie.length());			 
			cookies.add(gameCookie);
		}		

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
