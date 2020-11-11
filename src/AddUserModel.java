import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * AddUserModel is a component of AdminControlPanel.
 * Holds two pairs of field+button for registering entities to main session:
 * 1) User name field + add User button - adds a unique User to session, assigned a random id
 * 2) UserGroup name field + add UserGroup - adds a unique UserGroup to session, assigned a random id
 * **/
public class AddUserModel extends JPanel{
    private static AddUserModel single_instance = null;
    private UserGroup root; //all users in this session
    //components of this panel
    private JTextField nameInput, groupInput;
    private JButton addUser, addGroup;

    private AddUserModel(){
        this.root = AdminControlPanel.root;
        this.nameInput = nameField();
        this.groupInput = groupField();
        this.addUser = buttonAddUser();
        this.addGroup = buttonAddGroup();
        render();
    }

    public static AddUserModel getInstance(){
        if (single_instance == null) {
            synchronized (AddUserModel.class) {
                if (single_instance == null) {
                    single_instance = new AddUserModel();
                }
            }
        }
        return single_instance;
    }

    private void render() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Add New User or User Group"));
        setLayout(new GridLayout(2, 1));
        setPreferredSize(new Dimension(300,200));

        JPanel topField = new JPanel();
        JPanel btmField = new JPanel();

        topField.add(nameInput);
        topField.add(addUser);
        btmField.add(groupInput);
        btmField.add(addGroup);
        add(topField);
        add(btmField);
    }

    private JTextField nameField(){
        JTextField name = new JTextField("Enter User Name");
        name.setPreferredSize(new Dimension(250,50));
        name.setMaximumSize(new Dimension(500, 50));
        name.setMinimumSize(new Dimension(300, 50));
        return name;
    }

    private JTextField groupField(){
        JTextField group = new JTextField("Enter Group Name");
        group.setPreferredSize(new Dimension(250,50));
        group.setMaximumSize(new Dimension(500, 50));
        group.setMinimumSize(new Dimension(300, 50));
        return group;
    }

    private JButton buttonAddUser(){
        JButton addUser = new JButton("Add User");
        addUser.setSize(new Dimension(100,50));
        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser(nameInput.getText());
            }
        });
        return addUser;
    }

    private JButton buttonAddGroup(){
        JButton addGroup = new JButton("Add Group");
        addGroup.setSize(new Dimension(100,50));
        addGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerGroup(groupInput.getText());
            }
        });
        return addGroup;
    }

    private void registerUser(String name){
        Random rand = new Random();
        int id = 0;
        Account user = null;
        do{
            id = rand.nextInt(9000); //random id chosen
            user = root.findAccount(id); //user = null if id is NOT take
        } while(user!=null); // if user!=null, we need to generate new id

        user = new User(id); //id is not taken bc of previous check
        user.setName(name);
        root.addAccount(user);
    }

    private void registerGroup(String name){
        Random rand = new Random();
        int id = 0;
        Account group = null;
        do{
            id = rand.nextInt(9000); //random id chosen
            group = root.findAccount(id); //group = null if id is NOT take
        } while(group!=null); // if group!=null, we need to generate new id

        group = new UserGroup(id); //id is not taken bc of previous check
        group.setName(name);
        root.addAccount(group);
    }

}
