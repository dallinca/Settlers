package client.communication;

import java.util.*;

import client.base.*;
import shared.definitions.*;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, Observer {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		System.out.println("GameHistoryController GameHistoryController()");
		
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		System.out.println("GameHistoryController getView()");
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		
		getView().setEntries(entries);
	
		System.out.println("GameHistoryController initFromModel()");
		//</temp>
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("GameHistoryController initFromModel()");
	}
	
}

