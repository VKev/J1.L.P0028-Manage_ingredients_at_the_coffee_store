package CoffeeStore;


import CoffeeStore.Manager.DispensingManager;
import CoffeeStore.Manager.RecipeManager;
import CoffeeStore.Manager.StorageManager;
import CoffeeStore.Candidate.DispensingDrink;
import CoffeeStore.Candidate.Drink;
import CoffeeStore.Candidate.Ingredient;
import Manager.Candidate;
import Manager.Manager;
import Manager.Menu;
import Utils.CustomInput;
import Utils.Regex;
import java.util.ArrayList;

public class CoffeeStore {

    private StorageManager storage = new StorageManager();
    private RecipeManager recipes = new RecipeManager();
    private DispensingManager dispensingManager = new DispensingManager();

    private Menu mainMenu;
    private Menu storageMenu;
    private Menu recipesMenu;
    private Menu dispensingMenu;
    private Menu reportMenu;

    public CoffeeStore() {
        storageMenu = new Menu(4);
        storageMenu.SetTittle("★ Edit Ingredient Storage.");
        storageMenu.SetOption(0, "1. Add ingredient.", () -> {storage.AddIngredient(recipes); storage.SaveListToFile();});
        storageMenu.SetOption(1, "2. Delete ingredient.", () -> {storage.ExecuteIfListNotEmpty(() -> storage.DeleteCandidate("• Delete ")); ; storage.SaveListToFile();});
        storageMenu.SetOption(2, "3. Update ingredient.", () -> {storage.ExecuteIfListNotEmpty(() ->  ShowAfterUpdate(storage,storage.UpdateIngredient(recipes))); storage.SaveListToFile();});
        storageMenu.SetOption(3, "4. Show all ingredient.", () -> storage.ExecuteIfListNotEmpty(() -> {storage.SortByAttribute_Ascending(1); storage.ShowAll("• Show ");}));
        storageMenu.SetExitContent("5. Choose any other options to exit.");

        recipesMenu = new Menu(4);
        recipesMenu.SetTittle("★ Edit Beverage Recipes.");
        recipesMenu.SetOption(0, "1. Add new recipe.", () -> {recipes.AddRecipe(this.storage); recipes.SaveListToFile();});
        recipesMenu.SetOption(1, "2. Delete recipe.", () -> {recipes.ExecuteIfListNotEmpty(() -> recipes.DeleteCandidate("• Delete ")); recipes.SaveListToFile();});
        recipesMenu.SetOption(2, "3. Update recipe.", () -> {recipes.ExecuteIfListNotEmpty(() -> ShowAfterUpdate(recipes,recipes.UpdateCandidate("• Update "))); recipes.SaveListToFile();});
        recipesMenu.SetOption(3, "4. Show all recipe.", () -> recipes.ExecuteIfListNotEmpty(() -> {recipes.SortByAttribute_Ascending(0); recipes.ShowAll("• Show ");}));
        recipesMenu.SetExitContent("5. Choose any others option to exit.");

        dispensingMenu = new Menu(4);
        dispensingMenu.SetTittle("★ Dispensing Beverage.");
        dispensingMenu.SetOption(0, "1. Dispense beverage.", () -> {dispensingManager.DispensingDrink(this.recipes, this.storage); dispensingManager.SaveListToFile();});
        dispensingMenu.SetOption(1, "2. Update beverage.", () ->dispensingManager.ExecuteIfListNotEmpty(()-> {ShowAfterUpdate(dispensingManager,dispensingManager.UpdateDispensingDrink(this.recipes, this.storage)); dispensingManager.SaveListToFile();}));
        dispensingMenu.SetOption(2, "3. Delete recipe.", () -> {dispensingManager.ExecuteIfListNotEmpty(() -> dispensingManager.DeleteCandidate("• Delete ")); dispensingManager.SaveListToFile();});
        dispensingMenu.SetOption(3, "4. Show all beverage.", ()-> dispensingManager.ExecuteIfListNotEmpty(()-> {dispensingManager.SortByAttribute_Ascending(0);dispensingManager.ShowAll("• Show ");}));
        dispensingMenu.SetExitContent("5. Choose any others option to exit.");

        reportMenu = new Menu(3);
        reportMenu.SetTittle("★ Report.");
        reportMenu.SetOption(0, "1. Ingredients are available.", ()->ReportIngredient());
        reportMenu.SetOption(1, "2. Drinks which are unavailable.", ()->ReportDrink());
        reportMenu.SetOption(2, "3. Show all dispensing drinks information.", ()->ReportDispensingDrink());
        reportMenu.SetExitContent("4. Choose any others option to exit.");
        
        mainMenu = new Menu(4);
        mainMenu.SetTittle("✸ Coffee Store.");
        mainMenu.SetOption(0, "1. Edit Ingredient Storage.", () -> {
            System.out.println();
            storageMenu.Start();
        });
        mainMenu.SetOption(1, "2. Edit Beverage Recipes.", () -> {
            System.out.println();
            recipesMenu.Start();
        });
        mainMenu.SetOption(2, "3. Dispensing Beverage.", () -> {
            System.out.println();
            dispensingMenu.Start();
        });
        mainMenu.SetOption(3, "4. Report.", () -> {
            System.out.println();
            reportMenu.Start();
        });
        mainMenu.SetExitContent("5. Choose any others option to exit.");

    }

    public void Start() {
        storage.CreateFile_ObjectStream("Ingredients.dat");
        storage.LoadListFromFile();
        
        recipes.CreateFile_ObjectStream("Menu.dat");
        recipes.LoadListFromFile();
        
        dispensingManager.CreateFile_ObjectStream("Order.dat");
        dispensingManager.LoadListFromFile();
        
        mainMenu.Start();
        
        storage.CloseFile();
        recipes.CloseFile();
        dispensingManager.CloseFile();
    }
    
    private void ShowAfterUpdate(Manager manager, int index){
        if(index != -1){
            System.out.println("\nAFTER UPDATE:");
            System.out.println(manager.GetCandidate(index).ToString_AttributeType());
            System.out.println(manager.GetCandidate(index).ToString());
        }
        
    }
    
    private void ReportIngredient(){
        boolean firstAvailable = false;
        for(Candidate candi : storage.ToList()){
            Ingredient ingre = (Ingredient)candi;
            boolean available = false;
            for(Candidate drink : recipes.ToList()){
                Drink dri = (Drink)drink;
                Ingredient ingreInRecipe = (Ingredient)(dri.GetRecipe().GetCandidate(ingre.GetIdAttribute().GetValue().toString()));
                if(ingreInRecipe != null){
                    int amountInRecipe = Integer.parseInt( ingreInRecipe.GetAttribute(2).GetValue().toString());
                    int amountInStorage = Integer.parseInt(ingre.GetAttribute(2).GetValue().toString());
                    if(amountInStorage >= amountInRecipe){
                        available = true;
                        break;
                    }
                }
            }
            if(available){
                if(!firstAvailable){
                    firstAvailable = true;
                    System.out.println("\nAvailable ingredient:");
                    System.out.println(ingre.ToString_AttributeType());
                }
                System.out.println(ingre.ToString());
            }
        }
        
        if(!firstAvailable) System.out.println("\nAll ingredient are unavailable!");
    }
    
    private void ReportDrink(){
        boolean firstUnavailable = false;
        for(Candidate candi : storage.ToList()){
            Ingredient ingre = (Ingredient)candi;
            for(Candidate drink : recipes.ToList()){
                Drink dri = (Drink)drink;
                if(dri.GetAvailable() == false) continue;
                Ingredient ingreInRecipe = (Ingredient)(dri.GetRecipe().GetCandidate(ingre.GetIdAttribute().GetValue().toString()));
                if(ingreInRecipe != null){
                    int amountInRecipe = Integer.parseInt( ingreInRecipe.GetAttribute(2).GetValue().toString());
                    int amountInStorage = Integer.parseInt(ingre.GetAttribute(2).GetValue().toString());
                    if(amountInStorage < amountInRecipe){
                        dri.SetAvailable(false);
                        if(!firstUnavailable){
                            firstUnavailable = true;
                            System.out.println("\nUnavailable recipe:");
                            System.out.println(dri.ToString_AttributeType());
                        }
                        System.out.println(dri.ToString());
                    }
                }
            }
        }
        
        if(!firstUnavailable) System.out.println("\nAll recipe is available");
        for(Candidate drink : recipes.ToList()) ((Drink)drink).SetAvailable(true);
    }
    
    private void ReportDispensingDrink(){
        boolean first = false;
        if(dispensingManager.GetSize()>0){
            System.out.println("\nAll dispensing drink information:");
            System.out.println(String.format("%-35s%-80s", "DRINK", "RECIPE")+"\n"+new String(new char[40]).replaceAll("\0", "─"));
            DispensingDrink dispensingdrink = new DispensingDrink();
            System.out.println(dispensingdrink.ToString_AttributeType()+((Drink)recipes.GetCandidate(0)).ReportTittle());
            for(Candidate candi : dispensingManager.ToList()){
                DispensingDrink dispensedDrink = (DispensingDrink)candi;
                if(recipes.GetCandidate(dispensedDrink.GetAttributeValueString(0))!= null)
                    System.out.println(dispensedDrink.ToString()+((Drink)recipes.GetCandidate(dispensedDrink.GetAttributeValueString(0))).ReportContent());
                else{
                    System.out.println(dispensedDrink.ToString() +"NO RECIPE AVAILABLE\n");
                }
            }
        }
        else
            System.out.println("\nNot found any dispensing drink");
    }
}
