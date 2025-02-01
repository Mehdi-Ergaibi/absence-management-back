package pfe.abscence.management.abscence;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pfe.abscence.management.element.Element;
import pfe.abscence.management.element.ElementService;
import pfe.abscence.management.student.Student;
import pfe.abscence.management.student.StudentService;
import pfe.abscence.management.types.Semestre;


@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ElementService elementService;

    @Autowired
    private StudentService studentService;

    /* @Autowired
    private ElementRepository elementRepository; */

    private void sendEmailNotification(Optional<Student> student, String message) {
        String subject = "Direction ESTL, absence!!";
        String to = student.get().getEmail();
        emailService.sendEmail(to, subject, message);
    }

    public Abscence addAbsence(Abscence absence) {
        Abscence savedAbsence = absenceRepository.save(absence);

        

        Element element = elementService.getElementById(absence.getElement().getElementId());
        Optional<Student> student = studentService.getStudentByCne(absence.getStudent().getCne());



        String message = String.format("Attention!!! vous avez été absent à %s, de %s à %s.",
        element.getName(),
        absence.getStartTime(),
        absence.getEndTime());

        System.out.println(message);
        System.out.println("student " +  student.get());

        sendEmailNotification(student, message);

        
        double totalDurationForElement = getTotalDurationByStudentAndElement(student.get().getCne(), element.getElementId());
        double totalDurationForModule = getTotalDurationByStudentAndModule(student.get().getCne(), element.getModule().getModuleId());

        if (totalDurationForElement > 6) {
            String messageElement = String.format("Attention!!! Vous avez dépassé 6 heures d'absence dans %s. Vous risquez de ne pas passer l'examen normal.",
                                                 element.getName());
            sendEmailNotification(student, messageElement);
        }
    
        // Notification si la durée d'absence dépasse 16 heures dans le module
        if (totalDurationForModule > 16) {
            String messageModule = String.format("Attention!!! Vous avez dépassé 16 heures d'absence dans %s. Vous risquez de ne pas passer l'examen normal.",
                                                 element.getModule().getName());
            sendEmailNotification(student, messageModule);
        }
        
        return savedAbsence;
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

    public List<StudentAbsenceSummaryDTO> getAbsenceSummaryByFiliere(String filiereId, Semestre semestre, String type) {
        if (type.equals("element")) {
            return absenceRepository.getAbsenceSummaryByElementAndFiliere(filiereId, semestre);
        } else if (type.equals("module")) {
            return absenceRepository.getAbsenceSummaryByModuleAndFiliere(filiereId, semestre);
        } else {
            throw new IllegalArgumentException("Invalid type. Must be 'element' or 'module'.");
        }
    }
}
