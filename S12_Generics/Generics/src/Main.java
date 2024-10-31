//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
interface Player{
    String name();
}

record BaseballPlayer(String name, String position) implements Player{}
record FootballPlayer(String name, String position) implements Player{}


public class Main {
    public static void main(String[] args) {

        var philly = new Affiliation("city", "PA", "US");
        var astro = new Affiliation("city", "HT", "US");

        BaseballTeam phillies1 = new BaseballTeam("Philadelphia Phillies1");
        BaseballTeam astros1 = new BaseballTeam("Houston Astros1");
        scoreResult(phillies1, 3, astros1, 5);

        SportsTeam phillies2 = new SportsTeam("Philadelphia Phillies2");
        SportsTeam astros2 = new SportsTeam("Houston Astros2");
        scoreResult(phillies2, 3, astros2, 5);

        Team<BaseballPlayer, Affiliation> phillies = new Team<>("Philadelphia Phillies", philly);
        Team<BaseballPlayer, Affiliation> astros = new Team<>("Houston Astros", astro);
        scoreResult(phillies, 3, astros, 5);

        var harper = new BaseballPlayer("B Harper", "Right");
        var marsh = new BaseballPlayer("B Marsh", "Right");
        phillies.addTeamMember(harper);
        astros.addTeamMember(marsh);
        phillies.listTeamMembers();
        astros.listTeamMembers();

        SportsTeam afc1 = new SportsTeam("Crows1");
        Team<FootballPlayer, Affiliation> afc = new Team<>("Crows1");
        var tex = new FootballPlayer("Tex", "Mid");

        afc.addTeamMember(tex);
        //Type Checking in action
        //afc.addTeamMember(harper);
        afc.listTeamMembers();

        //Problem1: No limit on Type
        //Solution: Set an upper bound with <T extends Type>
        //Team<String> seoul = new Team<>("Bears");
        //seoul.addTeamMember("Seoul player");
        //seoul.listTeamMembers();
        //
        //var busan = new Team<String>("Giants");
        //busan.addTeamMember("Busan player");
        //busan.listTeamMembers();
        //
        //scoreResult(seoul, 0, busan, 1);
    }

    public static void scoreResult(BaseballTeam team1, int t1Score, BaseballTeam team2, int t2Score){
        String message = team1.setScore(t1Score, t2Score);
        team2.setScore(t2Score, t1Score);
        System.out.printf("%s %s %s %n", team1, message, team2);
    }

    public static void scoreResult(SportsTeam team1, int t1Score, SportsTeam team2, int t2Score){
        String message = team1.setScore(t1Score, t2Score);
        team2.setScore(t2Score, t1Score);
        System.out.printf("%s %s %s %n", team1, message, team2);
    }

    public static void scoreResult(Team team1, int t1Score, Team team2, int t2Score){
        String message = team1.setScore(t1Score, t2Score);
        team2.setScore(t2Score, t1Score);
        System.out.printf("%s %s %s %n", team1, message, team2);
    }

}