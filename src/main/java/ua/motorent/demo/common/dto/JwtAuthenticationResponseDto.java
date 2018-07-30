package ua.motorent.demo.common.dto;

import java.util.Date;

public class JwtAuthenticationResponseDto {

    private String tokenType = "Bearer";

    private String accessToken;

    private Date dateCreateToken;

    public JwtAuthenticationResponseDto(String accessToken, Date date) {
        this.accessToken = accessToken;
        this.dateCreateToken = date;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Date getDateCreateToken() {
        return dateCreateToken;
    }

    public void setDateCreateToken(Date dateCreateToken) {
        this.dateCreateToken = dateCreateToken;
    }
}
