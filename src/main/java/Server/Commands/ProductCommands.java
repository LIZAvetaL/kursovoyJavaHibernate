package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.BasketEntity;
import Server.Model.ProductEntity;
import Server.ServerThread;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "ShowProduct":
                result = ProductCommands.showProduct();
                break;
            case "addProduct":
                commands = command.split(",", 6);
                result = ProductCommands.addProduct(commands[2],commands[3],commands[4],commands[5]);
                break;
            case "deleteProduct":
                commands = command.split(",", 3);
                result = ProductCommands.deleteProduct(commands[2]);
                break;
            case "editProduct":
                commands = command.split(",", 3);
                result = ProductCommands.editProduct(commands[2]);
                break;
        }
        return result;
    }

    private static Object editProduct(String idString) {
        int id = Integer.parseInt(idString);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        ProductEntity product = session.get(ProductEntity.class, id);
        if (product == null) return "fail";
        try {
            ServerThread.getOutput().writeObject(product);

            //ProductCommands.updateProduct(product.get(0));
        } catch (IOException e) {
            e.printStackTrace();

            return "success";
        }
        return null;
    }

        private static String deleteProduct (String idString){
            int id = Integer.parseInt(idString);
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            ProductEntity product = session.get(ProductEntity.class, id);
            session.close();
            if (product == null) return "fail";
            ProductCommands.delete(product);
            return "success";
        }

        private static String addProduct (String type, String name, String amountString, String priceString){
            double price = Double.parseDouble(priceString);
            int amount = Integer.parseInt(amountString);
            ProductEntity product = new ProductEntity(type, name, amount, price);
            ProductCommands.save(product);
            return "success";
        }

        private static Object showProduct () {
            return HibernateSessionFactoryUtil.getSessionFactory().openSession().
                    createQuery("from ProductEntity ").list();

        }
        public static void updateProduct (ProductEntity product){
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.update(product);
            tx1.commit();
            session.close();
        }
        private static void delete (Object basket){
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.delete(basket);
            tx1.commit();
            session.close();
        }
        private static void save (Object user){
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
            session.close();
        }
}

