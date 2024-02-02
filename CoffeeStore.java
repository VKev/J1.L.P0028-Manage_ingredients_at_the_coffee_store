


import Candidate.DispensedDrink;
import Candidate.Drink;
import Candidate.Ingredient;
import Factory.Candidate;
import Factory.Manager;
import Factory.Menu;
import Utils.CustomInput;
import Utils.Regex;
import java.util.ArrayList;

public class CoffeeStore {
    private Manager<Ingredient> storage = new Manager<Ingredient>(Ingredient.class);
    private RecipeManager recipes = new RecipeManager();
    private DispensingManager dispensingManager = new DispensingManager();
    
    private Menu mainMenu;
    private Menu storageMenu;
    private Menu recipesMenu;
    private Menu DispensingMenu;
    
    public CoffeeStore(){
        storageMenu = new Menu(4);
        storageMenu.SetTittle("★ Edit Ingredient Storage.");
        storageMenu.SetOption(0,"1. Add ingredient.", ()->storage.AddCandidate("• Add "));
        storageMenu.SetOption(1,"2. Delete ingredient.", ()->storage.ExecuteIfListNotEmpty( ()->storage.DeleteCandidate("• Delete ") ));
        storageMenu.SetOption(2,"3. Update ingredient.", ()->storage.ExecuteIfListNotEmpty( ()->storage.UpdateCandidate("• Update ") ));
        storageMenu.SetOption(3,"4. Show all ingredient.", ()->storage.ExecuteIfListNotEmpty( ()->storage.ShowAll("• Show ") ));
        storageMenu.SetExitContent("5. Choose any other options to exit.");
        
        recipesMenu = new Menu(4);
        recipesMenu.SetTittle("★ Edit Beverage Recipes.");
        recipesMenu.SetOption(0, "1. Add new recipe.", ()->recipes.AddRecipe(this.storage));
        recipesMenu.SetOption(1, "2. Delete recipe.", ()->recipes.ExecuteIfListNotEmpty( ()->recipes.DeleteCandidate("• Delete ")));
        recipesMenu.SetOption(2, "3. Update recipe.", ()->recipes.ExecuteIfListNotEmpty( ()->recipes.UpdateCandidate("• Update ")));
        recipesMenu.SetOption(3, "4. Show all recipe.", ()->recipes.ExecuteIfListNotEmpty( ()->recipes.ShowAll("• Show ") ));
        recipesMenu.SetExitContent("5. Choose any others option to exit.");
        
        DispensingMenu = new Menu(2);
        DispensingMenu.SetTittle("★ Dispensing Beverage.");
        DispensingMenu.SetOption(0, "1. Dispense beverage.", ()->dispensingManager.DispensingDrink(this.recipes, this.storage));
        DispensingMenu.SetExitContent("3. Choose any others option to exit.");
        
        mainMenu = new Menu(3);
        mainMenu.SetTittle("✸ Coffee Store.");
        mainMenu.SetOption(0, "1. Edit Ingredient Storage.", ()-> {System.out.println();storageMenu.Start();} );
        mainMenu.SetOption(1, "2. Edit Beverage Recipes.", ()-> {System.out.println();recipesMenu.Start();} );
        mainMenu.SetOption(2, "3. Dispensing Beverage.", ()-> {System.out.println();DispensingMenu.Start();} );
        mainMenu.SetExitContent("4. Choose any others option to exit.");
        
    }
    
    public void Start(){
        mainMenu.Start();
    }
}

class DispensingManager extends Manager<DispensedDrink>{
    
    public DispensingManager() {
        super(DispensedDrink.class);
    }
    
    public void DispensingDrink(Manager<Drink> recipes, Manager<Ingredient> storage){
        DispensedDrink newDispensedDrink = new DispensedDrink();
        newDispensedDrink.Input("Enter ", "★ Dispensing beverage");
        if(this.FindCandidateIndexById(newDispensedDrink.GetIdAttribute().GetValue().toString()) == -1){
            Drink dispensingDrink = (Drink)recipes.GetCandidate(newDispensedDrink.GetIdAttribute().GetValue().toString());
            if(dispensingDrink != null){
                Manager<Ingredient> recipe =  dispensingDrink.GetRecipe();
                ArrayList<Integer> newAmount = new ArrayList<>();
                boolean canDispensing = true;
                for(Candidate ingreInRecipe : recipe.ToList()){
                    Candidate ingreInStorage = storage.GetCandidate(ingreInRecipe.GetIdAttribute().GetValue().toString());
                    int amountInStorage = Integer.parseInt( ingreInStorage.GetAttribute(2).GetValue().toString());
                    int amountInRecipe = Integer.parseInt( ingreInRecipe.GetAttribute(2).GetValue().toString());
                    newAmount.add(amountInStorage - amountInRecipe* newDispensedDrink.GetAmount());
                    if(newAmount.get(newAmount.size()-1) <0){
                        canDispensing = false;
                        break;
                    }    
                }
                if(canDispensing){
                    int i =0;
                    for(Candidate ingreInRecipe : recipe.ToList()) storage.GetCandidate(ingreInRecipe.GetIdAttribute().GetValue().toString()).GetAttribute(2).SetValue(newAmount.get(i));
                    System.out.println("Dispensing Success.");
                }else
                    System.out.println("Dispensing fail: Not enough ingredients.");
            }
        }else{
            System.out.println("Dispensing fail: "+newDispensedDrink.GetIdAttribute().GetName()+" beverage is already in dispensing drink list.");
        }
    }
}
 
class RecipeManager extends Manager<Drink>{
    
    public RecipeManager() {
        super(Drink.class);
    }
    
    public void AddRecipe(Manager<Ingredient> storage){
        System.out.println();
        System.out.println("◦ Add Drink");
        CustomInput input = new CustomInput();
        
        Drink newDrink = new Drink();
        newDrink.GetAttribute(0).SetValue( input.RegexBlankHandle("Enter name: ", Regex.ALL, null) );
        
        String code = input.RegexBlankHandle("Enter code: ", Regex.ALL, null);
        
        Ingredient newIngredient = new Ingredient();
        Ingredient ingredientInStorage = (Ingredient)storage.GetCandidate(code);
        if(ingredientInStorage!= null){
            newIngredient.GetAttribute(0).SetValue( ingredientInStorage.GetAttribute(0).GetValue() );
            newIngredient.GetAttribute(1).SetValue( ingredientInStorage.GetAttribute(1).GetValue() );
            newIngredient.GetAttribute(2).SetValue( input.RegexBlankHandle("Enter amount: ", Regex.NUMBER, null) );
            newDrink.AddIngredientToRecipe(newIngredient);
            this.Add(newDrink);
        }else{
            System.out.println("Ingredient code doesn't exist in store");
        }
        
    }
    
}



