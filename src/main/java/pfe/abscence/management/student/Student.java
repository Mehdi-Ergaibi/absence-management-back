package pfe.abscence.management.student;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pfe.abscence.management.abscence.Abscence;
import pfe.abscence.management.filiere.Filiere;
import pfe.abscence.management.module.Module;
import pfe.abscence.management.types.Semestre;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Student {

    @Id
    private String cne;
    private String FirstName;
    private String LastName;
    private String Email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Semestre semestre;  

    @ManyToMany
    @JoinTable(
        name = "student_module",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "module_id")
    )
    private List<Module> modules = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Abscence> absences = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "filiereId", nullable = false)
    private Filiere filiere;

}