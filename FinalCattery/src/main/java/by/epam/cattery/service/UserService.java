package by.epam.cattery.service;

import by.epam.cattery.entity.User;
import by.epam.cattery.service.exception.ServiceException;
import by.epam.cattery.service.exception.ValidationFailedException;

public interface UserService {
    int register(User user) throws ServiceException, ValidationFailedException;
    User logIn(String login, String password);
}
