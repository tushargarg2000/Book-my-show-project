package BookmyShowProject.Transformers;

import BookmyShowProject.Models.Movie;
import BookmyShowProject.RequestDtos.AddMovieRequest;

public class MovieTransformer {

    public static Movie convertAddMovieReqToMovie(AddMovieRequest addMovieRequest){

        Movie movie = Movie.builder().
                            movieName(addMovieRequest.getMovieName())
                .genre(addMovieRequest.getGenre())
                .releaseDate(addMovieRequest.getReleaseDate())
                .rating(addMovieRequest.getRating())
                .build();

        return movie;
    }
}
