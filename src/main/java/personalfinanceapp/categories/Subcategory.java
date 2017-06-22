package personalfinanceapp.categories;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import personalfinanceapp.user.User;

@Entity
@Table(name = "subcategories")
public class Subcategory {

	@Id
	private String subcategory;
	private String category;
	@ManyToOne
	@JoinColumn(name = "username")
	private User user;
	private String color;

	public Subcategory() {

	}

	public Subcategory(String name, String category, User user) {
		this.subcategory = name;
		this.category = category;
		this.user = user;
	}

	public Subcategory(String name, String category, User user, String color) {
		this.subcategory = name;
		this.category = category;
		this.user = user;
		this.color = color;
	}

	public String getName() {
		return subcategory;
	}

	public void setName(String name) {
		this.subcategory = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Subcategory [subcategory=" + subcategory + ", category=" + category + ", user=" + user + ", color="
				+ color + "]";
	}

}
