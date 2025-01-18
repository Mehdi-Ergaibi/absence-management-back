package pfe.abscence.management.module;

import java.util.List;

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
}

