<!DOCTYPE html>
<html>
<head>
    <title>eSales Point</title>
</head>
<body>
<h1>eSales Point</h1>
<hr>
<h4>Панель адміністратора</h4>
<table border="1">
    <tr>
        <th>eMail</th>
        <th>Номер телефону</th>
        <th>Активність</th>
        <th>Роль</th>
        <th>Бан</th>
        <th>Редагування</th>
        <th>Детальна інформація</th>
    </tr>
    <#list users as user>
    <tr>
        <th>${user.email}</th>
        <th>${user.phoneNumber}</th>
        <th><#if user.active>true<#else>false</#if></th>
        <th><#list user.roles as role>${role}<#sep>, </#list></th>
        <th>
            <form action="/admin/user/ban/${user.id}" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}">
                <input type="submit" value="Бан">
            </form>
        </th>
        <th><a href="/admin/user/edit/${user.id}">Редагування</a></th>
        <th><a href="/user/${user.id}">Детальна інформація</a></th>
    </tr>
    <#else>
    <h3>Користувачів немає</h3>
</#list>
</table>
</body>
</html>