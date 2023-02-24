package socialmedia;

public class Comment extends Post {
    public Comment(Account authorObject, Post postCommentingToObject, String messageToComment) throws InvalidPostException{
        super(authorObject, messageToComment);
        postCommentingToObject.addComment(this);
    }
}
