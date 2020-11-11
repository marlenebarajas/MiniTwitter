import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * User observes other Users, UserView observes a User
 */
public class User extends Observable implements Observer, Account {
    private int id; //user's unique ID
    private int groupId; //id of group user is in
    private String name;

    private ArrayList<String> personalFeed; //feed of this user's tweets
    private ArrayList<String> displayFeed; //feed updated with this user + following user's tweets
    private ArrayList<Account> following;//who this user follows

    public User(int id){
        this.id = id;
        this.personalFeed = new ArrayList<>();
        this.displayFeed = new ArrayList<>();
        this.following = new ArrayList<>();
        addObserver(this); //user observes their own feed
    }

    public void followUser(User user){
        this.following.add(user); //add to this user's following list
        user.addObserver(this); //this user's feed will get updates from who they follow
        setChanged();
        notifyObservers();
    }

    public boolean isFollowing(int id){
        for(Account following: following){
            if(following.getId() == id) return true;
        } return false;
    }

    public ArrayList<Account> getFollowing(){
        return following;
    }

    public void postTweet(String tweet){
        String formattedTweet = String.format("%s: -%s\n", getName(), tweet);
        this.personalFeed.add(formattedTweet);
        //update users who follow this feed
        setChanged();
        notifyObservers(formattedTweet);
    }

    public void addToDisplay(String tweet){
        this.displayFeed.add(tweet);
        setChanged();
        notifyObservers();
    }

    public ArrayList<String> getDisplayFeed(){
        return displayFeed;
    }

    public ArrayList<String> getTweets(){
        return personalFeed;
    }

    @Override
    public Account findAccount(int id){
        if(id==this.id) return this;
        else return null; //if this is not the account
    }

    @Override
    public boolean isUser(){
        return true;
    }

    @Override
    public boolean isGroup(){
        return false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(int id) {
        this.groupId = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        if(getId()==0){
            return "Root";
        } else{
            return name + String.format("(user id: %d)", getId());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg!=null){
            addToDisplay((String) arg); //user's own displayFeed + user's followers' displayFeeds
        }
    }

    @Override
    public double accept(Visitor v){
        return v.visitUser(this);
    }
}