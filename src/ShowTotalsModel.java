import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * ShowTotalsModel is a component of AdminControlPanel.
 * Holds four buttons, each with a choice to get information about current session.
 */
public class ShowTotalsModel extends JPanel{
    private static ShowTotalsModel single_instance = null;
    private UserGroup root;  //all users in this session
    //components of this panel
    protected JButton showUserTotal, showGroupTotal, showMessagesTotal, showPositivePercentage;

    public ShowTotalsModel(){
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
        userTotal.setActionCommand("userTotal");
        userTotal.setToolTipText("Click this to show total # of users.");
        userTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = String.format("There are %s users in this session.", visitRoot(new UserTotalVisitor()));
                showMessage(message);
            }
        });
        return userTotal;
    }

    private JButton buttonGroupTotal(){
        JButton groupTotal = new JButton("Show User Group Total");
        groupTotal.setActionCommand("groupTotal");
        groupTotal.setToolTipText("Click this to show total # of user groups.");
        groupTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = String.format("There are %s groups in this session.", visitRoot(new GroupTotalVisitor()));
                showMessage(message);
            }
        });
        return groupTotal;
    }

    private JButton buttonMessageTotal(){
        JButton messageTotal = new JButton("Show Messages Total");
        messageTotal.setActionCommand("messagesTotal");
        messageTotal.setToolTipText("Click this to show total # of messages.");
        messageTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = String.format("There are %s messages/tweets in this session.", visitRoot(new MessageTotalVisitor()));
                showMessage(message);
            }
        });
        return messageTotal;
    }

    private JButton buttonPosPercent(){
        JButton posPercent = new JButton("Show Positive Percentage");
        posPercent.setActionCommand("posPercentage");
        posPercent.setToolTipText("Click this to show percentage of positive words.");
        posPercent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double posMssg = visitRoot(new PositiveMessageVisitor());
                double totalMssg = visitRoot(new MessageTotalVisitor());
                double total = (posMssg/totalMssg) * 100;
                String message = String.format("%s%s of messages/tweets in this session are positive.", total, "%");
                showMessage(message);
            }
        });
        return posPercent;
    }

    //pops up JOptionPane with appropriate information displayed, called from button presses
    private void showMessage(String mssg){
        JFrame frame = new JFrame();
        JOptionPane.showMessageDialog(frame,mssg);
    }

    public double visitRoot(Visitor v){
        double total = 0.0;
        for(Account account : root.getGroup()){
            total += account.accept(v);
        }
        return total;
    }
}
