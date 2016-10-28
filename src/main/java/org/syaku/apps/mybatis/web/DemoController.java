package org.syaku.apps.mybatis.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.syaku.apps.mybatis.domain.Demo;
import org.syaku.apps.mybatis.service.DemoService;

import javax.annotation.Resource;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 10. 28.
 */
@Controller
public class DemoController {

	@Resource(name = "freemarkerConfig")
	FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name="demoService")
	DemoService demoService;

	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public String dispDemoList(Model model) {
		System.out.println(freeMarkerConfigurer.getConfiguration().getNamingConvention());
		model.addAttribute("demoList", demoService.getDemoList());
		return "demo/list";
	}

	@RequestMapping(value = "/demo/{idx}", method = RequestMethod.GET)
	public String dispDemoView(Model model, @PathVariable("idx") String userId) {
		model.addAttribute("demo", demoService.getDemoObject(userId, "Y"));
		return "demo/view";
	}

	@RequestMapping(value = "/demo/post", method = RequestMethod.GET)
	public String dispDemoPostInsert(Model model) {
		model.addAttribute("demo", new Demo());
		return "demo/write";
	}

	@RequestMapping(value = "/demo/post/{idx}", method = RequestMethod.GET)
	public String dispDemoPostUpdate(Model model, @PathVariable("idx") String userId) {
		model.addAttribute("demo", demoService.getDemoObject(userId, "Y"));
		return "demo/write";
	}

	@RequestMapping(value = "/demo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Demo> procDemoInsert(@RequestBody Demo demo) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");

		return new ResponseEntity<>(demoService.insert(demo), responseHeaders, HttpStatus.OK);
	}
}
