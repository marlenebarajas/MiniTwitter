import java.util.ArrayList;

public class PositiveMessageVisitor implements Visitor {
    private String[] posWords = { "good", "happy", "nice", "great", "amazing",  //words that will flag a message as positive
            "glad", "marvelous", "love", "positive", "wonderful" };

    @Override
    public double visitUser(User user) {
        double totalPos = 0.0;
        ArrayList<String> userTweets = user.getTweets();
        for(String tweet : userTweets){
            for(String pos : posWords){
                if(tweet.contains(pos)){
                    totalPos++; //consider this tweet a positive message
                    break; //this tweet is already considered positive, dont need to check for more positive words
                }
            }
        }
        return totalPos;
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
