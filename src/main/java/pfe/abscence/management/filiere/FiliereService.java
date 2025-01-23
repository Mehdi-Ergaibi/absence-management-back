package pfe.abscence.management.filiere;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FiliereService {

    @Autowired
    private FiliereRepository filiereRepository;

    public Filiere createFiliere(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filiere getFiliereById(Long id) {
        return filiereRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filiere not found"));
    }

    public void deleteFiliere(Long id) {
        filiereRepository.deleteById(id);
    }

    public List<String> getFiliersNames() {
        return filiereRepository.findAllFiliereNames();
    }
    public Filiere updateFiliere(Long filiereId, Filiere updatedFiliere) {
        Optional<Filiere> existingFiliere = filiereRepository.findById(filiereId);

        if (existingFiliere.isPresent()) {
            Filiere filiere = existingFiliere.get();
            filiere.setName(updatedFiliere.getName());
            return filiereRepository.save(filiere);
        } else {
            throw new RuntimeException("Filiere not found with id " + filiereId);
        }
    }
}

