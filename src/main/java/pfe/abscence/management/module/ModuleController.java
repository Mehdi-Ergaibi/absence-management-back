package pfe.abscence.management.module;

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

import pfe.abscence.management.types.Semestre;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping("/create")
    public ResponseEntity<Module> createModule(@RequestBody Module module) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.createModule(module));
    }

    @GetMapping("/all")
    public List<Module> getAllModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/filiere/{filiereId}")
    public List<Module> getModulesByFiliereId(@PathVariable Long filiereId) {
        return moduleService.getModulesByFiliereId(filiereId);
    }

    @GetMapping("/filiere_and_semestre/{filiereName}/{semestre}")
    public List<String> getModuleNamesByFiliereAndSemestre(@PathVariable String filiereName, @PathVariable Semestre semestre) {
        return moduleService.getModulesByFilierAndSemestre(filiereName, semestre).stream().map(Module::getName).toList(); 
    }
    @GetMapping("/{moduleId}")
    public ResponseEntity<Module> getOne(@PathVariable Long moduleId) {
        try {
            Module module = moduleService.getOne(moduleId);
            return ResponseEntity.ok(module);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{moduleId}")
    public ResponseEntity<Module> updateModule(@PathVariable Long moduleId, @RequestBody Module updatedModule) {
        try {
            Module updated = moduleService.updateModule(moduleId, updatedModule);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long moduleId) {
        try {
            moduleService.deleteModule(moduleId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

