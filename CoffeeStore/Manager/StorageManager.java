/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoffeeStore.Manager;

import CoffeeStore.Candidate.Ingredient;
import Manager.Manager;
import Utils.CustomInput;
import Utils.Regex;
import java.util.ArrayList;


public class StorageManager extends Manager<Ingredient> {

    public StorageManager() {
        super(Ingredient.class);
    }

    public void AddIngredient(RecipeManager recipeManager) {
        System.out.println();
        System.out.println("• Add Ingredient");
        
        do{
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
            String continu = input.Regex("Add more ingredient(Y/N)?",Regex.YESNO , null);
            if(continu.equals("n") || continu.equals("N"))
                break;
        }while(true);
    }

    public int UpdateIngredient(RecipeManager recipeManager) {
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
            newIngre.GetAttribute(1).SetValue(this.GetCandidate(index).GetAttribute(1).GetValue());
            newIngre.GetAttribute(2).SetValue(this.GetCandidate(index).GetAttribute(2).GetValue());
            newIngre.InputNoId("Enter new ");

            ((Ingredient) this.GetCandidate(index)).Update(newIngre);
            recipeManager.UpdateRecipeOfAllDrink(newcode, newIngre);
            System.out.println("Update success!");
        } else {
            System.out.println("Update fail: code not found!");
        }
        return index;
    }

}
