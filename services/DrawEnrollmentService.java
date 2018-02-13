package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment;

/**
 * Service interface for {@link be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment}.
 *
 * @author Thibault Helsmoortel
 */
public interface DrawEnrollmentService {

    void createDrawEnrollment(DrawEnrollment enrollment);
}
