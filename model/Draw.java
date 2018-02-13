package be.thibaulthelsmoortel.lotterymanagement.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Object representing a lottery draw.
 *
 * @author Thibault Helsmoortel
 */
@Data
public class Draw {

    private Long id;
    private String name;
    private Date drawDate;
    private int enrollmentCapacity;
    private BigDecimal enrollmentFee;
    private BigDecimal totalWon;
    private BigDecimal maxPossibleWin;
    private List<DrawEnrollment> enrollments;

    public boolean isDrawn() {
        return drawDate.after(new Date());
    }

    public void addEnrollment(DrawEnrollment enrollment) {
        if (!enrollment.getDraw().equals(this)) throw new IllegalArgumentException("The enrollment does not correspond to the draw.");
        if (enrollments.contains(enrollment)) throw new IllegalArgumentException("The enrollment was already registered to the draw.");
        if (!isOpenForEnrollment()) throw new IllegalArgumentException("The draw has reached its' maximum amount of enrollments.");
        if (isDrawn()) throw new IllegalStateException("The draw was already terminated.");

        enrollments.add(enrollment);
    }

    private boolean isOpenForEnrollment() {
        return enrollments.size() < enrollmentCapacity;
    }

    public void removeEnrollment(DrawEnrollment enrollment) {
        if (isDrawn() && enrollment.isFeePaid()) throw new IllegalStateException("A paid enrollment should not be removed when the draw has been performed.");

        enrollments.remove(enrollment);
    }

}
