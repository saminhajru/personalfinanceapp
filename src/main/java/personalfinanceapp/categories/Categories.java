package personalfinanceapp.categories;

public enum Categories {
	
	HOME(""), CAR(""), BILL("");
	
	
	private String description;
	
	private Categories(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
