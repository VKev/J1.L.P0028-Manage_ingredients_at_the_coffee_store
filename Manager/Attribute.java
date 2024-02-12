
package Manager;

import Utils.Regex;
import java.io.Serializable;

public class Attribute implements Serializable{
    private String name;
    private Object value;
    private String regex = Regex.ALL;
    
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
    
    public void SetRegex(String regex){
        this.regex = regex;
    }
    
    public String GetRegex(){
        return this.regex;
    }
}
