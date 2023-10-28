package BookmyShowProject.Service;

import BookmyShowProject.Enums.SeatType;
import BookmyShowProject.Models.Movie;
import BookmyShowProject.Models.Show;
import BookmyShowProject.Models.ShowSeat;
import BookmyShowProject.Models.Theater;
import BookmyShowProject.Models.TheaterSeat;
import BookmyShowProject.Repository.MovieRepository;
import BookmyShowProject.Repository.ShowRepository;
import BookmyShowProject.Repository.ShowSeatRespository;
import BookmyShowProject.Repository.TheaterRepository;
import BookmyShowProject.RequestDtos.AddShowRequest;
import BookmyShowProject.RequestDtos.AddShowSeatsRequest;
import BookmyShowProject.Transformers.ShowTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private ShowSeatRespository showSeatRespository;

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

    public String createShowSeats(AddShowSeatsRequest showSeatsRequest){

        //I need to create the show Seats and save to the DB.

        Show show = showRepository.findById(showSeatsRequest.getShowId()).get();
        Theater theater = show.getTheater();
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();

        List<ShowSeat> showSeatList = new ArrayList<>();


        for(TheaterSeat theaterSeat:theaterSeatList) {

            ShowSeat showSeat = ShowSeat.builder()
                    .seatNo(theaterSeat.getSeatNo())
                    .seatType(theaterSeat.getSeatType())
                    .isAvailable(true)
                    .isFoodAttached(false)
                    .show(show)
                    .build();

            if(theaterSeat.getSeatType().equals(SeatType.CLASSIC)){
                showSeat.setCost(showSeatsRequest.getPriceOfClassicSeats());
            }
            else{
                showSeat.setCost(showSeatsRequest.getPriceOfPremiumSeats());
            }

            showSeatList.add(showSeat);
        }

        show.setShowSeatList(showSeatList);

        //Either save parent or save child

        //child is alot of seats (you need to save that list)

        showRepository.save(show);
        return "The show seats have been added";

    }




}
