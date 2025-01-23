package pfe.abscence.management.filiere;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/filieres")
public class FiliereController {

    @Autowired
    private FiliereService filiereService;

    @PostMapping("/create")
    public ResponseEntity<Filiere> createFiliere(@RequestBody Filiere filiere) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filiereService.createFiliere(filiere));
    }

    @GetMapping("/all")
    public List<Filiere> getAllFilieres() {
        return filiereService.getAllFilieres();
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<Filiere> getFiliereById(@PathVariable Long id) {
        return ResponseEntity.ok(filiereService.getFiliereById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiliere(@PathVariable Long id) {
        filiereService.deleteFiliere(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/names")
    public List<String> getAllFiliersNames(){
        return filiereService.getFiliersNames();
    }

    @PutMapping("/{filiereId}")
    public ResponseEntity<Filiere> updateFiliere(
            @PathVariable Long filiereId, 
            @RequestBody Filiere updatedFiliere) {

        try {
            Filiere updated = filiereService.updateFiliere(filiereId, updatedFiliere);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

