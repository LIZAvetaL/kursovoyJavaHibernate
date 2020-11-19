package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.BasketEntity;
import Server.Model.ProductEntity;
import Server.Model.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BasketCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "addToBasket":
                commands = command.split(",", 4);
                result = BasketCommands.addToBasket(commands[2], commands[3]);
                break;
        }
        return result;
    }

    private static String addToBasket(String id_product, String id_user) {
        int idProduct=Integer.parseInt(id_product);
        int idUser=Integer.parseInt(id_user);
        Session session=HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<ProductEntity> product= session.createQuery("FROM ProductEntity WHERE id_product=:id").setParameter("id",idProduct).list();
        List<UsersEntity> user= session.createQuery("FROM UsersEntity WHERE id_user=:id").setParameter("id",idUser).list();
       session.close();
        BasketEntity basket=new BasketEntity();
       basket.setProduct(product.get(0));
       basket.setUser(user.get(0));
       basket.setPrice(product.get(0).getPrice());
       basket.setAmount(1);
       BasketCommands.save(basket);
       return "success";
    }
    public static void save(BasketEntity basket) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(basket);
        tx1.commit();
        session.close();
    }
}
