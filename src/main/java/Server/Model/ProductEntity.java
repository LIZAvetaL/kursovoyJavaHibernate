package Server.Model;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @Column(name = "idproduct")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idproduct;
    @Column(name = "type")
    private String type;
    @Column(name = "nameproduct")
    private String nameproduct;
    @Column(name = "amount")
    private int amount;
    @Column(name = "price")
    private int price;

    public ProductEntity(){}

    public ProductEntity(int idproduct, String type, String nameproduct, int amount, int price){
        this.idproduct=idproduct;
        this.type=type;
        this.nameproduct=nameproduct;
        this.amount=amount;
        this.price=price;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (idproduct != that.idproduct) return false;
        if (amount != that.amount) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (nameproduct != null ? !nameproduct.equals(that.nameproduct) : that.nameproduct != null) return false;
        if (price!= that.price) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idproduct;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (nameproduct != null ? nameproduct.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + price;
        return result;
    }
}
