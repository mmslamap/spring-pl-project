package com.pl.premier_league.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="player_stats")
public class Player {

    @Id
    private Integer id;
    @Column(name = "player_name", unique = true)
    private String name;
    private String nation;
    private String position;
    private Integer age;
    @Column(name = "matches_played")
    private Integer matches_pld;
    private Integer starts;
    @Column(name = "minutes_played")
    private Double min_pld;
    private Double goals;
    private Double assists;
    private Double penalties_scored;
    private Double yellow_cards;
    private Double red_cards;
    @Column(name = "expected_goals")
    private Double exp_goals;
    @Column(name = "expected_assists")
    private Double exp_assists;
    private String team_name;

    public Player(String name, String nation, String position, Integer age, Integer matches_pld, Integer starts, Double min_pld, Double goals, Double assists, Double penalties_scored, Double yellow_cards, Double red_cards, Double exp_goals, Double exp_assists, String team_name) {
        this.name = name;
        this.nation = nation;
        this.position = position;
        this.age = age;
        this.matches_pld = matches_pld;
        this.starts = starts;
        this.min_pld = min_pld;
        this.goals = goals;
        this.assists = assists;
        this.penalties_scored = penalties_scored;
        this.yellow_cards = yellow_cards;
        this.red_cards = red_cards;
        this.exp_goals = exp_goals;
        this.exp_assists = exp_assists;
        this.team_name = team_name;
    }

    public Player() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getMatches_pld() {
        return matches_pld;
    }

    public void setMatches_pld(Integer matches_pld) {
        this.matches_pld = matches_pld;
    }

    public Integer getStarts() {
        return starts;
    }

    public void setStarts(Integer starts) {
        this.starts = starts;
    }

    public Double getMin_pld() {
        return min_pld;
    }

    public void setMin_pld(Double min_pld) {
        this.min_pld = min_pld;
    }

    public Double getGoals() {
        return goals;
    }

    public void setGoals(Double goals) {
        this.goals = goals;
    }

    public Double getAssists() {
        return assists;
    }

    public void setAssists(Double assists) {
        this.assists = assists;
    }

    public Double getPenalties_scored() {
        return penalties_scored;
    }

    public void setPenalties_scored(Double penalties_scored) {
        this.penalties_scored = penalties_scored;
    }

    public Double getYellow_cards() {
        return yellow_cards;
    }

    public void setYellow_cards(Double yellow_cards) {
        this.yellow_cards = yellow_cards;
    }

    public Double getRed_cards() {
        return red_cards;
    }

    public void setRed_cards(Double red_cards) {
        this.red_cards = red_cards;
    }

    public Double getExp_goals() {
        return exp_goals;
    }

    public void setExp_goals(Double exp_goals) {
        this.exp_goals = exp_goals;
    }

    public Double getExp_assists() {
        return exp_assists;
    }

    public void setExp_assists(Double exp_assists) {
        this.exp_assists = exp_assists;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }
}
