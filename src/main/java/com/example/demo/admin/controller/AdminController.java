package com.example.demo.admin.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.admin.domain.model.BloodtypeDto;
import com.example.demo.admin.domain.model.GenerationDto;
import com.example.demo.admin.domain.model.MemberDto;
import com.example.demo.admin.domain.model.PrefectureDto;
import com.example.demo.admin.domain.service.BloodtypeService;
import com.example.demo.admin.domain.service.GenerationService;
import com.example.demo.admin.domain.service.MemberDetailService;
import com.example.demo.admin.domain.service.MemberService;
import com.example.demo.admin.domain.service.PrefectureService;
import com.example.demo.admin.form.MemberDetailForm;
import com.example.demo.admin.form.MemberForm;
import com.example.demo.common.domain.model.MemberDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {

  private final MemberDetailService memberDetailService;
  private final MemberService memberService;
  private final BloodtypeService bloodtypeService;
  private final PrefectureService prefectureService;
  private final GenerationService generationService;

  private final int N = 10; // ページ当たりの件数

  @GetMapping("/admin")
  public String getTable(Model model) {

    Integer page = (Integer) model.getAttribute("page");
    Pageable pageable = PageRequest.of(page == null ? 0 : page, N);
    Page<MemberDetailDto> md = memberDetailService.getMemberDetail(pageable);

    List<MemberDetailForm> form = md.stream().map(MemberDetailForm::fromDto).toList();

    model.addAttribute("form", form);
    model.addAttribute("pageable", md);

    return "admin/table";
  }

  @PostMapping("/admin/page")
  public String postTablePage(@RequestParam(required = false) Integer page,
      RedirectAttributes redirectAttributes) {

    redirectAttributes.addFlashAttribute("page", page);

    return "redirect:/admin";
  }

  @GetMapping("/admin/edit")
  public String getEdit(Model model) {

    MemberForm targetForm = (MemberForm) model.getAttribute("targetForm");
    Long id = (Long) model.getAttribute("id");
    MemberDto member =
        id == null || id == 0L ? memberService.getNewMember() : memberService.getMember(id);
    MemberForm form = MemberForm.fromDto(member);

    List<BloodtypeDto> bloodtypeList = bloodtypeService.getBloodtypeList();
    List<PrefectureDto> prefectureList = prefectureService.getPrefectureList();
    List<GenerationDto> generationList = generationService.getGenerationList();

    model.addAttribute("form", targetForm == null ? form : targetForm);
    model.addAttribute("originalForm", form);
    model.addAttribute("bloodtypeList", bloodtypeList);
    model.addAttribute("prefectureList", prefectureList);
    model.addAttribute("generationList", generationList);

    return "admin/edit";
  }

  // メンバ追加
  @PostMapping("/admin/add")
  public String postAdd() {
    return "redirect:/admin/edit";
  }

  // メンバ情報編集
  @PostMapping("/admin/edit")
  public String postEdit(@RequestParam Long id, RedirectAttributes redirectAttributes) {

    redirectAttributes.addFlashAttribute("id", id);

    return "redirect:/admin/edit";
  }

  // 変更箇所の確認
  @PostMapping("/admin/edit/check")
  public String postEditCheck(@Validated @ModelAttribute MemberForm form, BindingResult result,
      RedirectAttributes redirectAttributes) {

    redirectAttributes.addFlashAttribute("id", form.id());
    redirectAttributes.addFlashAttribute("targetForm", form);

    return "redirect:/admin/edit";
  }

  // 確定
  @PostMapping("/admin/edit/commit")
  public String postEditCommit(@Validated @ModelAttribute MemberForm form, BindingResult result,
      RedirectAttributes redirectAttributes) {

    if (result.hasErrors()) {
      redirectAttributes.addFlashAttribute("targetForm", form);
      redirectAttributes.addFlashAttribute(
          BindingResult.MODEL_KEY_PREFIX + "form", result);
      return "redirect:/admin/edit";
    }

    if (form != null) {
      MemberDto member = MemberDto.fromForm(form);
      int page = 0;
      if (member.id() == 0) {
        memberService.addMember(member);
      }
      else {
        memberService.updateMember(member);
        page = (int) ((member.id() - 1) / N);
      }
      redirectAttributes.addFlashAttribute("page", page);
    }
    return "redirect:/admin";
  }

  @PostMapping("/admin/edit/back")
  public String postEditBack() {

    return "redirect:/admin";
  }
}
