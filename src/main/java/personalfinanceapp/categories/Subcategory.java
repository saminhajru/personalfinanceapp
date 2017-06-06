package personalfinanceapp.categories;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import personalfinanceapp.user.User;

@Entity
@Table(name = "subcategories")
public class Subcategory {

	@Id
	private String name;
	private String category;
	@ManyToOne
	@JoinColumn(name = "username")
	private User user;

	public Subcategory() {

	}

	public Subcategory(String name, String category, User user) {
		this.name = name;
		this.category = category;
		this.user = user;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
