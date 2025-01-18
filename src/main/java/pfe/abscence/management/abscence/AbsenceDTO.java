package pfe.abscence.management.abscence;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AbsenceDTO {
    private String elementName;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private Long abscenceId;


}
