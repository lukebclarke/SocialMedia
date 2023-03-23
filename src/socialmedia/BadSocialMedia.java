package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class BadSocialMedia implements SocialMediaPlatform {
	private ArrayList<Account> arrOfAccounts;
	private ArrayList<Post> arrOfPosts;
	private ArrayList<EndorsedPost> arrOfEndorsedPosts;
	private ArrayList<Comment> arrOfComments;
	private ArrayList<Post> arrOfEmptyPosts;

	// TODO: deleting a comment/endorsed post may not work
	// To fix this, i think i can just change the type of the variable on variable
	// asignment in each for loop?

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		// TODO Auto-generated method stub (Luke)
		return 0;
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		// TODO Auto-generated method stub (Luke)
		return 0;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		// TODO Auto-generated method stub (Luke)

	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub (Luke)

	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		// TODO Auto-generated method stub (Luke)

	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub (Luke)

	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO Auto-generated method stub (Luke)
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// Ollie
		// Check if post message is of the correct length
		// TODO 'up to 100 characters' means less than 100? or <= 100 ?
		// TODO: does the 100 characters include the handle? I assumed not.
		if (message.length() >= 100) {
			throw new InvalidPostException("Post message contains: " + message.length()
					+ " characters. Message can only contain up to 100 characters.");
		} else if (message.length() == 0) {
			throw new InvalidPostException("Post message contains: 0 characters. The post message cannot be empty.");
		}

		// Search through all accounts until the account with given handle is found.
		Account author = null;
		for (int i = 0; i < handle.length(); i++) {
			String handleToCheck = this.arrOfAccounts.get(i).getHandle();
			if (handleToCheck == handle) {
				author = this.arrOfAccounts.get(i);
				break;
			}
		}

		// Check if the account handle given was valid
		if (author == null) {
			throw new HandleNotRecognisedException("Handle: " + handle + " is not valid.");
		}

		// create post object and append to arr of posts
		Post postObject = new Post(author, message);
		this.arrOfPosts.add(postObject);

		return postObject.getId();
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// (Ollie)
		// Search through all accounts until the account with given handle is found.
		Account authorObject = null;
		for (int i = 0; i < handle.length(); i++) {
			String handleToCheck = this.arrOfAccounts.get(i).getHandle();
			if (handleToCheck == handle) {
				authorObject = this.arrOfAccounts.get(i);
				break;
			}
		}

		// Check if the account handle given was valid
		if (authorObject == null) {
			throw new HandleNotRecognisedException("Handle: " + handle + " is not valid.");
		}

		// Search through all posts until the post with the given ID is found
		Post postObject = null;
		for (int i = 0; i < arrOfPosts.size(); i++) {
			int idToCheck = this.arrOfPosts.get(i).getId();
			if (idToCheck == id) {
				postObject = this.arrOfPosts.get(i);
				break;
			}
		}

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfComments.size(); i++) {
				int idToCheck = this.arrOfComments.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfComments.get(i);
					break;
				}
			}

		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post width given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfEndorsedPosts.size(); i++) {
				int idToCheck = this.arrOfEndorsedPosts.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfEndorsedPosts.get(i);
					break;
				}
			}

			// Throw exception if an endorsed post with the given id was found.
			if (postObject != null) {
				throw new NotActionablePostException(
						"You cannot endorse an endorsed post. ID of post you tried to endorse: " + id);
			}
		}

		// If a post with given ID is not found, search through all empty posts until
		// the post width given id is found.
		if (postObject != null) {
			boolean isAnEmptyPost = false;
			for (int i = 0; i < arrOfEmptyPosts.size(); i++) {
				int idToCheck = this.arrOfEmptyPosts.get(i).getId();
				if (idToCheck == id) {
					isAnEmptyPost = true;
					break;
				}
			}

			// Throw exception if an endorsed post with the given id was found.
			if (isAnEmptyPost) {
				throw new NotActionablePostException("You cannot endorse an empty post.");
			}
		}

		// Throw exception if no post with the given id was found
		if (postObject == null) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}

		// create post object and append to arr of posts
		// Try catch and null used because badsocialmedia thought i do not handle
		// TODO: make sure this works as i intended it to.
		EndorsedPost endorsedPostObject = null;
		try {
			endorsedPostObject = new EndorsedPost(authorObject, postObject);
		} catch (InvalidPostException e) {
			// TODO: this may not work as expected, needs to throw exception in Post.java
			e.printStackTrace();
		}
		this.arrOfEndorsedPosts.add(endorsedPostObject);
		postObject.addEndorsedPost(endorsedPostObject);
		authorObject.addPost(endorsedPostObject); // TODO: check the author doesnt need individual arrays

		return endorsedPostObject.getId();
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// (Ollie)
		// Search through all accounts until the account with given handle is found.
		Account authorObject = null;
		for (int i = 0; i < handle.length(); i++) {
			String handleToCheck = this.arrOfAccounts.get(i).getHandle();
			if (handleToCheck == handle) {
				authorObject = this.arrOfAccounts.get(i);
				break;
			}
		}

		// Check if the account handle given was valid
		if (authorObject == null) {
			throw new HandleNotRecognisedException("Handle: " + handle + " is not valid.");
		}

		// Search through all posts until the post with the given ID is found
		Post postObject = null;
		for (int i = 0; i < arrOfPosts.size(); i++) {
			int idToCheck = this.arrOfPosts.get(i).getId();
			if (idToCheck == id) {
				postObject = this.arrOfPosts.get(i);
				break;
			}
		}

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfComments.size(); i++) {
				int idToCheck = this.arrOfComments.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfComments.get(i);
					break;
				}
			}

		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post width given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfEndorsedPosts.size(); i++) {
				int idToCheck = this.arrOfEndorsedPosts.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfEndorsedPosts.get(i);
					break;
				}
			}

			// Throw exception if an endorsed post with the given id was found.
			if (postObject != null) {
				throw new NotActionablePostException(
						"You cannot endorse an endorsed post. ID of post you tried to endorse: " + id);
			}
		}

		// If a post with given ID is not found, search through all empty posts until
		// the post width given id is found.
		if (postObject != null) {
			boolean isAnEmptyPost = false;
			for (int i = 0; i < arrOfEmptyPosts.size(); i++) {
				int idToCheck = this.arrOfEmptyPosts.get(i).getId();
				if (idToCheck == id) {
					isAnEmptyPost = true;
					break;
				}
			}

			// Throw exception if an endorsed post with the given id was found.
			if (isAnEmptyPost) {
				throw new NotActionablePostException("You cannot endorse an empty post.");
			}
		}

		// Throw exception if no post with the given id was found
		if (postObject == null) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}

		// Check that the provided message is within the allowed length (0 to 100 chars)
		// TODO 'up to 100 characters' means less than 100? or <= 100 ?
		if (message.length() >= 100) {
			throw new InvalidPostException("Post message contains: " + message.length()
					+ " characters. Message can only contain up to 100 characters.");
		} else if (message.length() == 0) {
			throw new InvalidPostException("Post message contains: 0 characters. The post message cannot be empty.");
		}

		// create post object and append to arr of posts
		// Try catch and null used because badsocialmedia thought i do not handle
		// TODO: make sure this works as i intended it to.
		Comment commentObject = new Comment(authorObject, postObject, message); // TODO: check this throws
																				// invalidpostexception
		this.arrOfComments.add(commentObject);

		return commentObject.getId();
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// (Ollie)
		// Search through all posts until the post with the given ID is found
		Post postObject = null;
		for (int i = 0; i < arrOfPosts.size(); i++) {
			int idToCheck = this.arrOfPosts.get(i).getId();
			if (idToCheck == id) {
				postObject = this.arrOfPosts.get(i);
				break;
			}
		}

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfComments.size(); i++) {
				int idToCheck = this.arrOfComments.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfComments.get(i);
					break;
				}
			}
		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post with the given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfEndorsedPosts.size(); i++) {
				int idToCheck = this.arrOfEndorsedPosts.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfEndorsedPosts.get(i);
					break;
				}
			}
		}

		// Throw exception if no post with the given id was found
		if (postObject == null) {
			throw new PostIDNotRecognisedException("The post with ID: " + id + " was not found.");
		}

		// Turn the post into an empty post
		postObject.setEmptyPost();
		// TODO: when a post of empty should it be removed from the list of posts or
		// not? I think not? I assumed not.
		// arrOfEmprtPosts.remove(postObject)
		arrOfEmptyPosts.add(postObject);
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// (Ollie)
		// Check the id is valid, and get the post object.
		// Search through all posts until the post with the given ID is found
		Post postObject = null;
		for (int i = 0; i < arrOfPosts.size(); i++) {
			int idToCheck = this.arrOfPosts.get(i).getId();
			if (idToCheck == id) {
				postObject = this.arrOfPosts.get(i);
				break;
			}
		}

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfComments.size(); i++) {
				int idToCheck = this.arrOfComments.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfComments.get(i);
					break;
				}
			}

		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post width given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfEndorsedPosts.size(); i++) {
				int idToCheck = this.arrOfEndorsedPosts.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfEndorsedPosts.get(i);
					break;
				}
			}
		}

		// Throw exception if a post with the given id was not found.
		if (postObject == null) {
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
		Post postObject = null;
		for (int i = 0; i < arrOfPosts.size(); i++) {
			int idToCheck = this.arrOfPosts.get(i).getId();
			if (idToCheck == id) {
				postObject = this.arrOfPosts.get(i);
				break;
			}
		}

		// If a post with given ID is not found, search through all comments until the
		// post width given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfComments.size(); i++) {
				int idToCheck = this.arrOfComments.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfComments.get(i);
					break;
				}
			}

		}

		// If a post with given ID is not found, search through all endorsed posts until
		// the post width given id is found.
		if (postObject == null) {
			for (int i = 0; i < arrOfEndorsedPosts.size(); i++) {
				int idToCheck = this.arrOfEndorsedPosts.get(i).getId();
				if (idToCheck == id) {
					postObject = this.arrOfEndorsedPosts.get(i);
					break;
				}
			}

			// Throw exception if an endorsed post with the given id was found.
			if (postObject != null) {
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
			Comment commentObject = InitialArrOfComments.get(i);

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
		// TODO Auto-generated method stub (Luke)
		return 0;
	}

	@Override
	public int getTotalOriginalPosts() {
		// (Ollie)
		// TODO: this is wrong, as also includes deletion of comments and endorsements
		// in the calculation
		return this.arrOfPosts.size() - this.arrOfEmptyPosts.size();
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// (Ollie)
		// TODO: this is wrong, as also includes deletion of comments and endorsements
		// in the calculation
		return this.arrOfEndorsedPosts.size() - this.arrOfEmptyPosts.size();
	}

	@Override
	public int getTotalCommentPosts() {
		// (Ollie)
		// TODO: this is wrong, as also includes deletion of comments and endorsements
		// in the calculation
		return this.arrOfComments.size() - this.arrOfEmptyPosts.size();
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
		// TODO Auto-generated method stub (Luke)
		return 0;
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
