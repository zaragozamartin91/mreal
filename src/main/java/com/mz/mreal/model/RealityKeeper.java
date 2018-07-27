package com.mz.mreal.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class RealityKeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<Meme> memes = new HashSet<>();

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

    @Override
    public String toString() {
        return "RealityKeeper{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + "****" + '\'' +
                '}';
    }
}
