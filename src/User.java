import java.util.Observable;
import java.util.Observer;

/**
 * UserTweets is observed by other user's TweetFeed, if other user is following this user.
 * User observes Feeds, UserView observes Users
 */
public class User extends Observable implements Observer, Entity {
    // static variable single_instance of type Singleton
    private int id; //user's unique ID
    private String name;
    private boolean inGroup;
    private UserGroup followers; //followers of this user
    private UserGroup following; //users this user follows
    private Feed feed; //this user's personal feed
    private Feed displayFeed; //feed updated with this user + following user's tweets

    public User(int id){
        this.id = id;
        this.inGroup = false;
        this.followers = new UserGroup();
        this.following = new UserGroup();
        this.feed = new Feed();
        this.feed.addObserver(this); //a user observes their own tweets
        this.displayFeed = new Feed();
    }

    public void setInGroup(boolean tf){
        this.inGroup = tf;
    }

    public boolean inGroup(){
        return inGroup;
    }

    public UserGroup getFollowers(){
        return followers;
    }

    public UserGroup getFollowing(){
        return following;
    }

    public void followUser(User user){
        this.following.addUser(user); //add to this user's following list
        (user.getTweets()).addObserver(this); //this user's feed will get updates from who they follow
    }

    public Feed getTweets(){
        return feed;
    }

    public void postTweet(Tweet tweet){
        feed.addToFeed(tweet);
    }

    public Feed getDisplayFeed(){
        return displayFeed;
    }

    @Override
    public void setId(int id){
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
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
        this.displayFeed.addToFeed((Tweet) arg);
        setChanged();
        notifyObservers();
    }
}
