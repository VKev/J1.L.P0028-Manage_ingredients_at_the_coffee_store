
package CoffeeStore.Manager;

import CoffeeStore.Candidate.Drink;
import CoffeeStore.Candidate.Ingredient;
import Manager.Candidate;
import Manager.Manager;
import java.util.ArrayList;


public class RecipeManager extends Manager<Drink> {

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
