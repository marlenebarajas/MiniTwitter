import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * SelectUserModel is a component of AdminControlPanel.
 * Holds a TreeModel that displays all users in current session.
 * Observes UserGroup root so that SelectUserModel updates every time an User/UserGroup to the session.
 * **/
public class SelectUserModel extends JPanel implements Observer {
    private static SelectUserModel single_instance = null;
    private UserGroup root; //all users in this session
    //component of this panel
    private JTree userList;
    private JButton userViewButton;
    private Account selectedUser = null;

    private SelectUserModel(){
        this.root = AdminControlPanel.root;
        this.userList = createTree();
        this.userViewButton = buttonUserView();
        root.addObserver(this); //observes this sessions's users, to update display of users
        render();
    }

    public static SelectUserModel getInstance() {
        if (single_instance == null) {
            synchronized (SelectUserModel.class) {
                if (single_instance == null) {
                    single_instance = new SelectUserModel();
                }
            }
        }
        return single_instance;
    }

    public void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "User List"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setMinimumSize(new Dimension(100, 300));

        //USER LIST SCROLL PANE
        JPanel treePane = new JPanel();
        JScrollPane treeView = new JScrollPane(userList);
        treePane.setLayout(new BoxLayout(treePane, BoxLayout.PAGE_AXIS));
        treePane.add(treeView);
        //BUTTON PANEL
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(userViewButton);

        add(treePane);
        add(buttonPane);
    }

    private JButton buttonUserView(){
        JButton userView = new JButton("Open User View");
        userView.setMnemonic(KeyEvent.VK_V); //ctrl-v to show user view
        userView.setActionCommand("userView");
        userView.setToolTipText("Click to open the highlighted user's view.");
        userView.addActionListener(new ActionListener() { //add action listener
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedUser.isUser()) new UserView((User) selectedUser);
            }
        });
        return userView;
    }

    public JTree createTree(){
        //Create the nodes.
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(AdminControlPanel.root);
        createNodes(top, AdminControlPanel.root);
        //Create a tree that allows one selection at a time.
        JTree tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //Listen for when the selection changes
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if(node!=null){//if something is selected
                    Account nodeObject = (Account) node.getUserObject();
                    selectedUser = nodeObject;
                }
            }
        });
        return tree;
    }

    public void createNodes(DefaultMutableTreeNode top, Account root) {
        DefaultMutableTreeNode group = null;
        DefaultMutableTreeNode name = null;
        if(root.isGroup()){
            for(Account acc : ((UserGroup)root).getGroup()){
                if(acc.isGroup()){
                    group = new DefaultMutableTreeNode(acc);
                    top.add(group);
                    createNodes(group, acc); //recursively run through this UserGroup
                } else{ //else, this user is already in current group
                    name = new DefaultMutableTreeNode(acc);
                    top.add(name);
                }
            }
        } else{ //case: UserGroup that has no Users added to it yet
            name = new DefaultMutableTreeNode(root);
            top.add(name);
        }
    }

    //Recreate the tree with new users
    @Override
    public void update(Observable o, Object arg) {
        this.userList = createTree();
        this.removeAll();
        this.revalidate();
        this.repaint();
        render();
    }
}
