package BookmyShowProject.Service;

import BookmyShowProject.Models.Movie;
import BookmyShowProject.Models.Show;
import BookmyShowProject.Models.Theater;
import BookmyShowProject.Models.TheaterSeat;
import BookmyShowProject.Repository.MovieRepository;
import BookmyShowProject.Repository.ShowRepository;
import BookmyShowProject.Repository.TheaterRepository;
import BookmyShowProject.RequestDtos.AddShowRequest;
import BookmyShowProject.Transformers.ShowTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowRepository showRepository;

    public String addShow(AddShowRequest addShowRequest){

        //Goal is to set the attributes of the Show Entity and save it to db.

        Show show = ShowTransformers.convertAddRequestToEntity(addShowRequest);
        Movie movie = movieRepository.findMovieByMovieName(addShowRequest.getMovieName());

        Optional<Theater> optionalTheater = theaterRepository.findById(addShowRequest.getTheaterId());
        Theater theater = optionalTheater.get();

        //Setting the FK values
        show.setMovie(movie);
        show.setTheater(theater);

        //Setting the bidirectional mapping
        theater.getShowList().add(show);
        movie.getShowList().add(show);

        show = showRepository.save(show);

        return "Show has been saved to the DB with showId "+show.getShowId();

    }

    public String createShowSeats(Integer showId){


        //I need to create the show Seats and save to the DB.

        Show show = showRepository.findById(showId).get();
        Theater theater = show.getTheater();
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();

        List<ShowSeat>


        for(TheaterSeat theaterSeat:theaterSeatList){



        }


    }




}
