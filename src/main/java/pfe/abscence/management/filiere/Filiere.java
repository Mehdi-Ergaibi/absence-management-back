package pfe.abscence.management.filiere;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import pfe.abscence.management.module.Module;
import pfe.abscence.management.student.Student;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long filiereId;

    private String name;
    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL)
    private List<Module> modules = new ArrayList<>();

    @OneToMany(mappedBy = "filiere")
    private List<Student> students = new ArrayList<>();

}
