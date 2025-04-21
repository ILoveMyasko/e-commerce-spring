package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "carts" ,uniqueConstraints = {
@UniqueConstraint(columnNames = "session_id", name = "uk_cart_session_id")
})
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", unique = true, nullable = false, length = 36)
    private String sessionId;

    //CascadeType.Persist (part of all) will only save cartItems to corresponding table
    // if NEWLY created cart has items (Set) not empty.
    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true, // extra safety layer, can manage items solely from this class but its unclear
            fetch = FetchType.LAZY
    )
    private Set<CartItem> items = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this); // Set the back-reference
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null); // triggers orphan removal
    }

}
