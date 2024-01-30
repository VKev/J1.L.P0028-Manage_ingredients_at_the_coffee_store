package Factory;

import Utils.CustomInput;
import Utils.Regex;
import java.util.ArrayList;


public abstract class Candidate {
    protected ArrayList<Attribute> attributes = new ArrayList<>();
    
    
    public void Input(String content){
        CustomInput input = new CustomInput(); 
        for(Attribute attribute : attributes){
            String value = input.RegexBlankHandle(content +attribute.GetName()+": " , Regex.ALL, null);
            attribute.SetValue(value == null? attribute.GetValue():value );
        }
    };
    
   
    public abstract Attribute GetIdAttribute();
    public abstract String ToString();
    public abstract String ToString_AttributeType();
    
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
