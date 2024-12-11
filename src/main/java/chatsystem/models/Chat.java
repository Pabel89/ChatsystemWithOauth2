package chatsystem.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
// import jakarta.persistence.*; // for Spring Boot 3

@Entity
@Table(name = "chat")
public class Chat {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "published")
	private boolean published;

    @Column(name = "isPrivate")
    private boolean isPrivate;

	@Column(name = "chatAccesskey")
    private String chatAccesskey;

	@OneToMany(mappedBy = "chat",fetch = FetchType.EAGER)
    private List<Message> messages;

	@ManyToMany(mappedBy = "chats")
    private List<Chatuser> users;

	public Chat(){
		
	}
	public Chat(boolean published, boolean isPrivate) {
		this.published = published;
        this.isPrivate = isPrivate;
		messages = new ArrayList<Message>();
		users = new ArrayList<Chatuser>();
	}

	public long getId() {
		return id;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	public String getChatAccesskey() {
		return chatAccesskey;
	}

	public void setChatAccesskey(String _chatAccesskey) {
		this.chatAccesskey = _chatAccesskey;
	}

    public boolean getIsPrivate(){
        return isPrivate;
    }

    public void setIsPrivate(boolean _isPrivate){
        this.isPrivate = _isPrivate;
    }

	public List<Message> getMessages(){
		return messages;
	}

	public void addMessage(Message _message){
		messages.add(_message);
	}

	public List<Chatuser> getUsers(){
		return users;
	}

	public void addUser(Chatuser _user){
		users.add(_user);
	}

	@Override
	public String toString() {
		return "Chat [id=" + id + ", published=" + published + "]";
	}

}
