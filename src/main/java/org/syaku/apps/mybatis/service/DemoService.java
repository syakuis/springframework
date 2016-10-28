package org.syaku.apps.mybatis.service;

import org.springframework.stereotype.Service;
import org.syaku.apps.mybatis.dao.DemoDAO;
import org.syaku.apps.mybatis.domain.Demo;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 10. 28.
 */
@Service
public class DemoService {
	@Resource(name = "demoMapper")
	DemoDAO demoDAO;

	public List<Demo> getDemoList() {
		return demoDAO.select();
	}

	public Demo getDemoObject(String userId, String isUse) {
		return demoDAO.selectOne(userId, isUse);
	}

	public Demo insert(Demo demo) {
		demoDAO.insert(demo);
		return demo;
	}
}
