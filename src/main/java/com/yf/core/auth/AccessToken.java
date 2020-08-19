package com.yf.core.auth;

import lombok.Builder;
import lombok.Data;

/**
 * Created by xdmdpz on 2018/6/25.
 */
@Data
@Builder
public class AccessToken {
    private String token;
    private String expiresIn;
    private Integer type;
}
