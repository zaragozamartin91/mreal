package com.mz.mreal.controller.api.signup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mz.mreal.model.RealityKeeper;

/**
 * SignupResponse
 */
public class SignupResponse {
    @JsonIgnore
    private RealityKeeper realityKeeper;

    public SignupResponse(RealityKeeper realityKeeper) {
        this.realityKeeper = realityKeeper;
    }

    public Long getId() {
        return realityKeeper.getId();
    }

    public String getUsername() {
        return realityKeeper.getUsername();
    }
}