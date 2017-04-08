package com.cs40333.rmcmulle.lab2_rmcmulle;

import java.io.Serializable;

/**
 * Created by Ryan on 3/1/2017.
 */

public class Team implements Serializable {
    private String teamName;
    private String teamLogo;
    private String teamDate;
    private String teamDay;
    private String teamTime;
    private String teamMascot;
    private String teamRecord;
    private String teamScore;
    private String teamScoreTime;
    private String teamLocation;

    public Team(String team_logo, String team_name, String team_date, String team_day, String team_time, String team_mascot, String team_record,
                     String team_score, String team_score_time, String team_location) {
        setTeamLogo(team_logo);
        setTeamName(team_name);
        setTeamDate(team_date);
        setTeamDay(team_day);
        setTeamTime(team_time);
        setTeamMascot(team_mascot);
        setTeamRecord(team_record);
        setTeamScore(team_score);
        setTeamScoreTime(team_score_time);
        setTeamLocation(team_location);
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public void setTeamDate(String teamDate) {
        this.teamDate = teamDate;
    }

    public void setTeamDay(String teamDay) {
        this.teamDay = teamDay;
    }

    public void setTeamTime(String teamTime) {
        this.teamTime = teamTime;
    }

    public void setTeamMascot(String teamMascot) {
        this.teamMascot = teamMascot;
    }

    public void setTeamRecord(String teamRecord) {
        this.teamRecord = teamRecord;
    }

    public void setTeamScore(String teamScore) {
        this.teamScore = teamScore;
    }

    public void setTeamScoreTime(String teamScoreTime) {
        this.teamScoreTime = teamScoreTime;
    }

    public void setTeamLocation(String teamLocation) {
        this.teamLocation = teamLocation;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public String getTeamDate() {
        return teamDate;
    }

    public String getTeamDay() {
        return teamDay;
    }

    public String getTeamTime() {
        return teamTime;
    }

    public String getTeamMascot() {
        return teamMascot;
    }

    public String getTeamRecord() {
        return teamRecord;
    }

    public String getTeamScore() {
        return teamScore;
    }

    public String getTeamScoreTime() {
        return teamScoreTime;
    }

    public String getTeamLocation() {
        return teamLocation;
    }
}