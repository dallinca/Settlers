package server.database;

import shared.communication.User;
import shared.communication.params.nonmove.Register_Params;
import shared.communication.results.nonmove.Register_Result;

abstract public interface UserDAOInterface {

	public boolean validateUser(User user);
	public Register_Result register(Register_Params params);
	public boolean create(User f);
	public User update(User f);
	public boolean delete(User f);
	public User read(/*ValidateUserGetFieldsRequest NamePass*/);
	
}
