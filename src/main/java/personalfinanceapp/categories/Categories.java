package personalfinanceapp.categories;

public enum Categories {
	
	HOME("", "#3bbaff"), CAR("", "#2e98d1"), BILL("", "#2677a3");
	
	
	private String description;
	private String color;
	
	private Categories(String description, String color) {
		this.description = description;
		this.color = color;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getColor() {
		return color;
	}

}
