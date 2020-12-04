package Server.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class ReviewsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private int id;
    @Column(name = "review")
    private String review;
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "user", referencedColumnName = "login")
    private UsersEntity user;
    @ManyToOne (fetch=FetchType.LAZY)
    @JoinColumn (name = "product", referencedColumnName = "name")
    private ProductEntity product;

    public ReviewsEntity(){}

    public ReviewsEntity(String review, UsersEntity user, ProductEntity product) {
        this.review = review;
        this.user = user;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String reviews) {
        this.review = reviews;
    }


}
