package pfe.abscence.management.abscence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StudentAbsenceSummaryDTO {
    private String cne;
    private String name;
    private double totalDuration;
    private String elementOrModuleName;
}
