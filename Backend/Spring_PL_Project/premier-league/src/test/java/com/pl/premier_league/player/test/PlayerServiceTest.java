package com.pl.premier_league.player.test;

import com.pl.premier_league.player.Player;
import com.pl.premier_league.player.PlayerRepository;
import com.pl.premier_league.player.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {

        player1 = new Player();
        player2 = new Player();

        player1.setId(123456);
        player1.setName("George Washington");
        player1.setTeam_name("Patriots");
        player1.setPosition("MF");

        player2.setId(9999);
        player2.setName("George III");
        player2.setTeam_name("Loyalist");
        player2.setPosition("MF");

    }

    @Test
    public void testGetAllPlayers() {

        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

        List<Player> players = playerService.getAllPlayers();

        assertNotNull(players);
        assertEquals(2, players.size());
        assertTrue(players.contains(player1));
        assertTrue(players.contains(player2));

        verify(playerRepository, times(1)).findAll();

    }

    @Test
    public void testGetPlayersFromTeam() {

        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

        List<Player> players1 = playerService.getPlayersFromTeam("Patriots");
        List<Player> players2 = playerService.getPlayersFromTeam("Loyalist");

        assertNotNull(players1);
        assertEquals(1, players1.size());
        assertTrue(players1.contains(player1));

        assertNotNull(players2);
        assertEquals(1, players2.size());
        assertTrue(players2.contains(player2));

        verify(playerRepository, times(2)).findAll();

    }

    @Test
    public void testGetPlayersByName() {

        when(playerRepository.findByNameContainsIgnoreCase("George Washington")).thenReturn(Arrays.asList(player1));
        when(playerRepository.findByNameContainsIgnoreCase("George III")).thenReturn(Arrays.asList(player2));

        List<Player> players1 = playerService.getPlayersByName("George Washington");
        List<Player> players2 = playerService.getPlayersByName("George III");

        assertNotNull(players1);
        assertEquals(1, players1.size());
        assertTrue(players1.contains(player1));

        assertNotNull(players2);
        assertEquals(1, players2.size());
        assertTrue(players2.contains(player2));

        verify(playerRepository, times(1)).findByNameContainsIgnoreCase("George Washington");
        verify(playerRepository, times(1)).findByNameContainsIgnoreCase("George III");

    }

    @Test
    public void testGetPlayersByPosition() {

        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

        List<Player> players = playerService.getPlayersByPosition("MF");

        assertNotNull(players);
        assertEquals(2, players.size());
        assertTrue(players.contains(player1));
        assertTrue(players.contains(player2));

        verify(playerRepository, times(1)).findAll();

    }

    @Test
    public void testGetPlayersByTeamAndPosition() {

        when(playerRepository.findAll()).thenReturn(Arrays.asList(player1, player2));

        List<Player> players1 = playerService.getPLayersByTeamAndPosition("Patriots","MF");
        List<Player> players2 = playerService.getPLayersByTeamAndPosition("Loyalist","MF");

        assertNotNull(players1);
        assertEquals(1, players1.size());
        assertTrue(players1.contains(player1));

        assertNotNull(players2);
        assertEquals(1, players2.size());
        assertTrue(players2.contains(player2));

        verify(playerRepository, times(2)).findAll();

    }

    @Test
    public void testAddPlayer() {

        when(playerRepository.save(player1)).thenReturn(player1);

        Player addedPlayer = playerService.addPlayer(player1);

        assertNotNull(addedPlayer);
        assertEquals("George Washington", addedPlayer.getName());
        assertEquals("Patriots", addedPlayer.getTeam_name());
        assertEquals("MF", addedPlayer.getPosition());

        verify(playerRepository, times(1)).save(player1);
    }

    @Test
    void testUpdatePlayer_PlayerExists() {

        Player existingPlayer = new Player();
        existingPlayer.setName("John Doe");

        Player updatedPlayer = new Player();
        updatedPlayer.setName("John Doe");
        updatedPlayer.setAge(25);
        updatedPlayer.setNation("USA");
        updatedPlayer.setMatches_pld(30);

        when(playerRepository.findByName("John Doe")).thenReturn(Optional.of(existingPlayer));
        when(playerRepository.save(any(Player.class))).thenReturn(existingPlayer);

        Player result = playerService.updatePlayer(updatedPlayer);

        assertNotNull(result);
        assertEquals(25, result.getAge());
        assertEquals("USA", result.getNation());
        assertEquals(30, result.getMatches_pld());
        verify(playerRepository).save(existingPlayer);
    }

    @Test
    void testUpdatePlayer_PlayerDoesNotExist() {

        Player newPlayer = new Player();
        newPlayer.setName("Unknown Player");

        when(playerRepository.findByName("Unknown Player")).thenReturn(Optional.empty());

        Player result = playerService.updatePlayer(newPlayer);

        assertNull(result);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    void testDeletePlayer() {

        String playerName = "John Doe";

        playerService.deletePlayer(playerName);

        verify(playerRepository, times(1)).deleteByName(playerName);
    }
}
