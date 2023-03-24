package socialmedia;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class BadSocialMedia implements SocialMediaPlatform {
	private ArrayList<Account> arrOfAccounts = new ArrayList<Account>(0);
	private ArrayList<Post> arrOfPosts  = new ArrayList<Post>(0);
	private ArrayList<EndorsedPost> arrOfEndorsedPosts = new ArrayList<EndorsedPost>(0);
	private ArrayList<Comment> arrOfComments = new ArrayList<Comment>(0);
	private ArrayList<Post> arrOfEmptyPosts = new ArrayList<Post>(0);

	private ArrayList<Account> arrOfActiveAccounts = new ArrayList<Account>(0);
	private ArrayList<Account> arrOfDeactivatedAccounts = new ArrayList<Account>(0);
	// TODO: deleting a comment/endorsed post may not work
	// To fix this, i think i can just change the type of the variable on variable
	// asignment in each for loop?

	private Platform platform = new Platform();

	// LUKE TODO: what do the throw exceptions do, do i have to code that
	// Yes, easier to explain it in person but you can also look at my code, you
	// kind of put a message into one of the functions when there is an "error" like
	// if the handle has to be less than 10 characters long but the handle is 20
	// characters long it should throw an exception. (like the except block of try:
	// except: in python)

	// TODO: the platform class shouldnt be used i think which breaks a lot of your
	// code :( this is my fault but i will explain and we
	// can fix it, unless it works for yours then i can probably change my code to
	// use it maybe i was just being dumb when i made that class
	// because i didnt realise this file was a class.

	// TODO: (ollie) currently accounts can endorse their own post.

	// TODO: (ollie) most things require post object to be passed as param but
	// sometimes comment and endorsed post objects are passed instead which will
	// probably break the code
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		int numActiveAccounts = (this.arrOfActiveAccounts != null) ? this.arrOfActiveAccounts.size() : 0;
		int numDeactivatedAccounts = (this.arrOfDeactivatedAccounts != null) ? this.arrOfDeactivatedAccounts.size() : 0;

		int accountID = numActiveAccounts + numDeactivatedAccounts; // generates unique accountID

		Account account = new Account(accountID, handle, "");
		this.arrOfActiveAccounts.add(account);

		return account.getAccountID();
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		int accountID = this.arrOfAccounts.size() + this.arrOfDeactivatedAccounts.size(); // generates unique accountID

		Account account = new Account(accountID, handle, description);
		this.arrOfActiveAccounts.add(account);

		return account.getAccountID();
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		ArrayList<Account> accounts = new ArrayList<>(this.arrOfActiveAccounts);

		for (Account account : accounts) {
			if (account.getAccountID() == id) // iterates through all accounts until the desired account is found
			{
				arrOfActiveAccounts.remove(account);
				arrOfDeactivatedAccounts.add(account);

				//Delete all postss for a given account
				for (Post post : arrOfPosts) {
					post.setEmptyPost(); //TODO: this doesn't seem right? but i think will work ns if works with comments & endorsements.
				}
				//TODO: i think that this will not work on comments and endorsed posts as they wont be able to be put into the postArr in the account class.
			}
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		ArrayList<Account> accounts = this.arrOfActiveAccounts;

		for (Account account : accounts) {
			if (account.getHandle() == handle) // iterates through all accounts until the desired account is found
			{
				arrOfActiveAccounts.remove(account);
				arrOfDeactivatedAccounts.add(account);
				
				//Delete all postss for a given account
				for (Post post : arrOfPosts) {
					post.setEmptyPost(); //TODO: this doesn't seem right? but i think will work ns if works with comments & endorsements.
				}
				//TODO: i think that this will not work on comments and endorsed posts as they wont be able to be put into the postArr in the account class.
			}
		}
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		ArrayList<Account> accounts = this.arrOfActiveAccounts;

		for (Account account : accounts) {
			if (account.getHandle() == oldHandle) // iterates through all accounts until the desired account is found
			{
				account.setHandle(newHandle);
			}
		}
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		ArrayList<Account> accounts = this.arrOfActiveAccounts;

		for (Account account : accounts) {
			if (account.getHandle() == handle) // iterates through all accounts until the desired account is found
			{
				account.setDescription(description);
			}
		}
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO decide what this does
		// (luke) hover over showAccount() function - it shows what this should look
		// like :)
		return null;
	}

	/**
	 * @param message the message to be contained within a post
	 * @return true if the message is of the correct length
	 * @throws InvalidPostException if the message length is greater than or equal
	 *                              to 100 characters
	 */
	private boolean isMessageOfCorrectLength(String message) throws InvalidPostException {
		// TODO 'up to 100 characters' means less than 100? or <= 100 ?
		// TODO: does the 100 characters include the handle? I assumed not.
		// TODO: never returns false.
		if (message.length() >= 100) {
			throw new InvalidPostException("Post message contains: " + message.length()
					+ " characters. Message can only contain up to 100 characters.");
		} else if (message.length() == 0) {
			throw new InvalidPostException("Post message contains: 0 characters. The post message cannot be empty.");
		}
		return true;
	}

	/**
	 * This method is used to return the object of an account with a given handle.
	 * If no account with that handle is found,
	 * 
	 * @param handle handle to identify an account
	 * @return the object relating to the account handle. Returns null if no account
	 *         handle with the given handle was found.
	 * @throws
	 */
	private Account getAccountObject(String handle) throws HandleNotRecognisedException {
		Account accountObject = null;
		for (Account authorToCheck : arrOfAccounts) {
			String handleToCheck = authorToCheck.getHandle();
			if (handleToCheck == handle) {
				accountObject = authorToCheck;
				break; // Searching is no longer needed.
			}
		}

		// Check if the account handle given was valid by checking if an Account with
		// that handle was found.
		if (accountObject == null) {
			throw new HandleNotRecognisedException("Handle: " + handle + " is not valid.");
		}

		return accountObject;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// Ollie
		boolean isMessageCorrectLength = isMessageOfCorrectLength(message);

		Account author = getAccountObject(handle);

		// create post object and append to arr of posts
		Post postObject = new Post(author, message);
		this.arrOfPosts.add(postObject);

		return postObject.getId();
	}

	/**
	 * Checks if a post Id relates to a standard post.
	 * 
	 * @param postID The id of the post to check
	 * @return The object of the representing post if ID is a standard post.
	 *         Otherwise returns null.
	 */
	private Post searchForPost(int postID) {
		Post postObject = null;
		for (Post post : this.arrOfPosts) {
			int idToCheck = post.getId();

			if (idToCheck == postID) {
				postObject = post;
				break; // Stop searching for the post.
			}
		}

		return postObject;
	}

	/**
	 * Checks if a post Id relates to a comment post.
	 * 
	 * @param commentID The id of the post to check
	 * @return The object of the representing post if ID is a comment post.
	 *         Otherwise returns null.
	 */
	private Comment searchForComment(int commentID) {
		Comment commentObject = null;
		for (Comment comment : this.arrOfComments) {
			int idToCheck = comment.getId();

			if (idToCheck == commentID) {
				commentObject = comment;
				break; // Stop searching for the post.
			}
		}

		return commentObject;
	}

	/**
	 * Checks if a post ID relates to an endorsed post
	 * 
	 * @param endorsedPostID The ID of the post to check
	 * @return The object of the representing post if ID is an endorsed post.
	 *         Otherwise returns null.
	 */
	private EndorsedPost searchForEndorsedPost(int endorsedPostID) {
		EndorsedPost endorsedPostObject = null;
		for (EndorsedPost endorsedPost : this.arrOfEndorsedPosts) {
			int idToCheck = endorsedPost.getId();

			if (idToCheck == endorsedPostID) {
				endorsedPostObject = endorsedPost;
				break; // Stop searching for the post.
			}
		}

		return endorsedPostObject;
	}

	/**
	 * Returns whether the post with a given ID is an empty post. (a post that has
	 * been deleted)
	 * 
	 * @param postID the ID of the post to check if it is empty
	 * @return true if the post is empty, false otherwise.
	 */
	private boolean isPostEmpty(int postID) {
		// TODO: (ollie) make more readable
		Post postObject = searchForPost(postID);

		if (postObject == null) {
			Comment commentObject = searchForComment(postID);

			if (commentObject == null) {
				EndorsedPost endorsedPostObject = searchForEndorsedPost(postID);

				if (endorsedPostObject != null) {
					return endorsedPostObject.isEmptyPost();
				}
			} else {
				return commentObject.isEmptyPost();
			}
		} else {
			return postObject.isEmptyPost();
		}

		return false;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// (Ollie)
		Account authorObject = getAccountObject(handle);

		// Search through all posts until the post with the given ID is found
		Post postObject = searchForPost(id);

		// If a post with the given ID is not found, search for the id in comments
		Comment commentObject = null;
		if (postObject == null) {
			commentObject = searchForComment(id);
		}

		// If a comment with the given ID is not found, search for the id in endorsed
		// posts.
		EndorsedPost endorsedPostObject = null;
		if (authorObject == null && commentObject == null) {
			endorsedPostObject = searchForEndorsedPost(id);

			// Throw exception if an endorsed post with the given id was found.
			if (endorsedPostObject != null) {
				throw new NotActionablePostException(
						"You cannot endorse an endorsed post. ID of post you tried to endorse: " + id);
			}
		}

		// Throw exception if no post with the given id was found
		if (postObject == null && commentObject == null && endorsedPostObject == null) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}

		// If a post with given ID is found, check that the post is not an empty post.
		// Throw exception if the corresponding ID is to an empty post.
		// TODO: (ollie) this isnt done in the most efficient way but should work.
		if (isPostEmpty(id)) {
			throw new NotActionablePostException("You cannot endorse an empty post.");
		}

		// create an endorsed post object and append to arr of posts
		if (authorObject != null) {
			endorsedPostObject = new EndorsedPost(authorObject, postObject);
		}
		if (commentObject != null) {
			endorsedPostObject = new EndorsedPost(authorObject, commentObject);
		}

		this.arrOfEndorsedPosts.add(endorsedPostObject);
		authorObject.addPost(endorsedPostObject); // TODO: (Luke??) check the author doesnt need individual arrays (this
													// will be fixed if you use the arrays as i do) The way it is
													// currently set up in your class cannot work as your class only
													// accepts 1 type, whereas 3 post types are used (and 4 arrays are
													// needed)

		return endorsedPostObject.getId();
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// (Ollie)
		Account authorObject = getAccountObject(handle);

		Post postObject = searchForPost(id);

		// If a post with given ID is not found, search through all comments until the
		// given post id is found.
		Comment commentObject = null;
		if (postObject == null) {
			postObject = searchForComment(id);

		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post width given id is found.
		EndorsedPost endorsedPostObject = null;
		if (postObject == null && commentObject == null) {
			endorsedPostObject = searchForEndorsedPost(id);

			// Throw exception if an endorsed post with the given id was found.
			if (endorsedPostObject != null) {
				throw new NotActionablePostException(
						"You cannot endorse an endorsed post. ID of post you tried to endorse: " + id);
			}
		}

		// Throw exception if no post with the given id was found
		if (postObject == null && commentObject == null && endorsedPostObject == null) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}

		// If a post with given ID is found, check that the post is not an empty post.
		// Throw exception if the post found is an empty post.
		if (isPostEmpty(id)) {
			throw new NotActionablePostException("You cannot endorse an empty post.");
		}

		// Check that the provided message is within the allowed length (0 to 100 chars)
		isMessageOfCorrectLength(message);

		// create post object and append to arr of posts
		commentObject = new Comment(authorObject, postObject, message); // TODO: check this throws invalid post
																		// exception when needed.
		this.arrOfComments.add(commentObject);

		return commentObject.getId();
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// (Ollie)
		Post postObject = searchForPost(id);

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		Comment commentObject = null;
		if (postObject == null) {
			commentObject = searchForComment(id);
		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post with the given id is found.
		EndorsedPost endorsedPostObject = null;
		if (postObject == null && commentObject == null) {
			endorsedPostObject = searchForEndorsedPost(id);
		}

		// Throw exception if no post with the given id was found
		if (postObject == null && commentObject == null && endorsedPostObject == null) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}

		// TODO: is this right or not? should i be searching for this post or no?
		// If the post is an empty post, say it is not found
		if (isPostEmpty(id)) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}

		// Turn the post into an empty post
		postObject.setEmptyPost();

		// TODO: when a post of empty should it be removed from the list of posts or
		// not? I think not? I assumed not.
		// arrOfPosts.remove(postObject)
		arrOfEmptyPosts.add(postObject);
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// (Ollie)
		// Check the id is valid, and get the post object.
		// Search through all posts until the post with the given ID is found
		Post postObject = searchForPost(id);

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		Comment commentObject = null;
		if (postObject == null) {
			commentObject = searchForComment(id);

		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post width given id is found.
		EndorsedPost endorsedPostObject = null;
		if (postObject == null && commentObject == null) {
			endorsedPostObject = searchForEndorsedPost(id);
		}

		// TODO: should i be checking for empty posts? Right now it will display empty
		// posts, but i am not sure that it should.

		// Throw exception if a post with the given id was not found.
		if (postObject == null && commentObject == null && endorsedPostObject == null) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}

		// Get the author of the post
		Account author = postObject.getAuthor();

		String postDetails = "ID: " + id + "\n Account: " + author.getHandle() + "\n No. endorsements: "
				+ postObject.getNumberOfEndorsements() + " | " + postObject.getNumberOfComments() + "\n "
				+ postObject.getMessage();
		return postDetails;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// (Ollie)
		// Search through all posts until the post with the given ID is found
		Post postObject = searchForPost(id);

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		Comment commentObject = null;
		if (postObject == null) {
			commentObject = searchForComment(id);

		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post width given id is found.
		EndorsedPost endorsedPostObject = null;
		if (postObject == null && commentObject == null) {
			endorsedPostObject = searchForEndorsedPost(id);

			// Throw exception if an endorsed post with the given id was found.
			if (endorsedPostObject != null) {
				throw new NotActionablePostException(
						"You cannot endorse an endorsed post. ID of post you tried to endorse: " + id);
			}
		}

		// Output the initial post
		String stringToReturn = "";
		stringToReturn += showIndividualPost(id);

		// For each comment in the post, display the comment along with all of it's
		// comments
		ArrayList<Comment> InitialArrOfComments = postObject.getArrayOfComments();
		for (int i = 0; i < postObject.getNumberOfComments(); i++) {
			commentObject = InitialArrOfComments.get(i);

			stringToReturn += showComments(commentObject, 1);
		}

		// Convert the string to stringbuilder
		StringBuilder sb = new StringBuilder();
		sb.append(stringToReturn);

		return sb;
	}

	private String showComments(Comment commentObject, int commentLevel) {
		String stringToReturn = "";

		ArrayList<Comment> arrOfComments = commentObject.getArrayOfComments();

		// Calculate the spacing to the left of the message
		String spacing = "";
		for (int i = 0; i < commentLevel; i++) {
			spacing += "    ";
		}

		// Add to the string to return the comment of which this function was called by
		Account author = commentObject.getAuthor();

		stringToReturn += "\n" + "| > ID: " + commentObject.getId()
				+ "\n " + spacing + "Account: " + author.getHandle()
				+ "\n " + spacing + "No. endorsements: " + commentObject.getNumberOfEndorsements() + " | "
				+ commentObject.getNumberOfComments()
				+ "\n " + spacing + commentObject.getMessage();

		// Call this function again for any further comments on this comment post.
		for (int i = 0; i < arrOfComments.size(); i++) {
			showComments(commentObject, commentLevel + 1);
		}

		return stringToReturn;
	}

	@Override
	public int getNumberOfAccounts() {
		List<Account> accounts = platform.getActiveAccounts();

		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		// (Ollie)
		Integer numPosts = 0;
		for (Post post : arrOfPosts) {
			if (!post.isEmptyPost()) {
				numPosts++;
			}
		}

		return numPosts;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// (Ollie)
		Integer numPosts = 0;
		for (Post post : arrOfEndorsedPosts) {
			if (!post.isEmptyPost()) {
				numPosts++;
			}
		}

		return numPosts;
	}

	@Override
	public int getTotalCommentPosts() {
		// (Ollie)
		Integer numPosts = 0;
		for (Post post : arrOfComments) {
			if (!post.isEmptyPost()) {
				numPosts++;
			}
		}

		return numPosts;
	}

	@Override
	public int getMostEndorsedPost() {
		// (Ollie)
		// TODO: will cause an error if no posts are present when this function is
		// called
		int greatestNumberOfEndorsements = -1;
		int postID = -1;
		// Search through all posts and find the most endorsed post
		for (Post post : this.arrOfPosts) {
			if (post.getNumberOfEndorsements() >= greatestNumberOfEndorsements) {
				greatestNumberOfEndorsements = post.getNumberOfEndorsements();
				postID = post.getId();
			}
		}

		// Search through all comments and find the most endorsed comment
		for (Comment comment : this.arrOfComments) {
			if (comment.getNumberOfEndorsements() >= greatestNumberOfEndorsements) {
				greatestNumberOfEndorsements = comment.getNumberOfEndorsements();
				postID = comment.getId();
			}
		}

		return postID;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO: Do we have to make work when multiple accounts have the same
		// endorsement num?
		// I think you just have to display one of them, it shouldnt matter which.

		Account mostEndorsedAccount = null;
		int numEndorsementsOfMaxAccount = 0;

		ArrayList<Account> accounts = this.arrOfActiveAccounts;

		for (Account account : accounts) {
			if (account.getEndorsements() >= numEndorsementsOfMaxAccount) {
				mostEndorsedAccount = account;
			}
		}

		return mostEndorsedAccount.getAccountID();
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
