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
		solution.setUserName(user.getUserName());
        Problem problem = problemService.queryById(solution.getProblemId());
        Judge.judge(solution,problem);
		//insert fail
		if(solutionService.insertSolution(solution) == null) {
            return Msg.fail("insert solution fail");
		}
		return Msg.success();
	}

	@PostMapping("/set/{pageNumber}")
	public Msg result(@PathVariable("pageNumber") Integer pageNumber,@RequestBody(required=false) Solution solution) {
		PageHelper.startPage(pageNumber, 10);
		//需要获得用户名
		List<Solution> solutions = solutionService.getListBySelective(solution);
		PageInfo<Solution> page = new PageInfo<Solution>(solutions, 5);
		for(Solution s : page.getList()){
			s.setResultStr(Results.toString(s.getResult()));
			s.setLanguageStr(Languages.toString(s.getLanguage()));
		}
		return Msg.success().add("pageInfo", page);
	}

	@GetMapping("/countAcceptedAndSubmission")
	public Msg countAcceptedAndSubmission(){
        Long accepted = solutionService.countAcceptedByUser();
        Long submission = solutionService.countSubmissionByUser();
        return Msg.success().add("accepted",accepted).add("submission",submission);
	}
}
