package CoffeeStore;


import CoffeeStore.Manager.DispensingManager;
import CoffeeStore.Manager.RecipeManager;
import CoffeeStore.Manager.StorageManager;
import CoffeeStore.Candidate.DispensedDrink;
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
    private Menu DispensingMenu;

    public CoffeeStore() {
        storageMenu = new Menu(4);
        storageMenu.SetTittle("★ Edit Ingredient Storage.");
        storageMenu.SetOption(0, "1. Add ingredient.", () -> {storage.AddIngredient(recipes); storage.SaveListToFile();});
        storageMenu.SetOption(1, "2. Delete ingredient.", () -> {storage.ExecuteIfListNotEmpty(() -> storage.DeleteCandidate("• Delete ")); ; storage.SaveListToFile();});
        storageMenu.SetOption(2, "3. Update ingredient.", () -> {storage.ExecuteIfListNotEmpty(() -> storage.UpdateIngredient(recipes)); storage.SaveListToFile();});
        storageMenu.SetOption(3, "4. Show all ingredient.", () -> storage.ExecuteIfListNotEmpty(() -> storage.ShowAll("• Show ")));
        storageMenu.SetExitContent("5. Choose any other options to exit.");

        recipesMenu = new Menu(4);
        recipesMenu.SetTittle("★ Edit Beverage Recipes.");
        recipesMenu.SetOption(0, "1. Add new recipe.", () -> {recipes.AddRecipe(this.storage); recipes.SaveListToFile();});
        recipesMenu.SetOption(1, "2. Delete recipe.", () -> {recipes.ExecuteIfListNotEmpty(() -> recipes.DeleteCandidate("• Delete ")); recipes.SaveListToFile();});
        recipesMenu.SetOption(2, "3. Update recipe.", () -> {recipes.ExecuteIfListNotEmpty(() -> recipes.UpdateCandidate("• Update ")); recipes.SaveListToFile();});
        recipesMenu.SetOption(3, "4. Show all recipe.", () -> recipes.ExecuteIfListNotEmpty(() -> recipes.ShowAll("• Show ")));
        recipesMenu.SetExitContent("5. Choose any others option to exit.");

        DispensingMenu = new Menu(3);
        DispensingMenu.SetTittle("★ Dispensing Beverage.");
        DispensingMenu.SetOption(0, "1. Dispense beverage.", () -> {dispensingManager.DispensingDrink(this.recipes, this.storage); dispensingManager.SaveListToFile();});
        DispensingMenu.SetOption(1, "2. Update beverage.", () -> {dispensingManager.UpdateDispensingDrink(this.recipes, this.storage); dispensingManager.SaveListToFile();});
        DispensingMenu.SetOption(2, "3. Show all beverage.", () -> dispensingManager.ShowAll("• Show "));
        DispensingMenu.SetExitContent("4. Choose any others option to exit.");

        mainMenu = new Menu(3);
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
            DispensingMenu.Start();
        });
        mainMenu.SetExitContent("4. Choose any others option to exit.");

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
}
