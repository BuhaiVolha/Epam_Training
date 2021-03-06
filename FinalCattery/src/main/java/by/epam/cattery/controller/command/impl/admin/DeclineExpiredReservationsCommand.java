package by.epam.cattery.controller.command.impl.admin;

import by.epam.cattery.controller.command.ActionCommand;
import by.epam.cattery.controller.util.ConfigurationManager;
import by.epam.cattery.service.CatService;
import by.epam.cattery.service.ReservationService;
import by.epam.cattery.service.ServiceFactory;
import by.epam.cattery.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeclineExpiredReservationsCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(DeclineExpiredReservationsCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            ReservationService reservationService = ServiceFactory.getInstance().getReservationService();
            reservationService.declineExpiredReservations();

            response.sendRedirect(ConfigurationManager.getProperty("path.page.success-page"));

        } catch (ServiceException e) {
            logger.log(Level.ERROR, "Declining reservations failed: ", e);
            response.sendRedirect(ConfigurationManager.getProperty("path.page.error"));
        }
    }
}
