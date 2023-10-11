package br.com.springboot.todolist.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;


@Repository
public interface IUserRepository extends JpaRepository<UserModel, UUID>{
    List<UserModel> findByUsername(String username);

}
