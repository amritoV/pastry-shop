package it.amrito.pastryshopbe.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "schedaannuncio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "announcementform_generator")
    @SequenceGenerator(name = "announcementform_generator", sequenceName = "schedaannuncio_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "contatore")
    private Integer counter;

    @Column(name = "datacreazione")
    private LocalDate productionDate;

    @Column(name = "datapubblicazione")
    private LocalDateTime publicationDate;

    @Column(name = "backoffice")
    private String backOfficeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipologicadolce", nullable = false)
    private TypologicalSweetModel typologicalSweetModel;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardModel that = (DashboardModel) o;
        return Objects.equals(productionDate, that.productionDate) && Objects.equals(typologicalSweetModel, that.typologicalSweetModel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productionDate, typologicalSweetModel);
    }
}
