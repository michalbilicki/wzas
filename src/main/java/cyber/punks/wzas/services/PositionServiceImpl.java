package cyber.punks.wzas.services;


import com.vividsolutions.jts.geom.Point;
import cyber.punks.wzas.exceptions.AccountDoesNotExistException;
import cyber.punks.wzas.exceptions.AccountHasPositionAlready;
import cyber.punks.wzas.rest.RestConstatnts;
import cyber.punks.wzas.rest.model.location.AllPositionDto;
import cyber.punks.wzas.rest.model.location.CoordinateDto;
import cyber.punks.wzas.rest.model.location.PointDto;
import cyber.punks.wzas.rest.model.location.PositionDto;
import cyber.punks.wzas.repository.AccountRepository;
import cyber.punks.wzas.repository.LocationRepository;
import cyber.punks.wzas.services.interfaces.PositionService;
import cyber.punks.wzas.entities.AccountEntity;
import cyber.punks.wzas.entities.PositionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
public class PositionServiceImpl implements PositionService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    AccountRepository accountRepository;


    @Override
    public void addPosition(PositionDto positionDto) throws AccountHasPositionAlready {
        Optional<PositionEntity> check = locationRepository.findByAccount(positionDto.getAccount());
        if (check.isPresent()) {
            throw new AccountHasPositionAlready("accountHasAlreadPosition");
        }
        Optional<AccountEntity> account = accountRepository.findByLogin(positionDto.getAccount());
        PositionEntity position = PositionDto.convertDtoToEntity(positionDto, account.get());
        locationRepository.save(position);
    }

    @Override
    public void setCurrentPosition(PointDto currentPosition, String login) throws AccountDoesNotExistException {
        Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if (!entityOptional.isPresent()) {
            Optional<AccountEntity> accountEntity = accountRepository.findByLogin(login);

            if (!accountEntity.isPresent())
                throw new AccountDoesNotExistException("accountDoesNotExists");

            Point point = this.convertToPoint(currentPosition);
            PositionEntity positionEntity = new PositionEntity(accountEntity.get(), point, null);
            locationRepository.save(positionEntity);
            return;
        }

        PositionEntity position = entityOptional.get();
        Point point = this.convertToPoint(currentPosition);

        position.setCurrent(point);
        locationRepository.save(position);
    }

    @Override
    public void setDestinationPosition(PointDto destinationPosition, String login) throws AccountDoesNotExistException {
        Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if (!entityOptional.isPresent()) {
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }

        PositionEntity position = entityOptional.get();
        Point point = this.convertToPoint(destinationPosition);

        position.setDestination(PointDto.convertFromDto(destinationPosition));
        locationRepository.save(position);
    }

    @Override
    public void removeDestinationPosition(String login) throws AccountDoesNotExistException {
        Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if (!entityOptional.isPresent()) {
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }
        PositionEntity position = entityOptional.get();
        position.setDestination(null);
        locationRepository.save(position);
    }

    @Override
    public void removePosition(String login) throws AccountDoesNotExistException {
       Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if (!entityOptional.isPresent()) {
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }
        PositionEntity positionEntity = entityOptional.get();
        positionEntity.setCurrent(null);
        positionEntity.setDestination(null);
        locationRepository.save(positionEntity);
    }

    @Override
    public Optional<PositionDto> getPosition(String login) throws AccountDoesNotExistException {
        Optional<PositionEntity> positionEntity = locationRepository.findByAccount(login);
        if (!positionEntity.isPresent()) {
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }
        return Optional.of(PositionDto.convertEntityToDto(positionEntity.get()));
    }

    @Override
    public List<PositionDto> getAllPositions() {
        return locationRepository.findAll()
                .stream()
                .map(PositionDto::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AllPositionDto getPositionsAroundPoints(double x, double y) {
        List<CoordinateDto> currents = new ArrayList<>();
        List<CoordinateDto> destinations = new ArrayList<>();


        List<PositionDto> allPositions = locationRepository.findByRadius(x, y, RestConstatnts.RADIUS)
                .stream()
                .map(PositionDto::convertEntityToDto)
                .collect(Collectors.toList());


        for (PositionDto position : allPositions) {
            currents.add(new CoordinateDto(position.getAccount(), position.getCurrent()));
            destinations.add(new CoordinateDto(position.getAccount(), position.getDestination()));

        }

        return new AllPositionDto(currents, destinations);
    }

    @Override
    public void addTestPosition(Point position, Point destination) {
        PositionEntity positionEntity = new PositionEntity(position, destination);
        locationRepository.save(positionEntity);
    }

    @Override
    public void deleteAllTesPositions() {
        locationRepository.deleteAllTestPositions();
    }


    private boolean pointIsAround(PointDto point, PointDto centerPoint) {
        if (!(point.getLatitude() > centerPoint.getLatitude() - RestConstatnts.RADIUS) || !(point.getLatitude() < centerPoint.getLatitude() + RestConstatnts.RADIUS)) {
            return false;
        }
        if (!(point.getLongitude() > centerPoint.getLongitude() - RestConstatnts.RADIUS) || !(point.getLongitude() < centerPoint.getLongitude() + RestConstatnts.RADIUS)) {
            return false;
        }
        return true;
    }


    private Point convertToPoint(PointDto pointDto) throws AccountDoesNotExistException {
        Point point = PointDto.convertFromDto(pointDto);
        if (point.getX() == 0 && point.getY() == 0) {
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }
        return point;
    }

}
