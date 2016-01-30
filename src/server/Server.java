package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import server.handlers.*;
import server.facade.ServerFacade;
import client.proxy.ServerProxy;

import com.sun.net.httpserver.*;

/**
 * Online server capable of receiving calls from clients, and creating a digital database which can
 * perform operations on a set of data.
 *
 */
public class Server {

	private static int SERVER_PORT_NUMBER = 39640; 
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

		try {
			ServerProxy.initialize();		
		}
		catch (ServerException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}

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
		
		server.createContext("/DownloadBatch", DownloadBatchHandler);
		server.createContext("/DownloadFile", ClientDownloadFileHandler);
		server.createContext("/GetFields", GetFieldsHandler);
		server.createContext("/GetProjects", GetProjectsHandler);
		server.createContext("/GetSampleImage", GetSampleImageHandler);
		server.createContext("/Search", SearchHandler);
		server.createContext("/SubmitBatch", SubmitBatchHandler);
		server.createContext("/ValidateUser", ValidateUserHandler);		
		
		//Calls not from a client to download a file will be forwarded to the download file handler.
		server.createContext("/images", DownloadFileHandler); 
		server.createContext("/Records", DownloadFileHandler);
		server.createContext("/fieldhelp", DownloadFileHandler);
		server.createContext("/knowndata", DownloadFileHandler);
		 
		logger.info("Starting HTTP Server");

		server.start();
	}
	
	private HttpHandler DownloadBatchHandler = new DownloadBatchHandler();
	private HttpHandler DownloadFileHandler = new DownloadFileHandler();
	private HttpHandler GetFieldsHandler = new GetFieldsHandler();
	private HttpHandler GetProjectsHandler = new GetProjectsHandler();
	private HttpHandler GetSampleImageHandler = new GetSampleImageHandler();
	private HttpHandler SearchHandler = new SearchHandler();
	private HttpHandler SubmitBatchHandler = new SubmitBatchHandler();
	private HttpHandler ValidateUserHandler = new ValidateUserHandler();
	private HttpHandler ClientDownloadFileHandler = new ClientDownloadFileHandler();

	public static void main(String[] args) {
		if (args[0].equals("")){
			//Go with default 39640 for the port number.
		}
		else if (args.length==1){
			SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
			//Set the port number to a given value.
		}	
		new Server().run();
	}

}
