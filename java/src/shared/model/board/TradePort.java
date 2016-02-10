package shared.model.board;

import shared.definitions.PortType;

/**
 * The TradePort class stores the kind of trade port available to improve
 * trade rates for a player.
 * 
 * Operations include getting and setting the port type
 *
 */
public class TradePort {

	private PortType portType;
	
	public TradePort(PortType portType) {
		this.portType = portType;
	}

	public PortType getPortType() {
		return portType;
	}

	public void setPortType(PortType portType) {
		this.portType = portType;
	}
	
	
}
