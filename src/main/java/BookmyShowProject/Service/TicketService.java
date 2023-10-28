package BookmyShowProject.Service;

import BookmyShowProject.Models.Movie;
import BookmyShowProject.Models.Show;
import BookmyShowProject.Models.ShowSeat;
import BookmyShowProject.Models.Theater;
import BookmyShowProject.Models.Ticket;
import BookmyShowProject.Models.User;
import BookmyShowProject.Repository.MovieRepository;
import BookmyShowProject.Repository.ShowRepository;
import BookmyShowProject.Repository.TheaterRepository;
import BookmyShowProject.Repository.TicketRepository;
import BookmyShowProject.Repository.UserRepository;
import BookmyShowProject.RequestDtos.BookTicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public String bookTicket(BookTicketRequest bookTicketRequest){

        Show show = findRightShow(bookTicketRequest);
        //My steps are :
        List<ShowSeat> showSeatList = show.getShowSeatList();
        //Whatever are the requested seats : mark them as not available in show seats

        int totalPrice = 0;
        for(ShowSeat showSeat:showSeatList) {

            if(bookTicketRequest.getRequestedSeatNos().contains(showSeat.getSeatNo())) {
                showSeat.setAvailable(false);
                totalPrice = totalPrice + showSeat.getCost();
            }
        }

        User user = userRepository.findById(bookTicketRequest.getUserId()).get();

        Ticket ticket = Ticket.builder()
                .movieName(show.getMovie().getMovieName())
                .theaterAddress(show.getTheater().getAddress())
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .bookedSeats(bookTicketRequest.getRequestedSeatNos().toString())
                .user(user)
                .show(show)
                .totalPrice(totalPrice)
                .build();

        show.getTicketList().add(ticket);
        user.getTicketList().add(ticket);


        ticketRepository.save(ticket);


        return "Ticket has been booked";

        //Calculate total Price

        //We also need to add it to list of booked tickets against user





    }

    private Show findRightShow(BookTicketRequest bookTicketRequest){

        Movie movie = movieRepository.findMovieByMovieName(bookTicketRequest.getMovieName());
        Theater theater = theaterRepository.findById(bookTicketRequest.getTheaterId()).get();

        Show show = showRepository.findShowByShowDateAndShowTimeAndMovieAndTheater(bookTicketRequest.getShowDate()
                                                                        ,bookTicketRequest.getShowTime(),
                                                                        movie,theater);


        return show;
    }

}
