
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
        storageMenu.SetOption(0, "1. Add ingredient.", () -> storage.AddIngredient(recipes));
        storageMenu.SetOption(1, "2. Delete ingredient.", () -> storage.ExecuteIfListNotEmpty(() -> storage.DeleteCandidate("• Delete ")));
        storageMenu.SetOption(2, "3. Update ingredient.", () -> storage.ExecuteIfListNotEmpty(() -> storage.UpdateIngredient(recipes)));
        storageMenu.SetOption(3, "4. Show all ingredient.", () -> storage.ExecuteIfListNotEmpty(() -> storage.ShowAll("• Show ")));
        storageMenu.SetExitContent("5. Choose any other options to exit.");

        recipesMenu = new Menu(4);
        recipesMenu.SetTittle("★ Edit Beverage Recipes.");
        recipesMenu.SetOption(0, "1. Add new recipe.", () -> recipes.AddRecipe(this.storage));
        recipesMenu.SetOption(1, "2. Delete recipe.", () -> recipes.ExecuteIfListNotEmpty(() -> recipes.DeleteCandidate("• Delete ")));
        recipesMenu.SetOption(2, "3. Update recipe.", () -> recipes.ExecuteIfListNotEmpty(() -> recipes.UpdateCandidate("• Update ")));
        recipesMenu.SetOption(3, "4. Show all recipe.", () -> recipes.ExecuteIfListNotEmpty(() -> recipes.ShowAll("• Show ")));
        recipesMenu.SetExitContent("5. Choose any others option to exit.");

        DispensingMenu = new Menu(2);
        DispensingMenu.SetTittle("★ Dispensing Beverage.");
        DispensingMenu.SetOption(0, "1. Dispense beverage.", () -> dispensingManager.DispensingDrink(this.recipes, this.storage));
        DispensingMenu.SetOption(1, "2. Update beverage.", () -> dispensingManager.UpdateDispensingDrink(this.recipes, this.storage));
        DispensingMenu.SetExitContent("3. Choose any others option to exit.");

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
        mainMenu.Start();
    }
}

class DispensingManager extends Manager<DispensedDrink> {

    public DispensingManager() {
        super(DispensedDrink.class);
    }

    public void DispensingDrink(Manager<Drink> recipes, Manager<Ingredient> storage) {
        System.out.println("\n★ Update dispensed beverage");
        CustomInput input = new CustomInput();
        String name  = input.RegexBlankHandle("Enter name: ", Regex.ALL, null);
        if (this.FindCandidateIndexById(name) != -1) {
            System.out.println("Dispensing fail: " + name + " beverage is already in dispensing drink list.");
            return;
        }
        
        DispensedDrink newDispensedDrink = new DispensedDrink();
        newDispensedDrink.GetIdAttribute().SetValue(name);
        newDispensedDrink.InputNoId("Enter ");
        

        Drink dispensingDrink = (Drink) recipes.GetCandidate(newDispensedDrink.GetIdAttribute().GetValue().toString());
        if (dispensingDrink != null) {
            Manager<Ingredient> recipe = dispensingDrink.GetRecipe();
            ArrayList<Integer> newAmount = new ArrayList<>();
            boolean canDispensing = true;
            for (Candidate ingreInRecipe : recipe.ToList()) {
                Candidate ingreInStorage = storage.GetCandidate(ingreInRecipe.GetIdAttribute().GetValue().toString());
                int amountInStorage = Integer.parseInt(ingreInStorage.GetAttribute(2).GetValue().toString());
                int amountInRecipe = Integer.parseInt(ingreInRecipe.GetAttribute(2).GetValue().toString());
                newAmount.add(amountInStorage - amountInRecipe * newDispensedDrink.GetAmount());
                if (newAmount.get(newAmount.size() - 1) < 0) {
                    canDispensing = false;
                    break;
                }
            }
            if (canDispensing) {
                int i = 0;
                for (Candidate ingreInRecipe : recipe.ToList()) {
                    storage.GetCandidate(ingreInRecipe.GetIdAttribute().GetValue().toString()).GetAttribute(2).SetValue(newAmount.get(i));
                    i++;
                }
                this.Add(newDispensedDrink);
                System.out.println("Dispensing Success.");
            } else {
                System.out.println("Dispensing fail: Not enough ingredients.");
            }
        } else {
            System.out.println("Dispensing fail: No recipe for this bevarage.");
        }
    }

    public void UpdateDispensingDrink(Manager<Drink> recipes, Manager<Ingredient> storage) {
        System.out.println("\n★ Update dispensed beverage");
        CustomInput input = new CustomInput();
        String name = input.RegexBlankHandle("Enter beverage name: ", Regex.ALL, null);
        int index = this.FindCandidateIndexById(name);
        if (index == -1) {
            System.out.println("Update fail: name not found!");
            return;
        }

        DispensedDrink newDispensedDrink = new DispensedDrink();
        newDispensedDrink.GetAttribute(0).SetValue(name);

        Drink dispensingDrink = (Drink) recipes.GetCandidate(newDispensedDrink.GetIdAttribute().GetValue().toString());
        if (dispensingDrink != null) {
            newDispensedDrink.InputNoId("Enter new ");
            Manager<Ingredient> recipe = dispensingDrink.GetRecipe();
            ArrayList<Integer> newAmount = new ArrayList<>();
            boolean canDispensing = true;
            for (Candidate ingreInRecipe : recipe.ToList()) {
                Candidate ingreInStorage = storage.GetCandidate(ingreInRecipe.GetIdAttribute().GetValue().toString());
                int amountInStorage = Integer.parseInt(ingreInStorage.GetAttribute(2).GetValue().toString());
                int amountInRecipe = Integer.parseInt(ingreInRecipe.GetAttribute(2).GetValue().toString());
                newAmount.add(amountInStorage - amountInRecipe * (newDispensedDrink.GetAmount() - ((DispensedDrink)this.GetCandidate(index)).GetAmount()));
                if (newAmount.get(newAmount.size() - 1) < 0) {
                    canDispensing = false;
                    break;
                }
            }
            if (canDispensing) {
                int i = 0;
                for (Candidate ingreInRecipe : recipe.ToList()) {
                    storage.GetCandidate(ingreInRecipe.GetIdAttribute().GetValue().toString()).GetAttribute(2).SetValue(newAmount.get(i));
                    i++;
                }
                this.SetCandidate(newDispensedDrink, index);
                System.out.println("Update Success.");
            } else {
                System.out.println("Update fail: Not enough ingredients.");
            }
        } else {
            System.out.println("Update fail: No recipe for this bevarage.");
        }
    }

}

class RecipeManager extends Manager<Drink> {

    public RecipeManager() {
        super(Drink.class);
    }

    public void AddRecipe(Manager<Ingredient> storage) {
        Drink newdrink = new Drink();
        newdrink.SetStorage(storage);
        newdrink.Input("Enter ", "• Add Drink");
        this.Add(newdrink);
    }

    public void UpdateRecipeOfAllDrink(String code, Ingredient newIngre) {
        for (Candidate drink : candidateList) {
            Manager<Ingredient> recipe = ((Drink) drink).GetRecipe();
            Ingredient matchIngre = (Ingredient) recipe.GetCandidate(code);
            if (matchIngre != null) {
                String oldAmount = matchIngre.GetAttribute(2).GetValue().toString();
                matchIngre.Update(newIngre);
                matchIngre.GetAttribute(2).SetValue(oldAmount);
            }
        }
    }
}

class StorageManager extends Manager<Ingredient> {

    public StorageManager() {
        super(Ingredient.class);
    }

    public void AddIngredient(RecipeManager recipeManager) {
        System.out.println();
        System.out.println("• Add Ingredient");

        CustomInput input = new CustomInput();
        String code = input.RegexBlankHandle("Enter code: ", Regex.ALL, null);

        int index = this.FindCandidateIndexById(code);
        if (index == -1) {
            Ingredient newIngre = new Ingredient();
            newIngre.GetAttribute(0).SetValue(code);
            newIngre.InputNoId("Enter ");
            this.Add(newIngre);

            recipeManager.UpdateRecipeOfAllDrink(code, newIngre);

            System.out.println("Add success!");
        } else {
            System.out.println("Add fail: code already exist!");
        }
    }

    public void UpdateIngredient(RecipeManager recipeManager) {
        System.out.println();
        System.out.println("• Update Ingredient");

        CustomInput input = new CustomInput();
        String code = input.RegexBlankHandle("Enter code: ", Regex.ALL, null);
        int index = this.FindCandidateIndexById(code);
        if (index != -1) {

            Ingredient newIngre = new Ingredient();
            String newcode = null;
            String content = "Enter new code: ";
            while (true) {
                newcode = input.RegexBlankHandle(content, Regex.ALL, null);
                if (this.FindCandidateIndexById(newcode) == -1 || newcode.equals(code) || newcode == null) {
                    break;
                }
                content = "Code aready exist!, enter new code again: ";
            }
            newcode = newcode == null ? code : newcode;
            newIngre.GetAttribute(0).SetValue(newcode);
            newIngre.InputNoId("Enter new ");

            ((Ingredient) this.GetCandidate(index)).Update(newIngre);
            recipeManager.UpdateRecipeOfAllDrink(newcode, newIngre);
            System.out.println("Update success!");
        } else {
            System.out.println("Update fail: code not found!");
        }
    }

}
