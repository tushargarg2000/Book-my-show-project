package BookmyShowProject.Controllers;

import BookmyShowProject.RequestDtos.AddMovieRequest;
import BookmyShowProject.Service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movie")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("addMovie")
    public ResponseEntity addMovie(@RequestBody AddMovieRequest addMovieRequest){

        try{
            log.info("We have request : {}",addMovieRequest.toString());
            String result = movieService.addMovie(addMovieRequest);
            return new ResponseEntity(result, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
