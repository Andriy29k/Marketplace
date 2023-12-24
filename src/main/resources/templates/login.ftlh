<!DOCTYPE html>
<html>
<head>
    <title>eSales Point</title>
</head>
<body>
<h1>eSales Point</h1><hr>
<h4>Авторизація</h4>
<form action="/login" method="post">
    eMail: <input type="email" name="username"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Вхід"/>
</form>
<a href="/registration">Зареєструватися</a>
</body>
</html>