package pfe.abscence.management.student;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pfe.abscence.management.types.Semestre;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<Student> getStudentsByFiliereId(Long filiereId) {
        return studentRepository.findByFiliereFiliereId(filiereId);
    }
    
    public List<Student> getStudentBySemestre(Semestre semestre) {
        return studentRepository.findBySemestre(semestre);
    }

    public List<Map<String, Object>> getStudentDetailsByFiliereAndModule(String filiereName, String moduleName) {
        return studentRepository.findStudentsByFiliereAndModule(filiereName, moduleName);
    }
}

