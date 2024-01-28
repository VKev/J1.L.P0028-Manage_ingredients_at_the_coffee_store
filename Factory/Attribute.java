
package Factory;

public class Attribute {
    private String name;
    private Object value;
    
    public Attribute(String name, Object value){
        this.name = name;
        this.value = value;
    }
    
    public String GetName(){
        return name;
    }
    public Object GetValue(){
        return value;
    } 
    
    public void SetValue(Object value){
        this.value = value;
    }
    
    public void SetName(String name){
        this.name = name;
    }
}
