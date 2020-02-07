package co.kc.CountryFinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.kc.CountryFinder.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	@Query("SELECT max(u.userId) FROM User u")
	public Integer findMaxId();

}
