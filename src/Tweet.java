public class Tweet {
    String author;
    String content;

    public Tweet(String name, String text){
        this.author = name;
        this.content = text;
    }

    public void setAuthor(String name){
        this.author = name;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getAuthor(){
        return author;
    }

    public String getContent(){
        return content;
    }
}
