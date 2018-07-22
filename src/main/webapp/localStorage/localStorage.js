/* 封装登陆方法，用户登陆页面*/
/* set localStorage*/
function set(key,value){
    var curTime = new Date().getTime();
    localStorage.setItem(key,JSON.stringify({data:value,time:curTime}));
}
function get(key){
    var exp = 24000*60*60;// 设置过期时间为 1 天 单位 ms
    var data = localStorage.getItem(key);
    var dataObj = JSON.parse(data);
    if(dataObj!=null){
        if (new Date().getTime() - dataObj.time>exp) {
            console.log('信息已过期');
            localStorage.removeItem(key);
        }else{
            var dataObjDatatoJson = dataObj.data;
            return dataObjDatatoJson;
        }
    }
}
function remove() {
    localStorage.removeItem("token");
    localStorage.removeItem("name");
    localStorage.removeItem("time");
}
/* 设置Cookie*/
function SetCookie(name, value) {
    var expdate = new Date();
    var argv = SetCookie.arguments;
    var argc = SetCookie.arguments.length;
    var expires = (argc > 2) ? argv[2] : null;
    var path = (argc > 3) ? argv[3] : null;
    var domain = (argc > 4) ? argv[4] : null;
    var secure = (argc > 5) ? argv[5] : false;
    if (expires != null) expdate.setTime(expdate.getTime() + (expires * 1000));
    document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expdate.toGMTString()))
        + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain))
        + ((secure == true) ? "; secure" : "");
}
/* 删除Cookie*/
function DelCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = GetCookie(name);
    document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
}
/* 获取Cookie*/
function GetCookie(cookieName) {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for(var i = 0; i < arrCookie.length; i++){
        var arr = arrCookie[i].split("=");
        if(cookieName == arr[0]){
            return arr[1];
        }
    }
    return "";
}
/* 动态添加js*/
function loadjscssfile(filename, filetype) {
    if (filetype == "js") {
        var fileref = document.createElement('script')
        fileref.setAttribute("type", "text/javascript")
        fileref.setAttribute("src", filename)
    } else if (filetype == "css") {
        var fileref = document.createElement("link")
        fileref.setAttribute("rel", "stylesheet")
        fileref.setAttribute("type", "text/css")
        fileref.setAttribute("href", filename)
    } if (typeof fileref != "undefined")
        document.getElementsByTagName("head")[0].appendChild(fileref)

}