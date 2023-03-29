package socialmedia;

public class EndorsedPost extends Post {
    private Post postObject;

    public EndorsedPost(Account authorObject, Post postObject) {
        //Call Post constructor
        super(authorObject, postObject);
        this.postObject = postObject;

        //Change the post message to include handle of user to endorse
        postObject.addEndorsedPost(this);
    }

    public Post getParentPost()
    {
        return postObject;
    }

    @Override
    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'addComment' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void addComment(Comment commentObject) {
        //System.out.println("The operation: 'addComment' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    @Override
    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'removeComment' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void removeComment(Comment commentObject) {
        //System.out.println("The operation: 'removeComment' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    @Override
    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'addEndorsement' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void addEndorsedPost(EndorsedPost endorsedPostObject) {
        //System.out.println("The operation: 'addEndorsement' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    @Override
    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'removeComment' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void removeEndorsedPost(EndorsedPost endorsedPostObject) {
        //System.out.println("The operation: 'removeEndorsedPost' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    //TODO better implementation of removing the ability to endorse and comment on posts
}
