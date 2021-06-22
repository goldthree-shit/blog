package com.xiaoxinblog.service;

import com.xiaoxinblog.Exception.NotFindException;
import com.xiaoxinblog.dao.TagRepository;
import com.xiaoxinblog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;

	@Transactional // 开启事务管理
	@Override
	public Tag saveTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Transactional
	@Override
	public Tag getTag(Long id) {
		return tagRepository.getOne(id);
	}


	@Override
	public Tag getTagByName(String name) {
		return tagRepository.findByName(name);
	}

	@Override
	public List<Tag> listTag(String ids) {
		return tagRepository.findAllById(this.convertToList(ids));
	}

	@Override
	public List<Tag> listTag() {
		return tagRepository.findAll();
	}

	@Override
	public List<Tag> listTagTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
		Pageable pageable = PageRequest.of(0,size,sort);
		return tagRepository.findTop(pageable);
	}

	@Transactional
	@Override
	public Page<Tag> listTag(Pageable pageable) {
		return tagRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Tag updateTag(Long id, Tag tag) {
		Tag t = tagRepository.getOne(id);
		if(t == null) {
			throw new NotFindException("不存在该类型");
		}

		BeanUtils.copyProperties(tag, t);

		return tagRepository.save(t);
	}

	@Transactional
	@Override
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);
	}

	private  List<Long> convertToList(String ids) {
		List<Long> list= new ArrayList<>();
		if(!"".equals(ids) && ids != null) {
			String[] idArray = ids.split(",");
			for (int i = 0; i < idArray.length; i++) {
				list.add(Long.valueOf(idArray[i]));
			}
		}
		return list;
	}
}
