package tourism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

@RestController
@RequestMapping("attractions")
public class TouristController {
    private final TouristService service;

    @Autowired
    public TouristController(TouristService service) {
        this.service = service;
    }


    // CRUD endpoints
    @GetMapping
    public ResponseEntity<?> getAttractions() {
        return ResponseEntity.ok("Her vil attraktioner vises");
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getAttractionsByName(@PathVariable String name) {
        return ResponseEntity.ok("Her vil attraktionerne med navnet: " + name + " fremgå");
    }
    @PostMapping("/add")
    public ResponseEntity<?> addAttraction(@RequestBody String attractionDetails) {
        return ResponseEntity.ok("En ny attraktion er blevet tilføjet: " +attractionDetails);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAttraction(@PathVariable String name, @RequestBody TouristAttraction attraction) {
        service.updateAttraction(name, attraction);
        return ResponseEntity.ok("Attraktionen " + name + " er blevet opdateret");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteAttration(@PathVariable String name) {
        return ResponseEntity.ok(name + " er slettet");
    }
}
