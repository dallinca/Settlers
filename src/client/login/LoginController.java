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

		this.messageView = messageView;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
	
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() {
		
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
		if(registername.length() < 3 || registername.length() > 7){
			return false;
		}
		for(int i = 0; i < registername.length(); i++){
			char c = registername.charAt(i);
			if(!Character.isLetter(c) || !Character.isDigit(c)|| c != '-' || c != '_'){
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
		if(registerpassword.length() < 5){
			return false;
		}
		for(int i = 0; i < registerpassword.length(); i++){
			char c = registerpassword.charAt(i);
			if(!Character.isLetter(c) || !Character.isDigit(c)|| c != '-' || c != '_'){
				getMessageView().setMessage("The password must be five or "
						+ "more characters: letters, digits, _ and - ");
				return false;
			}
			if(registerpassword.charAt(i) != repeatregisterpassword.charAt(i)){
				getMessageView().setMessage("The two passwords must match");
				return false;
			}
		}
		return true; 
	}
	
	@Override
	public void register() {
		ILoginView loginview = getLoginView();
		
		String registername = loginview.getRegisterUsername();
		String registerpassword = loginview.getRegisterPassword();
		String repeatregisterpassword = loginview.getRegisterPasswordRepeat();
		
		if(checkName(registername)){
			if(checkPassword(registerpassword, repeatregisterpassword)){
				// If register succeeded
				getLoginView().closeModal();
				loginAction.execute();
			}
			else{
				return;
			}
		}
		else{
			getMessageView().setMessage("The username must be between three and "
					+ "seven characters: letters, digits, _ and - ");
			return;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

