package pfe.abscence.management.student;

import java.util.List;
import java.util.Map;

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
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/one/{cne}")
    public ResponseEntity<Map<String, Object>> getStudentById(@PathVariable String cne) {
        return ResponseEntity.ok(studentService.getStudentById(cne));
    }

    @GetMapping("/filiere/{filiereId}")
    public List<Student> getStudentsByFiliereId(@PathVariable Long filiereId) {
        return studentService.getStudentsByFiliereId(filiereId);
    }

    @GetMapping("/filiere_and_module/details/{filiereName}/{moduleName}")
    public List<Map<String, Object>> getStudentDetails(@PathVariable String filiereName,
                                                       @PathVariable String moduleName) {
        return studentService.getStudentDetailsByFiliereAndModule(filiereName, moduleName);
    }

    @PutMapping("/{cne}")
    public ResponseEntity<Student> updateStudent(@PathVariable String cne, @RequestBody Student updatedStudent) {
        try {
            Student student = studentService.updateStudent(cne, updatedStudent);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{cne}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String cne) {
        try {
            studentService.deleteStudent(cne);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
