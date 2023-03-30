package socialmedia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * SocialMedia is a functioning implementor of
 * the SocialMediaPlatform interface.
 */
public class SocialMedia implements SocialMediaPlatform {
	private ArrayList<Post> arrOfPosts  = new ArrayList<Post>(0);
	private ArrayList<EndorsedPost> arrOfEndorsedPosts = new ArrayList<EndorsedPost>(0);
	private ArrayList<Comment> arrOfComments = new ArrayList<Comment>(0);
	private ArrayList<Post> arrOfEmptyPosts = new ArrayList<Post>(0);

	private ArrayList<Account> arrOfActiveAccounts = new ArrayList<Account>(0);
	private ArrayList<Account> arrOfDeactivatedAccounts = new ArrayList<Account>(0);

	// TODO: currently accounts can endorse their own post but I don't think it says anywhere that they shouldn't be able to?
	//TODO: a single account can endorse a post more than once? but it doesnt say anywhere that you shouldnt be able to?
	
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
					arrOfPosts.remove(post);
					post.setEmptyPost();
				}

				//Delete all endorsed posts for a given account
				for (EndorsedPost endorsedPost : account.getEndorsedPosts()) {
					arrOfEndorsedPosts.remove(endorsedPost);
					endorsedPost.setEmptyPost();
				}

				//Deletes all comments for a given account
				for (Comment comment : account.getComments()) {
					arrOfComments.remove(comment);
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
					arrOfPosts.remove(post);
					post.setEmptyPost();
				}				

				//Deletes all endorsed posts for a given account
				for (EndorsedPost endorsedPost : account.getEndorsedPosts()) {
					arrOfEndorsedPosts.remove(endorsedPost);
					endorsedPost.setEmptyPost();
				}

				//Deletes all comments for a given account
				for (Comment comment : account.getComments()) {
					arrOfComments.remove(comment);
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
		if (message.length() > 100) {
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
		if (isPostEmpty(id)) {
			throw new NotActionablePostException("You cannot endorse an empty post.");
		}

		// create an endorsed post object and append to arr of posts
		if (postObject != null) {
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
		commentObject = new Comment(authorObject, postObject, message);
		this.arrOfComments.add(commentObject);
		authorObject.addComment(commentObject);

		return commentObject.getId();
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// If the post is an empty post, say it is not found
		if (isPostEmpty(id)) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}

		Post postObject = searchForPost(id);
		
		ArrayList<EndorsedPost> arrOfPostEndorsements  = new ArrayList<EndorsedPost>(0);

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		Comment commentObject = null;
		if (postObject == null) {
			commentObject = searchForComment(id);
		}
		else if (postObject != null) {
			//Remove the post from an account's list of posts
			Account authorObject = postObject.getAuthor();
			authorObject.removePost(postObject);

			//Get an array of endorsements for this post.
			arrOfPostEndorsements = postObject.getArrayOfEndorsements();

			// Turn the post into an empty post
			postObject.setEmptyPost();
			arrOfEmptyPosts.add(postObject);
		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post with the given id is found.
		EndorsedPost endorsedPostObject = null;
		if (postObject == null && commentObject == null) {
			endorsedPostObject = searchForEndorsedPost(id);
		}
		else if (commentObject != null){
			//Remove the post from an account's list of posts
			Account authorObject = commentObject.getAuthor();
			authorObject.removeComment(commentObject);

			//Get an array of endorsements for this post.
			arrOfPostEndorsements = commentObject.getArrayOfEndorsements();

			// Turn the post into an empty post
			commentObject.setEmptyPost();
			arrOfEmptyPosts.add(commentObject);
		}

		// Throw exception if no post with the given id was found
		if (postObject == null && commentObject == null && endorsedPostObject == null) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}
		else if (endorsedPostObject != null) {
			//Remove the post from an account's list of posts
			Account authorObject = endorsedPostObject.getAuthor();
			authorObject.removeEndorsedPost(endorsedPostObject);

			//Get an array of endorsements for this post.
			arrOfPostEndorsements = endorsedPostObject.getArrayOfEndorsements();

			// Turn the post into an empty post
			endorsedPostObject.setEmptyPost();
			arrOfEmptyPosts.add(endorsedPostObject);
		}

		//Remove all endorsements for this post.
		for (EndorsedPost epObject : arrOfPostEndorsements) {
			arrOfEndorsedPosts.remove(epObject);
		}

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// Check the id is valid, and get the post object.
		//Initaalize variables
		Account author = null;
		String handle = null;
		Integer commentId = null;
		Integer numEndorsements = null;
		Integer numComments = null;
		String message = null;

		// Search through all posts until the post with the given ID is found
		Post postObject = searchForPost(id);

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		Comment commentObject = null;
		if (postObject == null) {
			commentObject = searchForComment(id);

		} else {
			author = postObject.getAuthor();
			handle = (!postObject.isEmptyPost()) ? author.getHandle() : "";
			commentId = postObject.getId();
			numEndorsements = postObject.getNumberOfEndorsements();
			numComments = postObject.getNumberOfComments();
			message = postObject.getMessage();
		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post width given id is found.
		EndorsedPost endorsedPostObject = null;
		if (postObject == null && commentObject == null) {
			endorsedPostObject = searchForEndorsedPost(id);
		} 
		else if(commentObject != null)  {
			author = commentObject.getAuthor();
			handle = (!commentObject.isEmptyPost()) ? author.getHandle() : "";
			commentId = commentObject.getId();
			numEndorsements = commentObject.getNumberOfEndorsements();
			numComments = commentObject.getNumberOfComments();
			message = commentObject.getMessage();
		}

		// Throw exception if a post with the given id was not found.
		if (postObject == null && commentObject == null && endorsedPostObject == null) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		} else if(endorsedPostObject != null) {
			author = endorsedPostObject.getAuthor();
			handle = (!endorsedPostObject.isEmptyPost()) ? author.getHandle() : "";
			commentId = endorsedPostObject.getId();
			numEndorsements = endorsedPostObject.getNumberOfEndorsements();
			numComments = endorsedPostObject.getNumberOfComments();
			message = endorsedPostObject.getMessage();
		}

		String postDetails = String.format("ID: %d"
			+ "\n " + "Account: %s"
			+ "\n " + "No. endorsements: %d | No. comments: %d"
			+ "\n " + "%s", commentId, handle, numEndorsements, numComments, message);
		return postDetails;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
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
						"You cannot show the children of an endorsed post.");
			}
		}

		// Output the initial post
		String stringToReturn = "";
		stringToReturn += showIndividualPost(id);

		// For each comment in the post, display the comment along with all of it's
		// comments
		for (Comment comment : postObject.getArrayOfComments()) {

			stringToReturn += showComments(comment, 1);
		}

		// Convert the string to stringbuilder
		StringBuilder sb = new StringBuilder();
		sb.append(stringToReturn);

		return sb;
 
	}

	private String showComments(Comment commentObject, int commentLevel) {
		String stringToReturn = "";

		// Calculate the spacing to the left of the message
		String spacing = "";
		String idSpacing = "";
		for (int i = 0; i < commentLevel; i++) {
			spacing += "    ";

			if (i > 0) {
				idSpacing += "    ";
			}
		}

		// Add to the string to return the comment of which this function was called by
		Account author = commentObject.getAuthor();
		String handle = (!commentObject.isEmptyPost()) ? author.getHandle() : "";
		Integer commentId = commentObject.getId();
		Integer numEndorsements = commentObject.getNumberOfEndorsements();
		Integer numComments = commentObject.getNumberOfComments();
		String message = commentObject.getMessage();

		stringToReturn += String.format("\n " + idSpacing + "| > ID: %d"
				+ "\n " + spacing + "Account: %s"
				+ "\n " + spacing + "No. endorsements: %d | No. comments: %d"
				+ "\n " + spacing + "%s", commentId, handle, numEndorsements, numComments, message);

		// Call this function again for any further comments on this comment post.
		for (Comment comment : commentObject.getArrayOfComments()) {
			stringToReturn += showComments(comment, commentLevel + 1);
		}

		return stringToReturn;
	}

	@Override
	public int getNumberOfAccounts() {
		return this.arrOfActiveAccounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
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
		this.arrOfPosts  = new ArrayList<Post>(0);
		this.arrOfEndorsedPosts = new ArrayList<EndorsedPost>(0);
		this.arrOfComments = new ArrayList<Comment>(0);
		this.arrOfEmptyPosts = new ArrayList<Post>(0);

		this.arrOfActiveAccounts = new ArrayList<Account>(0);
		this.arrOfDeactivatedAccounts = new ArrayList<Account>(0);

		Post.setNextPostId(0);
	}

	@Override
	public void savePlatform(String filename) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
			oos.writeObject(this.arrOfPosts);
			oos.writeObject(this.arrOfEndorsedPosts);
			oos.writeObject(this.arrOfComments);
			oos.writeObject(this.arrOfEmptyPosts);

			oos.writeObject(this.arrOfActiveAccounts);
			oos.writeObject(this.arrOfDeactivatedAccounts);

		} catch (IOException e) {
			new IOException("Error when saving platform.");
		}
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {

			//Update the fields of the current object
			this.arrOfPosts = (ArrayList<Post>) ois.readObject();
			this.arrOfEndorsedPosts = (ArrayList<EndorsedPost>) ois.readObject();
			this.arrOfComments = (ArrayList<Comment>) ois.readObject();
			this.arrOfEmptyPosts = (ArrayList<Post>) ois.readObject();

			this.arrOfActiveAccounts = (ArrayList<Account>) ois.readObject();
			this.arrOfDeactivatedAccounts = (ArrayList<Account>) ois.readObject();

		} catch (IOException e) {
			new IOException("Error when loading platform.", e);
		} catch (ClassNotFoundException e) {
			new ClassNotFoundException("Error when loading platform.", e);
		}
	}
	
}
