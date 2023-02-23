package socialmedia;

public class Comment extends Post {
    public Comment(Account authorObject, Post postCommentingToObject, String messageToComment) {
        super(authorObject, messageToComment);
        postCommentingToObject.addComment(this);
    }
}
