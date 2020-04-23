//请求相关函数封装
//获取url参数
function getUrlParam(id) {
    var regExp = new RegExp('([?]|&)' + id+ '=([^&]*)(&|$)');
    var result = window.location.href.match(regExp);
    if (result) {
        return decodeURIComponent(result[2]);
    } else {
        return null;
    }
}

function getPath() {
    //获取当前网址，如：
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录如：/Tmall/index.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如：//localhost:8080
    var localhostPaht = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/Tmall
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return localhostPaht;
}

function redirectWindow(url){
    window.location.href=getPath()+url;
}

function postAjax(url,param,success,error){
    $.ajax({
        type: "POST",
        url: getPath()+url,
        data: JSON.stringify(param),
        async: true,
        dataType:'json',
        contentType:"application/json",
        success: success,
        error: error
    });
}

function getAjax(url,param,success,error){
    $.ajax({
        url : getPath() + url+"?"+param,
        type : 'GET',
        dataType : 'json',
        // data:JSON.stringify(param),
        success : success,
        error : error
    });
}
function postFormAjax(url,formId,success,error){
    $.ajax({
        type: "POST",
        url: getPath()+url,
        data: $("#"+formId).serializeArray(),
        async: true,
        dataType:'text',
        contentType:"application/x-www-form-urlencoded",
        success: success,
        error: error
    });
}



