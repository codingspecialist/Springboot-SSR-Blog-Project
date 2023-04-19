<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container my-3">
    <div class="container">
        <form onsubmit="return valid()">
            <div class="d-flex form-group mb-2">
                <input type="text" name="username" class="form-control" placeholder="Enter username"
                       id="username">
                <button type="button" class="badge bg-secondary ms-2">중복확인</button>
            </div>

            <div class="form-group mb-2">
                <input type="password" name="password" class="form-control" placeholder="Enter password"
                       id="password">
            </div>

            <div class="form-group mb-2">
                <input type="password" class="form-control" placeholder="Enter passwordCheck"
                       id="passwordCheck">
            </div>

            <div class="form-group mb-2">
                <input type="email" name="email" class="form-control" placeholder="Enter email">
            </div>

            <button class="btn btn-primary">회원가입</button>
        </form>

    </div>
</div>

<script>
    function valid() {
        alert("회원가입 유효성 검사");
    }
</script>

<%@ include file="../layout/footer.jsp" %>