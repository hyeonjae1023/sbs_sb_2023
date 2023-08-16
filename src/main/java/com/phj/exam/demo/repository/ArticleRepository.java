package com.phj.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.phj.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {
//	@Insert("INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = #{title}, `body` = #{body}")
	public void writeArticle(@Param("memberId")int memberId, @Param("title") String title,@Param("body") String body);

//	@Select("SELECT * FROM article WHERE id = #{id}")
	public Article getArticle(@Param("id") int id);

//	@Select("SELECT * FROM article")
	public List<Article> getArticles();

//	@Delete("DELETE FROM article WHERE id = #{id}")
	public void removeArticle(@Param("id") int id);

//	@Update("UPDATE article SET updateDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}")
	public void modifyArticle(@Param("id") int id,@Param("title") String title,@Param("body") String body);

//	@Select("SELECT LAST_INSERT_ID();")
	public int getLastArticleId();

}
