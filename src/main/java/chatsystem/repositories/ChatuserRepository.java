package chatsystem.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import chatsystem.models.Chatuser;

@Repository
public interface ChatuserRepository extends CrudRepository<Chatuser, Long> {

  List<Chatuser> findByUsername(String username);

  Iterable<Chatuser> findAll();

  Chatuser findById(long id);


}