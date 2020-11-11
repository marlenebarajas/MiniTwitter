import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinGroupModel extends JPanel {
    private JTextField idInput;
    private JButton joinGroup;
    private User currentUser;
    private UserGroup root;

    public JoinGroupModel(){
        this.currentUser = UserView.currentUser;
        this.root = AdminControlPanel.root;
        this.joinGroup = buttonJoin();
        this.idInput = fieldJoin();
        render();
    }

    private void render() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Join Group"));
        setLayout(new GridLayout(1, 2));
        setPreferredSize(new Dimension(300,50));
        add(idInput);
        add(joinGroup);
    }

    private JButton buttonJoin(){
        JButton follow = new JButton("Join Group");
        follow.setSize(new Dimension(100,50));
        follow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    join(idInput.getText());
                }catch(NullPointerException n){
                    errorJoining(false);
                }
            }
        });
        return follow;
    }

    private JTextField fieldJoin(){
        JTextField follow = new JTextField("Enter Group Id");
        return follow;
    }

    private void join(String input){
        int groupid = 0;
        if(input!=null){
            try {
                groupid = Integer.parseInt(input);
            } catch(NumberFormatException | NullPointerException e){
                errorJoining(false);
                return;
            }
            Account newGroup = root.findAccount(groupid);
            if(newGroup.isGroup()) { //case: group exists and was found
                //oldGroup is showing up as null when currentUser is in a group for some reason
                int currentGroup = currentUser.getGroupId();
                if(currentGroup==0){ //if currentUser is in root, they can join new group easily
                    root.removeAccount(currentUser);
                    ((UserGroup) newGroup).addAccount(currentUser);
                    root.update(); // to update user tree in AdminControlPanel
                }
                else if(leaveGroup()){ //currentUser is in another group, need to know if they want to leave oldGroup
                    Account oldGroup = root.findAccount(currentGroup); //find oldGroup currentUser is in
                    ((UserGroup)oldGroup).removeAccount(currentUser);
                    ((UserGroup) newGroup).addAccount(currentUser);
                    root.update(); // to update user tree in AdminControlPanel
                }
                else{ //case: user did not want to leave oldGroup, despite asking to join newGroup
                    errorJoining(true);
                }
            } else{
                errorJoining(false);
            }
        }
    }

    private boolean leaveGroup(){
        JFrame frame = new JFrame();
        int answer = JOptionPane.showConfirmDialog(frame, "You are already in a group. Would you like to leave your current group?", "Please Choose One" ,JOptionPane.YES_NO_CANCEL_OPTION);
        if(answer==JOptionPane.YES_OPTION){ //user will leave group for new group
            return true;
        } else{ //user doesn't want to leave group
            return false;
        }
    }

    //true: error joining because user did not want to leave current group
    //false: error joining because group was not found
    private void errorJoining(boolean tf){
        JLabel errorMssg1, errorMssg2;

        JFrame error = new JFrame();
        error.setPreferredSize(new Dimension(400,100));
        error.setLayout(new FlowLayout());
        error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if(tf) {
            errorMssg1 = new JLabel("Error: You are already in a group.");
            errorMssg2 = new JLabel("You have to leave current group to join a new group.");
        } else{
            errorMssg1 = new JLabel("Error: No Group with such ID was found.");
            errorMssg2 = new JLabel("Please input a valid ID and try again.");
        }
        //Add content to the window.
        error.add(errorMssg1);
        error.add(errorMssg2);
        //Display the window.
        error.pack();
        error.setVisible(true);
    }

}
