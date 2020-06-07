package cyber.punks.wzas.rest.controller;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import cyber.punks.wzas.services.interfaces.PositionService;
import cyber.punks.wzas.utils.PointUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.vividsolutions.jts.geom.Point;


@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    PositionService positionService;

    @PostMapping(value = "create-test-data")
    public void createTestData(@RequestParam int amount) {
        for(int i = 0; i < amount; i++){
            positionService.addTestPosition(PointUtils.getRandomPoint(), PointUtils.getRandomDestination());
        }
    }

    @DeleteMapping(value = "delete")
    public void deleteAllTestPositions(){
        positionService.deleteAllTesPositions();
    }


}
