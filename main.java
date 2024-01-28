
import Candidate.Drink;
import Candidate.Ingredient;
import Factory.Manager;
import Factory.Menu;


public class main {

    
    public static void main(String[] args) {
        Manager ingreManager = new Manager<Ingredient>(Ingredient.class);
        
        Menu ingreMenu = new Menu(4);
        ingreMenu.SetOption(0, "1. Add ingredient.", ()->ingreManager.AddCandidate());
        ingreMenu.SetOption(1, "2. Delete ingredient.", ()->ingreManager.ExecuteIfListNotEmpty( ()->ingreManager.DeleteCandidate() ));
        ingreMenu.SetOption(2, "3. Update ingredient.", ()->ingreManager.ExecuteIfListNotEmpty( ()->ingreManager.UpdateCandidate() ));
        ingreMenu.SetOption(3, "4. Show all ingredient.", ()->ingreManager.ExecuteIfListNotEmpty( ()->ingreManager.ShowAll() ));
        ingreMenu.Start();

        
    }
    
}
