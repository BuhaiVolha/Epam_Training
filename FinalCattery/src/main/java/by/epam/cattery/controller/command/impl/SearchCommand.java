package by.epam.cattery.controller.command.impl;

import by.epam.cattery.controller.command.ActionCommand;
import by.epam.cattery.controller.util.ConfigurationManager;
import by.epam.cattery.entity.*;
import by.epam.cattery.service.CatService;
import by.epam.cattery.service.ServiceFactory;
import by.epam.cattery.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SearchCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(SearchCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Cat> cats = null;
        HttpSession session = request.getSession();

        try {
            Cat cat = createCat(request);
            CatService catService = ServiceFactory.getInstance().getCatService();

            if (session.getAttribute("role") == Role.USER) {
                logger.log(Level.DEBUG, "User is logged in, showing cats with discount");
                int userId = Integer.parseInt(session.getAttribute("userId").toString());
                cats = catService.searchForCatWithDiscount(cat, userId);

            } else {
                logger.log(Level.DEBUG, "Showing just prices without any discount");
                cats = catService.searchForCat(cat);
            }

            request.setAttribute("cats", cats);
            request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.cats")).forward(request, response);

        } catch (ServiceException e) {
            response.sendRedirect(ConfigurationManager.getProperty("path.page.error"));
            logger.log(Level.ERROR, "Cat's are not here: ", e);
        }
    }


    private Cat createCat(HttpServletRequest request) {
        Cat cat = new Cat();

        if (!request.getParameter("price").isEmpty()) {
            cat.setPrice(Double.parseDouble(request.getParameter("price")));
        }
        //cat.setPrice(Double.parseDouble(request.getParameter("price")));
        if (!request.getParameter("gender").isEmpty()) {
            cat.setGender(Gender.valueOf(request.getParameter("gender")));
        }
        if (!request.getParameter("status").isEmpty()) {
            cat.setStatus(CatStatus.valueOf(request.getParameter("status")));
        }
        cat.setBodyColour(request.getParameter("body"));
        cat.setEyesColour(request.getParameter("eyes"));

        return cat;
    }
}

//        cat.setPrice(Double.parseDouble(request.getParameter("price")));
//                cat.setGender(Gender.valueOf(request.getParameter("gender")));
//                cat.setBodyColour(request.getParameter("body"));
//                cat.setEyesColour(request.getParameter("eyes"));