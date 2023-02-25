package socialmedia;

//import java.util.ArrayList;

public class Account {
    private int accountID;
    private String handle;
    private String description;
    //private ArrayList<Post> Posts; //used to track which posts the author has for easy deletion (not sure if needed - TODO: double check)

    public int getAccountID() {
        return accountID;
    }

    public String getHandle() {
        return handle;
    }

    public String getDescription() {
        return description;
    }

    public void setAccountID(int newID) {
        accountID = newID;
    }

    public void setHandle(String newHandle) {
        handle = newHandle;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    //TODO: write function to delete account/all posts

}
