package com.github.instagram4j.instagram4j.requests.accounts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.IGGetRequest;
import com.github.instagram4j.instagram4j.responses.accounts.AccountsAccessToolHtmlResponse;
import kotlin.Pair;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class AccountsAccessToolRequest extends IGGetRequest<AccountsAccessToolHtmlResponse> {
    @Override
    public String path() {
        return "accounts/access_tool/";
    }

    @Override
    public HttpUrl formUrl(IGClient client) {
        return HttpUrl.parse(baseApiUrl() + path() + getQueryString(client));
    }

    @Override
    public Class<AccountsAccessToolHtmlResponse> getResponseType() {
        return AccountsAccessToolHtmlResponse.class;
    }

    @Override
    public AccountsAccessToolHtmlResponse parseResponse(Pair<Response, String> response) {
        return new AccountsAccessToolHtmlResponse(response.getSecond());
    }

    @Override
    protected Request.Builder applyHeaders(IGClient client, Request.Builder req) {
        super.applyHeaders(client, req);
        req.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 11; LE2120 Build/RKQ1.201105.002; wv) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/98.0.4758.87 Mobile Safari/537.36 " +
                "Instagram 212.0.0.38.119 " +
                "Android (30/11; 450dpi; 1080x2263; OnePlus; LE2120; OnePlus9Pro; qcom; ru_RU; 329675731)");
        req.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif," +
                "image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        return req;
    }
}
