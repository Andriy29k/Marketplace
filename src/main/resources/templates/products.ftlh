<!DOCTYPE html>
<html>
<head>
    <title>eSales Point</title>
</head>
<body>
    <h1>eSalesPoint</h1><hr>
    <#if user.email??>
    <h3>І'мя користувача: <i>${user.name}</i></h3>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Вийти"/>
    </form>
    <#if user.isAdmin()>
    <a href="/admin">Панель адміністратора</a>
    </#if>
    <#else>
    <a href="/login">Увійти</a>
    </#if>
    <hr>
    <h4>Товари з всієї України</h4>
    <form action="/" method="get">
        Пошук по назві оголошення: <input type="text" name="title">
        <input type="submit" value="Поиск"/>
    </form>
    <#list products as product>
    <div>
        <p><b>${product.title}</b> ${product.price} грн. | <a href="/product/${product.id}">Детальніше...</a></p>
    </div>
    <#else>
    <h3>Товарів немає</h3>
    </#list>
    <#if user.email??>
    <hr>
    <h3>Створити новий товар</h3>
    <form action="/product/create" method="post" enctype="multipart/form-data">
    Назва оголошення: <input type="text" name="title"/><br><br>
    Опис оголошення: <input type="text" name="description"/><br><br>
    Ціна: <input type="number" name="price"/><br><br>
    Місто: <input type="text" name="city"/><br><br>
    Перше фото: <input type="file" name="file1"/><br><br>
    Друге фото: <input type="file" name="file2"/><br><br>
    Третє фото: <input type="file" name="file3"/><br><br>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="submit" value="Додати товар"/>
    </form>
    </#if>
</body>
</html>