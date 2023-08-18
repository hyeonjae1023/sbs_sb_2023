package com.phj.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phj.exam.demo.service.ArticleService;
import com.phj.exam.demo.util.Ut;
import com.phj.exam.demo.vo.Article;
import com.phj.exam.demo.vo.ResultData;
import com.phj.exam.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller 
public class UserArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpServletRequest req, String title, String body) {
		Rq rq = new Rq(req);
		
		if(rq.isLogined() == false) {
			return ResultData.from("F-A","로그인 후 이용해 주세요.");
		}

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 작성해주세요.");
		}
		
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 작성해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(),title, body);
		
		int id = writeArticleRd.getData1();
		
		Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);
		
		return ResultData.newData(writeArticleRd, article);
	}
	
	@RequestMapping("/user/article/delete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = new Rq(req);
		
		if(rq.isLogined() == false) {
			return Ut.jsHistoryback("로그인 후 이용해 주세요.");
		}
		
		Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);
		
		if(article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryback("권한이 없습니다.");
		}
		
		if(article == null) {
			return Ut.jsHistoryback("게시물이 존재하지 않습니다.");
		}
		articleService.deleteArticle(id);
		
		return Ut.jsReplace(Ut.f("%d번 게시글이 삭제 되었습니다.",id),"../article/list");
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(HttpServletRequest req, int id) {
		Rq rq = new Rq(req);

		Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.",id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), article);
	}
	
	@RequestMapping("/user/article/modify")
	@ResponseBody
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = new Rq(req);

		if(rq.isLogined() == false) {
			return ResultData.from("F-A","로그인 후 이용해 주세요.");
		}
		
		Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);
		
		if(article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id,title,body);
	}
	
	@RequestMapping("/user/article/list")
	public String showList(HttpServletRequest req, Model model) {
		Rq rq = new Rq(req);

		List<Article> articles = articleService.getforPrintArticles(rq.getLoginedMemberId());

		model.addAttribute("articles",articles);
		
		return "user/article/list";
	}
	
	@RequestMapping("/user/article/detail")
	public String showDetail(HttpServletRequest req, Model model,int id) {
		Rq rq = new Rq(req);
		
	Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);
		model.addAttribute("article",article);
		if(article == null) {
		return id+ "번 게시글이 존재하지 않습니다.";
		}
		return  "user/article/detail";
	}
	//액션 메서드 끝
}
