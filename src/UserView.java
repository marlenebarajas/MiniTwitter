import javax.swing.*;
import java.awt.*;

public class UserView {
    static User currentUser;
    static JPanel topPanel, midTopPanel, midBtmPanel, btmPanel;

    public UserView(){
        currentUser = null;
        renderError();
    }

    public UserView(User user){
        currentUser = user;
        topPanel = topPanel();
        midTopPanel = midTopPanel();
        midBtmPanel = midBtmPanel();
        btmPanel = btmPanel();
        render();
    }

    private static void render() {
        //Create and set up the window.
        JFrame frame = new JFrame(String.format("User View: %s",currentUser));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800,700));
        frame.setPreferredSize(new Dimension(800, 750));
        frame.setLayout(new FlowLayout());

        //Add content to the window.
        frame.add(topPanel);
        frame.add(midTopPanel);
        frame.add(midBtmPanel);
        frame.add(btmPanel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel topPanel(){
        JPanel panel = new JPanel();
        panel.add(FollowListViewModel.getInstance());
        return panel;
    }

    private static JPanel midTopPanel(){
        JPanel panel = new JPanel();
        panel.add(TweetFeedModel.getInstance());
        panel.add(PostTweetModel.getInstance());
        return panel;
    }

    private static JPanel midBtmPanel(){
        JPanel panel = new JPanel();
        panel.add(FollowUserModel.getInstance());
        return panel;
    }

    private static JPanel btmPanel(){
        JPanel panel = new JPanel();
        panel.add(JoinGroupModel.getInstance());
        return panel;
    }

    private static void renderError(){
        //Create and set up the window.
        JFrame frame = new JFrame("User View");
        frame.setPreferredSize(new Dimension(400,100));
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel errorMssg1 = new JLabel("Error: Either no User was selected or a User Group was selected.");
        JLabel errorMssg2 = new JLabel("Please select a User and try again.");
        //Add content to the window.
        frame.add(errorMssg1);
        frame.add(errorMssg2);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
