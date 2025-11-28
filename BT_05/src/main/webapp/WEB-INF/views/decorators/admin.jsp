<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property="title"/></title>
</head>
<body>
<header>
        <nav class="navbar navbar-dark bg-dark">
            <span class="navbar-text">
                Sinh viên: Nguyễn Văn A - MSSV: 12345678
            </span>
        </nav>
    </header>

    <div class="container">
        <sitemesh:write property="body"/>
    </div>

    <footer class="bg-light text-center text-lg-start mt-4">
        <div class="text-center p-3">
            Link Github: <a href="https://github.com/your-repo">Tại đây</a>
        </div>
    </footer>
</body>
</html>