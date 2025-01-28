package com.example.play_view.game;

import com.example.play_view.company.CompanyEntity;
import com.example.play_view.publisher.PublisherEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "games_publishers",
        joinColumns = @JoinColumn(name = "cod_game"),
        inverseJoinColumns = @JoinColumn(name = "cod_publisher")
    )
    private Set<PublisherEntity> publishers = new HashSet<>();

    @Column(name = "title")
    private String title;

    @Column(name = "cover_url")
    private String coverUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "game_description")
    private String description;

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

    public Set<PublisherEntity> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<PublisherEntity> publishers) {
        this.publishers = publishers;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String gameDescription) {
        this.description = gameDescription;
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
                ", gameDescription='" + description + '\'' +
                ", indication='" + indication + '\'' +
                '}';
    }
}
