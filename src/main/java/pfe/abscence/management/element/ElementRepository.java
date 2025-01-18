package pfe.abscence.management.element;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {
    List<Element> findByModuleName(String moduleName);
    Optional<Element> findByName(String name);
    
}
