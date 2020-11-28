package Server.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "product")
public class ProductEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_product;
    @Column(name = "type")
    private String type;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private double price;
   @OneToMany(fetch = FetchType.LAZY,
           mappedBy = "product",
            cascade = CascadeType.ALL)
    private List<BasketEntity> basketList;

    public ProductEntity(){}

    public ProductEntity( String type, String nameproduct, int amount, double price){
        this.type=type;
        this.name=nameproduct;
        this.amount=amount;
        this.price=price;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int idproduct) {
        this.id_product = idproduct;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String nameproduct) {
        this.name = nameproduct;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<BasketEntity> getBasketList() {
        return basketList;
    }

    public void setBasketList(List<BasketEntity> basketList) {
        this.basketList = basketList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (id_product != that.id_product) return false;
        if (amount != that.amount) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price!= that.price) return false;

        return true;
    }

}
