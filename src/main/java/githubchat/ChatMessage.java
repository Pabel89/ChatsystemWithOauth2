package githubchat;

public class ChatMessage {
    private String content;
    private String type; // "received" oder "sent"

    // Getter und Setter
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}