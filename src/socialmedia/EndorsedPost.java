package socialmedia;

public class EndorsedPost extends Post {
    public EndorsedPost(Account authorObject, Post postObject) {
        super(authorObject, postObject.getMessage());
        postObject.addEndorsement(this);
    }

    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'addComment' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void addComment(Comment commentObject) {
        System.out.println("The operation: 'addComment' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'removeComment' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void removeComment(Comment commentObject) {
        System.out.println("The operation: 'removeComment' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'addEndorsement' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void addEndorsement(EndorsedPost endorsedPostObject) {
        System.out.println("The operation: 'addEndorsement' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    /**<p>DO NOT USE</p>
     * Overrides the existing superclass method, outputs a warning message to the console:
     * "The operation: 'removeComment' cannot be performed on an endorsed post.\n
     *  operation FAILED!"
    */
    public void removeEndorsement(EndorsedPost endorsedPostObject) {
        System.out.println(
                "The operation: 'removeEndorsement' cannot be performed on an endorsed post.\noperation FAILED!");
    }

    
    //TODO better implementation of removing the ability to endorse and comment on posts
}
