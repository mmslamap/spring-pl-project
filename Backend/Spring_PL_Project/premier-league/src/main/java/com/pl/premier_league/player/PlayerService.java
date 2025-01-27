package com.pl.premier_league.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {

        this.playerRepository = playerRepository;

    }

    public List<Player> getPlayer(){

        return playerRepository.findAll();
    }

    public List<Player> getPlayerFromTeam(String teamName){

        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeam_name()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerByName(String playerName) {

        List<Player> playerList = playerRepository.findByNameContainsIgnoreCase(playerName);

        return playerList == null || playerList.isEmpty() ? new ArrayList<>() : playerList;

    }

    public List<Player> getPlayerByPosition(String playerPosition) {

        return playerRepository.findAll().stream()
                .filter(player -> playerPosition.equals(player.getPosition()))
                .collect(Collectors.toList());
    }

    public List<Player> getPLayerByTeamAndPosition(String teamName, String position) {

        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeam_name()) && position.equals(player.getPosition()))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player) {

        playerRepository.save(player);

        return player;
    }

    public Player updatePlayer(Player player) {

        Optional<Player> existingPlayer = playerRepository.findByName(player.getName());

        if (existingPlayer.isPresent()) {

            Player playerToUpdate = existingPlayer.get();
            playerToUpdate.setName(player.getName());
            playerToUpdate.setAge(player.getAge());
            playerToUpdate.setNation(player.getNation());
            playerToUpdate.setMatches_pld(player.getMatches_pld());
            playerToUpdate.setPosition(player.getPosition());
            playerToUpdate.setMin_pld(player.getMin_pld());
            playerToUpdate.setGoals(player.getGoals());
            playerToUpdate.setAssists(player.getAssists());
            playerToUpdate.setPenalties_scored(player.getPenalties_scored());
            playerToUpdate.setExp_goals(player.getExp_goals());
            playerToUpdate.setExp_assists(player.getExp_assists());
            playerToUpdate.setStarts(player.getStarts());
            playerToUpdate.setRed_cards(player.getRed_cards());
            playerToUpdate.setYellow_cards(player.getYellow_cards());
            playerToUpdate.setTeam_name(player.getTeam_name());

            playerRepository.save(playerToUpdate);

            return playerToUpdate;

        }

        return null;

    }

    @Transactional
    public void deletePlayer(String playerName) {

        playerRepository.deleteByName(playerName);

    }
}
