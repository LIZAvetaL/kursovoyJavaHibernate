package Server.DAO;

import Server.Model.UsersEntity;

import java.util.List;

public interface UserDAO {
    void save(UsersEntity user);
    List<UsersEntity> findAll();
}
