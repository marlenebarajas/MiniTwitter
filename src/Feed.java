import java.util.ArrayList;
import java.util.Observable;

/***
 * Users observe Feeds, and every User has a Feed.
 * */

public class Feed extends Observable {
    private ArrayList<Tweet> tweets;
    private int tweetTotal;

    public Feed(){
        this.tweets = new ArrayList<>();
        this.tweetTotal = 0;
    }

    public void addToFeed(Tweet tweet){
        this.tweets.add(tweet);
        this.tweetTotal++;
        setChanged();
        notifyObservers(tweet); //notifies users following this feed
    }

    public ArrayList<Tweet> getFeed(){
        return tweets;
    }

    public int getTweetTotal(){
        return tweetTotal;
    }
}
