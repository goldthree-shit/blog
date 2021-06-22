package com.xiaoxinblog.service;

import com.xiaoxinblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

	Tag saveTag(Tag tag);

	Tag getTag(Long id);

	Tag getTagByName(String name);

	List<Tag> listTag(String ids);

	List<Tag> listTag();

	List<Tag> listTagTop(Integer size);

	Page<Tag> listTag(Pageable pageable);

	Tag updateTag(Long id, Tag tag);

	void deleteTag(Long id);



}
