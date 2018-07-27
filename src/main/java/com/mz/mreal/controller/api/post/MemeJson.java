package com.mz.mreal.controller.api.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mz.mreal.model.Meme;

import java.util.Date;

public class MemeJson {
    @JsonIgnore
    private Meme meme;

    public MemeJson(Meme meme) { this.meme = meme; }

    public Long getId() {return meme.getId();}

    public String getTitle() {return meme.getTitle();}

    public String getImgName() {return meme.getImgName();}

    public Date getDate() {return meme.getDate();}

    public Long getUpvotes() {return meme.getUpvotes();}

    public Long getDownvotes() {return meme.getDownvotes();}

    public String getUsername() {return meme.getOwner().getUsername();}
}
