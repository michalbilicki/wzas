package cyber.punks.wzas.services.interfaces;

import cyber.punks.wzas.exceptions.AccountDoesNotExistException;
import cyber.punks.wzas.rest.model.location.PointDto;
import cyber.punks.wzas.rest.model.location.PositionDto;

import java.util.List;
import java.util.Optional;

public interface PositionService {

    void addPosition(PositionDto positionDto);

    void setCurrentPosition(PointDto currentPosition, String login) throws AccountDoesNotExistException;

    void setDestinationPosition(PointDto destinationPosition, String login) throws AccountDoesNotExistException;

    void removeDestinationPosition(String login) throws AccountDoesNotExistException;

    Optional<PositionDto> getPosition(String login) throws AccountDoesNotExistException;

    List<PositionDto> getAllPositions();
}
