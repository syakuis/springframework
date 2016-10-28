package org.syaku.apps.mybatis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 10. 28.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Demo extends Extend {
	@JsonProperty("user_name")
	public String user_name;
	@JsonProperty("is_use")
	public String is_use;

	public String getUserName() {
		return user_name;
	}

	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	public String getIsUse() {
		return is_use;
	}

	public void setIsUse(String is_use) {
		this.is_use = is_use;
	}
}
