import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

//access to UserView.var
public class FollowListViewModel extends JPanel implements TreeModel, Observer {
    private static FollowListViewModel single_instance = null;
    private JTree userList;
    private UserGroup following;

    private FollowListViewModel(){
        this.following = UserView.currentUser.getFollowing();
        this.userList = createTree();
        following.addObserver(this); //updates view when user follows somebody new
        render();
    }

    public static FollowListViewModel getInstance(){
        if (single_instance == null) {
            synchronized (FollowListViewModel.class) {
                if (single_instance == null) {
                    single_instance = new FollowListViewModel();
                }
            }
        }
        return single_instance;
    }

    public void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Following"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(700, 200));
        //SCROLL PANE
        JPanel treePane = new JPanel();
        JScrollPane treeView = new JScrollPane(userList);
        treePane.setLayout(new BoxLayout(treePane, BoxLayout.LINE_AXIS));
        treePane.add(treeView);
        add(treePane);
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
