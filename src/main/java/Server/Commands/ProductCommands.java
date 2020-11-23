package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.ProductEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 2);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "ShowProduct":
                result = ProductCommands.showProduct();
                break;
        }
        return result;
    }

    private static Object showProduct() {
        List<ProductEntity> list =HibernateSessionFactoryUtil.getSessionFactory().openSession().
                createQuery("from ProductEntity ").list();
        ArrayList<String> list2=new ArrayList<>();
        for (ProductEntity product:list) {
            String id= String.valueOf(product.getId_product());
            String type=product.getType();
            String name=product.getName();
            String amount= String.valueOf(product.getAmount());
            String price= String.valueOf(product.getPrice());
            list2.add(id+" "+type+" "+name+" "+amount+" "+price);
        }
       return list2;
    }
    public static void updateProduct(ProductEntity product){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(product);
        tx1.commit();
        session.close();
    }
}
