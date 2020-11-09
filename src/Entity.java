public interface Entity  {
    public int getId(); //Users and User Groups have IDs.
    public void setId(int id); //Users and User Groups have IDs.
    public String getName(); // Users and User Groups have names.
    public void setName(String name); // Users and User Groups get named.
    public String toString(); //Users and User Groups will need this method to be displayed.
}
