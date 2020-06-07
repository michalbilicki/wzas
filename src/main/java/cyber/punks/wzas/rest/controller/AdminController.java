package cyber.punks.wzas.rest.controller;

;
import cyber.punks.wzas.services.interfaces.PositionService;
import cyber.punks.wzas.utils.PointUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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
