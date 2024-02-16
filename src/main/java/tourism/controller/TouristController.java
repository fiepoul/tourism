package tourism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {
    private final TouristService service;

    @Autowired
    public TouristController(TouristService service) {
        this.service = service;
    }


    // CRUD endpoints
    @GetMapping
    public ResponseEntity<List<TouristAttraction>> getAttractions() {
        List<TouristAttraction> attractions = service.getAllAttractions();
        return ResponseEntity.ok(attractions);
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionsByName(@PathVariable String name) {
        TouristAttraction attraction = service.getAttractionByName(name);
        return ResponseEntity.ok(attraction);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addAttraction(@RequestBody TouristAttraction newAttraction) {
        service.addAttraction(newAttraction);
        return ResponseEntity.ok("En ny attraktion er blevet tilføjet: " + newAttraction.getName());
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateAttraction(@PathVariable String name, @RequestBody TouristAttraction attraction) {
        service.updateAttraction(name, attraction);
        return ResponseEntity.ok("Attraktionen " + name + " er blevet opdateret");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteAttration(@PathVariable String name) {
        service.deleteAttraction(name);
        return ResponseEntity.ok(name + " er slettet");
    }
}
