public class GroupTotalVisitor implements Visitor {
    @Override
    public double visitUser(User user) {
        return 0.0;
    }
    @Override
    public double visitGroup(UserGroup group) {
        return 1.0;
    }
}
