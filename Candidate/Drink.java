package Candidate;
import Factory.Attribute;
import Factory.Candidate;
import Factory.Manager;
import Utils.CustomInput;

import java.util.ArrayList;

public class Drink extends Candidate{
    private Manager<Ingredient> recipe = new Manager<Ingredient>(Ingredient.class);
    
    public Drink(){
       this.AddAttribute("name", null);
    }

    @Override
    public Attribute GetIdAttribute() {
        return this.GetAttribute(0);
    }
    
    
    @Override
    public String ToString() {
        return null;
    }

    @Override
    public String AttributeTypeToString() {
        return null;
    }

    

}
