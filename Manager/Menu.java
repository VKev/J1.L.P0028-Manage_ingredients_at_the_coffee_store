
package Manager;

import Utils.CustomInput;
import Utils.Regex;
import java.util.ArrayList;
import java.util.Collections;

public class Menu {
    private int optionAmount;
    private String tittle;
    private String exitContent;
    private ArrayList<Option> options;

    public Menu(int optionAmount) {
        this.optionAmount = optionAmount;
        this.options = new ArrayList<Option>(Collections.nCopies(optionAmount, null));
    }
    public void SetTittle(String tittle){
        this.tittle = tittle;
    }
    public void SetExitContent(String exitContent){
        this.exitContent = exitContent;
    }
    
    public void Start(){
        CustomInput input = new CustomInput();
        int option;
        while(true){
            ShowOption();
            option = Integer.parseInt(input.Regex("Enter option: ", Regex.NUMBER, null)) -1;
            if(option <0 || option >=optionAmount)
                break;
            if (options.get(option)!= null) 
                options.get(option).Execute();
            System.out.println();
        }
    }

    private void ShowOption(){
        System.out.println(tittle);
        for(Option option : options)
            if(option != null)
                option.ShowContent();
        System.out.println( exitContent);
    }
    
    public void SetOption(int optionNumber, String content ,Runnable action) {
        if (optionNumber <= optionAmount) 
            options.set(optionNumber, new Option( content,action));
         else 
            System.out.println("Option number exceeds the maximum amount of options.");
    }
}
