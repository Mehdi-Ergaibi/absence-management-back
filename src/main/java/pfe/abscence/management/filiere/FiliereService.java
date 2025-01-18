package pfe.abscence.management.filiere;

import java.util.List;

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
}

