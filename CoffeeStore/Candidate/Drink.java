package CoffeeStore.Candidate;
import Manager.Attribute;
import Manager.Candidate;
import Manager.Manager;
import Manager.Menu;
import Utils.CustomInput;
import Utils.Regex;
import java.io.Serializable;

import java.util.ArrayList;

public class Drink extends Candidate implements Serializable{
    private Recipe recipe = new Recipe();
    
    public Drink(){
       this.AddAttribute("name", null);
    }
    
    @Override
    public void Input(String content, String title){
       super.Input(content, title);

       Menu ingreMenu = new Menu(4);
       ingreMenu.SetTittle("Edit recipe:");
       ingreMenu.SetOption(0,"1. Add ingredient.", ()->recipe.AddIngredient());
       ingreMenu.SetOption(1,"2. Delete ingredient.", ()->recipe.ExecuteIfListNotEmpty( ()->recipe.DeleteCandidate("◦ Delete ") ));
       ingreMenu.SetOption(2,"3. Update ingredient.", ()->recipe.ExecuteIfListNotEmpty( ()->recipe.UpdateIngredient() ));
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
    public void SetStorage(Manager<Ingredient> storage){
        recipe.SetStorage(storage);
    }

}

class Recipe extends Manager<Ingredient> implements Serializable{
    
    private Manager<Ingredient> storage;
    
    public Recipe() {
        super(Ingredient.class);
    }
    public void AddIngredient(){
        System.out.println();
        System.out.println("◦ Add Ingredient");
        CustomInput input = new CustomInput();
        
        String code = input.RegexBlankHandle("Enter code: ", Regex.ALL, null);
        int index = this.FindCandidateIndexById(code);
        if(index==-1){
            Ingredient ingredientInStorage = (Ingredient)storage.GetCandidate(code);
            if(ingredientInStorage!= null){
                Ingredient newIngredient = new Ingredient();
                newIngredient.GetAttribute(0).SetValue( ingredientInStorage.GetAttribute(0).GetValue() );
                newIngredient.GetAttribute(1).SetValue( ingredientInStorage.GetAttribute(1).GetValue() );
                newIngredient.GetAttribute(2).SetValue( input.RegexBlankHandle("Enter amount: ", Regex.NUMBER, null) );
                this.Add(newIngredient);
                System.out.println("Add success!");
            }else
                System.out.println("Add fail: ingredient code not exist in storage!");
        }else
             System.out.println("Add fail: code aready exist!");
    }

    public void UpdateIngredient(){
        System.out.println();
        System.out.println("◦ Update Ingredient");
        CustomInput input = new CustomInput();
        
        String code = input.RegexBlankHandle("Enter code: ", Regex.ALL, null);
        int index = this.FindCandidateIndexById(code);
        if(index!=-1){
            String newcode = input.RegexBlankHandle("Enter new code: ", Regex.ALL, null);
            Ingredient newIngredient = new Ingredient();
            Ingredient ingredientInStorage = (Ingredient)storage.GetCandidate(newcode);
            if(ingredientInStorage!= null || newcode == null){
                this.GetCandidate(index).GetAttribute(0).SetValue( newcode==null? this.GetCandidate(index).GetAttribute(0).GetValue(): newcode);
                this.GetCandidate(index).GetAttribute(1).SetValue(newcode==null? this.GetCandidate(index).GetAttribute(1).GetValue(): ingredientInStorage.GetAttribute(1).GetValue() );
                String newAmount = input.RegexBlankHandle("Enter new amount: ", Regex.NUMBER, null);
                this.GetCandidate(index).GetAttribute(2).SetValue( newAmount != null? newAmount: this.GetCandidate(index).GetAttribute(2).GetValue() );
                System.out.println("Update success!");
            }else
                System.out.println("Update fail: new ingredient code not exist in storage!");
        }else
             System.out.println("Update fail: code not found!");
    }
    public void FindAndUpdateIngredient(String code,Ingredient newIngredient){
        int index = this.FindCandidateIndexById(code);
        if(index != -1){
            
        }
    }
    public void SetStorage(Manager<Ingredient> storage){
        this.storage = storage;
    }
}

