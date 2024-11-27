package githubchat.repositories;

import java.util.List;
import githubchat.models.Chatuser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatuserRepository extends CrudRepository<Chatuser, Long> {

  List<Chatuser> findByUsername(String username);

  Iterable<Chatuser> findAll();

  Chatuser findById(long id);


}