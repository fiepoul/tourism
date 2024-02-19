package tourism.repository;

import jdk.jfr.Registered;
import org.springframework.stereotype.Repository;
import tourism.model.TouristAttraction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TouristRepository {
    private List<TouristAttraction> attractions = new ArrayList<>();

    public TouristRepository() {
        attractions.add(new TouristAttraction("flyvergrillen", "et spisested og udsigtspunkt over flyvebanen i Kastrup", "Amager", List.of("mad", "udsigt", "eventyr")));
        attractions.add(new TouristAttraction("Cisternerne", "En tidligere vandreservoir under Frederiksberg Hill som nu er en kunst udstillingsplads.", "Frederiksberg", List.of("kunst", "natur")));
        attractions.add(new TouristAttraction("BabyZoo på Gavnø Slot", "En lille zoologisk have, der fokuserer på babydyr.", "Gavnø", List.of("dyr", "slotbesøg")));
        attractions.add(new TouristAttraction("Råbjerg Mile", "En vandrende sandklit nær Skagen.", "Råbjerg Mile", List.of("natur", "vandretur")));
        attractions.add(new TouristAttraction("Verdenskortet ved Klejtrup Sø", "En stor park med et stort kort over verden i skala 1:100.000.", "Klejtrup", List.of("natur", "eventyr")));
        attractions.add(new TouristAttraction("Gravhøjene i Mols Bjerge", "Præhistoriske gravhøje med fantastisk udsigt.", "Mols Bjerge", List.of("historisk", "udsigt", "natur")));
    }

    //CRUD metoder
    public Optional<TouristAttraction> findByName(String name) {
        return attractions.stream().filter(attraction -> attraction.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<TouristAttraction> findAll() {
        return attractions;
    }

    public void add(TouristAttraction attraction) {
        attractions.add(attraction);
    }

    public void update(String name, TouristAttraction updatedAttraction) {
        for (TouristAttraction attraction: attractions) {
            if (attraction.getName().equalsIgnoreCase(name)) {
                attraction.setName(updatedAttraction.getName());
                attraction.setDescription(updatedAttraction.getDescription());
            }
        }
    }

    public void delete(String name) {
        findByName(name).ifPresent(attractions::remove);
    }



}
