package pfe.abscence.management.abscence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pfe.abscence.management.types.Semestre;

@RestController
@RequestMapping("/api/absences")
public class AbsenceController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private AbsenceService absenceService;

    @PostMapping("/add-absences")
    public ResponseEntity<List<Abscence>> createAbsences(@RequestBody List<Abscence> absences) {
        List<Abscence> savedAbsences = absences.stream()
            .map(absenceService::addAbsence)
            .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAbsences);
    }


    @GetMapping("/student/{cne}")
    public ResponseEntity<List<AbsenceDTO>> getAbsencesByStudent(@PathVariable String cne) {
        return ResponseEntity.ok(absenceService.getAbsencesByStudent(cne));
    }

    
    @GetMapping("/element/{elementId}")
    public ResponseEntity<List<Abscence>> getAbsencesByElement(@PathVariable Long elementId) {
        return ResponseEntity.ok(absenceService.getAbsencesByElement(elementId));
    }
    @GetMapping("/{date}")
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

    @PostMapping("/{absenceId}/{motif}/upload-proof")
    public ResponseEntity<String> uploadProof(
        @PathVariable Long absenceId, 
        @PathVariable String motif, 
        @RequestParam("file") MultipartFile file) {
    try {
        // Determine file type using file content, not file path
        String fileType = file.getContentType();
        if (fileType == null || 
            (!fileType.startsWith("image") && !fileType.equals("application/pdf"))) {
            return ResponseEntity.badRequest().body("Unsupported file type.");
        }

        // Save the file to the specified directory
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());

        // Update the absence proof path in the database
        absenceService.updateProof(absenceId, motif, filePath.toString());

        return ResponseEntity.ok("File uploaded successfully.");
    } catch (IOException e) {
        return ResponseEntity.status(500).body("File upload failed.");
    }
}

    @GetMapping("/filiere/{filiereName}/bilan")
    public ResponseEntity<List<StudentAbsenceSummaryDTO>> getAbsenceSummaryByFiliere(
            @PathVariable String filiereName,
            @RequestParam Semestre semestre,
            @RequestParam String type) {

        try {
/*             System.out.println("in try");
 */            List<StudentAbsenceSummaryDTO> absenceSummary = absenceService.getAbsenceSummaryByFiliere(filiereName, semestre, type);
            return ResponseEntity.ok(absenceSummary);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
}
