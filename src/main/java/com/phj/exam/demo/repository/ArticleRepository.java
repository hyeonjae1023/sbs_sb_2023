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

	@Select("""
			SELECT A.*,
			M.nickName AS writerName
			FROM article AS A
			LEFT JOIN `member` AS M
			ON A.memberId = M.id		
			WHERE A.id = #{id}
			""")
	public Article getForprintArticle(@Param("id") int id);

	@Select("""
			<script>
			SELECT A.*,
			M.nickName AS writerName
			FROM article AS A
			LEFT JOIN `member` AS M
			ON A.memberId = M.id
			<if test="boardId != 0">
			WHERE A.boardId = #{boardId}
			</if>
			ORDER BY A.id DESC
			</script>
			""")
	public List<Article> getforPrintArticles(@Param("boardId") int boardId);

//	@Delete("DELETE FROM article WHERE id = #{id}")
	public void removeArticle(@Param("id") int id);

//	@Update("UPDATE article SET updateDate = NOW(), title = #{title}, `body` = #{body} WHERE id = #{id}")
	public void modifyArticle(@Param("id") int id,@Param("title") String title,@Param("body") String body);

//	@Select("SELECT LAST_INSERT_ID();")
	public int getLastArticleId();

	@Select("""
			<script>
				SELECT COUNT(*) AS cnt
				FROM article AS A
				<if test="board != 0">
				WHERE A.boardId = #{boardId}
				</if>
			</script>
			""") //parameter board? 오류 발생
	public int getArticleCounts(@Param("boardId") int boardId);

}
