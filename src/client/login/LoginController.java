package client.login;

import client.Client;
import client.ClientException;
import client.ClientFacade;
import client.MockClientFacade;
import client.base.*;
import client.misc.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import shared.communication.params.nonmove.Login_Params;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.nonmove.Login_Result;
import shared.communication.results.nonmove.Register_Result;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController, Observer {

	private Client clientInfo;
	private ClientFacade clientFacade;
	private IMessageView messageView;
	private IAction loginAction;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		System.out.println("LoginController LoginController()");
		this.messageView = messageView;
		this.clientInfo = Client.getInstance();
		this.clientFacade = ClientFacade.getInstance();
	}
	
	public ILoginView getLoginView() {
		System.out.println("LoginController getLoginView()");
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		System.out.println("LoginController getMessageView()");
	
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		System.out.println("LoginController setLoginAction()");
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		System.out.println("LoginController getLoginAction()");
		
		return loginAction;
	}

	@Override
	public void start() {
		System.out.println("LoginController start()");
		
		getLoginView().showModal();
	}

	/**
	 * Sign in a user that is already registered
	 * 
	 * @pre None
	 * @post loginAction executed if valid, else error message is shown
	 */
	@Override
	public void signIn() {
		System.out.println("LoginController signIn()");
		
		ILoginView loginview = getLoginView();
		String username = loginview.getLoginUsername();
		String userpassword = loginview.getLoginPassword();

		if(checkName(username)){
			if(checkLoginPassword(userpassword)){
				// call the client facade with the username and password to attempt registry 
				Login_Result login_result = null;
				login_result = clientFacade.login(username, userpassword);
				
				// If a result is passed back check to see if the registry was successful
				if( login_result == null || login_result.isWasLoggedIn() == false) {
					getMessageView().setMessage("Login failed, possibly no connection to the internet");
					getMessageView().showModal();
					return;
				}
				
				// If register succeeded
				clientInfo.setName(login_result.getName());
				clientInfo.setUserId(login_result.getID());
				System.out.println("about to login");
				getLoginView().closeModal();
				loginAction.execute();
				return;
			}
			else{
				return;
			}
		}
		else{
			getMessageView().setMessage("The username must be between three and "
					+ "seven characters: letters, digits, _ and - ");
			getMessageView().showModal();
			return;
		}
	}
	
	/**
	 * Checks that the input conditions of registering a new name correct
	 * 
	 * @pre None
	 * @post checks name, if invalid returns false, else if valid return true
	 * 
	 * @param String - the name the user input
	 * @return boolean - weather name is valid
	 */
	private boolean checkName(String registername){
		System.out.println("LoginController checkName()");
		if(registername.length() < 3 || registername.length() > 7){
			System.out.println("LoginController checkName() returning false 1");
			return false;
		}
		for(int i = 0; i < registername.length(); i++){
			char c = registername.charAt(i);
			if(Character.isLetter(c) || Character.isDigit(c)|| c == '-' || c == '_'){ }
			else {
				System.out.println("LoginController checkName() returning false 2");
				return false; 
			}
		}
		System.out.println("LoginController checkName() returning true");
		return true;
	}
	
	/**
	 * Checks that the input conditions of a loginpassword
	 * 
	 * @pre if username is valid
	 * @post false if invalid, true if valid
	 * 
	 * @param String - the password the user input
	 * @return boolean - weather password is valid
	 */
	private boolean checkLoginPassword(String loginpassword){
		
		for(int i = 0; i < loginpassword.length(); i++){
			char c = loginpassword.charAt(i);
			if(Character.isLetter(c) || Character.isDigit(c)|| c == '-' || c == '_'){ }
			else{
				getMessageView().setMessage("The password must be five or "
						+ "more characters: letters, digits, _ and - ");
				getMessageView().showModal();
				return false;
			}
		}
		return true; 
		
	}
	
	/**
	 * Checks that the input conditions of registering a new password 
	 * 
	 * @pre if registername is valid
	 * @post false if invalid, true if valid
	 * 
	 * @param String registerpassword - the password a user wants to register
	 * @param String repeatregisterpassword - the repeated password a user wants to register
	 * @return boolean - weather Password is valid
	 */
	private boolean checkPassword(String registerpassword, String repeatregisterpassword){
		System.out.println("LoginController checkPassword()");
		if(registerpassword.length() < 5){
			getMessageView().setMessage("The password must be five or "
					+ "more characters: letters, digits, _ and - ");
			getMessageView().showModal();
			return false;
		}
		// This is checking to make sure they are of the same length (without this we fail with "dallin" and "dallina" as the passwords)
		if(registerpassword.length() != repeatregisterpassword.length()) {
			getMessageView().setMessage("The passwords must be the same length");
			getMessageView().showModal();
			return false;
		}
		for(int i = 0; i < registerpassword.length(); i++){
			char c = registerpassword.charAt(i);
			if(Character.isLetter(c) || Character.isDigit(c)|| c == '-' || c == '_'){ }
			else{
				getMessageView().setMessage("The password must be five or "
						+ "more characters: letters, digits, _ and - ");
				getMessageView().showModal();
				return false;
			}
			if(registerpassword.charAt(i) != repeatregisterpassword.charAt(i)){
				getMessageView().setMessage("The two passwords must match");
				getMessageView().showModal();
				return false;
			}
		}
		return true; 
	}

	/**
	 * Registers a new user
	 * 
	 * @pre none
	 * @post loginAction executed if valid, else error message is shown
	 * 
	 * @param none
	 * @return none
	 */
	@Override
	public void register() {
		System.out.println("LoginController register()");
		ILoginView loginview = getLoginView();
		
		String registername = loginview.getRegisterUsername();
		String registerpassword = loginview.getRegisterPassword();
		String repeatregisterpassword = loginview.getRegisterPasswordRepeat();
		
		if(checkName(registername)){
			if(checkPassword(registerpassword, repeatregisterpassword)){
				// call the client facade with the username and password to attempt registry 
				Register_Result register_result = null;
				register_result = clientFacade.register(registername, registerpassword);
				
				// If a result is passed back check to see if the registry was successful
				if( register_result == null || register_result.isValid() == false) {
					getMessageView().setMessage("Registration failed, possibly no connection to the internet");
					getMessageView().showModal();
					return;
				}
				
				// If register succeeded
				clientInfo.setName(register_result.getName());
				clientInfo.setUserId(register_result.getId());
				System.out.println("about to register");
				getLoginView().closeModal();
				loginAction.execute();
				return;
			}
			else{
				return;
			}
		}
		else{
			getMessageView().setMessage("The username must be between three and "
					+ "seven characters: letters, digits, _ and - ");
			getMessageView().showModal();
			return;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("LoginController update()");
		
	}

}

