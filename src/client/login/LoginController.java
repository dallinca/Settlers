package client.login;

import client.ClientFacade;
import client.base.*;
import client.misc.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController, Observer {

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

	@Override
	public void signIn() {
		System.out.println("LoginController signIn()");
		
		// TODO: log in user
		ILoginView loginview = getLoginView();
		String username = loginview.getLoginUsername();
		String userpassword = loginview.getLoginPassword();
		
		//Am I suppose to pass the username and password to the clientfacade login or is there a client commonunicator. 
		//ClientFacade clientfacade = new ClientFacade();
		
		// If log in succeeded
		getLoginView().closeModal();
		loginAction.execute();
	}
	
	/**
	 * Checks that the input conditions of registering a new name correct
	 * @param String registername
	 * @return boolean, weather name is valid
	 */
	public boolean checkName(String registername){
		System.out.println("LoginController checkName()");
		if(registername.length() < 3 || registername.length() > 7){
			getMessageView().setMessage("Your username must be between 3 and 7 characters long (inclusive)");
			getMessageView().showModal();
			System.out.println("incorrect length");
			return false;
		}
		System.out.println("correct length");
		for(int i = 0; i < registername.length(); i++){
			char c = registername.charAt(i);
			if(Character.isLetter(c) || Character.isDigit(c)|| c == '-' || c == '_'){ }
			else {
				getMessageView().setMessage("The username must be letters, digits, '_' or '-'");
				getMessageView().showModal();
				System.out.println("bad characters");
				return false; 
			}
		}
		return true;
	}
	
	/**
	 * Checks that the input conditions of registering a new password 
	 * @param String registerpassword, String repeatregisterpassword
	 * @return boolean, weather Password is valid
	 */
	public boolean checkPassword(String registerpassword, String repeatregisterpassword){
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
	
	@Override
	public void register() {
		System.out.println("LoginController register()");
		ILoginView loginview = getLoginView();
		
		String registername = loginview.getRegisterUsername();
		String registerpassword = loginview.getRegisterPassword();
		String repeatregisterpassword = loginview.getRegisterPasswordRepeat();
		
		if(checkName(registername)){
			if(checkPassword(registerpassword, repeatregisterpassword)){
				// If register succeeded

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
	 * TODO
	 * 
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("LoginController update()");
		// Auto-generated method stub
		
	}

}

