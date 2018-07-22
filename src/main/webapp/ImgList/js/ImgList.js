/* 取出图片集总数 */
$.getScript("/localStorage/localStorage.js", function () {
    /* 加载loading */
    $("body").append('<div class="loading"><div class="mask"></div>' +
        '<div id="animationTipBox" style="width: 180px; height: 150px; margin-left: -90px; margin-top: -75px;"><div class="load"><div class="icon_box"><div class="cirBox1"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox2"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox3"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div></div></div><div class="dec_txt">加载中...</div></div></div>');
    $.post("/Img/TotalNum", {
        name:get("name"),
        time:get("time"),
        token:get("token")
    },function (PageResult) {
        $(".loading").remove();
        $.each(PageResult, function (i, result) {
            if(result.errMsg=="OK"){
                layui.use(['laypage', 'layer'], function () {
                    var laypage = layui.laypage
                        , layer = layui.layer;
                    laypage.render({
                        elem: 'pagelistbox'
                        , count: result.totalNum//数据总数
                        , groups: 4 // 连续显示分页数
                        , curr: 1 // 设置当前页
                        , limit : 30
                        //,skin: 'pag-number'   //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                        , prev: '<P class="icon icon-scroll-left"></P>' //若不显示，设置false即可
                        , next: '<P class="icon icon-scroll-right"></P>'//若不显示，设置false即可
                        , jump: function (obj) {
                            $("body").append('<div class="loading"><div class="mask"></div>' +
                                '<div id="animationTipBox" style="width: 180px; height: 150px; margin-left: -90px; margin-top: -75px;"><div class="load"><div class="icon_box"><div class="cirBox1"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox2"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox3"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div></div></div><div class="dec_txt">加载中...</div></div></div>');
                            $(".loading").remove();
                            $.post("/Img/ImgList", {
                                name:get("name"),
                                time:get("time"),
                                token:get("token"),
                                Page:obj.curr
                            },function (Result) {
                                $.each(Result, function (i, results) {
                                    if(results.errMsg=="OK"){
                                        for(var i = 0 ; i < results.ListSize ; i++){
                                            $(".list").prepend().append('<table width="98%" border="0" cellpadding="1" class="ilist" cellspacing="1" bgcolor="#FAFAF1" align="center" style="margin-top:8px;"><tbody><tr><td width="13%" align="center">' +
                                                '<img src="'+results.cover[i].img_address+'" width="80" height="60" alt="文档图片"' +
                                                'border="0"></td><td width="35%"><input name="arcID" type="checkbox" value="6374" class="np"  data-listID="'+results.ImgList[i].list_id+'">' +
                                                '<a href="javascript:void(0)" onclick="Pictureset(this)" data-listID="'+results.ImgList[i].list_id+'"><b>'+results.ImgList[i].list_title+'</b></a></td>' +
                                                '<td width="12%" height="26" align="center">'+results.ImgList[i].list_label+'</td>' +
                                                '<td width="10%" align="center">'+results.ImgList[i].list_user+'</td></tr></tbody></table>')
                                        }
                                    }else{
                                        popup({type:'error',msg:result.errMsg,delay:3000,bg:true});
                                    }
                                });
                            });
                        }
                    });
                });
            }else {
                popup({type:'error',msg:result.errMsg,delay:3000,bg:true});
            }
        });
    });
});
/* 搜索按钮 */
$("input[name=imageField]").click(function () {
    Search();
})
$("input[name=keyword]").keypress(function (e) {
    var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
    if (eCode == 13) {
        Search();
    }
});

/**
 * 搜索
 * @constructor
 */
function Search() {
    if($("input[name=keyword]").val().length<1){
        popup({type:'error',msg:"关键字长度小于1",delay:2000,bg:true});
    }else{
        if ($("input[name=keyword]").val().match(/^\s+$/) || $("input[name=keyword]").val().match(/^[ ]+$/)
            || $("input[name=keyword]").val().match(/^[ ]/)
            || $("input[name=keyword]").val().match(/^[ ]*$/)
            || $("input[name=keyword]").val().match(/^\s*$/)) {
            popup({type:'error',msg:"存在非法字符",delay:2000,bg:true});
        } else {
            /* 确认搜索后的数量 */
            $.post("/Img/Number_of_results",{
                Keyword:$("input[name=keyword]").val()
            },function (Number_of_results) {
                $(".loading").remove();
                $.each(Number_of_results, function (i, results) {
                    if(results.errMsg=="OK"){
                        layui.use(['laypage', 'layer'], function () {
                            var laypage = layui.laypage
                                , layer = layui.layer;
                            laypage.render({
                                elem: 'pagelistbox'
                                , count: results.Size//数据总数
                                , groups: 4 // 连续显示分页数
                                , curr: 1 // 设置当前页
                                , limit : 30
                                //,skin: 'pag-number'   //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                                , prev: '<P class="icon icon-scroll-left"></P>' //若不显示，设置false即可
                                , next: '<P class="icon icon-scroll-right"></P>'//若不显示，设置false即可
                                , jump: function (obj) {
                                    $("body").append('<div class="loading"><div class="mask"></div>' +
                                        '<div id="animationTipBox" style="width: 180px; height: 150px; margin-left: -90px; margin-top: -75px;"><div class="load"><div class="icon_box"><div class="cirBox1"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox2"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox3"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div></div></div><div class="dec_txt">加载中...</div></div></div>');
                                    $.post("/Img/SearchResult", {
                                        Keyword:$("input[name=keyword]").val(),
                                        Page:obj.curr
                                    },function (Result) {
                                        $(".loading").remove();
                                        $(".list").html("");
                                        $.each(Result, function (i, results) {
                                            if(results.errMsg=="OK"){
                                                for(var i = 0 ; i < results.Size ; i++){
                                                    $(".list").prepend().append('<table width="98%" border="0" cellpadding="1" class="ilist" cellspacing="1" bgcolor="#FAFAF1" align="center" style="margin-top:8px;"><tbody><tr><td width="13%" align="center">' +
                                                        '<img src="'+results.cover[i].img_address+'" width="80" height="60" alt="文档图片"' +
                                                        'border="0"></td><td width="35%"><input name="arcID" type="checkbox" value="6374" class="np"  data-listID="'+results.Search[i].list_id+'">' +
                                                        '<a href="javascript:void(0)" onclick="Pictureset(this)" data-listID="'+results.Search[i].list_id+'"><b>'+results.Search[i].list_title+'</b></a></td>' +
                                                        '<td width="12%" height="26" align="center">'+results.Search[i].list_label+'</td>' +
                                                        '<td width="10%" align="center">'+results.Search[i].list_user+'</td></tr></tbody></table>')
                                                }
                                            }else{
                                                popup({type:'error',msg:results.errMsg,delay:2000,bg:true});
                                            }
                                        });
                                    });
                                }
                            });
                        });
                    }else{
                        popup({type:'error',msg:results.errMsg,delay:2000,bg:true});
                    }
                });
            })
        }
    }
}
/* 删除选中图片集 */
function delImgList() {
    if($("input[type=checkbox]:checkbox:checked").length>0){
        $.confirm({
            title: '确认删除',
            content: '你确定要删除它们么',
            autoClose: 'cancel|10000',
            confirmButtonClass: 'btn-danger',
            confirmButton: '确认删除',
            cancelButton: '关闭',
            confirm: function () {
                /* 加载loading */
                $("body").append('<div class="loading"><div class="mask"></div>' +
                    '<div id="animationTipBox" style="width: 180px; height: 150px; margin-left: -90px; margin-top: -75px;"><div class="load"><div class="icon_box"><div class="cirBox1"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox2"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox3"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div></div></div><div class="dec_txt">生成数据中...</div></div></div>');
                /* 生成JSON */
                var JSON = '[{"ImgListID":{"ImgListID":[';
                $("input[type=checkbox]:checkbox:checked").each(function(i,results){
                    if($("input[type=checkbox]:checkbox:checked").length-1==i){
                        JSON = JSON + $(this).data("listid");
                    }else{
                        JSON = JSON + $(this).data("listid") + ",";
                    }
                })
                $(".dec_txt").html("执行操作中...");
                $.getScript("/localStorage/localStorage.js", function () {
                    // 执行删除
                    $.post("/Img/delImgList",{
                        JSON:JSON+ ']}}]',
                        name:get("name"),
                        time:get("time"),
                        token:get("token")
                    },function (results) {
                        $(".loading").remove();
                        $.each(results, function (i, result) {
                            if(result.errMsg=="OK"){
                                popup({type:'success',msg:"删除成功",delay:2000,callBack:function(){
                                        location.href="/Jump/List"
                                    }});
                            }else{
                                popup({type:'error',msg:result.errMsg,delay:3000,bg:true});
                            }
                        });
                    });
                });
            }
        });
    }else{
        popup({type:'error',msg:"你没有勾选图集",delay:2000,bg:true});
    }
}
/* 展开图片集 */
function Pictureset(obj) {
    /* 加载loading */
    $("body").append('<div class="loading" style="display: none"><div class="mask"></div>' +
        '<div id="animationTipBox" style="width: 180px; height: 150px; margin-left: -90px; margin-top: -75px;"><div class="load"><div class="icon_box"><div class="cirBox1"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox2"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox3"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div></div></div><div class="dec_txt">加载中...</div></div></div>');
    // 验证Token
    $.getScript("/user-login/token/token.js", function () {
        VerificationToken();
    });
    $.post("/Pictureset/PictureAddress",{
        ImgListID:$(obj).data("listid")
    },function (ImgInfo) {
        $(".zyc").nextAll().remove();
        $(".ImgInfo").load("/ImgList/ImgInfo.html",function () {
            $("#thumbnailsEdit").html("");
            $("#thumbnailsdit").html("");
            $("#thumbnailsEdits textarea").html("");
            $.each(ImgInfo, function (i, result) {
                $("#thumbnailsEdit").append("<p>"+result.img_address+"</p>");
                $("#thumbnailsdit").append('<div class="albCt albEdit"><img src="'+result.img_address+'" width="244" height="180"></div>')
                $("#thumbnailsEdits textarea").append("http://localhost:8080"+result.img_address+"\n")
            });
            $("#thumbnailsEdit").append("<br/>")
        });
    });
    // 获取选中图片集的地址
    console.log($(obj).data("listid"))
}