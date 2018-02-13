package be.thibaulthelsmoortel.lotterymanagement.model;

import lombok.Data;

/**
 * Object representing a participant, player.
 *
 * A player can exist even though (s)he has never participated in a draw.
 *
 * @author Thibault Helsmoortel
 */
@Data
public class Player {

    private Long id;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
