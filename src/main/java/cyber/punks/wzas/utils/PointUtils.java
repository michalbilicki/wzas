package cyber.punks.wzas.utils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import java.util.Random;

public class PointUtils {

    private final static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);


    public static Point getRandomDestination(){
        Random random = new Random();
        int index = random.nextInt(4);
        Point [] points = getDestinationPoints();
        return points[index];
    }

    public static Point getRandomPoint(){
        double x = getRandomDoubleInRange(51.7555781,51.7721407);
        double y = getRandomDoubleInRange(19.4403786,19.4883816);
        return geometryFactory.createPoint(new Coordinate(x,y));
    }


    public static double getRandomDoubleInRange(double min, double max){
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    private static Point[] getDestinationPoints() {
        Point [] points = new Point[4];
        points[0] = geometryFactory.createPoint(new Coordinate(51.7516032,19.4458585));
        points[1] = geometryFactory.createPoint(new Coordinate(51.7456681,19.4551188));
        points[2] = geometryFactory.createPoint(new Coordinate(51.757333,19.4444537));
        points[3] = geometryFactory.createPoint(new Coordinate(51.7492351,19.4775722));
        return points;
    }
}
