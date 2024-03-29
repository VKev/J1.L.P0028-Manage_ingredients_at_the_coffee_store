/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoffeeStore.Candidate;

import Manager.Attribute;
import Manager.Candidate;
import Utils.CustomInput;
import Utils.Regex;


public class DispensingDrink extends Candidate{

    public DispensingDrink(){
        this.AddAttribute("name", null);
        this.AddAttribute("quantity", null); this.GetAttribute(1).SetRegex(Regex.NUMBER);
    }
    
    
    @Override
    public Attribute GetIdAttribute() {
       return this.GetAttribute(0);
    }

    @Override
    public String ToString() {
        return String.format("%-20s%-15s", this.GetAttributeValueString(0),this.GetAttributeValueString(1));
    }

    @Override
    public String ToString_AttributeType() {
        return String.format("%-20s%-15s", 
                this.GetAttribute(0).GetName().toUpperCase(),
                this.GetAttribute(1).GetName().toUpperCase());
    }
    
    public void InputNoId(String content){
        CustomInput input = new CustomInput(); 
        for(Attribute attribute : attributes){
            if(attribute != GetIdAttribute()){
                String value = input.RegexBlankHandle(content +attribute.GetName()+": " ,attribute.GetRegex() , null);
                attribute.SetValue(value == null? attribute.GetValue():value );
            }
        }
    }
    
    public int GetAmount(){
        return Integer.parseInt( this.GetAttribute(1).GetValue().toString());
    }
}
