package client.data;

import shared.communication.results.ClientModel.ResourceList;

public class TradeInfo {
	private int sender;
	private int receiver;
	ResourceList offer;
		
	/**
	 * 
	 * @param sender
	 * @param receiver
	 * @param offer
	 */
	public TradeInfo(int sender, int receiver, ResourceList offer) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.offer = offer;
	}
	
	public int getSender() {
		return sender;
	}
	public int getReceiver() {
		return receiver;
	}
	public ResourceList getOffer() {
		return offer;
	}
	@Override
	public String toString() {
		return "TradeOffer [sender=" + sender + ", receiver="
				+ receiver + ", offer=" + offer + "]";
	}
	
}
