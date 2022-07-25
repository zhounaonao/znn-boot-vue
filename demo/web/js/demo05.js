
// 当鼠标悬浮时，显示背景颜色
function showBGColor(){
    // event:当前发生的事件
    // event.srcElement :事件源
    // event.srcElement.tagName :事件源的标签
    // alert(event.srcElement);
    // alert(event.srcElement.tagName);
    if (event && event.srcElement && event.srcElement.tagName==="TD"){
        var td = event.srcElement;
        var tr = td.parentElement; // 获取td父元素
        // js 设置某节点的样式，则需要加上.style
        tr.style.backgroundColor = "navy";
        // tr.cells获取tr中的所有单元格
        var tds = tr.cells;
        for (var i = 0; i < tds.length; i++) {
            tds[i].style.color = "white";
        }
    }

}

// 当鼠标离开的时候
function clearBGColor(){
    if (event && event.srcElement && event.srcElement.tagName==="TD"){
        var td = event.srcElement;
        var tr = td.parentElement; // 获取td父元素
        tr.style.backgroundColor = "transparent";
        var tds = tr.cells;
        for (var i = 0; i < tds.length; i++) {
            tds[i].style.color = "threeddarkshadow";
        }
    }
}

// 当鼠标悬浮在单价单元格上，显示手势
function showHand(){
    if (event && event.srcElement && event.srcElement.tagName==="TD"){
        var td = event.srcElement;
        td.style.cursor = "hand";

    }
}