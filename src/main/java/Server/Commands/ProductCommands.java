package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.ProductEntity;
import Server.Model.UsersEntity;

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
       return list;
    }
}
