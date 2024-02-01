package Candidate;
import Factory.Attribute;
import Factory.Candidate;
import Factory.Manager;
import Factory.Menu;
import Utils.CustomInput;
import Utils.Regex;

import java.util.ArrayList;

public class Drink extends Candidate{
    private Manager<Ingredient> recipe = new Manager<Ingredient>(Ingredient.class);
    
    public Drink(){
       this.AddAttribute("name", null);
    }
    
    @Override
    public void Input(String content, String title){
       super.Input(content, title);

       Menu ingreMenu = new Menu(4);
       ingreMenu.SetTittle("Edit recipe:");
       ingreMenu.SetOption(0,"1. Add ingredient.", ()->recipe.AddCandidate("◦ Add "));
       ingreMenu.SetOption(1,"2. Delete ingredient.", ()->recipe.ExecuteIfListNotEmpty( ()->recipe.DeleteCandidate("◦ Delete ") ));
       ingreMenu.SetOption(2,"3. Update ingredient.", ()->recipe.ExecuteIfListNotEmpty( ()->recipe.UpdateCandidate("◦ Update ") ));
       ingreMenu.SetOption(3,"4. Show all ingredient.", ()->recipe.ExecuteIfListNotEmpty( ()->recipe.ShowAll("◦ Show ") ));
       ingreMenu.SetExitContent("5. Choose any other options to exit.");
       ingreMenu.Start();
    }
    

    @Override
    public Attribute GetIdAttribute() {
        return this.GetAttribute(0);
    }
    
    
    @Override
    public String ToString() {
        String result = "";
        if(recipe.GetSize()==0) 
            return "  " +String.format("%-20s",this.GetAttributeValueString(0)) + String.format("%-10s%-20s%-10s\n", "X", "X", "X");
        
        result += recipe.GetSize() ==1? "  " +String.format("%-20s",this.GetAttributeValueString(0)) + recipe.GetCandidate(0).ToString()
                                       :"  " +String.format("%-20s",this.GetAttributeValueString(0)) + recipe.GetCandidate(0).ToString()+ "\n";
        for(int i=1; i<recipe.GetSize()-1;i++) result += "  " +String.format("%-20s","") + recipe.GetCandidate(i).ToString() + "\n";
        result += recipe.GetSize() >1? "  " +String.format("%-20s","") + recipe.GetCandidate(recipe.GetSize()-1).ToString(): "";
        
        return result + "\n";
    }

    @Override
    public String ToString_AttributeType() {
        
        String result =  "  " + String.format("%-20s%-40s", "DRINK", "RECIPE") + "\n"
                        +"  "+ new String(new char[35]).replaceAll("\0", "─")+ "\n"
                        +"  "+String.format("%-20s", this.GetAttribute(0).GetName().toUpperCase()) 
                        + recipe.NewInstance().ToString_AttributeType();
        return result;
    }

    public Manager<Ingredient> GetRecipe(){
        return recipe;
    }
    public void AddIngredientToRecipe(Ingredient newIngredient){
         recipe.Add(newIngredient);
    }

}

