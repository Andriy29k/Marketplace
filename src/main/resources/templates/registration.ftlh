<!DOCTYPE html>
<html>
<head>
    <title>eSales Point</title>
</head>
<body>
<h1>eSales Point</h1><hr>
<h4>Реєстрація</h4>
<form action="/registration" method="post">
    Ім'я користувача: <input type="text" name="name"><br><br>
    eMail: <input type="email" name="email"><br><br>
    Номер телефону: <input type="text" name="phoneNumber"><br><br>
    Пароль: <input type="password" name="password"><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Зареєструватись"/>
</form>
<#if errorMessage??>
<h2 style="color: red">${errorMessage}</h2>
</#if>
</body>
</html>