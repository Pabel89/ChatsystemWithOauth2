package chatsystem.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "chatuser")
public class Chatuser {
    
    @Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "fullname")
    private String fullname;

    @OneToMany(mappedBy = "chatuser")
    private List<Message> messages;

    //joinColumns bezieht sich hier auf CHATUSER_ID weil es in User vorkommt und eine "Viele Haben" Beziehung vorgibt
    //inverseJoinColumns bezieht sich auf die CHAT_ID
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_in_chat", 
      joinColumns = @JoinColumn(name = "chatuser_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "chat_id", 
      referencedColumnName = "id"))
    private List<Chat> chats;

    public Chatuser(){
        
    }
    public Chatuser(String _username, String _fullname) {
		this.username = _username;
		this.fullname = _fullname;
        this.messages = new ArrayList<Message>();
        this.chats = new ArrayList<Chat>();
		
	}

    public long getId() {
		return id;
	}

    public String getUsername(){
        return username;
    }

    public void setUsername(String _username){
        this.username = _username;
    }

    public String getFullname(){
        return fullname;
    }

    public void setFullname(String _fullname){
        this.fullname = _fullname;
    }

    public List<Message> getMessages(){

        return messages;
    }

    public void addMessage(Message _message){
        messages.add(_message);
    }

    public List<Chat> getChat(){
        return chats;
    }

    public void addChat(Chat _chat){
        chats.add(_chat);
    }

}
