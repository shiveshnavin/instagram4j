package com.github.instagram4j.instagram4j.exceptions;

import com.github.instagram4j.instagram4j.responses.IGResponse;
import com.github.instagram4j.instagram4j.responses.challenge.ChallengeRequiredResponse;

public class ChallengeRequiredException extends IGResponseException{
    private ChallengeRequiredResponse challengeResponse;

    public ChallengeRequiredException(IGResponse response, ChallengeRequiredResponse challengeResponse) {
        super(response);
        this.challengeResponse = challengeResponse;
    }
}
