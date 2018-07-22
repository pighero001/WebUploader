    VerificationToken();
    /* 验证用户Token */
    function VerificationToken() {
        if(get("token")!=null&&get("name")!=null){
            /* 加载loading */
            $("body").append('<div class="loading"><div class="mask"></div>' +
                '<div id="animationTipBox" style="width: 180px; height: 150px; margin-left: -90px; margin-top: -75px;"><div class="load"><div class="icon_box"><div class="cirBox1"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox2"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox3"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div></div></div><div class="dec_txt">加载中...</div></div></div>');
            $.post("/token/Verification", {
                token:get("token"),
                name: get("name"),
                time: get("time")
            }, function (VerificationResult) {
                $(".loading").remove();
                $.each(VerificationResult, function (i, result) {
                    if(result.errMsg=="OK"){
                        console.log("进入上传页");
                        location.href="/Jump/Uploader";
                    }else{
                        remove();
                        if(result.cause!=null){
                            console.log(result.errMsg);
                            console.log(result.cause);
                        }else{
                            console.log(result.errMsg);
                        }
                    }
                });
            });
        }else{
            remove();
            /* 无效的token */
            console.log("Invalid token");
        }
    }

    /* 用户登陆方法  可自定义返回状态*/
    function user_login(name,password) {
        /* 加载loading */
        $("body").append('<div class="loading"><div class="mask"></div>' +
            '<div id="animationTipBox" style="width: 180px; height: 150px; margin-left: -90px; margin-top: -75px;"><div class="load"><div class="icon_box"><div class="cirBox1"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox2"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox3"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div></div></div><div class="dec_txt">加载中</div></div></div>');
        if(name!=""&&password!=""){
            $.post("/login/user_login", {
                UserName: name,
                Password: password
            }, function (loginResult) {
                $.each(loginResult, function (i, result) {
                    if(result.errMsg=="OK"){
                        $(".loading").remove();
                        console.log("登入成功");
                        console.log("用户Token:"+result.token);
                        $(".Error_Prompt").html("");
                        set("token",result.token);
                        set("name",name);
                        set("time",result.time);
                        location.href="/Jump/Uploader";
                    }else{
                        $(".loading").remove();
                        $(".Error_Prompt").html(result.errMsg);
                    }
                });
            });
        }else{
            $(".loading").remove();
            $(".Error_Prompt").html("The username or password can not be empty");
        }
    }


    /* localStorage 存储 */
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