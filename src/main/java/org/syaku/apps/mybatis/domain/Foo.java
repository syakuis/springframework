package org.syaku.apps.mybatis.domain;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 10. 28.
 */
public class Foo {
	private String name;
	private int personCount;
	private List<Demo> dataList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPersonCount() {
		return personCount;
	}

	public void setPersonCount(int personCount) {
		this.personCount = personCount;
	}

	public List<Demo> getDataList() {
		return dataList;
	}

	public void setDataList(List<Demo> dataList) {
		this.dataList = dataList;
	}
}
