package socialmedia;

import java.io.IOException;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class BadSocialMedia implements SocialMediaPlatform {

	private Platform platform = new Platform();

	//LUKE TODO: what do the throw exceptions do, do i have to code that

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		Account account = new Account(numAccounts, handle, "");

		return account;
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		Account account = new Account(numAccounts, handle, description);

		return account;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		ArrayList<Account> accounts = platform.getActiveAccounts();

		for (Account account : accounts)
		{
			if (account.getAccountID() == id) //iterates through all accounts until the desired account is found
			{
				account.deleteAccount();
			}
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		ArrayList<Account> accounts = platform.getActiveAccounts();

		for (Account account : accounts)
		{
			if (account.getAccountID() == handle) //iterates through all accounts until the desired account is found
			{
				account.deleteAccount();
			}
		}
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		
		ArrayList<Account> accounts = platform.getActiveAccounts();

		for (Account account : accounts)
		{
			if (account.getAccountID() == oldHandle) //iterates through all accounts until the desired account is found
			{
				account.setHandle(newHandle);
			}
		}
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		ArrayList<Account> accounts = platform.getActiveAccounts();

		for (Account account : accounts)
		{
			if (account.getAccountID() == handle) //iterates through all accounts until the desired account is found
			{
				account.setDescription(description);
			}
		}
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		// TODO decide what this does
		return null;
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// TODO Auto-generated method stub (Ollie)
		return 0;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub (Ollie)
		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// TODO Auto-generated method stub (Ollie)
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub (Ollie)

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub (Ollie)
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub (Ollie)
		return null;
	}

	@Override
	public int getNumberOfAccounts() {
		accounts = platform.getActiveAccounts();

		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		// TODO Auto-generated method stub (Ollie)
		return 0;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub (Ollie)
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub (Ollie)
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub (Ollie)
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		//TODO: Do we have to make work when multiple accounts have the same endorsement num?

		Account mostEndorsedAccount = null;
		int numEndorsementsOfMaxAccount = 0;

		ArrayList<Account> accounts = platform.getActiveAccounts();

		for (Account account : accounts)
		{
			if (account.getEndorsements() >= numEndorsementsOfMaxAccount)
			{
				mostEndorsedAccount = account;
			}
		}

		return mostEndorsedAccount;
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
