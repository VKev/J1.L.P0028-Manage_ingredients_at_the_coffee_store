package Candidate;
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
    public String GetId() {
        return this.GetAttributeValueString(0);
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
