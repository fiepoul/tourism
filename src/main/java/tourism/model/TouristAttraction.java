package tourism.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TouristAttraction {
    private String name;
    private String description;
    private String location;
    private Set<AttractionTag> tags;

    public TouristAttraction(String name, String description, String location, Set<AttractionTag> tags) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<AttractionTag> getTags() {
        return tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
