package com.jns.backweb.auth.application;


import com.jns.backweb.auth.model.MemberInfo;

import java.util.Map;

public interface OauthClient {
   Map<String, Object> getOauthMemberInfo(String code);
}
