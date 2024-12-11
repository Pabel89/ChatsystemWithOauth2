package chatsystem.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import chatsystem.models.Chat;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {

  List<Chat> findAll();

  @Query(value = "SELECT c.ID, c.IS_Private, c.PUBLISHED, c.CHAT_ACCESSKEY FROM CHAT AS c JOIN USER_IN_CHAT AS uic ON c.ID = uic.CHAT_ID JOIN CHATUSER AS cu ON uic.CHATUSER_ID = cu.ID WHERE cu.USERNAME = :username", nativeQuery = true)
  Collection<Chat> findChatsFromUser(@Param("username") String username);

  @Query(value = "Select * from Chat where id = :_id",nativeQuery = true)
  Chat findChatById(@Param("_id") long _id);

  @Query(value = "Select * from Chat where chat_accesskey = :_chat_accesskey",nativeQuery = true)
  Chat findChatById(@Param("_chat_accesskey") String _chat_accesskey);
  
}