package Factory;


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
        newCandidate.Input();
        if(FindCandidateIndexById(newCandidate.GetId()) == -1){
            candidateList.add(newCandidate);
        }
    }
    private int FindCandidateIndexById(String Id){
        for(int i=0; i< candidateList.size(); i++){
            if( candidateList.get(i).GetId().equals(Id) ){
                return i;
            }
         }
        return -1;
    }
    
    public void ShowAll(){
        System.out.println(candidateList.get(0).AttributeTypeToString());
        for(Candidate candidate : candidateList){
           System.out.println( candidate.ToString() );
        }
    }
}
