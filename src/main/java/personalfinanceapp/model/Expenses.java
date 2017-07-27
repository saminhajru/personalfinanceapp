package personalfinanceapp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;

class Views {
	public interface Summary {};
}

@Entity
public class Expenses {
	
	@Id
	private int expensesId;
	@ManyToOne
	@JoinColumn(name = "subcategory")
	private Subcategory subcategory;
	private String category;
	@JsonView(Views.Summary.class)
	private double amountOfExpense;
	private String description;
	
	//TODO use User class instead of username 
	private String username;
	
	@JsonView(Views.Summary.class)
	@Temporal(TemporalType.DATE)
	private Date dateOFMakingtheExpense;
	
	public Expenses() {
		
	}
	
	public Expenses(Subcategory subcategory) {
		this.subcategory = subcategory;
	}
	
	public Expenses(int expensesId, Subcategory subcategory, String category, double amountOfExpense,
			String description, String user, Date dateOFMakingtheExpense) {
		this.expensesId = expensesId;
		this.subcategory = subcategory;
		this.category = category;
		this.amountOfExpense = amountOfExpense;
		this.description = description;
		this.username = user;
		this.dateOFMakingtheExpense = dateOFMakingtheExpense;
	}

	public int getExpensesId() {
		return expensesId;
	}

	public void setExpensesId(int expensesId) {
		this.expensesId = expensesId;
	}

	public Subcategory getSubcategory() {
		return subcategory;
	}
	
	public String getSubcategoryName() {
		return subcategory.getName();
	}

	public void setSubcategory(Subcategory subcategory) {
		this.subcategory = subcategory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amountOfExpense;
	}

	public void setAmount(double amount) {
		this.amountOfExpense = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUser() {
		return username;
	}

	public void setUser(String user) {
		this.username = user;
	}

	public Date getDate() {
		return dateOFMakingtheExpense;
	}

	public void setDate(Date date) {
		this.dateOFMakingtheExpense = date;
	}

	@Override
	public String toString() {
		return "Expenses [expensesId=" + expensesId + ", subcategory=" + subcategory + ", category=" + category
				+ ", amountOfExpense=" + amountOfExpense + ", description=" + description + ", user=" + username
				+ ", dateOFMakingtheExpense=" + dateOFMakingtheExpense + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + expensesId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expenses other = (Expenses) obj;
		if (expensesId != other.expensesId)
			return false;
		return true;
	}
	
	

}