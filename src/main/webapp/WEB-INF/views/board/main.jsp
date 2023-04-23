<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container my-3">

    <div class="d-flex justify-content-end">
        <form class="d-flex col-lg-3" action="/" method="get">
            <input class="form-control" type="text" placeholder="Search" name="keyword">
            <button class="btn btn-primary">Search</button>
        </form>
    </div>
    <div class="my-board-box row">

        <c:forEach items="${boardPG.content}" var="board">
            <%-- 글 아이템 시작 --%>
            <div class="card col-lg-3 pt-2">
                <img class="card-img-top" style="height: 250px;" src="${board.thumbnail}">
                <hr/>
                <div class="card-body">
                    <div>작성자 : ${board.user.username}</div>
                    <h4 class="card-title my-text-ellipsis">${board.title}</h4>
                    <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
                </div>
            </div>
            <%-- 글 아이템 끝 --%>
        </c:forEach>
    </div>

    <ul class="pagination mt-3 d-flex justify-content-center">

        <li class="page-item ${boardPG.first ? "disabled" : ""}"><a class="page-link" href="/?page=${boardPG.number -1}">Previous</a></li>
        <li class="page-item ${boardPG.last ? "disabled" : ""}"><a class="page-link" href="/?page=${boardPG.number +1}">Next</a></li>
    </ul>
</div>

<%@ include file="../layout/footer.jsp" %>