package com.example.light_up_travel.repository;

import com.example.light_up_travel.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourite, Long> {
    @Query("SELECT f FROM Favourite f WHERE f.user.id = ?1 AND f.dateDeleted IS NULL")
    List<Favourite> findAllNotDeletedFavouriteByUserId(Long userId);

    @Query("SELECT f FROM Favourite f WHERE f.user.id = ?1 AND f.dateDeleted IS NULL")
    Favourite findNotDeletedFavouriteByUserId(Long userId);
}
