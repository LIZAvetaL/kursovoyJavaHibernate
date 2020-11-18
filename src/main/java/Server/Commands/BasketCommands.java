package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.BasketEntity;
import Server.Model.ProductEntity;
import Server.Model.UsersEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        ProductEntity product= (ProductEntity)HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("FROM ProductEntity WHERE id_product=?").setParameter(0,idProduct);
        UsersEntity user= (UsersEntity) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("FROM UsersEntity WHERE id_user=?").setParameter(0,idUser);
       BasketEntity basket=new BasketEntity();
       basket.setProduct(product);
       basket.setUser(user);
       basket.setPrice(product.getPrice());
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
