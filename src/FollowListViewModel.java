import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

//access to UserView.var
public class FollowListViewModel extends JPanel implements TreeModel, Observer {
    private JTree userList;
    private UserGroup following;

    public FollowListViewModel(){
        this.following = UserView.currentUser.getFollowing();
        this.userList = createTree();
        following.addObserver(this); //updates view when user follows somebody new
        render();
    }

    public void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Following"));
        setLayout(new GridLayout(1,2));
        setPreferredSize(new Dimension(300, 300));
        setMinimumSize(new Dimension(300, 300));
        add(userList);
    }

    public JTree createTree(){
        //Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(following);
        createNodes(top, following);
        //Create a tree that allows one selection at a time.
        JTree tree = new JTree(top);
        return tree;
    }

    public void createNodes(DefaultMutableTreeNode top, UserGroup root) {
        DefaultMutableTreeNode name = null;
        for(Entity entity : root.getGroup()){
            if(entity instanceof User){ //everything in group should be a User
                name = new DefaultMutableTreeNode(entity);
                top.add(name);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.userList = createTree();
        this.removeAll();
        this.revalidate();
        this.repaint();
        render();
    }
}
