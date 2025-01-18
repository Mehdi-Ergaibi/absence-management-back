package pfe.abscence.management.abscence;


import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pfe.abscence.management.element.Element;
import pfe.abscence.management.student.Student;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Abscence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long abscenceId;

    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;
    
    private String motif;
    private String proof;

    @ManyToOne
    @JoinColumn(name = "cne", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "elementId", nullable = false)
    private Element element;
}
