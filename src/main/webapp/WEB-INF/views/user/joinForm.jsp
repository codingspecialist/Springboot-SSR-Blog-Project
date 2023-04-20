<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container my-3">
    <div class="container">
        <form action="/join" method="post" onsubmit="return valid()">
            <div class="form-group mb-2">
                <div class="d-flex">
                    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username"
                           maxlength="20" onchange="changeUsernameState()" required>
                    <button type="button" class="badge bg-secondary ms-2" onclick="checkSameUsername()">중복확인</button>
                </div>
                <div id="usernameErrorMsg" class="error-msg"></div>
            </div>

            <div class="form-group mb-2">
                <input type="password" name="password" class="form-control" placeholder="Enter password" id="password"
                       maxlength="20" required>
                <div id="passwordErrorMsg" class="error-msg"></div>
            </div>

            <div class="form-group mb-2">
                <input type="password" class="form-control" placeholder="Enter samePassword" id="samePassword"
                       maxlength="20" required>
                <div id="samePasswordErrorMsg" class="error-msg"></div>
            </div>

            <div class="form-group mb-2">
                <input type="email" name="email" class="form-control" placeholder="Enter email" id="email"
                       maxlength="50" required>
            </div>

            <button class="btn btn-primary">회원가입</button>
        </form>

    </div>
</div>

<script>
    let isUsernameSameCheck = false;

    function changeUsernameState() {
        isUsernameSameCheck = false;
    }

    function checkSameUsername(){
        isUsernameSameCheck = true;
    }

    function valid() {
        let password = document.querySelector("#password").value;
        let samePassword = document.querySelector("#samePassword").value;
        if (isUsernameSameCheck === false) {
            document.querySelector("#usernameErrorMsg").innerHTML = "유저네임 중복체크가 되지 않았습니다";
            return false;
        }
        if (password !== samePassword) {
            document.querySelector("#passwordErrorMsg").innerHTML = "패스워드가 동일하지 않습니다";
            document.querySelector("#samePasswordErrorMsg").innerHTML = "패스워드가 동일하지 않습니다";
            return false;
        }
        return true;
    }
</script>

<%@ include file="../layout/footer.jsp" %>