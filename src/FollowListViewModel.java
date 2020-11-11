import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class FollowListViewModel extends JPanel implements Observer {
    private User currentUser;
    private JTextArea followList;
    private ArrayList<Account> following;

    public FollowListViewModel(){
        this.currentUser = UserView.currentUser;
        currentUser.addObserver(this);
        render();
    }

    public void render(){
        following = currentUser.getFollowing();
        followList = textFollowList();
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Following"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(600, 200));
        add(followList);
    }

    private JTextArea textFollowList(){
        JTextArea area = new JTextArea(10,35);
        area.setEditable(false);
        area.setPreferredSize(new Dimension(350,100));
        if(following != null){
            for(Account user : following){
                area.append(String.format("- %s\n", user.getName()));
            }
        }
        return area;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.removeAll();
        this.revalidate();
        this.repaint();
        render();
    }
}
