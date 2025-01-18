package pfe.abscence.management.student;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/one/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
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
}

