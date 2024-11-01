import java.util.Arrays;

public interface Mappable {

    void render();

    static double[] stringToLatLon(String location){
        var splits = location.split(",");
        double lat = Double.valueOf(splits[0]);
        double lng = Double.valueOf(splits[1]);
        return new double[]{lat, lng};
    }

}//Mappable interface


//Abstract Class can have FIELDS, while Interface cannot
//Abstract Class can have constructors, while Interface cannot

abstract class Point implements Mappable{

    private double[] location = new double[2];

    public Point(String location){
        this.location = Mappable.stringToLatLon(location);
    }

    //becuase this class is abstract, no error occurrs even when Mappable methods aren't implemented
    //but here, we will just override it

    @Override
    public void render() {
        //here, 'this' refers to the current instance of the Point class
        System.out.println("Render " + this + " as POINT (" + location() + ")");
    }

    private String location(){
        return Arrays.toString(location);
    }

}//Point class

abstract class Line implements Mappable{

    private double[][] locations;

    //accepts a variable number of String arguments(0 or more String values)
    public Line(String... locations){
        this.locations = new double[locations.length][];
        int index = 0;
        for(var location : locations){
            this.locations[index++] = Mappable.stringToLatLon(location);
        }
    }

    @Override
    public void render() {
        System.out.println("Render " + this + " as Line (" + locations()+ ")");
    }

    private String locations(){
        //Converting multiple layered arrays into a string
        return Arrays.deepToString(locations);
    }

}//Line class