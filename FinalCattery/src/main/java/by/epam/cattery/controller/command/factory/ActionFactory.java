package by.epam.cattery.controller.command.factory;

import by.epam.cattery.controller.command.ActionCommand;
import by.epam.cattery.controller.command.EmptyCommand;
import by.epam.cattery.controller.command.client.CommandEnum;
import by.epam.cattery.resource.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        String action = request.getParameter("command");

        if ((action == null)
                || (action.isEmpty())) {
            return current;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();

        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + MessageManager.getProperty("message.wrongaction"));
        }
        return current;
    }
}
