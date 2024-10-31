import java.util.ArrayList;
import java.util.List;

public class BaseballTeam {

    private String teamName;
    private List<BaseballPlayer> teamMembers = new ArrayList<>();
    private int totalWins = 0;
    private int totalLosses = 0;
    private int totalTies = 0;

    public BaseballTeam(String teamName) {
        this.teamName = teamName;
    }

    public void addTeamMember(BaseballPlayer player){
        //Record implicitly apples equals() and hashCode() methods
        if (!teamMembers.contains(player)) {
            teamMembers.add(player);
        }
    }

    public void listTeamMembers(){
        System.out.println(teamName+ " Roster:");
        //List has an implicitly defined toString() method
        System.out.println(teamMembers);
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
