package be.thibaulthelsmoortel.lotterymanagement.model;

import java.util.Date;
import lombok.Data;

/**
 * Object representing an enrollment for a draw.
 *
 * @author Thibault Helsmoortel
 */
@Data
public class DrawEnrollment {

    private Long id;
    private Draw draw;
    private Player player;
    private boolean feePaid;
    private boolean winningsPaid;
    private Date created;

}
