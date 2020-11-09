import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostTweetModel extends JPanel {
    private JTextField tweet;
    private JButton post;
    private User currentUser;

    public PostTweetModel(){
        this.tweet = fieldTweet();
        this.post = buttonPostTweet();
        this.currentUser = UserView.currentUser;
        render();
    }

    private void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Post Tweet"));
        setLayout(new GridLayout(2, 1));
        setPreferredSize(new Dimension(300,200));

        add(tweet);
        add(post);
    }

    private JTextField fieldTweet(){
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(250,50));
        input.setMaximumSize(new Dimension(500, 50));
        input.setMinimumSize(new Dimension(300, 50));
        return input;
    }

    private JButton buttonPostTweet(){
        JButton post = new JButton("Post Tweet");
        post.setSize(new Dimension(100,50));
        post.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tweet newTweet = new Tweet(currentUser.getName(), tweet.getText());
                currentUser.postTweet(newTweet);
            }
        });
        return post;
    }
}
