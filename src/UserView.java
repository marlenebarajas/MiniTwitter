import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserView {
    static User currentUser;
    JPanel topPanel, midTopPanel, midBtmPanel, btmPanel;

    public UserView(){
        currentUser = null;
        renderError();
    }

    public UserView(User user){
        currentUser = user;
        render();
    }

    private void render() {
        //Formatting UserView's header
        SimpleDateFormat title = new SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss z");
        Date date = new Date(currentUser.getCreationTime());

        //Create and set up the window.
        JFrame frame = new JFrame(String.format("%s Creation Time: %s",currentUser, title.format(date)));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800,550));
        frame.setPreferredSize(new Dimension(800, 550));
        frame.setLayout(new FlowLayout());

        //Add content to the window.
        frame.add(new FollowListViewModel());
        frame.add(new FollowUserModel());
        frame.add(new JoinGroupModel());
        frame.add(new TweetFeedModel());
        frame.add(new PostTweetModel());
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
