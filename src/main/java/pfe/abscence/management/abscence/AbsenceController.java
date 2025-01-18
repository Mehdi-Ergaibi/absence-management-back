package pfe.abscence.management.abscence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/absences")
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @PostMapping("/add-absences")
    public ResponseEntity<List<Abscence>> createAbsences(@RequestBody List<Abscence> absences) {
        List<Abscence> savedAbsences = absences.stream()
            .map(absenceService::addAbsence)
            .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAbsences);
    }


    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Abscence>> getAbsencesByStudent(@PathVariable String cne) {
        return ResponseEntity.ok(absenceService.getAbsencesByStudent(cne));
    }
    @GetMapping("/student/{elementId}")
    public ResponseEntity<List<Abscence>> getAbsencesByElement(@PathVariable Long elementId) {
        return ResponseEntity.ok(absenceService.getAbsencesByElement(elementId));
    }
    @GetMapping("/student/{date}")
    public ResponseEntity<List<Abscence>> getAbsencesByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok(absenceService.getAbsencesByDate(date));
    }

    @GetMapping("/element/{cne}/{elementId}")
    public ResponseEntity<Double> getTotalDurationByStudentAndElement(
            @PathVariable String cne, 
            @PathVariable Long elementId) {
        double totalDuration = absenceService.getTotalDurationByStudentAndElement(cne, elementId);
        return ResponseEntity.ok(totalDuration);
    }

    @GetMapping("/module/{cne}/{moduleId}")
    public ResponseEntity<Double> getTotalDurationByStudentAndModule(
            @PathVariable String cne, 
            @PathVariable Long moduleId) {
        double totalDuration = absenceService.getTotalDurationByStudentAndModule(cne, moduleId);
        return ResponseEntity.ok(totalDuration);
    }
    @GetMapping("/element-exam-status/{cne}/{elementId}")
    public ResponseEntity<String> getExamStatusByElement(
            @PathVariable String cne, 
            @PathVariable Long elementId) {
        boolean canPassNormalExam = absenceService.canPassNormalExamByElement(cne, elementId);
        String status = canPassNormalExam
                ? "L'etudiant a le droit de passer normal."
                : "l'etudiant n'a pas le droit";
        return ResponseEntity.ok(status);
    }

    @GetMapping("/module-exam-status/{cne}/{moduleId}")
    public ResponseEntity<String> getExamStatusByModule(
            @PathVariable String cne, 
            @PathVariable Long moduleId) {
        boolean canPassNormalExam = absenceService.canPassNormalExamByModule(cne, moduleId);
        String status = canPassNormalExam
                ? "L'etudiant a le droit de passer normal."
                : "l'etudiant n'a pas le droit";
        return ResponseEntity.ok(status);
    }
}
