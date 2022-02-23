package com.github.instagram4j.instagram4j.responses.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.instagram4j.instagram4j.responses.accounts.LoginResponse;
import com.github.instagram4j.instagram4j.utils.IGUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ChallengeRequiredResponse extends LoginResponse {

    public static ChallengeRequiredResponse parseResponse(String json) throws JsonProcessingException {
        return IGUtils.jsonToObject(json, ChallengeRequiredResponse.class);
    }
}
