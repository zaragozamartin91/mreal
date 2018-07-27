package com.mz.mreal.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Meme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private RealityKeeper owner;

    private String imgName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE", nullable = false)
    private Date date = new Date();

    private Long upvotes = 0L;
    private Long downvotes = 0L;

    public Meme(String title, RealityKeeper owner, String imgName) {
        this.title = title;
        this.owner = owner;
        this.imgName = imgName;
    }

    public Meme() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealityKeeper getOwner() {
        return owner;
    }

    public void setOwner(RealityKeeper owner) {
        this.owner = owner;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Long upvotes) {
        this.upvotes = upvotes;
    }

    public Long getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Long downvotes) {
        this.downvotes = downvotes;
    }

    @Override public String toString() {
        return "Meme{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imgName='" + imgName + '\'' +
                ", date=" + date +
                ", upvotes=" + upvotes +
                ", downvotes=" + downvotes +
                '}';
    }
}
