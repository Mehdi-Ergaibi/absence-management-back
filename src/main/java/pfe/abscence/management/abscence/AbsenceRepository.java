package pfe.abscence.management.abscence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceRepository extends JpaRepository<Abscence, Long> {
    List<Abscence> findByStudentCne(String cne);
    List<Abscence> findByElementElementId(Long elementId);
    List<Abscence> findByDate(LocalDate date);

    @Query("SELECT a FROM Abscence a WHERE a.student.cne = :cne AND a.element.elementId = :elementId")
    List<Abscence> findByStudentAndElement(@Param("cne") String cne, @Param("elementId") Long elementId);

    @Query("SELECT a FROM Abscence a JOIN a.element e WHERE a.student.cne = :cne AND e.module.moduleId = :moduleId")
    List<Abscence> findByStudentAndModule(@Param("cne") String cne, @Param("moduleId") Long moduleId);
}
