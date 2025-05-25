package model;

public class Voter {
    private int id;
    private String name;
    private String email;
    private boolean voted;

    public Voter(String name, String email) {
        this.name = name;
        this.email = email;
        this.voted = false;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean hasVoted() { return voted; }
    public void setVoted(boolean voted) { this.voted = voted; }
}
