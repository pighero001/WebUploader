jQuery(function() {
    var $ = jQuery,    // just in case. Make sure it's not an other libaray.

        $wrap = $('#uploader'),

        // 图片容器
        $queue = $('<ul class="filelist"></ul>')
            .appendTo( $wrap.find('.queueList') ),

        // 状态栏，包括进度和控制按钮
        $statusBar = $wrap.find('.statusBar'),

        // 文件总体选择信息。
        $info = $statusBar.find('.info'),

        // 上传按钮
        $upload = $wrap.find('.uploadBtn'),

        // 没选择文件之前的内容。
        $placeHolder = $wrap.find('.placeholder'),

        // 总体进度条
        $progress = $statusBar.find('.progress').hide(),

        // 添加的文件数量
        fileCount = 0,

        // 添加的文件总大小
        fileSize = 0,

        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

        // 缩略图大小
        thumbnailWidth = 110 * ratio,
        thumbnailHeight = 110 * ratio,

        // 可能有pedding, ready, uploading, confirm, done.
        state = 'pedding',

        // 所有文件的进度信息，key为file id
        percentages = {},

        supportTransition = (function(){
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                    'WebkitTransition' in s ||
                    'MozTransition' in s ||
                    'msTransition' in s ||
                    'OTransition' in s;
            s = null;
            return r;
        })(),

        // WebUploader实例
        uploader;

    if ( !WebUploader.Uploader.support() ) {
        alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
        throw new Error( 'WebUploader does not support the browser you are using.' );
    }

    // 实例化
    uploader = WebUploader.create({
        pick: {
            id: '#filePicker',
            label: '点击选择图片'
        },
        dnd: '#uploader .queueList',
        paste: document.body,

        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        },

        // swf文件路径
        swf: '/Uploader/js/Uploader.swf',

        disableGlobalDnd: true,

        // 分片上传
        chunked: true,
        server: '/Uploader/upload',
        fileNumLimit: 300,
        fileSizeLimit: 500 * 1024 * 1024,    // 200 M
        fileSingleSizeLimit: 100 * 1024 * 1024    // 50 M
    });

    // 添加“添加文件”的按钮，
    uploader.addButton({
        id: '#filePicker2',
        label: '继续添加'
    });

    // 当有文件添加进来时执行，负责view的创建
    function addFile( file ) {
        var $li = $( '<li id="' + file.id + '">' +
            '<p class="title">' + file.name + '</p>' +
            '<p class="imgWrap"></p>'+
            '<p class="progress"><span></span></p>' +
            '</li>' ),

            $btns = $('<div class="file-panel">' +
                '<span class="cancel">删除</span>' +
                '<span class="rotateRight">向右旋转</span>' +
                '<span class="rotateLeft">向左旋转</span></div>').appendTo( $li ),
            $prgress = $li.find('p.progress span'),
            $wrap = $li.find( 'p.imgWrap' ),
            $info = $('<p class="error"></p>'),

            showError = function( code ) {
                switch( code ) {
                    case 'exceed_size':
                        text = '文件大小超出';
                        break;

                    case 'interrupt':
                        text = '上传暂停';
                        break;

                    default:
                        text = '上传失败，请重试';
                        break;
                }

                $info.text( text ).appendTo( $li );
            };

        if ( file.getStatus() === 'invalid' ) {
            showError( file.statusText );
        } else {
            // @todo lazyload
            $wrap.text( '预览中' );
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $wrap.text( '不能预览' );
                    return;
                }

                var img = $('<img src="'+src+'">');
                $wrap.empty().append( img );
            }, thumbnailWidth, thumbnailHeight );

            percentages[ file.id ] = [ file.size, 0 ];
            file.rotation = 0;
        }

        file.on('statuschange', function( cur, prev ) {
            if ( prev === 'progress' ) {
                $prgress.hide().width(0);
            } else if ( prev === 'queued' ) {
                $li.off( 'mouseenter mouseleave' );
                $btns.remove();
            }

            // 成功
            if ( cur === 'error' || cur === 'invalid' ) {
                console.log( file.statusText );
                showError( file.statusText );
                percentages[ file.id ][ 1 ] = 1;
            } else if ( cur === 'interrupt' ) {
                showError( 'interrupt' );
            } else if ( cur === 'queued' ) {
                percentages[ file.id ][ 1 ] = 0;
            } else if ( cur === 'progress' ) {
                $info.remove();
                $prgress.css('display', 'block');
            } else if ( cur === 'complete' ) {
                $li.append( '<span class="success"></span>' );
            }

            $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
        });

        $li.on( 'mouseenter', function() {
            $btns.stop().animate({height: 30});
        });

        $li.on( 'mouseleave', function() {
            $btns.stop().animate({height: 0});
        });

        $btns.on( 'click', 'span', function() {
            var index = $(this).index(),
                deg;

            switch ( index ) {
                case 0:
                    uploader.removeFile( file );
                    return;

                case 1:
                    file.rotation += 90;
                    break;

                case 2:
                    file.rotation -= 90;
                    break;
            }

            if ( supportTransition ) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $wrap.css({
                    '-webkit-transform': deg,
                    '-mos-transform': deg,
                    '-o-transform': deg,
                    'transform': deg
                });
            } else {
                $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                // use jquery animate to rotation
                // $({
                //     rotation: rotation
                // }).animate({
                //     rotation: file.rotation
                // }, {
                //     easing: 'linear',
                //     step: function( now ) {
                //         now = now * Math.PI / 180;

                //         var cos = Math.cos( now ),
                //             sin = Math.sin( now );

                //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                //     }
                // });
            }


        });

        $li.appendTo( $queue );
    }

    // 负责view的销毁
    function removeFile( file ) {
        var $li = $('#'+file.id);

        delete percentages[ file.id ];
        updateTotalProgress();
        $li.off().find('.file-panel').off().end().remove();
    }

    function updateTotalProgress() {
        var loaded = 0,
            total = 0,
            spans = $progress.children(),
            percent;

        $.each( percentages, function( k, v ) {
            total += v[ 0 ];
            loaded += v[ 0 ] * v[ 1 ];
        } );

        percent = total ? loaded / total : 0;

        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
        updateStatus();
    }

    function updateStatus() {
        var text = '', stats;

        if ( state === 'ready' ) {
            text = '选中' + fileCount + '张图片，共' +
                WebUploader.formatSize( fileSize ) + '。';
        } else if ( state === 'confirm' ) {
            stats = uploader.getStats();
            if ( stats.uploadFailNum ) {
                text = '已成功上传' + stats.successNum+ '张照片至XX相册，'+
                    stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
            }

        } else {
            stats = uploader.getStats();
            text = '共' + fileCount + '张（' +
                WebUploader.formatSize( fileSize )  +
                '），已上传' + stats.successNum + '张';

            if ( stats.uploadFailNum ) {
                text += '，失败' + stats.uploadFailNum + '张';
            }
        }

        $info.html( text );
    }

    function setState( val ) {
        var file, stats;

        if ( val === state ) {
            return;
        }

        $upload.removeClass( 'state-' + state );
        $upload.addClass( 'state-' + val );
        state = val;

        switch ( state ) {
            case 'pedding':
                $placeHolder.removeClass( 'element-invisible' );
                $queue.parent().removeClass('filled');
                $queue.hide();
                $statusBar.addClass( 'element-invisible' );
                uploader.refresh();
                break;

            case 'ready':
                $placeHolder.addClass( 'element-invisible' );
                $( '#filePicker2' ).removeClass( 'element-invisible');
                $queue.parent().addClass('filled');
                $queue.show();
                $statusBar.removeClass('element-invisible');
                uploader.refresh();
                break;

            case 'uploading':
                $( '#filePicker2' ).addClass( 'element-invisible' );
                $progress.show();
                $upload.text( '暂停上传' );
                break;

            case 'paused':
                $progress.show();
                $upload.text( '继续上传' );
                break;

            case 'confirm':
                $progress.hide();
                $upload.text( '开始上传' ).addClass( 'disabled' );

                stats = uploader.getStats();
                if ( stats.successNum && !stats.uploadFailNum ) {
                    setState( 'finish' );
                    return;
                }
                break;
            case 'finish':
                stats = uploader.getStats();
                if ( stats.successNum ) {
                    //alert( '上传成功' );
                } else {
                    // 没有成功的图片，重设
                    state = 'done';
                    location.reload();
                }
                break;
        }

        updateStatus();
    }

    uploader.onUploadProgress = function( file, percentage ) {
        var $li = $('#'+file.id),
            $percent = $li.find('.progress span');

        $percent.css( 'width', percentage * 100 + '%' );
        percentages[ file.id ][ 1 ] = percentage;
        updateTotalProgress();
    };

    uploader.onFileQueued = function( file ) {
        fileCount++;
        fileSize += file.size;

        if ( fileCount === 1 ) {
            $placeHolder.addClass( 'element-invisible' );
            $statusBar.show();
        }

        addFile( file );
        setState( 'ready' );
        updateTotalProgress();
    };

    uploader.onFileDequeued = function( file ) {
        fileCount--;
        fileSize -= file.size;

        if ( !fileCount ) {
            setState( 'pedding' );
        }

        removeFile( file );
        updateTotalProgress();

    };

    uploader.on( 'all', function( type ) {
        var stats;
        switch( type ) {
            case 'uploadFinished':
                setState( 'confirm' );
                break;

            case 'startUpload':
                setState( 'uploading' );
                break;

            case 'stopUpload':
                setState( 'paused' );
                break;

        }
    });

    /* 所有文件上传成功后调用 */
    uploader.on('uploadFinished', function (response) {
        $.getScript("/localStorage/localStorage.js", function () {
            /* 加载loading */
            $("body").append('<div class="loading"><div class="mask"></div>' +
                '<div id="animationTipBox" style="width: 180px; height: 150px; margin-left: -90px; margin-top: -75px;"><div class="load"><div class="icon_box"><div class="cirBox1"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox2"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div><div class="cirBox3"><div class="cir1"></div><div class="cir2"></div><div class="cir3"></div><div class="cir4"></div></div></div></div><div class="dec_txt">验证用户...</div></div></div>');
            // 验证Token
            if(get("token")!=null&&get("name")!=null){
                $.post("/token/Verification", {
                    token:get("token"),
                    name: get("name"),
                    time: get("time")
                }, function (VerificationResult) {
                    $.each(VerificationResult, function (i, result) {
                        if(result.errMsg=="OK"){
                            var address = "";
                            $(".dec_txt").html("获取上传文件信息...");
                            /* 遍历文件信息 */
                            $.each(uploader.getFiles(), function (i, FileInfo) {
                                address = address+"\""+$("#"+FileInfo.id).data("fileAddress")+"\",";
                            });
                            address = "["+address.substring(0, address.substring(1, address.length).length)+"]";
                            /* 生成固定格式的JSON数据 */
                            var JSONData = '[{"fileInfo":{"title":"'+$("input[name=title]").val()+'","label":"'+$("input[name=label]").val()+'","address":'+address+',"name":"'+get("name")+'","token":"'+get("token")+'","time":"'+get("time")+'"}}]';
                            $(".dec_txt").html("保存数据中...");
                            /* 存入数据 */
                            $.ajax({
                                data: {
                                    "JSONData" : JSONData // 发送 JSONData  数据
                                },
                                type: "POST",
                                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                                scriptCharset: 'utf-8',
                                url: "/Preservation/UploaderData",
                                success: function (result) {
                                    $.each(result, function (i, results) {
                                        $(".loading").remove();
                                        if(results.errMsg=="OK"){
                                            popup({type:"success",msg:"保存成功",delay:2000});
                                        }else{
                                            popup({type:'error',msg:results.errMsg,delay:2000,bg:true});
                                        }
                                    });
                                },
                                error : function(jqXHR, textStatus, errorThrown) {
                                    $(".loading").remove();
                                    popup({type:'error',msg:"ajax调用失败。",delay:2000,bg:true});
                                    /*弹出jqXHR对象的信息*/
                                    console.log(jqXHR.responseText)
                                    console.log(jqXHR.status);
                                    console.log(jqXHR.readyState);
                                    console.log(jqXHR.statusText);
                                    /*弹出其他两个参数的信息*/
                                    console.log(textStatus);
                                    console.log(errorThrown);
                                }
                            });
                        }else{
                            remove();
                            $(".loading").remove();
                            if(result.cause!=null){
                                console.log(result.errMsg);
                                console.log(result.cause);
                            }else{
                                console.log(result.errMsg);
                            }
                            popup({type:'error',msg:result.errMsg,delay:2000,bg:true});
                        }
                    });
                });
            }else{
                remove();
                $(".loading").remove();
                /* 无效的token */
                popup({type:'error',msg:"Invalid token",delay:2000,bg:true});
            }
        });
    });

    /* 配置上传成功*/
    uploader.on( 'uploadSuccess', function( file , response ) {
        console.log(response)
        if(response!="false"){
            // 保存 文件路径
            $("#"+file.id).data("fileAddress","/ImgFile/"+response._raw.split("ImgFile")[1].split("\\")[1])
        }else{
            popup({type:'error',msg:file.name+" 写入失败",delay:2000,bg:true});
        }
    });
    

    uploader.onError = function( code ) {
        if(code=="Q_EXCEED_NUM_LIMIT"){
        	alert("error: 文件数量超出限制");
        }else if(code=="Q_TYPE_DENIED"){
        	alert("error: 文件类型不满足条件");
        }else{
        	alert("error: 未定义的错误 "+code);
        }
    };

    /* 点击上传 */
    $upload.on('click', function() {
        /* 拿到标题标签 */
        if($("input[name=title]").val()!=""&&$("input[name=label]").val()!=""){

            if ( $(this).hasClass( 'disabled' ) ) {
                return false;
            }

            if ( state === 'ready' ) {
                uploader.upload();
            } else if ( state === 'paused' ) {
                uploader.upload();
            } else if ( state === 'uploading' ) {
                uploader.stop();
            }

        }else{
            popup({type:'tip',msg:"标题和标签不能为空",delay:1000});
        }
    });

    $info.on( 'click', '.retry', function() {
        uploader.retry();
    } );

    $info.on( 'click', '.ignore', function() {
        alert( 'todo' );
    } );

    $upload.addClass( 'state-' + state );
    updateTotalProgress();
});
