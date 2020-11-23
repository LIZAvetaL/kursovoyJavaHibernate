package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.BasketEntity;
import Server.Model.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class UserCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "checkSingInClient":
                commands = command.split(",", 4);
                result = UserCommands.checkSingInClient(commands[2],commands[3]);
                break;
            case "checkSingInAdmin":
                commands = command.split(",", 4);
                result = UserCommands.checkSingInAdmin(commands[2],commands[3]);
                break;
            case "checkLogin":
                commands = command.split(",", 3);
                result = UserCommands.checkSingLogin(commands[2]);
                break;
            case "addClient":
                commands = command.split(",", 4);
                result = UserCommands.addClient(commands[2], commands[3]);
                break;
        }
        return result;
    }

    private static String addClient(String login, String password) {
        UsersEntity user = new UsersEntity();
        user.setLogin(login);
        user.setPassword(password);
        UserCommands.save(user);
        return "success";
    }

    private static String checkSingLogin(String loginTemp) {
        List<UsersEntity> list =null;
        list=HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from UsersEntity where login=:name").setParameter("name", loginTemp).list();
        if(list!= null) return "success";
        else return "fail";
    }

    public static String checkSingInClient(String username, String password) {
        String message="" ;
        List<UsersEntity> users= HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("FROM UsersEntity ").list();
        try {
            for (UsersEntity user : users) {
                String tableLogin = user.getLogin();
                String tablePassword = user.getPassword();
                if (tableLogin.equals(username) && tablePassword.equals(password)) {
                    message = Integer.toString(user.getIdUser());
                    break;
                } else message = "fail";
            }
        } catch (Exception var12) {
            System.out.println("Exception in Table of users");
        }
        return message;
    }

    public static String checkSingInAdmin(String username, String password) {
        String message = "";
        List<UsersEntity> users= HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("FROM UsersEntity ").list();
        try {
            for (UsersEntity user : users) {
                String tableLogin = user.getLogin();
                String tablePassword = user.getPassword();
                if (tableLogin.equals(username) && tablePassword.equals(password)) {
                    message = "successAdmin";

                    break;
                } else message = "fail";
            }
        } catch (Exception var12) {
            System.out.println("Exception in Table of users");
        }
        return message;
    }

    private static void save(UsersEntity user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }
}
