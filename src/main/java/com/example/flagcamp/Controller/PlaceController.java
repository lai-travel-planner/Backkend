package com.example.flagcamp.Controller;

import com.example.flagcamp.Service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PlaceController {
    private PlaceService placeService; // create the corresponding Service logic
    @Autowired
    public PlaceController(PlaceService placeService)  {
        this.placeService = placeService;
    }

    @RequestMapping(value = "/place", method = RequestMethod.GET)
    public void getPlace(@RequestParam(value = "place_name", required = false) String placeName, HttpServletResponse response) throws IOException {

    }

}
