package pfe.abscence.management.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiliereSemestreStats {
    private String filiere;
    private Semestre semestre;
    private Double averageAbsences;

}
