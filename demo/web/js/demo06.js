// window.onload = function(){
//     alert("22");
// } ;
// window.addEventListener('load',function(){
//        // alert("Aa");
// }) ;

// 窗口加载的时候进行事件绑定
window.onload = function () {
    updateZJ();
    // 当页面加载完成，我们需要绑定各种事件
    var fruitTbl = document.getElementById("tbl_fruit");
    // 获取表格中改动所有行
    var rows = fruitTbl.rows;
    for (var i = 1; i < rows.length - 1; i++) {
        var tr = rows[i];
        // 1.绑定鼠标悬浮以及离开设置背景颜色事件,此处没有小括号就是绑定事件，带小括号就是执行后的结果赋值
        tr.onmouseover = showBGColor; // 回调函数
        tr.onmouseout = clearBGColor;
        // tr.cells获取tr中的所有单元格
        var tds = tr.cells;
        var priceTD = tds[1]; // 绑定单价单元格的事件
        priceTD.onmouseover = showHand;
        priceTD.onclick = editPrice;
    }
}

function editPrice() {
    if (event && event.srcElement && event.srcElement.tagName === "TD") {
        var priceTD = event.srcElement;
        // 目的是判断当前priceTD有子节点，而且第一个子节点是文本节点， TextNdoe = 3,ElementNode = 1
        if (priceTD.firstChild && priceTD.firstChild.nodeType === 3) {
            // innerText 获取当前节点内部文本
            var oldPrice = priceTD.innerText;
            // innerHTML 设置当前节点的内部HTML
            priceTD.innerHTML = "<input type='text' size='4'/>";
            // <td><input type='text' size='4'/></td>
            var input = priceTD.firstChild;
            if (input.tagName === "INPUT") {
                input.value = oldPrice;
                // 选中输入框内部的文本
                input.select();
                // 绑定输入框失去焦点事件，失去焦点，更新单价
                input.onblur = updatePrice;
            }
        }
    }
}

function updatePrice(){
    if (event && event.srcElement && event.srcElement.tagName === "INPUT") {
        var input = event.srcElement;
        var newPrice = input.value;
        // input 的父节点是td
        var priceTD = input.parentElement;

        priceTD.innerText = newPrice;
        // 更新当前行的小计这个一个格子的值
        updateXJ(priceTD.parentElement);
    }
}

// 更新指定行的小计
function updateXJ(tr){

    if (tr && tr.tagName==="TR"){
        var tds = tr.cells;
        var price = tds[1].innerText;
        var count = tds[2].innerText;
        // innerText取出来的是字符串，需要类型转换才能数学运算
        var xj = parseInt(price) * parseInt(count)
        tds[3].innerText = xj;

        updateZJ();
    }
}

// 更新总计
function updateZJ(){
    var fruitTbl = document.getElementById("tbl_fruit");
    var rows = fruitTbl.rows;
    var sum = 0;
    for (var i = 1; i < rows.length - 1; i++) {
        var tr = rows[i];
        var xj = parseInt(tr.cells[3].innerText);
        sum = sum + xj;
    }
    rows[rows.length - 1].cells[1].innerText = sum;
}

// 当鼠标悬浮时，显示背景颜色
function showBGColor() {
    // event:当前发生的事件
    // event.srcElement :事件源
    // event.srcElement.tagName :事件源的标签
    // alert(event.srcElement);
    // alert(event.srcElement.tagName);
    if (event && event.srcElement && event.srcElement.tagName === "TD") {
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
function clearBGColor() {
    if (event && event.srcElement && event.srcElement.tagName === "TD") {
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
function showHand() {
    if (event && event.srcElement && event.srcElement.tagName === "TD") {
        var td = event.srcElement;
        td.style.cursor = "hand";

    }
}