package com.edhaorganics.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edhaorganics.backend.beans.EdhaUser;

@Repository
public interface UserRepository extends JpaRepository<EdhaUser, String>{

	//getOne is not serializable (lazy load issue)
	//findone needs iteration so custom query
	EdhaUser findTop1ByUsername(String userName);

	List<EdhaUser> findByActive(boolean isActive);

}
