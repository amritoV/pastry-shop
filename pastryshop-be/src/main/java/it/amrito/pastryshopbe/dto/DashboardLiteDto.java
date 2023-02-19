package it.amrito.pastryshopbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardLiteDto {
    @NotNull
    private Long typologicalSweetId;
    @NotNull
    private LocalDate productionDate;
    @NotNull
    @Min(0)
    private Integer counter;
}
