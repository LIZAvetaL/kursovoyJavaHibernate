package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.AdminEntity;
import Server.Model.BasketEntity;
import Server.Model.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split("_", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "checkSingInClient":
                commands = command.split("_", 4);
                result = UserCommands.checkSingInClient(commands[2],commands[3]);
                break;
            case "checkSingInAdmin":
                commands = command.split("_", 4);
                result = UserCommands.checkSingInAdmin(commands[2],commands[3]);
                break;
            case "checkLogin":
                commands = command.split("_", 3);
                result = UserCommands.checkSingLogin(commands[2]);
                break;
            case "addClient":
                commands = command.split("_", 4);
                result = UserCommands.addClient(commands[2], commands[3]);
                break;
            case "addAdmin":
                commands = command.split("_", 4);
                result = UserCommands.addAdmin(commands[2], commands[3]);
                break;
            case "deleteUser":
                commands = command.split("_", 3);
                result = UserCommands.deleteUser(commands[2]);
                break;
            case "findByID":
                commands = command.split("_", 3);
                result = UserCommands.findByID(Integer.parseInt(commands[2]));
                break;
            case "changePassword":
                commands = command.split("_", 4);
                result = UserCommands.changePassword(commands[2], commands[3]);
                break;
            case "showUser":
                result = UserCommands.showUser();
                break;
        }
        return result;
    }

    private static String changePassword(String idUserString, String password) {
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        UsersEntity user= session.get(UsersEntity.class,Integer.parseInt(idUserString));
        session.close();
        user.setPassword(password);
        try {
            update(user);
        }catch (Exception e){e.printStackTrace();}

        return "success";
    }

    private static String addAdmin(String login, String password) {
        UsersEntity user = new UsersEntity(login,password);
        AdminEntity admin=new AdminEntity(user);
        save(admin);
        return "success";
    }

    private static String deleteUser(String idUserString) {
        int idUSer= Integer.parseInt(idUserString);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        UsersEntity user=session.get(UsersEntity.class,idUSer);
        session.close();
        delete(user);
        return "success";
    }

    private static Object showUser() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from UsersEntity").list();
    }

    private static String addClient(String login, String password) {
        UsersEntity user = new UsersEntity(login,password);
        save(user);
        return "success";
    }

    private static String checkSingLogin(String loginTemp) {
        List<UsersEntity> list =null;
        list=HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from UsersEntity where login=:name").setParameter("name", loginTemp).list();
        if(list!= null) return "success";
        else return "fail";
    }

    public static String checkSingInClient(String login, String password) {
        UsersEntity users= (UsersEntity) HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("FROM UsersEntity where login=:log and password=:pas").
                setParameter("log",login).setParameter("pas",password).uniqueResult();
        if(users==null)return "fail";
        return String.valueOf(users.getIdUser());
    }

    public static String checkSingInAdmin(String login, String password) {
        AdminEntity admin= (AdminEntity) HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("FROM AdminEntity where  user.login=:log and user.password=:pas").
                setParameter("log",login).setParameter("pas",password).uniqueResult();
        if(admin==null) return"fail";
        return "successAdmin";
    }

    private static void save(Object user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }
    private static void delete(Object user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }
    private static void update(Object product){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(product);
        tx1.commit();
        session.close();
    }

    public static UsersEntity findByID(int idUser) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .get(UsersEntity.class,idUser);
    }
}
