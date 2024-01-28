package Factory;

import Utils.CustomInput;
import java.util.ArrayList;


public abstract class Candidate {
    protected ArrayList<Attribute> attributes = new ArrayList<>();
    
    
    public void Input(){
        CustomInput input = new CustomInput(); 
        for(Attribute attribute : attributes){
            attribute.SetValue(input.GetLine("Enter " +attribute.GetName()+": " ));
        }
    };
    
    
    public abstract String GetId();
    public abstract String ToString();
    public abstract String AttributeTypeToString();
    
    public Attribute GetAttribute(int index){
        return attributes.get(index);
    }
    public String GetAttributeValueString(int index){
        return attributes.get(index).GetValue().toString();
    }
    
    public void AddAttribute(String name, Object value){
        if(FindAttributeIndexByName(name) == -1)
            attributes.add( new Attribute(name, value));
    }
    public int FindAttributeIndexByName(String name){
        for(int i=0; i< attributes.size(); i++){
            if( attributes.get(i).GetName().equals(name) ){
                return i;
            }
         }
        return -1;
    }
}
