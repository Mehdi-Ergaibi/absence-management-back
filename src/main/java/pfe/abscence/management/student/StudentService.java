package pfe.abscence.management.student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<Student> getStudentByCne(String cne) {
        return studentRepository.findByCne(cne);
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

    public Student updateStudent(String cne, Student updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findByCne(cne);

        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setFirstName(updatedStudent.getFirstName());
            student.setLastName(updatedStudent.getLastName());
            student.setEmail(updatedStudent.getEmail());
            student.setSemestre(updatedStudent.getSemestre());
            student.setModules(updatedStudent.getModules());
            student.setFiliere(updatedStudent.getFiliere());
            return studentRepository.save(student);
        } else {
            throw new RuntimeException("Student not found with CNE " + cne);
        }
    }

    public void deleteStudent(String cne) {
        if (studentRepository.existsByCne(cne)) {
            studentRepository.deleteByCne(cne);
        } else {
            throw new RuntimeException("Student not found with CNE " + cne);
        }
    }
}

