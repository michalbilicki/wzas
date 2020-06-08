package cyber.punks.wzas.entities;

import com.vividsolutions.jts.geom.Point;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "position")
public class PositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;


    @Column(name = "current", unique = false, columnDefinition = "geometry(Point,4326)")
    @Getter
    @Setter
    private Point current;

    @Column(name = "destination", unique = false, columnDefinition = "geometry(Point,4326)")
    @Getter
    @Setter
    private Point destination;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    @Getter
    private AccountEntity accountEntity;


    public PositionEntity() {

    }

    public PositionEntity(Point current, Point destination){
        this.current = current;
        this.destination = destination;
    }

    public PositionEntity(AccountEntity accountEntity, Point current, Point destination) {
        this.current = current;
        this.destination = destination;
        this.accountEntity = accountEntity;
    }


    public PositionEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }
}