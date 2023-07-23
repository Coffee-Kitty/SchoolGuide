<%--
  Created by IntelliJ IDEA.
  User: Dilicious
  Date: 2022/12/25
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Map</title>
</head>
<body>
<canvas id="myCanvas" width="300" height="150"></canvas>
<script>
    //显示边
    // 获取 canvas 元素和绘图上下文
    var canvas = document.getElementById('myCanvas');
    var ctx = canvas.getContext('2d');

    // // 获取两个元素节点的坐标
    // var element1 = document.getElementById('circle1');
    // var element2 = document.getElementById('circle2');
    //
    // // 计算直线的起点和终点坐标
    // let x1=element1.style.left;
    // let y1=element1.style.top;
    // let x2=element2.style.left;
    // let y2=element1.style.top;

    // 在 canvas 中绘制直线
    ctx.moveTo(50, 50);
    ctx.lineTo(200, 200);
    ctx.lineWidth = 2;
    ctx.strokeStyle = 'red';
    ctx.stroke();
</script>
</body>
</html>
