package cyber.punks.wzas.rest.controller;


import cyber.punks.wzas.exceptions.AccountDoesNotExistException;
import cyber.punks.wzas.rest.model.account.AccountDto;
import cyber.punks.wzas.rest.model.location.PointDto;
import cyber.punks.wzas.services.interfaces.AccountService;
import cyber.punks.wzas.services.interfaces.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cyber.punks.wzas.rest.model.location.PositionDto;

import java.util.List;

@RestController
@RequestMapping("location")
public class LocationController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPosition(@RequestBody PositionDto positionDto) {
        try {
            accountService.getAccount(positionDto.getAccount());
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
        positionService.addPosition(positionDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/current/{login}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editCurrentPosition(@PathVariable String login,
                                    @RequestBody PointDto positionDto) {
        try {
            positionService.setCurrentPosition(positionDto, login);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/destination/{login}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editDestinationPosition(@PathVariable String login,
                                        @RequestBody PointDto positionDto) {
        try {
            positionService.setDestinationPosition(positionDto, login);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/destination/{login}")
    public ResponseEntity<?> removerDestinationPosition(@PathVariable String login) {
        try {
            positionService.removeDestinationPosition(login);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PositionDto> getAllPositions() {
        return positionService.getAllPositions();
    }


    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PositionDto> getPositionByLogin(@PathVariable String login) {
        try {
            return ResponseEntity.ok(positionService.getPosition(login).get());
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
