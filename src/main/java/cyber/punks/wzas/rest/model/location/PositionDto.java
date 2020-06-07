package cyber.punks.wzas.rest.model.location;

import cyber.punks.wzas.entities.AccountEntity;
import cyber.punks.wzas.entities.PositionEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PositionDto {

    @NotBlank
    @Getter
    @Setter
    private String account;

    @NotNull
    @Getter
    @Setter
    private PointDto current;

    @Getter
    @Setter
    private PointDto destination;


    public static PositionDto convertEntityToDto(PositionEntity positionEntity) {
        PositionDto accountDto = new PositionDto();
        if (positionEntity.getAccountEntity() != null) {
            accountDto.account = positionEntity.getAccountEntity().getLogin();
        } else {
            accountDto.account = null;
        }
        accountDto.current = PointDto.convertToDto(positionEntity.getCurrent());
        accountDto.destination = PointDto.convertToDto(positionEntity.getDestination());
        return accountDto;
    }

    public static PositionEntity convertDtoToEntity(PositionDto positionDto, AccountEntity accountEntity) {
        PositionEntity positionEntity = new PositionEntity(accountEntity);
        positionEntity.setDestination((positionDto.getDestination() != null) ? PointDto.convertFromDto(positionDto.getDestination()) : null);
        positionEntity.setCurrent((positionDto.getCurrent() != null) ? PointDto.convertFromDto(positionDto.getCurrent()) : null);
        return positionEntity;
    }


}
