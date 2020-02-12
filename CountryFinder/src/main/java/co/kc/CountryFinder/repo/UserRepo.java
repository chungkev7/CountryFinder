package co.kc.CountryFinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.kc.CountryFinder.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	// Selects the highest Id number
	// When a new user is created, displays the max Id number + 1 to user
	@Query("SELECT max(u.userId) FROM User u")
	public Integer findMaxId();

}
