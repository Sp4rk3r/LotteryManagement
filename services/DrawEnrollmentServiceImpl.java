package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.mappers.DrawEnrollmentMapper;
import be.thibaulthelsmoortel.lotterymanagement.mappers.DrawEnrollmentMapper.CreateDrawEnrollmentRequest;
import be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for {@link be.thibaulthelsmoortel.lotterymanagement.model.DrawEnrollment}.
 *
 * @author Thibault Helsmoortel
 */
@Service
public class DrawEnrollmentServiceImpl implements DrawEnrollmentService {

    private final DrawEnrollmentMapper mapper;

    @Autowired
    public DrawEnrollmentServiceImpl(DrawEnrollmentMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void createDrawEnrollment(DrawEnrollment enrollment) {
        CreateDrawEnrollmentRequest request = new CreateDrawEnrollmentRequest();
        request.setDrawId(enrollment.getDraw().getId());
        request.setPlayerId(enrollment.getPlayer().getId());
        request.setFeePaid(enrollment.isFeePaid());
        mapper.createDrawEnrollment(request);
    }
}
