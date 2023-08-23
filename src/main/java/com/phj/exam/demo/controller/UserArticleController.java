package com.phj.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phj.exam.demo.service.ArticleService;
import com.phj.exam.demo.service.BoardService;
import com.phj.exam.demo.util.Ut;
import com.phj.exam.demo.vo.Article;
import com.phj.exam.demo.vo.Board;
import com.phj.exam.demo.vo.ResultData;
import com.phj.exam.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller 
public class UserArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body, String replaceUri) {
		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.empty(title)) {
			return rq.jsHistoryBack("title(을)를 작성해주세요.");
		}
		
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("body(을)를 작성해주세요.");
		}
		
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(),title, body);
		
		int id = writeArticleRd.getData1();
		
		if(Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d",id);
		}
		
		return rq.jsReplace(Ut.f("%d번 게시글이 작성되었습니다.",id), replaceUri);
	}
	
	@RequestMapping("/user/article/delete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		if(rq.isLogined() == false) {
			return rq.jsHistoryBack("로그인 후 이용해 주세요.");
		}
		
		Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);
		
		if(article == null) {
			ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		if(article.getMemberId() != rq.getLoginedMemberId()) {
			return rq.jsHistoryBack("권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		
		return rq.jsReplace(Ut.f("%d번 게시물이 삭제되었습니다.", id), "../article/list");
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData<Article> getArticle(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.",id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물 입니다.", id), article);
	}
	
	@RequestMapping("/user/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq)req.getAttribute("rq");
		
		Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);
		
		if(article == null) {
			return rq.historyBackJsOnview(Ut.f("%d번 게시물이 존재하지 않습니다.",id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		model.addAttribute("article",article);
		
		if(actorCanModifyRd.isFail()) {
			return rq.historyBackJsOnview(actorCanModifyRd.getMsg());
		}
		
		return "user/article/modify";
	}
	
	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");

		if(rq.isLogined() == false) {
			return rq.jsHistoryBack("로그인 후 이용해 주세요.");
		}
		
		Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);
		
		if(article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.",id));
		}
		
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		
		if(actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}
		
		return rq.jsReplace(Ut.f("%d번 글이 수정되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}
	
	@RequestMapping("/user/article/write")
	public String showWrite() {
		return "user/article/write";
	}
	
	@RequestMapping("/user/article/list")
	public String showList(HttpServletRequest req, Model model, int boardId) {
		Board board = boardService.getBoardById(boardId);
		
		Rq rq = (Rq) req.getAttribute("rq");

		if(board == null) {
			return rq.historyBackJsOnview(Ut.f("%d번 게시판은 존재하지 않습니다.",boardId));
		}
		
		int articleCounts = articleService.getArticleCounts(boardId);
		List<Article> articles = articleService.getforPrintArticles(rq.getLoginedMemberId(), boardId);

		model.addAttribute("board",board);
		model.addAttribute("articleCounts",articleCounts);
		model.addAttribute("articles",articles);
		
		return "user/article/list";
	}
	
	@RequestMapping("/user/article/detail")
	public String showDetail(HttpServletRequest req, Model model,int id) {
		Rq rq = (Rq) req.getAttribute("rq");
		

	Article article = articleService.getForprintArticle(rq.getLoginedMemberId(), id);
		model.addAttribute("article",article);
		if(article == null) {
		return id+ "번 게시글이 존재하지 않습니다.";
		}
		return  "user/article/detail";
	}
	//액션 메서드 끝
}
