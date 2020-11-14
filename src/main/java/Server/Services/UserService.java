package Server.Services;

import Server.DAO.UserDAOImpl;
import Server.Model.UsersEntity;

import java.util.List;

public class UserService {

     private static UserDAOImpl usersDao = new UserDAOImpl();

    public UserService() {
    }

    public UsersEntity findUser(int id) {
        return usersDao.findById(id);
    }

    public static String checkSingIn(String login, String password){return usersDao.checkSingIn(login, password);}

    public void saveUser(UsersEntity user) {
        usersDao.save(user);
    }

    public void deleteUser(UsersEntity user) {
        usersDao.delete(user);
    }

    public void updateUser(UsersEntity user) {
        usersDao.update(user);
    }

    public List<UsersEntity> findAllUsers() {
        return usersDao.findAll();
    }




}
