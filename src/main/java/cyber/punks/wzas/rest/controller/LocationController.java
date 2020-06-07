package cyber.punks.wzas.rest.controller;


import cyber.punks.wzas.entities.AccountEntity;
import cyber.punks.wzas.exceptions.AccountDoesNotExistException;
import cyber.punks.wzas.exceptions.AccountHasPositionAlready;
import cyber.punks.wzas.rest.model.account.AccountDto;
import cyber.punks.wzas.rest.model.location.AllPositionDto;
import cyber.punks.wzas.rest.model.location.PointDto;
import cyber.punks.wzas.services.interfaces.AccountService;
import cyber.punks.wzas.services.interfaces.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import cyber.punks.wzas.rest.model.location.PositionDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/location")
public class LocationController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPosition(@RequestBody @Valid PositionDto positionDto) {
        try {
            positionService.addPosition(positionDto);
        } catch (AccountHasPositionAlready e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/current", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editCurrentPosition(@RequestBody PointDto positionDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            positionService.setCurrentPosition(positionDto, (String) principal);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/destination/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editDestinationPosition(@RequestBody PointDto positionDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            positionService.setDestinationPosition(positionDto, (String) principal);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/destination/delete")
    public ResponseEntity<?> removerDestinationPosition() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            positionService.removeDestinationPosition((String) principal);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> removerPosition() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            positionService.removePosition((String) principal);
            return ResponseEntity.ok().build();
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PositionDto> getAllPositions() {
        return positionService.getAllPositions();
    }



    @GetMapping(value = "/own", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PositionDto> getPositionByLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            return ResponseEntity.ok(positionService.getPosition((String) principal).get());
        } catch (AccountDoesNotExistException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping(value = "/around", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPositionAroundUser(@RequestParam double latitude, @RequestParam double longitude) {
        return ResponseEntity.ok(positionService.getPositionsAroundPoints(latitude,longitude));
    }
}
