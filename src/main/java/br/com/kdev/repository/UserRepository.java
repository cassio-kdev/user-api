package br.com.kdev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.kdev.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByCpf(String cpf);
	List<User> queryByNomeLike(String name);
	

}
