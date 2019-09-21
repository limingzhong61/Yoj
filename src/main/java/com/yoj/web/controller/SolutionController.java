package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.nuts.auth.UserUtils;
import com.yoj.nuts.judge.Judge;
import com.yoj.nuts.judge.bean.static_fianl.Languages;
import com.yoj.nuts.judge.bean.static_fianl.Results;
import com.yoj.web.bean.Msg;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.Solution;
import com.yoj.web.bean.User;
import com.yoj.web.service.ProblemService;
import com.yoj.web.service.SolutionService;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/s")
public class SolutionController {
	@Autowired
	public SolutionService solutionService;
	@Autowired
	public ProblemService problemService;
	@Autowired
	private Judge Judge;
	@Autowired
	public UserService UserService;

	@PostMapping("/submit")
	public String submit(Solution solution, HttpServletRequest request,Map<String, Object> map) {
		User user = UserUtils.getUser();
//		if (user == null) {
//			request.setAttribute("msg", "提交代码前请先登录");
//			return "problem/submit";
//		}
		solution.setUserId(user.getUserId());
        Problem problem = problemService.queryById(solution.getProblemId());
        Judge.judge(solution,problem);
		//insert fail
		if(!solutionService.insertSolution(solution)) {
			map.put("msg", "insert solution fail");
			return "error/my_error";
		}
		problemService.updateSubmit(solution);
		// 重定向不能被thymeleaf解析，因为redirect重新发了一个请求，接受不到。。。
		return "redirect:/solution/set";
	}

	@RequestMapping("/set/{pageNumber}")
    @ResponseBody
	public Msg result(@PathVariable("pageNumber") Integer pageNumber) {
		PageHelper.startPage(pageNumber, 10);
		//需要获得用户名
		List<Solution> emps = solutionService.getAllWithUserName();
		PageInfo<Solution> page = new PageInfo<Solution>(emps, 5);
		for(Solution solution : page.getList()){
			solution.setResultStr(Results.toString(solution.getResult()));
			solution.setLanguageStr(Languages.toString(solution.getLanguage()));
		}
		return Msg.success().add("pageInfo", page);
	}
}
