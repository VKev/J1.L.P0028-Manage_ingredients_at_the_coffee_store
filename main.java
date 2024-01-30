
import Candidate.Drink;
import Candidate.Ingredient;
import Factory.Manager;
import Factory.Menu;


public class main {

    
    public static void main(String[] args) {
        Manager drinkManager = new Manager<Drink>(Drink.class);
        
        Menu drinkMenu = new Menu(4);
        drinkMenu.SetTittle("Drink Manager");
        drinkMenu.SetOption(0, "1. Add drink", ()->drinkManager.AddCandidate());
        drinkMenu.SetOption(3, "4. Show all drinks.", ()->drinkManager.ExecuteIfListNotEmpty( ()->drinkManager.ShowAll() ));
        drinkMenu.Start();
        
    }
    
}
