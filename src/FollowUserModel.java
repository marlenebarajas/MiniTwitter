import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 * Model for inputting a UserId to follow a user.
 */
public class FollowUserModel extends JPanel {
    private JTextField idInput;
    private JButton followUser;
    private User currentUser;
    private UserGroup root;

    public FollowUserModel(){
        this.currentUser = UserView.currentUser;
        this.root = AdminControlPanel.root;
        this.followUser = buttonFollow();
        this.idInput = followField();
        render();
    }

    private void render() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Follow User"));
        setLayout(new GridLayout(1, 2));
        setPreferredSize(new Dimension(300,50));

        add(idInput);
        add(followUser);
    }

    private JButton buttonFollow(){
        JButton follow = new JButton("Follow User");
        follow.setSize(new Dimension(100,50));
        follow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                follow(idInput.getText());
            }
        });
        return follow;
    }

    private JTextField followField(){
        JTextField follow = new JTextField("Enter User Id");
        return follow;
    }

    private void follow(String input){
        int inputVal = 0;
        if(input!=null){
            try {
                inputVal = Integer.parseInt(input);
            } catch(NumberFormatException e){
                userNotFound();
            }
            User foundUser = root.findUser(inputVal); //find user they want to follow

            if(foundUser!=null){ //case: user exists and was found
                currentUser.followUser(foundUser);
                //update observers of this group?
            }else{ //case: user does not exist
                userNotFound();
            }
        } else{ //case: user input was not correct
            userNotFound();
        }
    }

    private void userNotFound(){
        JFrame error = new JFrame();
        error.setPreferredSize(new Dimension(400,100));
        error.setLayout(new FlowLayout());
        error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel errorMssg1 = new JLabel("Error: No User with such ID was found.");
        JLabel errorMssg2 = new JLabel("Please input a valid ID and try again.");

        //Add content to the window.
        error.add(errorMssg1);
        error.add(errorMssg2);

        //Display the window.
        error.pack();
        error.setVisible(true);
    }
}
