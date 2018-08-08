package com.mz.mreal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RealityKeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    /* Si borro un rkeeper, entonces borro sus memes asociados */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Meme> memes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "rkeeper_upvotes")
    private Set<Meme> upvotedMemes = new HashSet<>();

    public RealityKeeper(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public RealityKeeper() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Meme> getMemes() {
        return memes;
    }

    public void setMemes(Set<Meme> memes) {
        this.memes = memes;
    }

    public Set<Meme> getUpvotedMemes() {
        return upvotedMemes;
    }

    public void setUpvotedMemes(Set<Meme> upvotedMemes) {
        this.upvotedMemes = upvotedMemes;
    }

    @Override
    public String toString() {
        return "RealityKeeper{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + "****" + '\'' +
                '}';
    }
}
