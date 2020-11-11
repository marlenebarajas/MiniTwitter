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
    private JTextArea feed;
    private User currentUser;

    public TweetFeedModel(){
        this.currentUser = UserView.currentUser;
        currentUser.addObserver(this); //so that when user's feed is updated, this is too
        this.feed = textFeed();
        render();
    }

    private void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Tweet Feed"));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setMinimumSize(new Dimension(100, 300));
        //SCROLL PANE
        JScrollPane tweetView = new JScrollPane(feed);
        tweetView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(tweetView);
    }


    private JTextArea textFeed(){
        JTextArea area = new JTextArea(10,35);
        area.setEditable(false);
        area.setPreferredSize(new Dimension(400,300));
        ArrayList<String> currentTweets = currentUser.getDisplayFeed();
        if(currentTweets != null){
            for(String tweet : currentTweets){
                area.append(tweet);
            }
        }
        return area;
    }

    //called when user's displayFeed is updated
    @Override
    public void update(Observable o, Object tweet) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        this.feed = textFeed(); //update feed, which is updated in User when somebody they follow tweets
        render();
    }
}

