package pfe.abscence.management.module;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pfe.abscence.management.types.Semestre;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByFiliereFiliereId(Long filiereId);
    List<Module> findBySemestre(Semestre semestre);
    List<Module> findByFiliereNameAndSemestre(String filierName, Semestre semestre);
}

