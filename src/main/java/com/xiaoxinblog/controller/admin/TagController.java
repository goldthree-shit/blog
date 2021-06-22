package com.xiaoxinblog.controller.admin;


import com.xiaoxinblog.po.Tag;
import com.xiaoxinblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class TagController {

	@Autowired
	private TagService tagService;


	@GetMapping("/tags")
	public String list(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
					   Model model) {
		model.addAttribute("page", tagService.listTag(pageable));
		return "admin/tags";
	}

	@GetMapping("/tags/input")
	public String input(Model model) {
		model.addAttribute("tag", new Tag());
		return "admin/tags-input";
	}

	@GetMapping("/tags/{id}/input")
	public String editInput(@PathVariable Long id, Model model) {
		model.addAttribute("tag", tagService.getTag(id));
		return "admin/tags-input";
	}

	@PostMapping("/tags")// 因为提交方式不同所以可以用一样的url
	public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {

		if(tagService.getTagByName(tag.getName()) != null) {
			result.rejectValue("name","nameError", "不能添加重复分类");
		}
		if (result.hasErrors()) {
			return "admin/tags-input";
		}

		Tag t = tagService.saveTag(tag);
		if(t == null) {
			attributes.addFlashAttribute("message", "添加失败");
		}
		attributes.addFlashAttribute("message", "添加成功");
		return "redirect:/admin/tags";
	}

	@PostMapping("/tags/{id}")// 因为提交方式不同所以可以用一样的url
	public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id,RedirectAttributes attributes) {

		if(tagService.getTagByName(tag.getName()) != null) {
			result.rejectValue("name","nameError", "不能添加重复分类");
		}
		if (result.hasErrors()) {
			return "admin/tags-input";
		}

		Tag t = tagService.updateTag(id, tag);
		if(t == null) {
			attributes.addFlashAttribute("message", "更新失败");
		}
		attributes.addFlashAttribute("message", "更新成功");
		return "redirect:/admin/tags";
	}

	@GetMapping("/tags/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		tagService.deleteTag(id);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/tags";
	}
}
