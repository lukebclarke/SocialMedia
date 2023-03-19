package socialmedia;

import java.util.ArrayList;

public class Account {
    private int accountID;
    private String handle;
    private String description;
    private ArrayList<Post> Posts; //used to track which posts the author has for easy deletion (not sure if needed - TODO: double check)
    private int numEndorsements = 0;

    public Account(int ID, String Username, String bio)
    {
        this.accountID = ID;
        this.handle = Username;
        this.description = bio;
        this.nummEndorsements = 0;
    }

    public int getAccountID() {
        return this.accountID;
    }

    public String getHandle() {
        return this.handle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setAccountID(int newID) {
        this.accountID = newID;
    }

    public void setHandle(String newHandle) {
        this.handle = newHandle;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void addPost(Post postObject) {
        Posts.add(postObject);
    }

    public void deleteAccount(Platform platform)
    {
        for (Post post : Posts)
        {
            post.deletePost();
        }

        platform.addDeactivatedAccount(this);
        platform.removeActiveAccount(this);
    }

    public void giveEndorsement()
    {
        this.numEndorsements = this.numEndorsements + 1;
    }

    public int getEndorsements()
    {
        return this.numEndorsements;
    }

    //TODO: write function to delete account/all posts

}
