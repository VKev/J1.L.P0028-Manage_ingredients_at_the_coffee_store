
import Candidate.Drink;
import Candidate.Ingredient;
import Factory.Manager;
import Factory.Menu;


public class main {

    
    public static void main(String[] args) {
        Manager ingreManager = new Manager<Ingredient>(Ingredient.class);
        Menu ingreMenu = new Menu(4);
        
        ingreMenu.SetOption(0, "1. Add ingredient.", ()->{ ingreManager.AddCandidate();});
        ingreMenu.Start();
        
        
    }
    
}
