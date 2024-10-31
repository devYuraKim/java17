import java.util.ArrayList;
import java.util.List;

record Affiliation(String name, String type, String countryCode){
    @Override
    public String toString() {
        return name+" (" + type + " in " + countryCode + ")";
    }
}

//T stands for Type
//multiple types are applicable
public class Team<T extends Player, S> {

    private String teamName;
    private List<T> teamMembers = new ArrayList<>();
    private int totalWins = 0;
    private int totalLosses = 0;
    private int totalTies = 0;
    private S affiliation;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public Team(String teamName, S affiliation) {
        this.affiliation = affiliation;
        this.teamName = teamName;
    }

    public void addTeamMember(T t){
        //Record implicitly apples equals() and hashCode() methods
        if (!teamMembers.contains(t)) {
            teamMembers.add(t);
        }
    }

    public void listTeamMembers() {
        System.out.println(teamName + " Roster:");
        //List has an implicitly defined toString() method
        //System.out.println(teamMembers);
        System.out.println(affiliation==null? "" : "===" + affiliation);
        for(T t: teamMembers){
            //generic type parameter T with an upper bound set to an interface that contains a name() method
            System.out.println(t.name());
        }
    }

    public int ranking(){
        return totalLosses*2+totalTies+1;
    }

    public String setScore(int ourScore, int theirScore){
        String message = "";
        if(ourScore>theirScore){
            totalWins++;
            message = "beat";
        }else if (ourScore==theirScore){
            totalTies++;
            message = "tied";
        }else{
            totalLosses++;
            message = "lost to";
        }
        return message;
    }

    @Override
    public String toString() {
        return teamName + " (Ranked " + ranking() + ")";
    }
}
