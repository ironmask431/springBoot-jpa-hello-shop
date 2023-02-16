package com.leesh.controller;

import com.leesh.domains.Address;
import com.leesh.domains.Member;
import com.leesh.dtos.MemberForm;
import com.leesh.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm memberForm, BindingResult bindingResult){

        //bindingResult 를 사용하면 에러 발생시 (validation 통과실패, exception등?) 사유를 가지고 특정페이지로 리턴이 가능하다.
        if(bindingResult.hasErrors()){
            return "members/createMemberForm";
        }
        Address address = new Address(memberForm.getCity(), memberForm.getStreet(),memberForm.getZipcode());
        Member member = new Member(memberForm.getName(), address);
        memberService.save(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.finalAll();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
