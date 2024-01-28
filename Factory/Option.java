
package Factory;

public class Option {
    private Runnable action;
    private String content;
    
    public Option(String content, Runnable action){
        this.action = action;
        this.content = content;
    }
    
    public void Execute(){
        if(action != null)
            action.run();
    }
    public void ShowContent(){
        System.out.println(content);
    }
}
