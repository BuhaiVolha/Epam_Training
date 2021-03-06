package by.epam.cattery.controller.command;

import by.epam.cattery.controller.util.ConfigurationManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmptyCommand implements ActionCommand {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String page = ConfigurationManager.getProperty("path.page.error");
    }
}
