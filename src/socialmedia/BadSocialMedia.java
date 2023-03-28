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
	private ArrayList<Post> arrOfPosts  = new ArrayList<Post>(0);
	private ArrayList<EndorsedPost> arrOfEndorsedPosts = new ArrayList<EndorsedPost>(0);
	private ArrayList<Comment> arrOfComments = new ArrayList<Comment>(0);
	private ArrayList<Post> arrOfEmptyPosts = new ArrayList<Post>(0);

	private ArrayList<Account> arrOfActiveAccounts = new ArrayList<Account>(0);
	private ArrayList<Account> arrOfDeactivatedAccounts = new ArrayList<Account>(0);
	// TODO: deleting a comment/endorsed post may not work
	// To fix this, i think i can just change the type of the variable on variable
	// asignment in each for loop?

	// TODO: the platform class shouldnt be used i think which breaks a lot of your
	// code :( this is my fault but i will explain and we
	// can fix it, unless it works for yours then i can probably change my code to
	// use it maybe i was just being dumb when i made that class
	// because i didnt realise this file was a class.

	// TODO: (ollie) currently accounts can endorse their own post.

	// TODO: (ollie) most things require post object to be passed as param but
	// sometimes comment and endorsed post objects are passed instead which will
	// probably break the code
	

	//TODO: (ollie) i currently use 'arrayOfAccounts' where i should be using 'arrayOfActiveAccounts' in all post classes
	
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		
		for (Account account : arrOfActiveAccounts) {
			if (account.getHandle().equals(handle)) // iterates through all accounts to check none of them have already used the handle
			{
				throw new IllegalHandleException("Illegal handle: " + handle); //an illegal handle is a handle already in use
			}
		}

		if (handle.length() == 0 || handle.length() > 30 || handle.contains(" ")) //if the handle empty, too long or contains whitespace
		{
			throw new InvalidHandleException(); 
		}
		
		int accountID = arrOfActiveAccounts.size() + arrOfDeactivatedAccounts.size(); // generates unique accountID

		Account account = new Account(accountID, handle, "");
		this.arrOfActiveAccounts.add(account); //adds reference to account object to list of accounts

		return account.getAccountID();
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		
		for (Account account : arrOfActiveAccounts) {
			if (account.getHandle().equals(handle)) // iterates through all accounts to check none of them have already used the handle
			{
				throw new IllegalHandleException("Illegal handle: " + handle); //an illegal handle is a handle already in use
			}
		}

		if (handle.length() == 0 || handle.length() > 30 || handle.contains(" ")) //if the handle empty, too long or contains whitespace
		{
			throw new InvalidHandleException(); 
		}
		
		int accountID = arrOfActiveAccounts.size() + arrOfDeactivatedAccounts.size(); // generates unique accountID
		
		Account account = new Account(accountID, handle, description);
		this.arrOfActiveAccounts.add(account); //adds reference to account object to list of accounts

		return account.getAccountID();
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		for (Account account : arrOfActiveAccounts) {
			if (account.getAccountID() == id) // iterates through all accounts until the desired account is found
			{
				//Delete all posts for a given account
				for (Post post : account.getPosts()) {
					post.setEmptyPost();
				}
				
				//TODO: What actually is an endorsed post? Is it just a normal post added to another list that tracks
				//the specific posts a user has liked? If so, instead of setting the post to empty should I just
				//be removing an endorsement from a post?

				//Deletes all endorsed posts for a given account
				for (EndorsedPost endorsedPost : account.getEndorsedPosts()) {
					endorsedPost.setEmptyPost();
				}

				//Deletes all comments for a given account
				for (Comment comment : account.getComments()) {
					comment.setEmptyPost();
				}

				arrOfActiveAccounts.remove(account);
				arrOfDeactivatedAccounts.add(account);

				return;
			}
		}
		
		throw new AccountIDNotRecognisedException("Account ID " + id + " not found."); //if account not found
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		for (Account account : arrOfActiveAccounts) {
			if (account.getHandle().equals(handle)) // iterates through all accounts until the desired account is found
			{
				//Delete all posts for a given account
				for (Post post : account.getPosts()) {
					post.setEmptyPost();
				}
				
				//TODO: What actually is an endorsed post? Is it just a normal post added to another list that tracks
				//the specific posts a user has liked? If so, instead of setting the post to empty should I just
				//be removing an endorsement from a post?

				//Deletes all endorsed posts for a given account
				for (EndorsedPost endorsedPost : account.getEndorsedPosts()) {
					endorsedPost.setEmptyPost();
				}

				//Deletes all comments for a given account
				for (Comment comment : account.getComments()) {
					comment.setEmptyPost();
				}

				arrOfActiveAccounts.remove(account);
				arrOfDeactivatedAccounts.add(account);

				return;
			}
		}
		
		throw new HandleNotRecognisedException("Account handle " + handle + " not found."); //if account not found

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle) throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		Account account = getAccountObject(oldHandle); 
		account.setHandle(newHandle); //changes handle from old one to new
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		Account account = getAccountObject(handle);
		account.setDescription(description); //updates description
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		Account account = getAccountObject(handle);
		
		int ID = account.getAccountID();
		String desc = account.getDescription();
		int PostCount = account.getPosts().size();
		int EndorseCount = account.getNumEndorsements();

		String accountDetails = String.format("ID: %d \nHandle: %s \nDescription: %s \nPost Count: %d \nEndorse Count: %d", ID, handle, desc, PostCount, EndorseCount);

		return accountDetails;
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
		for (Account authorToCheck : arrOfActiveAccounts) {
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
		author.addPost(postObject);

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
		// Throw exception if the corresponding ID is to an empty post.
		// TODO: (ollie) this isnt done in the most efficient way but should work.
		if (isPostEmpty(id)) {
			throw new NotActionablePostException("You cannot endorse an empty post.");
		}

		// create an endorsed post object and append to arr of posts
		if (authorObject != null) {
			endorsedPostObject = new EndorsedPost(authorObject, postObject);
		}
		else if (commentObject != null) {
			endorsedPostObject = new EndorsedPost(authorObject, commentObject);
		}

		this.arrOfEndorsedPosts.add(endorsedPostObject);
		authorObject.addEndorsedPost(endorsedPostObject);

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
		authorObject.addComment(commentObject);

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

		Account authorObject = postObject.getAuthor();
		authorObject.removePost(postObject);
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
		return this.arrOfActiveAccounts.size();
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
		Account mostEndorsedAccount = null;
		int numEndorsementsOfMaxAccount = 0;

		for (Account account : arrOfActiveAccounts) {
			if (account.getNumEndorsements() >= numEndorsementsOfMaxAccount) {
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
