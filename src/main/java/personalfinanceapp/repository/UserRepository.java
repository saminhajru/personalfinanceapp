package personalfinanceapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import personalfinanceapp.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>  {

}
