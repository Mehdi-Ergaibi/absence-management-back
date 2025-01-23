package pfe.abscence.management.student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pfe.abscence.management.abscence.Abscence;
import pfe.abscence.management.types.Semestre;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByFiliereFiliereId(Long filiereId);
    List<Abscence> findByAbsencesAbscenceId(Long abscenceId);
    List<Student> findBySemestre(Semestre semestre);
    Optional<Student> findByCne(String cne);
    boolean existsByCne(String cne);
    void deleteByCne(String cne);
    
    @Query("SELECT new map(s.cne as cne, s.FirstName as firstName, s.LastName as lastName) " +
           "FROM Student s " +
           "JOIN s.modules m " +
           "WHERE s.filiere.name = :filiereName AND m.name = :moduleName")
    List<Map<String, Object>> findStudentsByFiliereAndModule(String filiereName, String moduleName);

}

