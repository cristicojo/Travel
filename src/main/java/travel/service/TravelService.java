package travel.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import travel.exception.BorderException;
import travel.exception.CountryNotFoundException;
import travel.model.Country;
import travel.model.JourneyResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service
@Slf4j
public class TravelService {
    private final Map<String, List<String>> graph = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        URL url = new URL("https://raw.githubusercontent.com/mledoze/countries/master/countries.json");

        InputStream is = url.openStream();
        List<Country> countries = mapper.readValue(is, new TypeReference<>() {});

        log.debug("borders for each country:");
        countries.forEach(c -> {
            log.debug("{} : {}", c.getCca3(), c.getBorders());
            graph.put(c.getCca3(), c.getBorders());
        });
    }


    public JourneyResponse findRoute(String origin, String destination) {

        log.info("origin is: {}", origin);
        log.info("destination is: {}", destination);

        if (!graph.containsKey(origin) || !graph.containsKey(destination)) {
            log.error("Invalid country code for origin or destination");
            throw new CountryNotFoundException("Invalid country code for origin or destination");
        }

        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(List.of(origin));
        visited.add(origin);

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String last = path.get(path.size() - 1);

            if (last.equals(destination)) {
                JourneyResponse journeyResponse = new JourneyResponse();
                journeyResponse.setRoute(path);
                log.info("route is {}", path);
                return journeyResponse;
            }

            for (String neighbor : graph.getOrDefault(last, List.of())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);

                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);

                    queue.add(newPath);
                }
            }
        }

        log.error("No land route found");
        throw new BorderException("No land route found");
    }
}