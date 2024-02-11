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
        attractions.add(new TouristAttraction("flyvergrillen", "et spisested og udsigtspunkt over flyvebanen i Kastrup"));
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
