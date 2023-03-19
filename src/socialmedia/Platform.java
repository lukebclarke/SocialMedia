package socialmedia;

import java.util.ArrayList;

public class Platform {
    private static ArrayList<Account> activeAccountsArr;
    private static ArrayList<Account> deactivatedAccountsArr;
    private static ArrayList<Post> arrOfAllPosts;
    private static ArrayList<Post> arrOfDeletedPosts;

    public Platform() {
        /*
         * TODO: implement this without creating a new instance each time.
         * Currently a new instance is created to ensure the array is empty when an
         * empty
         * platform is initialised after a platform has already been loaded.
         */
        Platform.activeAccountsArr = new ArrayList<Account>();
        Platform.deactivatedAccountsArr = new ArrayList<Account>();
        Platform.arrOfAllPosts = new ArrayList<Post>();
        Platform.arrOfDeletedPosts = new ArrayList<Post>();
    }

    public Platform(ArrayList<Account> activeAccountsArr, ArrayList<Account> deactivatedAccountsArr,
            ArrayList<Post> arrOfAllPosts, ArrayList<Post> arrOfDeletedPosts) {
        Platform.activeAccountsArr = activeAccountsArr;
        Platform.deactivatedAccountsArr = deactivatedAccountsArr;
        Platform.arrOfAllPosts = arrOfAllPosts;
        Platform.arrOfDeletedPosts = arrOfDeletedPosts;

    }

    public ArrayList<Account> getActiveAccounts() {
        return activeAccountsArr;
    }

    public ArrayList<Account> getDeactivatedAccounts() {
        return deactivatedAccountsArr;
    }

    public ArrayList<Post> getAllPosts() {
        return arrOfAllPosts;
    }

    public ArrayList<Post> getAllDeletedPosts() {
        return arrOfDeletedPosts;
    }

    public void addActiveAccount(Account accountObject) {
        Platform.activeAccountsArr.add(accountObject);
    }

    public void addDeactivatedAccount(Account accountObject) {
        Platform.deactivatedAccountsArr.add(accountObject);
    }

    public void addPost(Post postObject) {
        Platform.arrOfAllPosts.add(postObject);
    }

    public void addDeletedPost(Post postObject) {
        Platform.arrOfDeletedPosts.add(postObject);
    }

    // TODO: use this class when adding new accounts, new posts, comments,
    // endorsements etc.

    //TEMP: REMOVING FROM ACTIVE ACCOUNT (DUNNO IF THERE IS BETTER WAY TO DO THIS AGAIN CAUSE I DUNNO WHY THIS METHOD ISNT INCLUDED IN THE FIRST PLACE)
    public void removeActiveAccount(Account account)
    {
        activeAccountsArr.remove(account);
    }

}
