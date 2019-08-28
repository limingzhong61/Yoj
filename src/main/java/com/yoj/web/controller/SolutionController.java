package com.yoj.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.judge.Judge;
import com.yoj.web.bean.Msg;
import com.yoj.web.bean.Solution;
import com.yoj.web.bean.User;
import com.yoj.web.service.SolutionService;

@Controller
@RequestMapping("/solution")
public class SolutionController {

	@Autowired
	public SolutionService solutionService;

	@PostMapping("/submit")
	public String submit(Solution solution, HttpServletRequest request,Map<String, Object> map) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", "提交代码前请先登录");
			return "/problem/submit";
		}
		solution.setUserId(user.getUserId());
		solution.setProblemId((Integer) session.getAttribute("pid"));
		Judge.judge(solution);
		//insert fail
		if(!solutionService.insertSolution(solution)) {
			map.put("msg", "insert solution fail");
			return "error/my_error";
		}
		return "redirect:/problem/result";
	}
	@ResponseBody
	@RequestMapping("result/{pageNumber}")
	public Msg result(@PathVariable("pageNumber") Integer pageNumber) {
		PageHelper.startPage(pageNumber, 10);
		List<Solution> emps = solutionService.getAllByDesc();
		PageInfo<Solution> page = new PageInfo<Solution>(emps, 5);
		return Msg.success().add("pageInfo", page);
	}
}
