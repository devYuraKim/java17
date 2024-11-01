//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //create one-dimensional array of Park objects
        var nationalUSParks = new Park[]{
                new Park("Yellowstone", "44,-110" ),
                new Park("Grand Canyon", "36,-112"),
                new Park("Yosemite", "37,-119")
        };

        Layer<Park> parkLayer = new Layer<>(nationalUSParks);
        parkLayer.renderLayer();


        System.out.println("=".repeat(80));

        var majorUSRivers = new River[]{
                new River("Mississippi", "47,-95", "29,-89","35,-90"),
                new River("Missouri", "45,-111", "38,-90")
        };

        Layer<River> riverLayer = new Layer<>(majorUSRivers);

        riverLayer.addElements(
                new River("Colorado", "40,-105", "31,-114"),
                new River("Delaware", "42,-75", "39,-75")
        );
        riverLayer.renderLayer();
    }
}