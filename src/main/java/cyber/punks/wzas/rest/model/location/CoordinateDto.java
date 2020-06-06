package cyber.punks.wzas.rest.model.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class CoordinateDto {

    @NotBlank
    @Getter
    @Setter
    private String username;

    @NotNull
    @Getter
    @Setter
    private PointDto coordinate;
}
