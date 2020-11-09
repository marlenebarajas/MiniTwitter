import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Visitor Pattern - two different trees in our app will have the same methods, but act upon them differently
 * */

public interface TreeModel {
    void render();
    JTree createTree();
    void createNodes(DefaultMutableTreeNode top, UserGroup root);
}
