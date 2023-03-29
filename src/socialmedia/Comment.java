package socialmedia;

public class Comment extends Post {
    private Post postCommentingToObject;

    public Comment(Account authorObject, Post postCommentingToObject, String messageToComment) throws InvalidPostException{
        super(authorObject, messageToComment);
        this.postCommentingToObject = postCommentingToObject;
        postCommentingToObject.addComment(this);
    }

    public Post getParentPost()
    {
        return postCommentingToObject;
    }
}
