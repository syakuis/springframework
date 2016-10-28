package org.syaku.apps.mybatis.dao;

import org.syaku.apps.mybatis.domain.Demo;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 10. 28.
 */
public interface DemoDAO {
	List<Demo> select();
	Demo selectOne(String userId, String isUse);
	void insert(Demo demo);
}
