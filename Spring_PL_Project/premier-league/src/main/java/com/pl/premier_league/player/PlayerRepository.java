package com.pl.premier_league.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    void deleteByName(String playerName);

    List<Player> findByNameContainsIgnoreCase(String name);

    Optional<Player> findByName(String name);
}
