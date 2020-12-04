public interface Account {
    int getId(); //Users and UserGroups have IDs.
    void setGroupId(int id); //Users and UserGroups can be in UserGroups, which have an id.
    int getGroupId();
    String getName(); // Users and UserGroups have names.
    void setName(String name); // Users and UserGroups get named.
    void setCreationTime(long time);
    long getCreationTime();
    String toString(); //Users and UserGroups will need this method to be displayed.
    Account findAccount(int id);
    boolean isUser();
    boolean isGroup();
    double accept(Visitor v);
}
