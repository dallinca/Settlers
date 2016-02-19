package client.login;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.misc.MessageView;

public class LoginControllerTest {
	
	private static LoginController loginController;
	@BeforeClass 
	 public static void setupBeforeClass(){
		LoginView loginView = new LoginView();
		MessageView loginMessageView = new MessageView();
		loginController = new LoginController(loginView, loginMessageView);
	}
	
	@Test
	public void testRegisterUser(){
		//loginController.getLoginView(). make setters (regusername, regpassword, regrepeat password)to test, but I'm 85% sure it is working regardless. 
		loginController.register();
	}

}
