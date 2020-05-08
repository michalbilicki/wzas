package cyber.punks.wzas.services;


import cyber.punks.wzas.exceptions.AccountDoesNotExistException;
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
    public void addPosition(PositionDto positionDto) {
        Optional<AccountEntity> account = accountRepository.findByLogin(positionDto.getAccount());
        PositionEntity position = PositionDto.convertDtoToEntity(positionDto, account.get());
        locationRepository.save(position);
    }

    @Override
    public void setCurrentPosition(PointDto currentPosition, String login) throws AccountDoesNotExistException {
        Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if(entityOptional.isPresent()){
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
        if(entityOptional.isPresent()){
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }

        PositionEntity position = entityOptional.get();
        position.setDestination(PointDto.convertFromDto(destinationPosition));
        locationRepository.save(position);
    }

    @Override
    public void removeDestinationPosition(String login) throws AccountDoesNotExistException{
        Optional<PositionEntity> entityOptional = locationRepository.findByAccount(login);
        if(entityOptional.isPresent()){
            throw new AccountDoesNotExistException("accountDoesNotExists");
        }
        PositionEntity position = entityOptional.get();
        position.setDestination(null);
        locationRepository.save(position);
    }

    @Override
    public Optional<PositionDto> getPosition(String login) throws AccountDoesNotExistException{
        Optional<PositionEntity> positionEntity = locationRepository.findByAccount(login);
        if(positionEntity.isPresent()){
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

}
