import javax.swing.*;
import java.awt.*;

public class UserView {
    static User currentUser;

    public UserView(){
        currentUser = null;
        renderError();
    }

    public UserView(User user){
        currentUser = user;
        render();
    }

    private static void render() {
        //Create and set up the window.
        JFrame frame = new JFrame("User View");
        frame.setMinimumSize(new Dimension(800,400));
        frame.setPreferredSize(new Dimension(800, 400));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        //Add content to the window.
        frame.add(new FollowListViewModel());
        frame.add(new FollowUserModel());
        frame.add(new JoinGroupModel());
        frame.add(new PostTweetModel());
        frame.add(new TweetFeedModel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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
