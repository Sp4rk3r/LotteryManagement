package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.model.Draw;

import java.util.List;

/**
 * Service interface for {@link be.thibaulthelsmoortel.lotterymanagement.model.Draw}.
 *
 * @author Thibault Helsmoortel
 * @since 3/10/2017
 */
public interface DrawService {

    List<Draw> getAllDraws();

    void createDraw(Draw draw);
}
