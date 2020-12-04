package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.BasketEntity;
import Server.Model.OrdersEntity;
import Server.Model.ProductEntity;
import Server.Model.UsersEntity;
import Server.ServerThread;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "addToOrder":
                commands = command.split(",", 3);
                result = OrderCommands.addToOrder(commands[2]);
                break;
            case "showOrder":
                commands = command.split(",", 3);
                result = OrderCommands.showOrder(commands[2]);
                break;
            case "showOrderAdmin":
                result = OrderCommands.showOrderAdmin();
                break;
            case "changeStatus":
                commands = command.split(",", 4);
                result = OrderCommands.changeStatus(commands[2],commands[3]);
                break;
        }
        return result;
    }

    private static String changeStatus(String idOrderString, String status) {
        int idOrder= Integer.parseInt(idOrderString);
        OrdersEntity order=HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .get(OrdersEntity.class,idOrder);
        order.setStatus(status);
        OrderCommands.update(order);
        return "success";
    }

    private static Object showOrderAdmin() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("FROM OrdersEntity ").list();
    }

    private static Object showOrder(String idString) {
        int idUser= Integer.parseInt(idString);
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from OrdersEntity WHERE user.id_user=:id").setParameter("id",idUser).list();
    }

    private static String addToOrder(String idUserString) {
        int idUser= Integer.parseInt(idUserString);
        Session session= HibernateSessionFactoryUtil.getSessionFactory().openSession();
        UsersEntity user= session.get(UsersEntity.class,idUser);
        List<BasketEntity> list=session.createQuery("FROM BasketEntity where user.id_user=:id").setParameter("id",idUser).list();
        session.close();
        int totalPrice=0;
        for (BasketEntity basket:list)
            totalPrice+=basket.getPrice();
        OrdersEntity order=new OrdersEntity(totalPrice,user);
        OrderCommands.save(order);
        try {
            ServerThread.getOutput().writeObject("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //BasketCommands.deleteAll(idUser);
        return String.valueOf(totalPrice);
    }
    private static void save(OrdersEntity order) {
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(order);
            tx1.commit();
            session.close();
    }
    public static void update (Object product){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(product);
        tx1.commit();
        session.close();
    }
}
