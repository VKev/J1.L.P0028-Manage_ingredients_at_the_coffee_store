/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Candidate;

import Factory.Attribute;
import Factory.Candidate;


public class DispensedDrink extends Candidate{

    public DispensedDrink(){
        this.AddAttribute("name", null);
        this.AddAttribute("quantity", null);
    }
    
    
    @Override
    public Attribute GetIdAttribute() {
       return this.GetAttribute(0);
    }

    @Override
    public String ToString() {
        return String.format("%-20s%-10s", this.GetAttributeValueString(0),this.GetAttributeValueString(1));
    }

    @Override
    public String ToString_AttributeType() {
        return String.format("%-20s%-10s", 
                this.GetAttribute(0).GetName().toUpperCase(),
                this.GetAttribute(1).GetName().toUpperCase());
    }
    
    public int GetAmount(){
        return Integer.parseInt( this.GetAttribute(1).GetValue().toString());
    }
}