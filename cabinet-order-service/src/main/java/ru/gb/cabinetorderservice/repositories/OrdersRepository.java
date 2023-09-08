package ru.gb.cabinetorderservice.repositories;


import net.bytebuddy.dynamic.DynamicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.cabinetorderservice.entities.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.userId = ?1 and o.isDeleted = false")
    List<Order> findAllByUserId(long userId);
    @Query("select o from Order o where o.userId = ?1 and o.filmId = ?2 and o.isDeleted = false ")
    List<Order> findByUserIdAndFilmId(long userId, long filmId);
    @Query("select o from Order o where o.userId = ?1 and o.type = 'RENT' and o.isDeleted = false")
    List<Order> findAllByUserIfFilmIsRent(long userId);
    @Query("select o from Order o where o.userId = ?1 and o.type = 'SALE' and o.isDeleted = false")
    List<Order> findAllByUserIfFilmIsSale(long userId);



}

