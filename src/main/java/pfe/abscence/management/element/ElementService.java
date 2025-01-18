package pfe.abscence.management.element;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElementService {

    @Autowired
    private ElementRepository elementRepository;

    public Element createElement(Element element) {
        return elementRepository.save(element);
    }

    public List<Element> getAllElements() {
        return elementRepository.findAll();
    }

    public Element getElementById(Long id) {
        return elementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Element not found"));
    }

    public List<Element> getElementsByModuleName(String moduleName) {
        return elementRepository.findByModuleName(moduleName);
    }

    public Element updateElement(Long id, Element updatedElement) {
        Element existing = getElementById(id);
        existing.setName(updatedElement.getName());
        existing.setModule(updatedElement.getModule());
        return elementRepository.save(existing);
    }

    public void deleteElement(Long id) {
        elementRepository.deleteById(id);
    }

    public Optional<Long> getElementIdByName(String name) {
        return elementRepository.findByName(name).map(Element::getElementId);
    }
}
