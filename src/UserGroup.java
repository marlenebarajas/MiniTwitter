import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *  UserGroup is observable by SelectUserModel and UserListModel.
 *  SelectUserModel - used to update the client's master user list
 *  UserListModel - used to update a unique user's UserGroup of users they follow
 *  */

public class UserGroup extends Observable implements Entity {
    private int id;
    private int userTotal, groupTotal;
    private String name;
    private List<Entity> users; //users in this group

    public UserGroup(){
        this.users = new ArrayList<>();
        this.groupTotal = 0;
        //no id, used for "hidden" usergroups that hold a user's followers/following
    }

    public UserGroup(int id){
        this.users = new ArrayList<>();
        this.id = id;
        this.groupTotal = 0;
    }

    public UserGroup(int id, String name){
        this.users = new ArrayList<>();
        this.id = id;
        this.groupTotal = 0;
        this.name = name;
    }

    public void addUser(Entity user){
        this.users.add(user);
        if(user instanceof User) this.userTotal++;
        else this.groupTotal++;
        setChanged();
        notifyObservers(); //this will call update() for SelectUserModel and ListViewModel, to include new user
    }

    public void removeUser(Entity user){
        this.users.remove(user);
        if(user instanceof User) this.userTotal--;
        else this.groupTotal--;
        setChanged();
        notifyObservers(); //this will call update() for SelectUserModel and ListViewModel, to not include old user
    }

    public List<Entity> getGroup(){
        return users;
    }

    public int getUserTotal(){
        return userTotal;
    }

    public int getGroupTotal(){
        return groupTotal;
    }

    public User findUser(int id){
        for(Entity entity : getGroup()){
            if(entity instanceof UserGroup){
                User found = ((UserGroup) entity).findUser(id);
                if(found!=null) return found;
            }
            else if(entity.getId()==id && entity instanceof User){
                return (User) entity;
            }
        }
        return null; //if other return statement isn't found, no user to be found
    }

    public UserGroup getInnerGroup(int id){
        for(Entity entity : getGroup()){
            if(entity instanceof UserGroup){
               if(entity.getId() == id) return (UserGroup) entity;
               else{
                   UserGroup found = ((UserGroup) entity).getInnerGroup(id);
                   if(found!=null) return found;
               }
            }
        }
        return null; //if other return statement isn't found, no user to be found
    }

    public UserGroup findGroup(User user){
        for(Entity entity : getGroup()){ //searching through current group
            if(entity instanceof User){
                if(entity.getId()==user.getId()){ //if user exists in this group
                    return this;
                }
            } else{ //search inner group
                ((UserGroup) entity).findGroup(user);
            }
        }
        return null; //if other return statement isn't found, no group to be found
    }

    //to manually call an update to observers
    public void update(){
        setChanged();
        notifyObservers();
    }

    @Override
    public void setId(int id){
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
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
    public String toString() {
        if(getId()==0){
            return "Root";
        } else{
            return String.format("%s (group id: %d)", name, getId());
        }
    }

}
