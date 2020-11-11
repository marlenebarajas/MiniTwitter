public class MessageTotalVisitor implements Visitor{
    @Override
    public double visitUser(User user) {
        return user.getTweets().size();
    }
    @Override
    public double visitGroup(UserGroup group) {
        double total = 0.0;
        for(Account acc : group.getGroup()){
            total+=acc.accept(this);
        }
        return total;
    }
}
