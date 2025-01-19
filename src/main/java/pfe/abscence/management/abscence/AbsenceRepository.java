package pfe.abscence.management.abscence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pfe.abscence.management.types.Semestre;

@Repository
public interface AbsenceRepository extends JpaRepository<Abscence, Long> {
    List<Abscence> findByStudentCne(String cne);
    List<Abscence> findByElementElementId(Long elementId);
    List<Abscence> findByDate(LocalDate date);

    @Query("SELECT a FROM Abscence a WHERE a.student.cne = :cne AND a.element.elementId = :elementId AND a.proof IS NULL")
    List<Abscence> findByStudentAndElement(@Param("cne") String cne, @Param("elementId") Long elementId);
    

    @Query("SELECT a FROM Abscence a JOIN a.element e WHERE a.student.cne = :cne AND e.module.moduleId = :moduleId AND a.proof IS NULL")
    List<Abscence> findByStudentAndModule(@Param("cne") String cne, @Param("moduleId") Long moduleId);


    @Query("SELECT new pfe.abscence.management.abscence.AbsenceDTO(" +
           "a.element.name, a.startTime, a.endTime, a.date, a.abscenceId) " +
           "FROM Abscence a WHERE a.student.cne = :cne")
    List<AbsenceDTO> findAbsencesByStudentCne(@Param("cne") String cne);


    @Query("SELECT new pfe.abscence.management.abscence.StudentAbsenceSummaryDTO(" +
    "a.student.cne, CONCAT(a.student.FirstName, ' ', a.student.LastName), " +
    "CAST(SUM(TIMESTAMPDIFF(SECOND, a.startTime, a.endTime)) AS DOUBLE) / 3600.0, e.name) " +
    "FROM Abscence a " +
    "JOIN a.element e " +
    "WHERE a.student.filiere.name = :filiereName AND a.student.semestre = :semestre " +
    "GROUP BY a.student.cne, CONCAT(a.student.FirstName, ' ', a.student.LastName), e.name")
    List<StudentAbsenceSummaryDTO> getAbsenceSummaryByElementAndFiliere(
    @Param("filiereName") String filiereName, @Param("semestre") Semestre semestre);

    @Query("SELECT new pfe.abscence.management.abscence.StudentAbsenceSummaryDTO(" +
    "a.student.cne, CONCAT(a.student.FirstName, ' ', a.student.LastName), " +
    "CAST(SUM(TIMESTAMPDIFF(SECOND, a.startTime, a.endTime)) AS DOUBLE) / 3600.0, m.name) " +
    "FROM Abscence a " +
    "JOIN a.element e " +
    "JOIN e.module m " +
    "JOIN a.student.filiere f " +
    "WHERE f.name = :filierName AND a.student.semestre = :semestre " +
    "GROUP BY a.student.cne, CONCAT(a.student.FirstName, ' ', a.student.LastName), m.name")
    List<StudentAbsenceSummaryDTO> getAbsenceSummaryByModuleAndFiliere(
    @Param("filierName") String filierName, @Param("semestre") Semestre semestre);


     


}
