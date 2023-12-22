<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Marketplace</title>
</head>
<body>
<h1>eSales point</h1>
<div>
    <button >

    </button>
</div>
<h4>Детальна інформація про товар</h4>
<#list images as img>
<img src="/images/${img.id}" height="60px"/><br><br>
</#list>
<b>Назва товару: </b>${product.title}<br>
<b>Опис товару: </b>${product.description}<br>
<b>Ціна: </b>${product.price}<br>
<b>Місто: </b>${product.city}<br>
<b>Автор: </b>${product.user.name}<br>
<b>Номер телефону: </b>${product.user.phoneNumber}<br>
</body>
</html>