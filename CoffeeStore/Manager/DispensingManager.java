
package CoffeeStore.Manager;

import CoffeeStore.Candidate.DispensedDrink;
import CoffeeStore.Candidate.Drink;
import CoffeeStore.Candidate.Ingredient;
import Manager.Candidate;
import Manager.Manager;
import Utils.CustomInput;
import Utils.Regex;
import java.util.ArrayList;

public class DispensingManager extends Manager<DispensedDrink> {

    public DispensingManager() {
        super(DispensedDrink.class);
    }

    public void DispensingDrink(Manager<Drink> recipes, Manager<Ingredient> storage) {
        System.out.println("\n★ Update dispensed beverage");
        CustomInput input = new CustomInput();
        String name = input.RegexBlankHandle("Enter name: ", Regex.ALL, null);
        if (this.FindCandidateIndexById(name) != -1) {
            System.out.println("Dispensing fail: " + name + " beverage is already in dispensing drink list.");
            return;
        }

        DispensedDrink newDispensedDrink = new DispensedDrink();
        newDispensedDrink.GetIdAttribute().SetValue(name);
        Drink dispensingDrink = (Drink) recipes.GetCandidate(newDispensedDrink.GetIdAttribute().GetValue().toString());
        if (dispensingDrink == null) {
            System.out.println("Dispensing fail: No recipe for this bevarage.");
            return;
        }
        newDispensedDrink.InputNoId("Enter ");

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
            storage.SaveListToFile();
            System.out.println("Dispensing Success.");
        } else {
            System.out.println("Dispensing fail: Not enough ingredients.");
        }

    }

    public int UpdateDispensingDrink(Manager<Drink> recipes, Manager<Ingredient> storage) {
        System.out.println("\n★ Update dispensed beverage");
        CustomInput input = new CustomInput();
        String name = input.RegexBlankHandle("Enter beverage name: ", Regex.ALL, null);
        int index = this.FindCandidateIndexById(name);
        if (index == -1) {
            System.out.println("Update fail: name not found!");
            return -1;
        }

        DispensedDrink newDispensedDrink = new DispensedDrink();
        newDispensedDrink.GetAttribute(0).SetValue(name);
        newDispensedDrink.GetAttribute(1).SetValue(this.GetCandidate(index).GetAttribute(1).GetValue());

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
                newAmount.add(amountInStorage - amountInRecipe * (newDispensedDrink.GetAmount() - ((DispensedDrink) this.GetCandidate(index)).GetAmount()));
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
                storage.SaveListToFile();
                System.out.println("Update Success.");
                return index;
            } else {
                System.out.println("Update fail: Not enough ingredients.");
                return -1;
            }
        } else {
            System.out.println("Update fail: No recipe for this bevarage.");
            return -1;
        }
    }

}
