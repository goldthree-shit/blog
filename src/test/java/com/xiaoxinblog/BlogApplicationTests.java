package com.xiaoxinblog;

import com.xiaoxinblog.dao.TypeRepository;
import com.xiaoxinblog.po.Blog;
import com.xiaoxinblog.po.Tag;
import com.xiaoxinblog.po.Type;
import com.xiaoxinblog.service.BlogService;
import com.xiaoxinblog.service.TagService;
import com.xiaoxinblog.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	TypeRepository typeRepository;
	@Autowired
	BlogService blogService;
	@Autowired
	TypeService typeService;
	@Autowired
	TagService tagService;

	@Test
	void contextLoads() {


		System.out.println(typeService.listTypeTop(6));
		List<Type> types = typeService.listTypeTop(6);
		System.out.println("-----------------"+types.get(0).getBlogs().size());
		System.out.println();
		System.out.println(tagService.listTagTop(10));
		List<Tag> tags = tagService.listTagTop(10);
		System.out.println("------------------"+tags.get(0).getBlogs().size());
		List<Blog> blogs = blogService.listRecommendBlogTop(8);
	}

	@Test
	void blogTest() {
		List<Blog> blogs = blogService.listRecommendBlogTop(8);
		Blog blog = blogs.get(0);
		System.out.println(blog.getId());
		System.out.println(blog.getRecommend());
		System.out.println(blog.getTagIds());
//		System.out.println(blog.getTags());
		System.out.println(blog.getUser());
		System.out.println(blog.getType().getName());
	}

}
