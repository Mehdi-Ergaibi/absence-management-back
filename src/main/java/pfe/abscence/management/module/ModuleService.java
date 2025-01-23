package pfe.abscence.management.module;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pfe.abscence.management.types.Semestre;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    public List<Module> getModulesByFiliereId(Long filiereId) {
        return moduleRepository.findByFiliereFiliereId(filiereId);
    }

    public List<Module> getModulesBySemestre(Semestre semestre) {
        return moduleRepository.findBySemestre(semestre);
    }
    
    public List<Module> getModulesByFilierAndSemestre(String filiereName, Semestre semestre){
        return moduleRepository.findByFiliereNameAndSemestre(filiereName, semestre);
    }
    public Module getOne(Long moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with id " + moduleId));
    }

    public Module updateModule(Long moduleId, Module updatedModule) {
        Optional<Module> existingModule = moduleRepository.findById(moduleId);

        if (existingModule.isPresent()) {
            Module module = existingModule.get();
            module.setName(updatedModule.getName());
            module.setSemestre(updatedModule.getSemestre());
            module.setFiliere(updatedModule.getFiliere());
            return moduleRepository.save(module);
        } else {
            throw new RuntimeException("Module not found with id " + moduleId);
        }
    }

    public void deleteModule(Long moduleId) {
        if (moduleRepository.existsById(moduleId)) {
            moduleRepository.deleteById(moduleId);
        } else {
            throw new RuntimeException("Module not found with id " + moduleId);
        }
    }
}

