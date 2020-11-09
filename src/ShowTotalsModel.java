import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * ShowTotalsModel is a component of AdminControlPanel.
 * Holds four buttons, each with a choice to get information about current session:
 *  1) user total - amount of Users in session
 *  2) group total - amount of UserGroups in session
 *  3) message total - amount of tweets(?) in session
 *  4) percentage of positive words - ((positive words) / (words from tweets of everyone in session)) * 100
 */
public class ShowTotalsModel extends JPanel{
    private static ShowTotalsModel single_instance = null;
    private UserGroup root;  //all users in this session
    //components of this panel
    protected JButton showUserTotal, showGroupTotal, showMessagesTotal, showPositivePercentage;

    private ShowTotalsModel(){
        this.root = AdminControlPanel.root;
        this.showUserTotal = buttonUserTotal();
        this.showGroupTotal = buttonGroupTotal();
        this.showMessagesTotal = buttonMessageTotal();
        this.showPositivePercentage = buttonPosPercent();
        render();
    }

    public static ShowTotalsModel getInstance(){
        if(single_instance==null){
            synchronized (ShowTotalsModel.class) {
                if (single_instance == null) {
                    single_instance = new ShowTotalsModel();
                }
            }
        }
        return single_instance;
    }

    private void render(){
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Options"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setMinimumSize(new Dimension(100, 100));

        JPanel topButtons = new JPanel();
        topButtons.setLayout(new BoxLayout(topButtons, BoxLayout.LINE_AXIS));

        JPanel btmButtons = new JPanel();
        btmButtons.setLayout(new BoxLayout(btmButtons, BoxLayout.LINE_AXIS));

        //Add components
        topButtons.add(showUserTotal);
        topButtons.add(showGroupTotal);
        btmButtons.add(showMessagesTotal);
        btmButtons.add(showPositivePercentage);
        add(topButtons);
        add(btmButtons);
    }

    private JButton buttonUserTotal(){
        JButton userTotal = new JButton("Show User Total");
        userTotal.setMnemonic(KeyEvent.VK_U); //ctrl-U to show user totals
        userTotal.setActionCommand("userTotal");
        userTotal.setToolTipText("Click this to show total # of users.");
        userTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userTotal = root.getUserTotal(); //grabs total amount of Users in session
                String message = String.format("There are %o users in this session.", userTotal);
                showMessage(message);
            }
        });
        return userTotal;
    }

    private JButton buttonGroupTotal(){
        JButton groupTotal = new JButton("Show User Group Total");
        groupTotal.setMnemonic(KeyEvent.VK_G); //ctrl-g to show user group totals (THIS DOESNT WORK?)
        groupTotal.setActionCommand("groupTotal");
        groupTotal.setToolTipText("Click this to show total # of user groups.");
        groupTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int groupTotal = root.getGroupTotal(); //grabs total amount of UserGroups in session
                String message = String.format("There are %o groups in this session.", groupTotal);
                showMessage(message);
            }
        });
        return groupTotal;
    }

    private JButton buttonMessageTotal(){
        JButton messageTotal = new JButton("Show Messages Total");
        messageTotal.setMnemonic(KeyEvent.VK_M); //ctrl-m to show message totals
        messageTotal.setActionCommand("messagesTotal");
        messageTotal.setToolTipText("Click this to show total # of messages.");
        messageTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mssgTotal = getMessageTotal(root); //grabs total amount of UserGroups in session
                String message = String.format("There are %o messages/tweets in this session.", mssgTotal);
                showMessage(message);
            }
        });
        return messageTotal;
    }

    private JButton buttonPosPercent(){
        JButton posPercent = new JButton("Show Positive Percentage");
        posPercent.setMnemonic(KeyEvent.VK_P); //ctrl-p to show positive percentage
        posPercent.setActionCommand("posPercentage");
        posPercent.setToolTipText("Click this to show percentage of positive words.");
        posPercent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        return posPercent;
    }

    //pops up JOptionPane with appropriate information displayed, called from button presses
    private void showMessage(String mssg){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,mssg);
    }

    //starting at root, go through sum all user's tweets
    private int getMessageTotal(UserGroup group){
        int n = 0;
        List<Entity> users = group.getGroup();
        if(users!=null){
            for(Entity user : users){
                if(user instanceof UserGroup){
                    n += getMessageTotal((UserGroup) user);
                }
                else{
                    n += ((User) user).getTweets().getTweetTotal();
                }
            }
        }
        return n;
    }
}
