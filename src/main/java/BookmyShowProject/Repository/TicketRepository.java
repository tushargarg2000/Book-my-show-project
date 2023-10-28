package BookmyShowProject.Repository;

import BookmyShowProject.Models.Show;
import BookmyShowProject.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {




}
