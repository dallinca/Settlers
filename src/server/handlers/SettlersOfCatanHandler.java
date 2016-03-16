package server.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import server.facade.ServerFacade;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public abstract class SettlersOfCatanHandler {
	protected Gson gson;
	protected ServerFacade facade;
	
	public SettlersOfCatanHandler(){
		gson = new Gson();
		facade = new ServerFacade();
	}

	public void handle(HttpExchange arg0)  throws IOException{
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
		//Cookie cacher----------------------------------
		
		String gameCookie, userCookie;
		LinkedList<String> cookies = new LinkedList<String>();
		
		Map<String, List<String>> headers = exchange.getRequestHeaders();
		
		userCookie = headers.get("Cookie").get(0);
		
		cookies.add(userCookie);	
		
		if (headers.size()>1){
			 gameCookie = headers.get("Cookie").get(1);
			 cookies.add(gameCookie);
		}
		
		return cookies;
	}

}
