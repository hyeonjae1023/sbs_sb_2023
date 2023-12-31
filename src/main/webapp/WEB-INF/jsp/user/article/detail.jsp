<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="게시물 detail" />
<%@ include file="../common/head.jspf"%>

<section class="mt-5 ">
  <div class="container mx-auto px-3  ">
    <div class="table-box-type-1">
      <table>
      <colgroup>
      <col width="200"/>
      </colgroup>
        <tbody>
            <tr>
              <th>번호</th>
              <th>${article.id}</th>
            </tr>
             <tr>
              <th>작성날짜</th>
              <th>${article.regDateForPrint}</th>
            </tr>
             <tr>
              <th>수정날짜</th>
              <th>${article.updateDateForPrint}</th>
            </tr>
                <tr>
              <th>제목</th>
              <th>${article.title}</th>
            </tr>
               <tr>
              <th>내용</th>
              <th>${article.body}</th>
            </tr>
             <tr>
              <th>작성자</th>
              <th>${article.writerName}</th>
            </tr>
        </tbody>
      </table>
    </div>
    <div class="btns">
    <button class="btn btn-link" type="button" onclick="history.back();">뒤로가기</button>
    <c:if test="${article.exta__actorCanModify}">
    <a class="btn btn-link" href="../article/modify?id=${article.id}">게시물 수정</a>
    </c:if>
    <c:if test="${article.exta__actorCanDelete}">
    <a class="btn btn-link" onclick="if (confirm('정말 삭제하시겠습니까?')==false)return false;" href="../article/delete?id=${article.id}">게시물 삭제</a>
    </c:if>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf"%>