<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container my-3">
    <form class="mb-1">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Enter title" name="title" value="글제목">
        </div>

        <div class="form-group">
            <textarea class="form-control summernote" rows="5" name="content">글내용</textarea>
        </div>
        <button type="button" class="btn btn-primary">글수정완료</button>
    </form>
</div>

<script>
    $('.summernote').summernote({
        tabsize: 2,
        height: 500
    });
</script>

<%@ include file="../layout/footer.jsp" %>