import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;
import java.util.Observable;

/**
 * TweetFeed is an observer of many user's tweets.
 */

public class TweetFeedModel extends JPanel implements Observer {
    private JScrollPane feed;
    private JLabel lastUpdate;
    private User currentUser;

    public TweetFeedModel(){
        this.currentUser = UserView.currentUser;
        currentUser.addObserver(this); //so that when user's feed is updated, this is too
        this.feed = textFeed();
        this.lastUpdate = lastUpdate();
        render();
    }

    private void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Tweet Feed"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(new Dimension(100, 300));
        add(feed);
        add(lastUpdate); // displays the last update under the TweetFeed
    }

    private JScrollPane textFeed(){
        JTextArea area = new JTextArea(10,35);
        area.setEditable(false);
        area.setPreferredSize(new Dimension(100,200));
        ArrayList<String> currentTweets = currentUser.getDisplayFeed();
        if(currentTweets != null){
            for(String tweet : currentTweets){
                area.append(tweet);
            }
        }
        JScrollPane tweetView = new JScrollPane(area);
        setLayout(new BoxLayout(tweetView, BoxLayout.LINE_AXIS));
        tweetView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return tweetView;
    }

    private JLabel lastUpdate(){
        SimpleDateFormat title = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z");
        Date date = new Date(currentUser.getLastUpdateTime());
        return new JLabel(String.format("Last Update: %s", title.format(date)));
    }

    //called when user's displayFeed is updated
    @Override
    public void update(Observable o, Object tweet) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.feed = textFeed(); //update feed, which is updated in User when somebody they follow tweets
        currentUser.setLastUpdateTime(System.currentTimeMillis()); //this user's feed is now updated
        this.lastUpdate = lastUpdate();
        render();
    }
}

