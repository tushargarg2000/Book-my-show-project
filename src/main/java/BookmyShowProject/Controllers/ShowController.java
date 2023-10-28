package BookmyShowProject.Controllers;

import BookmyShowProject.RequestDtos.AddShowRequest;
import BookmyShowProject.RequestDtos.AddShowSeatsRequest;
import BookmyShowProject.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/addShow")
    public String addShow(@RequestBody AddShowRequest addShowRequest){

        String result = showService.addShow(addShowRequest);
        return result;
    }

    @PostMapping("/createShowSeats")
    public String enableShowSeats(@RequestBody AddShowSeatsRequest addShowSeatsRequest) {

        String result = showService.createShowSeats(addShowSeatsRequest);
        return result;
    }




}
