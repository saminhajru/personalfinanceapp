package personalfinanceapp.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import personalfinanceapp.validationGroups.FormValidationGroup;
import personalfinanceapp.validationGroups.PersistenceValidationGroup;

@Entity
public class User {

	@Id
	@Size(min = 6, max = 18, groups={FormValidationGroup.class, PersistenceValidationGroup.class})
	private String username;
	
	@Email
	private String email;
	
	@Size(min = 6, max = 18, groups={FormValidationGroup.class})
	private String password;
	
	private boolean enabled;
	
	private String authority;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", password=" + password + ", enabled=" + enabled
				+ ", authority=" + authority + "]";
	}

}