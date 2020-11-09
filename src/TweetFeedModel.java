import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

/**
 * TweetFeed is an observer of many user's tweets.
 */

public class TweetFeedModel extends JPanel implements Observer {
    private static TweetFeedModel single_instance = null;
    private JTextArea feed;
    private User currentUser;

    private TweetFeedModel(){
        this.currentUser = UserView.currentUser;
        currentUser.addObserver(this); //so that when user's feed is updated, this is too
        this.feed = textFeed();
        render();
    }

    public static TweetFeedModel getInstance(){
        if (single_instance == null) {
            synchronized (TweetFeedModel.class) {
                if (single_instance == null) {
                    single_instance = new TweetFeedModel();
                }
            }
        }
        return single_instance;
    }

    private void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Tweet Feed"));
        setLayout(new GridLayout(1,1));
        setMinimumSize(new Dimension(100, 300));

        add(feed);
    }


    private JTextArea textFeed(){
        JTextArea area = new JTextArea("");
        area.setPreferredSize(new Dimension(400,200));
        area.setMinimumSize(new Dimension(400,200));

        ArrayList<Tweet> currentTweets = currentUser.getDisplayFeed().getFeed();

        if(currentTweets != null){
            for(Tweet tweet : currentTweets){
                area.append(String.format("%s: -%s\n", tweet.getAuthor(), tweet.getContent()));
            }
        }
        return area;
    }

    @Override
    public void update(Observable o, Object tweet) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.feed = textFeed(); //update feed, which is updated in User when somebody they follow tweets
        render();
    }
}
