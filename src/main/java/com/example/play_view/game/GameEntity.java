package com.example.play_view.game;

import com.example.play_view.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private long gameId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cod_company")
    private CompanyEntity company;

    @Column(name = "title")
    private String title;

    @Column(name = "cover_url")
    private String coverUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "game_description")
    private String gameDescription;

    @Column(name = "indication")
    private String indication;

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    @Override
    public String toString() {
        return "GameEntity{" +
                "gameId=" + gameId +
                ", codCompany=" + company +
                ", title='" + title + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", releaseDate=" + releaseDate +
                ", gameDescription='" + gameDescription + '\'' +
                ", indication='" + indication + '\'' +
                '}';
    }
}
