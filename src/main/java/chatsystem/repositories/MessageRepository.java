package chatsystem.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import chatsystem.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

  List<Message> findAll();

   
  @Query(value = "SELECT message.ID, message.content, message.chat_id,message.chatuser_id FROM Message AS message JOIN Chat AS chat ON message.chat_Id = chat.id WHERE chat.id = :id_number", nativeQuery = true)
  Collection<Message> findMessagesFromChat(@Param("id_number") Long id_number);
  

}