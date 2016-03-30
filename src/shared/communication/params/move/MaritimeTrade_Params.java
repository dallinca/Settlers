package shared.communication.params.move;

import shared.communication.results.JsonConverter;
import shared.definitions.ResourceType;

/**
 * 
 * Defines a maritime trade command for the server.
 *
 */

public class MaritimeTrade_Params {
	
	private final String type = "maritimeTrade";
	private int playerIndex;
	private int ratio;
	private String inputResource;
	private String outputResource;
	
	
	/**
	 * 
	 * @param playerIndex
	 * @param ratio
	 * @param inputResource
	 * @param outputResource
	 */
	
	public MaritimeTrade_Params(int playerIndex, int ratio,
			ResourceType inputResource, ResourceType outputResource) {
		super();
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.inputResource = inputResource.toString().toLowerCase();
		this.outputResource = outputResource.toString().toLowerCase();
	}

	public int getPlayerIndex() {
		return playerIndex;
	}
	
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	public String getInputResource() {
		return inputResource;
	}
	public void setInputResource(String inputResource) {
		this.inputResource = inputResource;
	}
	public String getOutputResource() {
		
		
		
		
		return outputResource;
	}
	public void setOutputResource(String outputResource) {
		this.outputResource = outputResource;
	}
	

}
