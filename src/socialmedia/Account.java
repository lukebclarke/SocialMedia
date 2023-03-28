package socialmedia;

import java.util.ArrayList;

public class Account {
    private int accountID;
    private String handle;
    private String description;
    private ArrayList<Post> Posts = new ArrayList<Post>(0); //used to track which posts the author has created
    private ArrayList<Comment> Comments = new ArrayList<Comment>(0); 
    private ArrayList<EndorsedPost> EndorsedPosts = new ArrayList<EndorsedPost>();
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

    public void giveEndorsement()
    {
        this.numEndorsements = this.numEndorsements + 1; //increments number of endorsements
    }

    public int getNumEndorsements()
    {
        return this.numEndorsements;
    }

    public ArrayList<Post> getPosts()
    {
        return this.Posts;
    }

    public void addPost(Post postObject) {
        Posts.add(postObject);
    }

    public void removePost(Post postObject) {
        Posts.remove(postObject);
    }

    public ArrayList<Comment> getComments()
    {
        return this.Comments;
    }

    public void addComment(Comment commentObject) {
        Comments.add(commentObject);
    }

    public void removeComment(Comment commentObject) {
        Comments.remove(commentObject);
    }

    public ArrayList<EndorsedPost> getEndorsedPosts()
    {
        return this.EndorsedPosts;
    }

    public void addEndorsedPost(EndorsedPost endorsedPostObject) {
        EndorsedPosts.add(endorsedPostObject);
    }

    public void removeEndorsedPost(EndorsedPost endorsedPostObject) {
        EndorsedPosts.remove(endorsedPostObject);
    }

}
