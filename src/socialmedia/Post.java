package socialmedia;

import java.util.ArrayList;

public class Post {
    // Instantiate attributes
    private Account author;
    private String message;
    private static int id;
    private ArrayList<Comment> arrOfComments;
    private ArrayList<EndorsedPost> arrOfEndorsements;


    /**<p>Creates a new instance of the Post class.</p>
     * 
     * Parameters:
     * handle (String): The handle of the user posting the post
     * message (String): The message to post
     * 
     * 
    */
    public Post(Account author, String message) throws InvalidPostException{
        this.author = author;
        setMessage(message);
        
        //Increment and set the post id. (this means the id will start from 1)
        id++;
    }

    public Account getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Comment> getArrayOfComments() {
        return arrOfComments;
    }

    public void setHandle(Account newAuthor) {
        author = newAuthor;
    }

    /**Sets the message of the post to a new message. The message can be up to 100 characters long.
     * 
     * @param newMessage (String) the new post message.
     * 
     * @outputs (String) if the message is greater than 100 characters long, a warning message is output to the console and the message is not set.
     */
    public void setMessage(String newMessage) throws InvalidPostException{
        //TODO 'up to 100 characters' means less than 100? or <= 100 ?
        if (message.length() < 100) {
            message = newMessage;
        }
        else if (message.length() >= 100) {
            throw new InvalidPostException("Post message contains: " + message.length() + " characters. Message can only contain up to 100 characters.");
        }
        else {
            throw new InvalidPostException("Post message contains: 0 characters. The post message cannot be empty.");
        }
    }

    public void setId(int newId) {
        id = newId;
    }

    /**Adds a comment object to the post
     * 
     * @param commentObject The object of the comment to remove
    */
    public void addComment(Comment commentObject) {
        arrOfComments.add(commentObject);
    }

    /**Removes a comment object from the post
     * 
     * @param commentObject The object of the comment to remove
    */
    public void removeComment(Comment commentObject) {
        arrOfComments.remove(commentObject);
    }

    /**Adds a EndorsedPost object to the post
     * 
     * @param endorsedPostObject The object of the post endorsement
    */
    public void addEndorsement(EndorsedPost endorsedPostObject) {
        arrOfEndorsements.add(endorsedPostObject);
    }

    /**Removes a EndorsedPost object from the post
     * 
     * @param endorsedPostObject The object of the post endorsement
    */
    public void removeEndorsement(EndorsedPost endorsedPostObject) {
        arrOfEndorsements.remove(endorsedPostObject);
    }


}