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
    //unique
    //    @Column(name = "used_id", nullable = false)
    //    private Long userId;
    @Column(name = "session_id", unique = true, nullable = false, length = 36)
    private String sessionId;

    //CascadeType.Persist (part of all) will only save cartItems to corresponding table
    // if NEWLY created cart has items (Set) not empty.
    @OneToMany(
            mappedBy = "cart", // Maps to the 'cart' field in CartItem entity
            cascade = CascadeType.ALL, // Persist/Merge/Remove CartItems when Cart is persisted/merged/removed
            orphanRemoval = true, // Automatically remove CartItems from DB if they are removed from this collection
            fetch = FetchType.LAZY // Load items only when explicitly requested
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
