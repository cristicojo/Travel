package travel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import travel.exception.BorderException;
import travel.exception.CountryNotFoundException;
import travel.model.JourneyResponse;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TravelServiceTest {

    private final TravelService travelService = new TravelService();


    @BeforeEach
    void setUp() throws IOException {
        travelService.init();
    }


    @Test
    void findRouteTest() {
        JourneyResponse result = travelService.findRoute("CZE", "ROU");
        assertEquals(result.getRoute(), List.of("CZE", "AUT", "HUN", "ROU"));
    }

    @Test
    void findRouteTest_destination_throws_CountryNotFoundException() {
        CountryNotFoundException thrown = assertThrows(CountryNotFoundException.class, () -> travelService.findRoute("CZE", "890"));
        assertEquals("Invalid country code for origin or destination", thrown.getMessage());
    }

    @Test
    void findRouteTest_origin_throws_CountryNotFoundException() {
        CountryNotFoundException thrown = assertThrows(CountryNotFoundException.class, () -> travelService.findRoute("890", "CZE"));
        assertEquals("Invalid country code for origin or destination", thrown.getMessage());
    }

    @Test
    void findRouteTest_throws_BorderException() {
        BorderException thrown = assertThrows(BorderException.class, () -> travelService.findRoute("CZE", "ALA"));
        assertEquals("No land route found", thrown.getMessage());
    }

}
