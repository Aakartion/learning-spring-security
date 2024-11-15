package edu.miu.cs489.securitydemo.secured;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/management")
public class MemberController {

    @GetMapping("/member")
    public String member() {
        System.out.println("MEMBER HERE");
        return "Member: secured end point";
    }

    @GetMapping("/member-only")
    @PreAuthorize("hasAnyAuthority('member:read','member:write')")
    public String memberOnly(){
        return "MemberReadOnly: secured end point Read Only Member";
    }

//    When we have read only memebers then we can use like this
//    @GetMapping("/memberRead-only")
//    @PreAuthorize("hasAuthority('member:read')")
//    public String memberReadOnly(){
//        return "MemberOnly: secured end point for all members";
//    }
}