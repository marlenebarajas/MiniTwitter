import javax.swing.*;
import java.awt.*;

/**
 * AdminControlPanel renders when Driver is executed.
 */
public class AdminControlPanel{
    private static AdminControlPanel single_instance = null;
    static UserGroup root = new UserGroup(0, "Root"); //master user group- holds all users in session

    private AdminControlPanel() {
        render();
    }

    public static AdminControlPanel getInstance() {
        if (single_instance == null) {
            synchronized (AdminControlPanel.class) {
                if (single_instance == null) {
                    single_instance = new AdminControlPanel();
                }
            }
        }
        return single_instance;
    }

    private static void render() {
        //Create and set up the window.
        JFrame frame = new JFrame("Admin Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800,400));
        frame.setPreferredSize(new Dimension(800,400));
        frame.setLayout(new GridLayout(2, 2));

        //Add content to the window.
        frame.add(SelectUserModel.getInstance());
        frame.add(AddUserModel.getInstance());
        frame.add(ShowTotalsModel.getInstance());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
