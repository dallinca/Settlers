package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import server.handlers.move.devcard.*;
import server.handlers.move.*;
import server.handlers.nonmove.*;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Online server capable of receiving calls from clients, and creating a digital database which can
 * perform operations on a set of data.
 *
 */
public class Server {

	private static int SERVER_PORT_NUMBER = 8081; 
	private static final int MAX_WAITING_CONNECTIONS = 12;

	private static Logger logger;

	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}

	private static void initLog() throws IOException {

		Level logLevel = Level.FINE;

		logger = Logger.getLogger("settlers-of-catan"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);

		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}


	private HttpServer server;

	private Server() {
		return;
	}

	private void run() {

		logger.info("Initializing Model");

		logger.info("Initializing HTTP Server");

		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
					MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor

		//nonmoves-----------------------------------------------------------------------------------
		
		server.createContext("/user/login", (HttpHandler) LoginHandler);
		server.createContext("/user/register", (HttpHandler) RegisterHandler);
		server.createContext("/games/list", (HttpHandler) ListHandler);
		server.createContext("/games/create", (HttpHandler) CreateGameHandler);
		server.createContext("/games/join", (HttpHandler) JoinHandler);		
		server.createContext("/games/model?version=n", (HttpHandler) GetVersionHandler);

		//moves-----------------------------------------------------------------------------------

		server.createContext("/moves/acceptTrade", (HttpHandler) AcceptTradeHandler);	
		server.createContext("/moves/buildCity", (HttpHandler) BuildCityHandler);	
		server.createContext("/moves/buildRoad", (HttpHandler) BuildRoadHandler);	
		server.createContext("/moves/buildSettlement", (HttpHandler) BuildSettlementHandler);	
		server.createContext("/moves/buyDevCard", (HttpHandler) BuyDevCardHandler);	
		server.createContext("/moves/discardCards", (HttpHandler) DiscardCardsHandler);	
		server.createContext("/moves/finishTurn", (HttpHandler) FinishTurnHandler);	
		server.createContext("/moves/maritimeTrade", (HttpHandler) MaritimeTradeHandler);	
		server.createContext("/moves/offerTrade", (HttpHandler) OfferTradeHandler);	
		server.createContext("/moves/robPlayer", (HttpHandler) RobPlayerHandler);	
		server.createContext("/moves/rollNumber", (HttpHandler) RollNumberHandler);	
		server.createContext("/moves/sendChat", (HttpHandler) SendChatHandler);	
		
		//devcards-----------------------------------------------------------------------------------
		
		server.createContext("/moves/Monopoly", (HttpHandler) PlayMonopolyHandler);
		server.createContext("/moves/Monument", (HttpHandler) PlayMonumentHandler);
		server.createContext("/moves/Road_Building", (HttpHandler) PlayRoadBuildingHandler);
		server.createContext("/moves/Soldier", (HttpHandler) PlaySoldierHandler);
		server.createContext("/moves/Year_of_Plenty", (HttpHandler) PlayYearOfPlentyHandler);
		
		logger.info("Starting HTTP Server");

		server.start();
	}

	//nonmoves-----------------------------------------------------------------------------------
	private CreateGame_Handler CreateGameHandler = new CreateGame_Handler();
	private GetVersion_Handler GetVersionHandler = new GetVersion_Handler();
	private Join_Handler JoinHandler = new Join_Handler();
	private List_Handler ListHandler = new List_Handler();
	private Login_Handler LoginHandler = new Login_Handler();
	private Register_Handler RegisterHandler = new Register_Handler();

	//moves-----------------------------------------------------------------------------------
	private AcceptTrade_Handler AcceptTradeHandler = new AcceptTrade_Handler();
	private BuildCity_Handler BuildCityHandler = new BuildCity_Handler();
	private BuildRoad_Handler BuildRoadHandler = new BuildRoad_Handler();
	private BuildSettlement_Handler BuildSettlementHandler = new BuildSettlement_Handler();
	private BuyDevCard_Handler BuyDevCardHandler = new BuyDevCard_Handler();
	private DiscardCards_Handler DiscardCardsHandler = new DiscardCards_Handler();
	private FinishTurn_Handler FinishTurnHandler = new FinishTurn_Handler();
	private MaritimeTrade_Handler MaritimeTradeHandler = new MaritimeTrade_Handler();
	private OfferTrade_Handler OfferTradeHandler = new OfferTrade_Handler();
	private RobPlayer_Handler RobPlayerHandler = new RobPlayer_Handler();
	private RollNumber_Handler RollNumberHandler = new RollNumber_Handler();
	private SendChat_Handler SendChatHandler = new SendChat_Handler();

	//devcards-----------------------------------------------------------------------------------
	private PlayMonopoly_Handler PlayMonopolyHandler = new PlayMonopoly_Handler();
	private PlayMonument_Handler PlayMonumentHandler = new PlayMonument_Handler();
	private PlayRoadBuilding_Handler PlayRoadBuildingHandler = new PlayRoadBuilding_Handler();
	private PlaySoldier_Handler PlaySoldierHandler = new PlaySoldier_Handler();
	private PlayYearOfPlenty_Handler PlayYearOfPlentyHandler = new PlayYearOfPlenty_Handler();

	public static void main(String[] args) {
		if (args.length==0){
			//Do nothing, no parameters.
		}
		else if (args[0].equals("")){
			//Go with default 39640 for the port number.
		}
		else if (args.length==1){
			SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
			//Set the port number to a given value.
		}	
		new Server().run();
	}

}
