package cyber.punks.wzas.rest.model.location;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointDto {

    private final static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    private double x;
    private double y;

    public static PointDto convertToDto(Point point) {
        return new PointDto(point.getX(), point.getY());
    }

    public static Point convertFromDto(PointDto pointDto) {
        return geometryFactory.createPoint(new Coordinate(pointDto.getX(), pointDto.getY()));
    }
}
