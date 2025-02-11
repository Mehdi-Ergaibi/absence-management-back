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

// statistiqueeeeeeess booom ya dawla qarabt nsali
    @Query("SELECT COALESCE(SUM(TIMESTAMPDIFF(MINUTE, a.startTime, a.endTime) / 60.0), 0) FROM Abscence a " +
    "WHERE a.student.filiere.name = :filiere " +
    "AND a.student.semestre = :semestre " +
    "AND a.element.module.name = :module " +
    "AND a.element.name = :element")
    double getTotalAbsences(@Param("filiere") String filiere, 
                    @Param("semestre") Semestre semestre, 
                    @Param("module") String module, 
                    @Param("element") String element);

    // par moyenne par etudiant
    @Query("SELECT a.student.filiere.name, a.student.semestre, " +
    "AVG(TIMESTAMPDIFF(MINUTE, a.startTime, a.endTime) / 60.0) " +
    "FROM Abscence a " +
    "GROUP BY a.student.filiere.name, a.student.semestre " +
    "ORDER BY AVG(TIMESTAMPDIFF(MINUTE, a.startTime, a.endTime) / 60.0) DESC")
    List<Object[]> getTopFiliereSemestreAbsences();

    // Get students who don't have the right to pass based on total absence duration
    @Query("SELECT a.student.filiere.name, COUNT(DISTINCT a.student) FROM Abscence a " +
       "WHERE a.student.semestre = :semestre " +
       "GROUP BY a.student.filiere.name " +
       "HAVING SUM(TIMESTAMPDIFF(MINUTE, a.startTime, a.endTime) / 60.0) >= 24")
    List<Object[]> getStudentsWithoutRightsToPass(@Param("semestre") Semestre semestre);

    // Get absences by week (total duration in hours)
    @Query("SELECT WEEK(a.date), COALESCE(SUM(TIMESTAMPDIFF(MINUTE, a.startTime, a.endTime) / 60.0), 0) " +
       "FROM Abscence a " +
       "WHERE a.student.filiere.name = :filiere AND a.student.semestre = :semestre " +
       "GROUP BY WEEK(a.date)")
List<Object[]> getAbsencesByWeek(@Param("filiere") String filiere, @Param("semestre") Semestre semestre);


}
