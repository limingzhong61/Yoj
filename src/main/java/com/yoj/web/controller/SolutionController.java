package com.yoj.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yoj.nuts.auth.UserUtils;
import com.yoj.nuts.judge.Judge;
import com.yoj.nuts.judge.bean.static_fianl.Languages;
import com.yoj.nuts.judge.bean.static_fianl.Results;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.Solution;
import com.yoj.web.bean.User;
import com.yoj.web.bean.util.Msg;
import com.yoj.web.service.ProblemService;
import com.yoj.web.service.SolutionService;
import com.yoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solution")
public class SolutionController {
	@Autowired
	public SolutionService solutionService;
	@Autowired
	public ProblemService problemService;
	@Autowired
	private Judge Judge;
	@Autowired
	public UserService UserService;
	@Autowired
    public UserUtils userUtils;

	@PostMapping("/submit")
	public Msg submit(@RequestBody Solution solution) {
		User user = userUtils.getCurrentUser();
		if (user == null) {
			return Msg.fail("提交代码前请先登录");
		}
		solution.setUserId(user.getUserId());
        Problem problem = problemService.queryById(solution.getProblemId());
        Judge.judge(solution,problem);
		//insert fail
		if(solutionService.insertSolution(solution) == null) {
            return Msg.fail("insert solution fail");
		}
		if(problemService.updateSubmit(solution) == null){
            return Msg.fail("系统异常");
		};
		return Msg.success();
	}

	@RequestMapping("/set/{pageNumber}")
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
