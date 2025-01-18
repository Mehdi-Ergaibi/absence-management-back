package pfe.abscence.management.element;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pfe.abscence.management.module.Module;
import pfe.abscence.management.abscence.Abscence;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long elementId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "moduleId", nullable = false)
    private Module module;

    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Abscence> absences = new ArrayList<>();

}
