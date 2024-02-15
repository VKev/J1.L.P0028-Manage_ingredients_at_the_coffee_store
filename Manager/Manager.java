package Manager;


import Utils.CustomInput;
import Utils.FileManager;
import java.io.Serializable;
import java.util.ArrayList;


public class Manager<T extends Candidate> implements Serializable{
    protected ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
    private Class<T> candidateType;
    private FileManager fileManager = new FileManager();
    
    public Manager(Class<T> type){
        this.candidateType = type;
    }
    public T NewInstance(){
        try {
            return candidateType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
    

    
    public Candidate GetCandidate(String id){
        int index = FindCandidateIndexById(id);
        if(index != -1)
            return GetCandidate(index);
        return null;
    }
    public Candidate GetCandidate(int index){
        return candidateList.get(index);
    }
    public void SetCandidate(Candidate newCandi, int index){
        candidateList.set(index, newCandi);
    }
    public int GetSize(){
        return candidateList.size();
    }
    public void Add(Candidate newCandidate){
        candidateList.add(newCandidate);
    }
    public void Delete(int index){
        candidateList.remove(index);
    }
    public void Delete(Candidate candidate){
        candidateList.remove(candidate);
    }
    public ArrayList<Candidate> ToList(){
        return candidateList;
    }
    
    
    
    public void AddCandidate(String infrontTittle) {
        T newCandidate = NewInstance();
        newCandidate.Input("Enter ", infrontTittle!=null?infrontTittle+ candidateType.getSimpleName():"");
        if(FindCandidateIndexById(newCandidate.GetIdAttribute().GetValue().toString()) == -1){
            candidateList.add(newCandidate);
            System.out.println("Add Success.");
        }else{
            System.out.println("Add fail: "+newCandidate.GetIdAttribute().GetName()+" is already exits.");
        }
    }
    public void DeleteCandidate(String infrontTittle){
        if(infrontTittle != null){
            System.out.println();
            System.out.println(infrontTittle+ candidateType.getSimpleName());
        }
        
        CustomInput input = new CustomInput();
        String Id = input.GetLine("Enter " + candidateList.get(0).GetIdAttribute().GetName()+": ");
        int index = FindCandidateIndexById(Id);
        if(index != -1){
            candidateList.remove(index);
            System.out.println("Delete success.");
        }else
            System.out.println("Delete fail: "+candidateList.get(0).GetIdAttribute().GetName()+" not found!");
    }
    public int UpdateCandidate(String infrontTittle){
        if(infrontTittle != null){
            System.out.println();
            System.out.println(infrontTittle+ candidateType.getSimpleName());
        }
        CustomInput input = new CustomInput();
        String Id = input.GetLine("Enter " + candidateList.get(0).GetIdAttribute().GetName()+": ");
        int index = FindCandidateIndexById(Id);
        if(index != -1){
            candidateList.get(index).Input("Enter new ", null);
            System.out.println("Update success.");
        }else{
            System.out.println("Update fail: "+candidateList.get(0).GetIdAttribute().GetName()+" not found!");
        }
        return index;
    }
    public void ShowAll(String infrontTittle){
        if(infrontTittle != null){
            System.out.println();
            System.out.println(infrontTittle+ candidateType.getSimpleName());
        }
        
        System.out.println(candidateList.get(0).ToString_AttributeType());
        for(Candidate candidate : candidateList){
            System.out.println(candidate.ToString() );
        }
    }
    public void SortByAttribute_Ascending(int attributeIndex){
        candidateList.sort((a1,a2)->a1.GetAttribute(attributeIndex).GetValue().toString().compareTo(a2.GetAttribute(attributeIndex).GetValue().toString()));
    }
    public void SortByAttribute_Decending(int attributeIndex){
        candidateList.sort((a1,a2)->a2.GetAttribute(attributeIndex).GetValue().toString().compareTo(a1.GetAttribute(attributeIndex).GetValue().toString()));
    }
    
    public void ExecuteIfListNotEmpty(Runnable action){
        if(candidateList.size() >0)
            action.run();
        else
            System.out.println("\nNo " + candidateType.getSimpleName()+" in list.");
    }
    protected int FindCandidateIndexById(String Id){
        for(int i=0; i< candidateList.size(); i++){
            if( candidateList.get(i).GetIdAttribute().GetValue().toString().equals(Id) ){
                return i;
            }
         }
        return -1;
    }
    
    
    
    public void CreateFile_ObjectStream(String fileName){
        fileManager.CreateFile_ObjectStream(fileName);
    }
    public void SaveListToFile(){
        fileManager.ClearAndWriteObject(candidateList);
    }
    public void LoadListFromFile(){
        ArrayList<Candidate> loaddata = (ArrayList<Candidate>) fileManager.GetObject(); 
        candidateList = loaddata!= null? loaddata : candidateList;
    }
    public void CloseFile(){
        fileManager.CloseFile();
    }
}

