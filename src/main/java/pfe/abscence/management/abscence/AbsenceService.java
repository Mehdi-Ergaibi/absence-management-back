package pfe.abscence.management.abscence;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    /* @Autowired
    private ElementRepository elementRepository; */

    public Abscence addAbsence(Abscence absence) {

        return absenceRepository.save(absence);
    }

    public List<AbsenceDTO> getAbsencesByStudent(String cne) {
        return absenceRepository.findAbsencesByStudentCne(cne);
    }
    public List<Abscence> getAbsencesByElement(Long elementId) {
        return absenceRepository.findByElementElementId(elementId);
    }
    public List<Abscence> getAbsencesByDate(LocalDate date) {
        return absenceRepository.findByDate(date);
    }
    
    public void deleteAbsence(Long id) {
        absenceRepository.deleteById(id);
    }
    
    public double getTotalDurationByStudentAndElement(String cne, Long elementId) {
        List<Abscence> absences = absenceRepository.findByStudentAndElement(cne, elementId);
        System.out.println(calculateTotalDuration(absences));
        return calculateTotalDuration(absences);
    }

    public double getTotalDurationByStudentAndModule(String cne, Long moduleId) {
        List<Abscence> absences = absenceRepository.findByStudentAndModule(cne, moduleId);

        return calculateTotalDuration(absences);
    }

    private double calculateTotalDuration(List<Abscence> absences) {
        return absences.stream()
                .mapToDouble(a -> {
                    LocalTime startTime = a.getStartTime();
                    LocalTime endTime = a.getEndTime();
                    return Duration.between(startTime, endTime).toMinutes() / 60.0;
                })
                .sum();
    }
    public boolean canPassNormalExamByElement(String cne, Long elementId) {
        double totalDuration = getTotalDurationByStudentAndElement(cne, elementId);
        return totalDuration <= 9; // 9 heures par element
    }

    public boolean canPassNormalExamByModule(String cne, Long moduleId) {
        double totalDuration = getTotalDurationByStudentAndModule(cne, moduleId);
        return totalDuration <= 24; // 24 heures par module
    }

    public void updateProof(Long absenceId, String motif, String proofPath) {
        Abscence abscence = absenceRepository.findById(absenceId)
            .orElseThrow(() -> new RuntimeException("Absence not found"));

        abscence.setProof(proofPath);
        abscence.setMotif(motif);
        absenceRepository.save(abscence);
    }
}
