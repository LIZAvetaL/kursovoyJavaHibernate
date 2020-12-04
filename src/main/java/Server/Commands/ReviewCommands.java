package Server.Commands;

import Server.Database.HibernateSessionFactoryUtil;
import Server.Model.ProductEntity;
import Server.Model.ReviewsEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ReviewCommands {
    public static Object split(String command) {
        String[] commandNumber = command.split(",", 3);
        String[] commands;
        Object result = true;
        switch (commandNumber[1]) {
            case "showReviews":
                commands = command.split(",", 3);
                result = ReviewCommands.showReviews(commands[2]);
                break;
            case "addReview":
                commands = command.split(",", 5);
                result = ReviewCommands.addReview(commands[2],commands[3],commands[4]);
                break;

        }
        return result;
    }

    private static String addReview(String idUserString, String idProductString, String review) {


            int idUser= Integer.parseInt(idUserString), idProduct= Integer.parseInt(idProductString);
            ReviewsEntity temp= new ReviewsEntity(review,UserCommands.findByID(idUser),ProductCommands.findProductByID(idProduct));
            ReviewCommands.save(temp);


        return "success";
    }

    private static Object showReviews(String idProductString) {
        int idProduct= Integer.parseInt(idProductString);
        ProductEntity product= (ProductEntity) ProductCommands.findProductByID(idProduct);
        if(product==null) return null;
        return HibernateSessionFactoryUtil.getSessionFactory().openSession()
                    .createQuery("from ReviewsEntity where product.id_product=:id").setParameter("id", product.getId_product()).list();

    }
    private static void save (Object user){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }
}
