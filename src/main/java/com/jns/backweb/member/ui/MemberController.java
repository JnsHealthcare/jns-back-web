package com.jns.backweb.member.ui;

import com.jns.backweb.auth.model.LoginMember;
import com.jns.backweb.common.dto.ApiResponse;
import com.jns.backweb.member.ui.dto.MemberSimpleInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/members")
public class MemberController {


    @GetMapping("/me")
    public ApiResponse<MemberSimpleInfo> getMemberSimpleData(@AuthenticationPrincipal LoginMember loginMember) {
        MemberSimpleInfo memberSimpleInfo = new MemberSimpleInfo(loginMember.getName(), loginMember.getEmail());

        return ApiResponse.success(memberSimpleInfo);
    }

}
