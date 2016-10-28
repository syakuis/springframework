package org.syaku.apps.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.syaku.apps.mybatis.dao.DemoDAO;
import org.syaku.apps.mybatis.domain.Demo;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 10. 28.
 */
@MapperScan
public interface DemoMapper extends DemoDAO {
	Demo selectOne(@Param("user_id") String userId, @Param("is_use") String isUse);
}
