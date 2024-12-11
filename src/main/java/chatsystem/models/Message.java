package chatsystem.models;

import jakarta.persistence.*;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    private String content;

    @ManyToOne
    @JoinColumn(name="chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name="chatuser_id")
    private Chatuser chatuser;

    public Message(){
        
    }
    public Message(String _content, Chat _chat, Chatuser _user){
       
        this.content = _content;
        this.chat = _chat;
        this.chatuser = _user;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long _id){
      this.id = _id;
    }
   

    public String getContent(){
        return this.content;
    }

    public void setContent(String _content){
      this.content = _content;
    }

    public Chat getChat(){
        return chat;
    }

    public Chatuser getUser(){
        return chatuser;
    }

    
}
