package githubchat.repositories;

import java.util.Collection;
import java.util.List;
import githubchat.models.Chat;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {

  List<Chat> findAll();

  @Query(value = "Select * from Chat ch where EXISTS(SELECT * FROM CHAT AS c JOIN USER_IN_CHAT AS uic ON c.ID = uic.CHAT_ID JOIN CHATUSER AS cu ON uic.CHATUSER_ID = cu.ID WHERE cu.USERNAME = :username)", nativeQuery = true)
  Collection<Chat> findChatsFromUser(@Param("username") String username);

}