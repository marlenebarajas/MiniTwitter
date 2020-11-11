/**
 * Visitor pattern: every button that implements this pattern will get information from our session
 * getTotal() will perform one of the following, depending on the ConcreteVisitor
 * 1) user total - amount of Users in session
 * 2) group total - amount of UserGroups in session
 * 3) message total - amount of tweets(?) in session
 * 4) percentage of positive words - ((positive words) / (words from tweets of everyone in session)) * 100
 * **/
public interface Visitor {
    double visitUser(User user);
    double visitGroup(UserGroup group);
}
