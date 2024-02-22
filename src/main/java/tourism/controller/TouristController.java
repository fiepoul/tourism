package tourism.controller;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tourism.model.AttractionTag;
import tourism.model.TouristAttraction;
import tourism.service.TouristService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public String getAttractions(Model model) {
        List<TouristAttraction> attractions = service.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractionList"; //thymeleaf skabelonen
    }

    @GetMapping("/{name}/tags")
    public String showTags(@PathVariable("name") String name, Model model) {
        List<String> tags = service.getTagsForAttraction(name);
        model.addAttribute("tags", tags);
        model.addAttribute("attractionName", name);
        return "tags"; //thymeleaf skabelonen
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionsByName(@PathVariable String name) {
        TouristAttraction attraction = service.getAttractionByName(name);
        return ResponseEntity.ok(attraction);
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction, @RequestParam List<String> tags) {
        // Konverter streng tags til enum værdier
        List<AttractionTag> enumTags = tags.stream()
                .map(tagStr -> AttractionTag.valueOf(tagStr.toUpperCase().replace(" ", "_")))
                .collect(Collectors.toList());

        // Opret ny instans af TouristAttraction med enum tags
        TouristAttraction newAttraction = new TouristAttraction(
                attraction.getName(),
                attraction.getDescription(),
                attraction.getLocation(),
                enumTags
        );

        service.addAttraction(newAttraction);
        return "redirect:/attractions"; // Redirect til liste over attraktioner
    }

    @GetMapping("/add")
    public String showAddAttractionForm(Model model) {
        model.addAttribute("attraction", new TouristAttraction("", "", "", new ArrayList<>())); // Opretter en ny, tom instans for form binding
        model.addAttribute("allTags", AttractionTag.values()); // Sender alle tags til modellen
        return "add-attraction"; // Navnet på din Thymeleaf skabelon
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
