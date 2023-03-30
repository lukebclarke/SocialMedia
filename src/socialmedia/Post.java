package socialmedia;

import java.util.ArrayList;
import java.io.Serializable;

public class Post implements Serializable {
    private Account author;
    private String message;
    private static int nextPostID;
    private int postID;
    private ArrayList<Comment> arrOfComments = new ArrayList<Comment>(0); 
    private ArrayList<EndorsedPost> arrOfEndorsements  = new ArrayList<EndorsedPost>(0);

    private boolean isEmptyPost;


    /**<p>Creates a new instance of the Post class.</p>
     * 
     * Parameters:
     * handle (String): The handle of the user posting the post
     * message (String): The message to post
    */
    public Post(Account author, String message) throws InvalidPostException {
        this.author = author;
        setMessage(message);
        author.addPost(this); //Add the post to the list of posts created by the author
        this.isEmptyPost = false;
        //Increment and set the post id. (this means the id will start from 1)
        //TODO: if the id somehow reaches 2147483648 the program will crash
        this.postID = nextPostID;
        nextPostID++;
    }

    public Post(Account author, Post postObject) {
        this.author = author;
        this.message = postObject.getMessage();
        addEndorseeHandleToMessage(author.getHandle());

        author.addPost(this); //Add the post to the list of posts created by the author
        this.isEmptyPost = false;
        //Increment and set the post id. (this means the id will start from 1)
        //TODO: if the id somehow reaches 2147483648 the program will crash
        this.postID = nextPostID;
        nextPostID++;
    }

    public Account getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return postID;
    }

    public ArrayList<Comment> getArrayOfComments() {
        return arrOfComments;
    }

    public int getNumberOfComments() {
        return arrOfComments.size();
    }

    public int getNumberOfEndorsements() {
        return arrOfEndorsements.size();
    }

    public ArrayList<EndorsedPost> getArrayOfEndorsements() {
        return this.arrOfEndorsements;
    }

    public void setAuthor(Account newAuthor) {
        author = newAuthor;
        //TODO: validate
    }

    /**Sets the message of the post to a new message. The message can be up to 100 characters long.
     * 
     * @param newMessage (String) the new post message.
     * 
     * @outputs (String) if the message is greater than 100 characters long, a warning message is output to the console and the message is not set.
     */
    public void setMessage(String newMessage) throws InvalidPostException {
        if (newMessage.length() <= 100) {
            message = newMessage;
        } else if (newMessage.length() > 100) {
            throw new InvalidPostException("Post message contains: " + newMessage.length()
                    + " characters. Message can only contain up to 100 characters.");
        } else {
            throw new InvalidPostException("Post message contains: 0 characters. The post message cannot be empty.");
        }
    }
    
    public void setEmptyPost() {
        this.author = null;
        this.message = "The original content was removed from the system and is no longer available.";
        this.isEmptyPost = true;
        this.arrOfEndorsements  = new ArrayList<EndorsedPost>(0);
    }

    public void setPostId(int newPostId) {
        postID = newPostId;
    }

    public void addComment(Comment commentObject) {
         //Only set if the post is not an empty post
         if (author != null) {
            arrOfComments.add(commentObject);
        }
    }

    public void removeComment(Comment commentObject) {
        arrOfComments.remove(commentObject);
    }

    public void addEndorsedPost(EndorsedPost endorsedPostObject) {
        //Only set if the post is not an empty post
        if (author != null) {
            arrOfEndorsements.add(endorsedPostObject);
        }
    }

    public void removeEndorsedPost(EndorsedPost endorsedPostObject) {
        arrOfEndorsements.remove(endorsedPostObject);
    }

    public void addEndorseeHandleToMessage(String endorseeHandle) {
        this.message = "EP@" + endorseeHandle + ": " + this.getMessage();
    }
    
    public boolean isEmptyPost() {
        return this.isEmptyPost;
    }
}