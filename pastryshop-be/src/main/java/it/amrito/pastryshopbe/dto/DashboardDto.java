package it.amrito.pastryshopbe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDto implements Comparable<DashboardDto>{

    private TypologicalSweetLiteDto typologicalSweetDto;
    private Integer counter;
    private LocalDate productionDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DashboardDto that = (DashboardDto) o;
        return Objects.equals(typologicalSweetDto, that.typologicalSweetDto) && Objects.equals(productionDate, that.productionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typologicalSweetDto, productionDate);
    }

    @Override
    public int compareTo(DashboardDto o) {
        return this.getProductionDate().compareTo(o.getProductionDate());
    }
}
