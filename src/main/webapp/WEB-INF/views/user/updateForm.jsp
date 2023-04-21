<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container my-3">
    <div class="container">
        <form action="/s/user/${user.id}/update" method="post">
            <div class="form-group mb-2">
                <input type="text" name="username" class="form-control" placeholder="Enter username" value="${user.username}" readonly>
            </div>

            <div class="form-group mb-2">
                <input type="password" name="password" class="form-control" placeholder="Enter password" value="${user.password}">
            </div>

            <div class="form-group mb-2">
                <input type="email" name="email" class="form-control" placeholder="Enter email" value="${user.email}">
            </div>

            <button class="btn btn-primary">회원수정</button>
        </form>

    </div>
</div>

<%@ include file="../layout/footer.jsp" %>