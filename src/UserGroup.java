import java.util.ArrayList;
import java.util.Observable;

/**
 *  UserGroup is observable by SelectUserModel and UserListModel.
 *  SelectUserModel - used to update the client's root user list
 *  */

public class UserGroup extends Observable implements Account {
    private int id;
    private int groupId;
    private String name;
    private long creationTime; //time that the group was created
    private ArrayList<Account> accounts; //all accounts in this group

    public UserGroup(){
        this.accounts = new ArrayList<>();
    }

    public UserGroup(int id){
        this.accounts = new ArrayList<>();
        this.id = id;
    }

    public void addAccount(Account acc){
        this.accounts.add(acc);
        acc.setGroupId(this.getId());
        setChanged();
        notifyObservers();
    }

    public void removeAccount(Account acc){
        this.accounts.remove(acc);
    }

    //manually call an update to this group, used for AdminControlPanel to update whenever any group changes
    public void update(){
        setChanged();
        notifyObservers();
    }

    public ArrayList<Account> getGroup(){
        return accounts;
    }

    @Override
    public Account findAccount(int id){
        Account search = null;
        if(id==this.id) search = this;
        else{
            for(Account acc: accounts){
                if(acc.findAccount(id)!=null) search = acc.findAccount(id);
            }
        }
        return search;
    }

    @Override
    public boolean isUser(){
        return false;
    }

    @Override
    public boolean isGroup(){
        return true;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    @Override
    public void setGroupId(int id) {
        this.groupId = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public void setCreationTime(long time){
        this.creationTime = time;
    }

    @Override
    public long getCreationTime(){
        return creationTime;
    }

    @Override
    public String toString() {
        if(getId()==0){
            return "Root";
        } else{
            return String.format("Group: %s (id: %d)", name, getId());
        }
    }

    @Override
    public double accept(Visitor v){
        return v.visitGroup(this);
    }
}