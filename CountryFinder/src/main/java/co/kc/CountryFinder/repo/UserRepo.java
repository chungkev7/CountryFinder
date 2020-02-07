package co.kc.CountryFinder.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import co.kc.CountryFinder.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
