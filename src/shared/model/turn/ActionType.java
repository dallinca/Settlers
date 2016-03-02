package shared.model.turn;



/**
 * Specifies differing types of actions which can be performed.
 *
 */	


public enum ActionType  {
	
	/*
	 * Specifies the different categories of actions which can be performed.
	 * Actions are grouped based on basic similarities in execution.
	 */
	PURCHASE(), TRADE(), PLAYCARD(),

	PURCHASE_CITY(PURCHASE), PURCHASE_DEVELOPMENT(PURCHASE), 
	PURCHASE_ROAD(PURCHASE), PURCHASE_SETTLEMENT(PURCHASE),
	
	TRADE_BANK(TRADE), TRADE_PLAYER(TRADE),
	
	PLAYCARD_BUILDROADS(PLAYCARD), PLAYCARD_KNIGHT(PLAYCARD), 
	PLAYCARD_MONOPOLY(PLAYCARD), PLAYCARD_YEAROFPLENTY(PLAYCARD), 
	
	PLAYCARD_MONUMENT(PLAYCARD);
	    	
	private ActionType category;

	/**
	 * Assigns action category to given action type.
	 * @param category
	 * @pre None.
	 * @post Action category will be assigned to given action type.
	 */
    private ActionType(ActionType category) {
		System.out.println("ActionType ActionType(ActionType category)");
        this.category = category;
    }
    
    private ActionType(){
		System.out.println("ActionType ActionType()");
    	this.category = null;
    }
	
    /**
	 * Returns the category of action this is.
	 * @pre None.
	 * @post Returns action category of action type.
	 */
    ActionType getCategory(){
		System.out.println("ActionType getCategory()");
    	return this.category;
    };    	
}