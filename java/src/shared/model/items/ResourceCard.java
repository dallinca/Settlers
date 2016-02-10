package shared.model.items;

import shared.definitions.ResourceType;

public class ResourceCard {
	
	private ResourceType resourceType;
	
	public ResourceCard(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

}
