package socialmedia;

import java.util.ArrayList;

public class Account {
    private int accountID;
    private String handle;
    private String description;
    private ArrayList<Post> Posts = new ArrayList<Post>(0); //used to track which posts the author has for easy deletion (not sure if needed - TODO: double check)
    private int numEndorsements = 0;

    public Account(int ID, String Username, String bio)
    {
        this.accountID = ID;
        this.handle = Username;
        this.description = bio;
        this.numEndorsements = 0;
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

    public void removePost(Post postObject) {
        Posts.remove(postObject);
    }

    public void deleteAccount(Platform platform)
    {
        for (Post post : Posts)
        {
            //TODO: (luke) i deleted each post in the BadSocialMedia file, you need to write this same thing but in that file instead.
            //Basically you need to retrieve your array of posts in this class, compare it to my (3?4?) arrays then call the 
            // Delete post method in the badsocialmedia class where any matches are found
            // if easier i can do this but we should meet to do this to make it easier.
            //post.deletePost();
        }

        platform.addDeactivatedAccount(this);
        platform.removeActiveAccount(this);
        //^ this code doesnt make sense but my fault you did this i think i will explain :)
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
