package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.mappers.DrawMapper;
import be.thibaulthelsmoortel.lotterymanagement.mappers.DrawMapper.CreateDrawRequest;
import be.thibaulthelsmoortel.lotterymanagement.model.Draw;
import be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Thibault Helsmoortel
 * @since 3/10/2017
 */
@Service
public class DrawServiceImpl implements DrawService {

    private final DrawMapper drawMapper;

    @Autowired
    public DrawServiceImpl(DrawMapper drawMapper) {
        this.drawMapper = drawMapper;
    }

    @Override
    public List<Draw> getAllDraws() {
        List<Draw> allDraws = drawMapper.getAllDraws();
        allDraws.forEach(draw -> {
            // Fetch the draw's enrollments
            DrawMapper.GetDrawEnrollmentsRequest request = new DrawMapper.GetDrawEnrollmentsRequest();
            request.setDrawId(draw.getId());
            List<DrawEnrollment> drawEnrollments = drawMapper.getDrawEnrollments(request);
            drawEnrollments.forEach(drawEnrollment -> {
                // Fetch the enrollment's draw
                DrawMapper.GetDrawRequest drawRequest = new DrawMapper.GetDrawRequest();
                drawRequest.setDrawId(draw.getId());
                drawEnrollment.setDraw(drawMapper.getDraw(drawRequest));

                // Fetch the enrollment's player
                DrawMapper.GetDrawEnrollmentPlayerRequest playerRequest = new DrawMapper.GetDrawEnrollmentPlayerRequest();
                playerRequest.setDrawEnrollmentId(drawEnrollment.getId());
                drawEnrollment.setPlayer(drawMapper.getDrawEnrollmentPlayer(playerRequest));
            });
            draw.setEnrollments(drawEnrollments);
        });
        return allDraws;
    }

    @Override
    public void createDraw(Draw draw) {
        CreateDrawRequest request = new CreateDrawRequest();
        request.setName(draw.getName());
        request.setDrawDate(draw.getDrawDate());
        request.setEnrollmentCapacity(draw.getEnrollmentCapacity());
        request.setEnrollmentFee(draw.getEnrollmentFee());
        request.setTotalWon(draw.getTotalWon());
        request.setMaxPossibleWin(draw.getMaxPossibleWin());

        drawMapper.createDraw(request);
    }
}
