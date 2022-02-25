package com.github.instagram4j.instagram4j.responses.accounts;

import com.github.instagram4j.instagram4j.responses.IGResponse;
import lombok.Data;

import java.util.Date;

@Data
public class AccountsAccessToolHtmlResponse extends IGResponse {
    private String htmlText;
    private Date joinedDate;

    public AccountsAccessToolHtmlResponse(String htmlText) {
        this.htmlText = htmlText;
        parseJoinedDate();
    }

    private void parseJoinedDate() {
        int startIndex = htmlText.indexOf("timestamp", htmlText.indexOf("date_joined"));
        startIndex += "timestamp\":".length();
        int endIndex = htmlText.indexOf("}", startIndex);
        long dateSeconds = Long.parseLong(htmlText.substring(startIndex, endIndex));
        joinedDate = new Date(dateSeconds * 1000);
    }
}
