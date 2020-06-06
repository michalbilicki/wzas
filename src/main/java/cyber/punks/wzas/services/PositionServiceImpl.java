package cyber.punks.wzas.services;


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
        if(check.isPresent()){
            throw new AccountHasPositionAlready("accountHasAlreadPosition");
        }
        Optional<AccountEntity> account = accountRepository.findByLogin(positionDto.getAccount());

        PositionEntity position = PositionDto.convertDtoToEntity(positionDto, account.get());
        locationRepository.save(position);
    }

    @Override
    public void setCurrentPosition(PointDto currentPosition, String login) throws AccountDoesNotExistException {
        Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if(!entityOptional.isPresent()){
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }

        PositionEntity position = entityOptional.get();
        System.out.println(position);
        position.setCurrent(PointDto.convertFromDto(currentPosition));
        locationRepository.save(position);
    }

    @Override
    public void setDestinationPosition(PointDto destinationPosition, String login) throws AccountDoesNotExistException {
        Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if(!entityOptional.isPresent()){
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }

        PositionEntity position = entityOptional.get();
        position.setDestination(PointDto.convertFromDto(destinationPosition));
        locationRepository.save(position);
    }

    @Override
    public void removeDestinationPosition(String login) throws AccountDoesNotExistException{
        Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if(!entityOptional.isPresent()){
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }
        PositionEntity position = entityOptional.get();
        position.setDestination(null);
        locationRepository.save(position);
    }

    @Override
    public void removePosition(String login) throws AccountDoesNotExistException{
        Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if(!entityOptional.isPresent()){
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }
        locationRepository.delete(entityOptional.get());
    }

    @Override
    public Optional<PositionDto> getPosition(String login) throws AccountDoesNotExistException{
        Optional<PositionEntity> positionEntity = locationRepository.findByAccount(login);
        if(!positionEntity.isPresent()){
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
    public AllPositionDto getPositionsAroundPoints(String login) {
        Optional<PositionEntity> positionEntity = locationRepository.findByAccount(login);
        PositionDto positionDto = PositionDto.convertEntityToDto(positionEntity.get());

        List<CoordinateDto> currents = new ArrayList<>();
        List<CoordinateDto> destinations = new ArrayList<>();


        List<PositionDto> allPositions = locationRepository.findAll()
                .stream()
                .map(PositionDto::convertEntityToDto)
                .collect(Collectors.toList());


        for (PositionDto position: allPositions) {
            if(!position.getAccount().equals(positionEntity.get().getAccountEntity().getLogin())){
                if(position.getCurrent() != null){
                    if(pointIsAround(position.getCurrent(), positionDto.getCurrent())){
                        currents.add(new CoordinateDto(position.getAccount(), position.getCurrent()));
                    }
                } else {
                    currents.add(new CoordinateDto(position.getAccount(), null));
                }
                if(positionDto.getDestination() != null){
                    if(position.getDestination() != null){
                        if(pointIsAround(position.getDestination(), positionDto.getDestination())){
                            destinations.add(new CoordinateDto(position.getAccount(), position.getDestination()));
                        }
                    } else {
                        destinations.add(new CoordinateDto(position.getAccount(), null));
                    }
                }
            }
        }


        return new AllPositionDto(currents, destinations);
    }


    private boolean pointIsAround(PointDto point, PointDto centerPoint){
        if(!(point.getLatitude() > centerPoint.getLatitude() - RestConstatnts.RADIUS) || !(point.getLatitude() < centerPoint.getLatitude() + RestConstatnts.RADIUS)){
            return false;
        }
        if(!(point.getLongitude() > centerPoint.getLongitude() - RestConstatnts.RADIUS) || !(point.getLongitude() < centerPoint.getLongitude() + RestConstatnts.RADIUS)){
            return false;
        }
        return true;
    }


}
