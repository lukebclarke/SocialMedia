package socialmedia;

public class EndorsedPost extends Post {
    public EndorsedPost(Account authorObject, Post postObject) throws InvalidPostException {
        super(authorObject, postObject.getMessage());
        postObject.addEndorsedPost(this);
    }

    @Override
    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'addComment' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void addComment(Comment commentObject) {
        System.out.println("The operation: 'addComment' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    @Override
    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'removeComment' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void removeComment(Comment commentObject) {
        System.out
                .println("The operation: 'removeComment' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    @Override
    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'addEndorsement' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void addEndorsedPost(EndorsedPost endorsedPostObject) {
        System.out
                .println("The operation: 'addEndorsement' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    @Override
    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'removeComment' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void removeEndorsedPost(EndorsedPost endorsedPostObject) {
        System.out.println(
                "The operation: 'removeEndorsedPost' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    
    //TODO better implementation of removing the ability to endorse and comment on posts
}
