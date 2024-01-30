package Candidate;


import Factory.Attribute;
import Factory.Candidate;
import Utils.CustomInput;
import java.util.ArrayList;
import java.util.Scanner;


public class Ingredient extends Candidate {

    public Ingredient(){
       this.AddAttribute("code", null);
       this.AddAttribute("name", null);
       this.AddAttribute("amount", null);
    }

    @Override
    public Attribute GetIdAttribute() {
        return this.GetAttribute(0);
    }

    @Override
    public String ToString() {
       return String.format("%-10s%-20s%-10s", 
               this.GetAttributeValueString(0),
               this.GetAttributeValueString(1),
               this.GetAttributeValueString(2) );
    }

    @Override
    public String ToString_AttributeType() {
        return String.format("%-10s%-20s%-10s", 
                this.GetAttribute(0).GetName().toUpperCase()
                ,this.GetAttribute(1).GetName().toUpperCase(),
                this.GetAttribute(2).GetName().toUpperCase());
    }


}