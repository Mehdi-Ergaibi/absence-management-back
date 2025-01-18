package pfe.abscence.management.module;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pfe.abscence.management.filiere.Filiere;
import pfe.abscence.management.student.Student;
import pfe.abscence.management.types.Semestre;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private long moduleId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Semestre semestre;

    @ManyToMany(mappedBy = "modules")
    private List<Student> students = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "filiereId")
    private Filiere filiere;
}
