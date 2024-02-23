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

    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction attraction = service.getAttractionByName(name);
        if (attraction != null) {
            model.addAttribute("attraction", attraction);
            model.addAttribute("allTags", AttractionTag.values());
            return "edit-attraction";
        } else {
            return "redirect:/attractions";
        }
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction) {
        service.updateAttraction(attraction.getName(), attraction);
        return "redirect:/attractions";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction,
                                 @RequestParam(required = false) List<AttractionTag> tags) {

        if (tags == null) {
            tags = new ArrayList<>();
        }
        attraction.setTags(tags);
        service.addAttraction(attraction);
        return "redirect:/attractions";
    }

    @GetMapping("/add")
    public String showAddAttractionForm(Model model) {
        model.addAttribute("attraction", new TouristAttraction()); // tom instans for form
        model.addAttribute("allLocations", service.getLocations()); //todo: hvad går galt her? måske genstart
        model.addAttribute("allTags", AttractionTag.values()); // Sender alle tags til modellen
        return "add-attraction";
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
