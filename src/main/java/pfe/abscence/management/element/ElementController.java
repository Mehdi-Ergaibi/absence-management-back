package pfe.abscence.management.element;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("/api/elements")
public class ElementController {

    @Autowired
    private ElementService elementService;

    @PostMapping("/create")
    public ResponseEntity<Element> createElement(@RequestBody Element element) {
        return ResponseEntity.status(HttpStatus.CREATED).body(elementService.createElement(element));
    }

    @GetMapping("/all")
    public List<Element> getAllElements() {
        return elementService.getAllElements();
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<Element> getElementById(@PathVariable Long id) {
        return ResponseEntity.ok(elementService.getElementById(id));
    }

    @GetMapping("/module/{moduleName}")
    public List<String> getElementsByModuleName(@PathVariable String moduleName) {
        return elementService.getElementsByModuleName(moduleName).stream().map(Element::getName).toList();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Element> updateElement(@PathVariable Long id, @RequestBody Element updatedElement) {
        return ResponseEntity.ok(elementService.updateElement(id, updatedElement));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteElement(@PathVariable Long id) {
        elementService.deleteElement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}")
public Optional<Long> getElementIdByName(@PathVariable String name) {

    return elementService.getElementIdByName(name);
}
}