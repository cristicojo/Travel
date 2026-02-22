package travel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.model.JourneyResponse;
import travel.service.TravelService;

@RestController
@RequestMapping("/routing")
@RequiredArgsConstructor
public class TravelController {

    private final TravelService service;


    @GetMapping(value = "/{origin}/{destination}")
    public ResponseEntity<JourneyResponse> getBorderCrossing(@PathVariable String origin, @PathVariable String destination) {
        return new ResponseEntity<>(service.findRoute(origin, destination), HttpStatus.OK);
    }
}
