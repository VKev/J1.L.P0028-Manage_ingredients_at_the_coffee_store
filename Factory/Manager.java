package Factory;


import Utils.CustomInput;
import java.util.ArrayList;


public class Manager<T extends Candidate> {
    private ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
    private Class<T> candidateType;
    
    public Manager(Class<T> type){
        this.candidateType = type;
    }
    private T NewInstance(){
        try {
            return candidateType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
    

    
    public void AddCandidate() {
        T newCandidate = NewInstance();
        newCandidate.Input("Enter ");
        if(FindCandidateIndexById(newCandidate.GetIdAttribute().GetValue().toString()) == -1){
            candidateList.add(newCandidate);
        }
    }
    public void DeleteCandidate(){
        CustomInput input = new CustomInput();
        String Id = input.GetLine("Enter " + candidateList.get(0).GetIdAttribute().GetName()+": ");
        int index = FindCandidateIndexById(Id);
        if(index != -1){
            candidateList.remove(index);
            System.out.println("Delete success.");
        }else
            System.out.println("Delete fail: "+candidateList.get(0).GetIdAttribute().GetName()+"not found!");
    }
    public void UpdateCandidate(){
        CustomInput input = new CustomInput();
        String Id = input.GetLine("Enter " + candidateList.get(0).GetIdAttribute().GetName()+": ");
        int index = FindCandidateIndexById(Id);
        if(index != -1){
            candidateList.get(index).Input("Enter new ");
            System.out.println("Update success.");
        }else
            System.out.println("Update fail: "+candidateList.get(0).GetIdAttribute().GetName()+"not found!");
    }
    public void ShowAll(){
        System.out.println(candidateList.get(0).AttributeTypeToString());
        for(Candidate candidate : candidateList){
            System.out.println( candidate.ToString() );
        }
    }
    
    
    public void ExecuteIfListNotEmpty(Runnable action){
        if(candidateList.size() >0)
            action.run();
        else
            System.out.println("No " + candidateType.getSimpleName()+" in list.");
    }
    private int FindCandidateIndexById(String Id){
        for(int i=0; i< candidateList.size(); i++){
            if( candidateList.get(i).GetIdAttribute().GetValue().toString().equals(Id) ){
                return i;
            }
         }
        return -1;
    }
}

