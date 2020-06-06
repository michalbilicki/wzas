package cyber.punks.wzas.rest.model.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
public class AllPositionDto {

    @NotBlank
    @Getter
    @Setter
    private List<CoordinateDto> cuurents;

    @NotBlank
    @Getter
    @Setter
    private List<CoordinateDto> destinations;
}
