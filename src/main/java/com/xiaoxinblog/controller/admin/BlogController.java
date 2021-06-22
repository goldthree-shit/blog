package com.xiaoxinblog.controller.admin;

import com.xiaoxinblog.po.Blog;
import com.xiaoxinblog.po.User;
import com.xiaoxinblog.service.BlogService;
import com.xiaoxinblog.service.TagService;
import com.xiaoxinblog.service.TypeService;
import com.xiaoxinblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.Id;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private TypeService typeService;

	@Autowired
	private TagService tagService;

	private static final String INPUT = "admin/blogs-input";
	private static final String List = "admin/blogs";
	private static final String REDIRECT_LIST = "redirect:/admin/blogs";

	@GetMapping("/blogs")
	public String list(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
					   BlogQuery blog, Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("page", blogService.listBlog(pageable, blog));

		return "admin/blogs";
	}

	@PostMapping("/blogs/search")
	public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
						 BlogQuery blog, Model model) {
		System.out.println(blog);
		model.addAttribute("page", blogService.listBlog(pageable, blog));

		return "admin/blogs :: blogList";
	}

	@GetMapping("/blogs/input")
	public String input(Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("tags", tagService.listTag());
		Blog blog = new Blog();
		blog.setFlag("原创");
		blog.setRecommend(true);
		blog.setAppreciation(true);
		blog.setCommentabled(true);
		blog.setShareStatement(true);
		model.addAttribute("blog", blog);
		return "admin/blogs-input";
	}

	@GetMapping("/blogs/{id}/input")
	public String editInput(@PathVariable Long id, Model model) {
		model.addAttribute("types", typeService.listType());
		model.addAttribute("tags", tagService.listTag());
		Blog blog = blogService.getBlog(id);
		blog.init();
		model.addAttribute("blog", blog);
		return "admin/blogs-input";
	}

	@PostMapping("/blogs")
	public String post(Blog blog, HttpSession session, RedirectAttributes attributes) {
		blog.setUser((User) session.getAttribute("user"));
		blog.setType(typeService.getType(blog.getType().getId()));
		blog.setTags(tagService.listTag(blog.getTagIds()));

		Blog b;
		if(blog.getId() == null) {
			b = blogService.saveBlog(blog);
		} else {
			b = blogService.updateBlog(blog.getId(), blog);
		}
		if(b == null) {
			attributes.addFlashAttribute("message", "操作失败");
		} else {
			attributes.addFlashAttribute("message", "操作成功");
		}
		return "redirect:/admin/blogs";
	}

	@GetMapping("/blogs/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/blogs";
	}
}
