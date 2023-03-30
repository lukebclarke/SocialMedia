import java.io.IOException;

import socialmedia.AccountIDNotRecognisedException;
import socialmedia.BadSocialMedia;
import socialmedia.HandleNotRecognisedException;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.InvalidPostException;
import socialmedia.NotActionablePostException;
import socialmedia.PostIDNotRecognisedException;
import socialmedia.SocialMediaPlatform;

//testing

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new BadSocialMedia();

		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		// Test the platform after initialization
		testPlatform(platform);

		// Erase the platform and re-test it.
		platform.erasePlatform();
		testPlatform(platform);

		// Save the platform, erase the current platform, load the new platform and test
		// it again.
		try{
			platform.savePlatform("test_platform.ser");
			platform.erasePlatform();
		} catch (IOException e) {
			new IOException("error saving platform");
		}

		try{
			platform.loadPlatform("test_platform.ser");
			testLoadedPlatform(platform);
		} catch (IOException e) {
			new IOException("error loading platform");
		} catch (ClassNotFoundException e) {
			new ClassNotFoundException("class not found when trying to load platform.");
		}
		

	}

	private static void testPlatform(SocialMediaPlatform platform) {
		Integer id;
		try {
			id = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			platform.removeAccount(id);
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		}

		// Create a post then delete the post
		Integer postID2;
		try {
			Integer accountID = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

			postID2 = platform.createPost("my_handle", "Hello.");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			platform.deletePost(postID2);
			assert (platform.getTotalOriginalPosts()) == 0 : "number of posts registered in the system does not match";

			platform.removeAccount(accountID);
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		} catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
		}

		// // Create a new account to use in future tests
		try {
			id = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		}

		// // Create a post then endorse that post, delete the endorsed post and the
		// // original post
		Integer postID3;
		try {
			postID3 = platform.createPost("my_handle", "You Too.");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			int endorsedPostID = platform.endorsePost("my_handle", postID3);
			assert (platform.getTotalEndorsmentPosts()) == 1
					: "number of endorsed posts registered in the system does not match";

			platform.deletePost(endorsedPostID);
			assert (platform.getTotalEndorsmentPosts()) == 0
					: "number of endorsed posts registered in the system does not match";

			platform.deletePost(postID3);
			assert (platform.getTotalOriginalPosts()) == 0 : "number of posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		}

		// Create a post, endorse that post then delete the original post.
		Integer postID5;
		try {
			postID5 = platform.createPost("my_handle", "You Too.");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			int endorsedPostId = platform.endorsePost("my_handle", postID5);
			assert (platform.getTotalEndorsmentPosts()) == 1
					: "number of endorsed posts registered in the system does not match";

			platform.deletePost(postID5);
			assert (platform.getTotalOriginalPosts()) == 0
					: "number of original posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			System.out.println(e);
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		}

		// Create a post, comment on that post, delete the comment and the post.
		Integer postID6;
		try {
			postID6 = platform.createPost("my_handle", "What's the weather today?");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			Integer commentID = platform.commentPost("my_handle", postID6, "sunny.");
			assert (platform.getTotalCommentPosts()) == 1
					: "number of comment posts registered in the system does not match";

			platform.deletePost(commentID);
			assert (platform.getTotalCommentPosts()) == 0
					: "number of endorsed posts registered in the system does not match";
			assert (platform.getTotalOriginalPosts()) == 1
					: "number of original posts registered in the system does not match";

			platform.deletePost(postID6);
			assert (platform.getTotalOriginalPosts()) == 0
					: "number of original posts registered in the system does not match";
			assert (platform.getTotalCommentPosts()) == 0
					: "number of endorsed posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		}

		// Create a post, comment on that post, delete the post.
		Integer postID7;
		try {
			postID7 = platform.createPost("my_handle", "What's the weather today?");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			Integer commentID = platform.commentPost("my_handle", postID7, "sunny.");
			assert (platform.getTotalCommentPosts()) == 1
					: "number of comment posts registered in the system does not match";

			platform.deletePost(postID7);
			assert (platform.getTotalOriginalPosts()) == 0
					: "number of original posts registered in the system does not match";
			assert (platform.getTotalCommentPosts()) == 1
					: "number of comment posts registered in the system does not match";

			platform.deletePost(commentID);
			assert (platform.getTotalCommentPosts()) == 0
					: "number of comment posts registered in the system does not match";
			assert (platform.getTotalOriginalPosts()) == 0
					: "number of original posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		}

		// Try to comment on an empty post
		try {
			Integer postID = platform.createPost("my_handle", "How u doin?");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			platform.deletePost(postID);
			assert (platform.getTotalOriginalPosts()) == 0
					: "number of original posts registered in the system does not match";

			platform.commentPost("my_handle", postID, "Good thanks u?");
			assert (platform.getTotalCommentPosts()) == 0
					: "number of comment posts registered in the system does not match";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (true);
		}

		// Try to endorse an empty post
		try {
			Integer postID = platform.createPost("my_handle", "How u doin?");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			platform.deletePost(postID);
			assert (platform.getTotalOriginalPosts()) == 0
					: "number of original posts registered in the system does not match";

			platform.endorsePost("my_handle", postID);
			assert (platform.getTotalEndorsmentPosts()) == 0
					: "number of endorsed posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (true);
		}

		// Try to delete an empty post
		try {
			Integer postID = platform.createPost("my_handle", "How u doin?");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			platform.deletePost(postID);
			assert (platform.getTotalOriginalPosts()) == 0
					: "number of original posts registered in the system does not match";

			platform.deletePost(postID);
			assert (platform.getTotalOriginalPosts()) == 0
					: "number of original posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (true);
		}

		// Output a post as a string
		try {
			Integer postID = platform.createPost("my_handle", "Hello.");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			platform.showIndividualPost(postID);
			assert (platform.showIndividualPost(postID).equals(
					"ID: " + postID + "\n Account: my_handle\n No. endorsements: 0 | No. comments: 0\n Hello.")) == true
					: "message does not match inputs.";

			platform.deletePost(postID);
			assert (platform.getTotalOriginalPosts()) == 0 : "number of posts registered in the system does not match";

		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		}

		// Output a post as a string with both endorsements and comments.
		try {
			Integer postID = platform.createPost("my_handle", "Hello.");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

			Integer commentID1 = platform.commentPost("my_handle", postID, "Hi.");
			Integer commentID2 = platform.commentPost("my_handle", postID, "Hey.");

			Integer epID1 = platform.endorsePost("my_handle", postID);
			Integer epID2 = platform.endorsePost("my_handle", postID);

			platform.showIndividualPost(postID);
			assert (platform.showIndividualPost(postID).equals("ID: " + postID
					+ "\n Account: my_handle\n No. endorsements: 2 | No. comments: 2\n Hello.")) == true
					: "message does not match inputs.";

			platform.deletePost(postID);
			assert (platform.getTotalOriginalPosts()) == 0 : "number of posts registered in the system does not match";
			assert (platform.getTotalEndorsmentPosts() == 0)
					: "number of endorsed posts registered in the system does not match";

			platform.deletePost(commentID1);
			assert (platform.getTotalCommentPosts() == 1)
					: "number of endorsed posts registered in the system does not match";
			platform.deletePost(commentID2);
			assert (platform.getTotalCommentPosts() == 0)
					: "number of endorsed posts registered in the system does not match";

		} catch (PostIDNotRecognisedException e) {
			System.out.println(e);
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		}

		// Create a post with message length greater than 100 characters
		try {
			Integer postID = platform.createPost("my_handle",
					"mqqlkaifyqrixjordaaorufwiruuvtkewyvsadjepnoexviwoppksspszmuehcblosibmgiqocwzksnhrlgqjqhgajuxfwrqixmjb");
			assert (platform.getTotalOriginalPosts()) == 0 : "number of posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (true);
		}

		// Create a post with a message length of 0 characters
		try {
			Integer postID = platform.createPost("my_handle", "");
			assert (platform.getTotalOriginalPosts()) == 0 : "number of posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (true);
		}

		// Create a message of length 1 char
		try {
			Integer postID = platform.createPost("my_handle", "1");
			assert (platform.getTotalOriginalPosts()) == 1 : "number of posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		}

		// Create a message of length 100 chars
		try {
			Integer postID = platform.createPost("my_handle",
					"ntkfdnzeuygzhcjmgsxzqiadunpwaljcgxfhtlwoyxobiunqucrnmtuuikodoqmkbjhsfwhbgcjvyputfevipftvxfrbkqivkrfv");
			assert (platform.getTotalOriginalPosts()) == 2 : "number of posts registered in the system does not match";

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		}

		// Create a post, with 3 level 1 comments, with 3 level 1 comments, 2 level 2 comments and 1 level 3 comment
		// Check the output tree is correct.
		try {
			platform.erasePlatform();
			Integer accountID1 = platform.createAccount("testAcc1", "my bio");
			Integer accountID2 = platform.createAccount("testAcc2", "a bio");
			Integer accountID3 = platform.createAccount("testAcc3", "the bio");
			Integer accountID4 = platform.createAccount("testAcc4", "no bio");
			Integer accountID5 = platform.createAccount("testAcc5", "what is this?");
			assert (platform.getNumberOfAccounts()) == 5 : "Number of original posts in the platform does not match";

			Integer postID1 = platform.createPost("testAcc1", "post1");
			assert (platform.getTotalOriginalPosts()) == 1 : "Number of original posts in the platform does not match";

			Integer commentID1 = platform.commentPost("testAcc2", postID1, "comment 1 msg");
			Integer commentID2 = platform.commentPost("testAcc3", postID1, "comment 2 msg");
			Integer commentID3 = platform.commentPost("testAcc4", postID1, "comment 3 msg");
			Integer commentID4 = platform.commentPost("testAcc1", commentID1, "comment 4 msg");
			Integer commentID5 = platform.commentPost("testAcc1", commentID2, "comment 5 msg");
			Integer commentID6 = platform.commentPost("testAcc5", commentID3, "comment 6 msg");
			Integer commentID7 = platform.commentPost("testAcc4", commentID5, "comment 7 msg");
			Integer commentID8 = platform.commentPost("testAcc1", commentID6, "comment 8 msg");
			Integer commentID9 = platform.commentPost("testAcc5", commentID8, "comment 9 msg");
			assert (platform.getTotalCommentPosts()) == 9 : "Number of comments in the platform does not match";

			//System.out.println(platform.showPostChildrenDetails(postID1));

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		}

		// Create a post tree containing a post, 3 level 1 comments, each has 1 level 2
		// comment. Endorse each post once. Delete one of the level 1 comments.
		try {
			platform.erasePlatform();
			Integer accountID1 = platform.createAccount("acc1");
			Integer accountID2 = platform.createAccount("acc2");
			Integer accountID3 = platform.createAccount("acc3");
			assert (platform.getNumberOfAccounts()) == 3 : "Number of accounts in the platform does not match";

			Integer postID1 = platform.createPost("acc1", "post1");
			assert (platform.getTotalOriginalPosts()) == 1 : "Number of original posts in the platform does not match";

			Integer commentID1 = platform.commentPost("acc1", postID1, "acc1 comment 1");
			Integer commentID2 = platform.commentPost("acc2", postID1, "acc1 comment 2");
			Integer commentID3 = platform.commentPost("acc3", postID1, "acc1 comment 3");
			Integer commentID4 = platform.commentPost("acc1", commentID2, "acc1 comment 4");
			Integer commentID5 = platform.commentPost("acc2", commentID3, "acc1 comment 5");
			Integer commentID6 = platform.commentPost("acc3", commentID1, "acc1 comment 6");
			assert (platform.getTotalCommentPosts()) == 6 : "Number of comments in the platform does not match";

			Integer endorsedPostID2 = platform.endorsePost("acc2", commentID3);
			Integer endorsedPostID1 = platform.endorsePost("acc2", commentID3);

			platform.deletePost(commentID3);
			assert (platform.getTotalCommentPosts()) == 5 : "Number of comments in the platform does not match";

			//System.out.println(platform.showPostChildrenDetails(postID1));

		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
		} catch (NotActionablePostException e) {
			assert (false) : "NotActionablePostException thrown incorrectly";
		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
		}

		// Create an account, create posts of that account then delete the account, the posts should be deleted too.
		try{
			platform.erasePlatform();
			Integer accID = platform.createAccount("anAccount");
			assert(platform.getNumberOfAccounts()) == 1 : "Number of accounts in the platform does not match";

			Integer postID = platform.createPost("anAccount", "anAccount Post 1");
			assert(platform.getTotalOriginalPosts()) == 1 : "Total original posts in the platform does not match";
			Integer commentID = platform.commentPost("anAccount", postID, "anAccount Comment 1");
			assert(platform.getTotalCommentPosts()) == 1 : "total comment posts in the platform does not match";
			Integer epID = platform.endorsePost("anAccount", postID);
			Integer epID2 = platform.endorsePost("anAccount", commentID);
			assert (platform.getTotalEndorsmentPosts()) == 2 : "total endorsment posts in the platform does not match";

			platform.removeAccount(accID);
			assert(platform.getNumberOfAccounts()) == 0;
			assert(platform.getTotalOriginalPosts()) == 0;
			assert(platform.getTotalCommentPosts()) == 0;
			assert(platform.getTotalEndorsmentPosts()) == 0;
			
		} catch (IllegalHandleException e) {
			new IllegalHandleException("The account handle input is invalid, the handle input is already in use.");
		} catch (InvalidHandleException e) {
			new InvalidHandleException("the handle input is not valid, the handle must be of the correct length");
		} catch (HandleNotRecognisedException e) {
			new HandleNotRecognisedException("The handle input does not exist in the system.");
		} catch (InvalidPostException e) {
			new InvalidPostException("The post input is empty or exceeds the permitted message length");
		} catch (PostIDNotRecognisedException e) {
			new PostIDNotRecognisedException("The post with the given id does not exist in the system");
		} catch (NotActionablePostException e) {
			new NotActionablePostException("this action cannot be performed on this post.");
		} catch (AccountIDNotRecognisedException e) {
			new AccountIDNotRecognisedException("The account id input does not exist in the system");
		}
	}
	
	private static void testLoadedPlatform(SocialMediaPlatform platform) {
		assert (platform.getTotalCommentPosts()) == 5 : "Number of comments in the platform does not match";
	}

}
