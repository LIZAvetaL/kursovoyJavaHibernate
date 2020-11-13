package Server.Model;
import javax.persistence.*;

@Entity
@Table(name="Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_prod;
    private String name;
    private String type;
    private int price;
    private int amount;

}
