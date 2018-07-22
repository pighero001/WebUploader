/* 验证用户Token */
function VerificationToken() {
    $.getScript("/localStorage/localStorage.js", function () {
        if(get("token")!=null&&get("name")!=null){
            $.post("/token/Verification", {
                token:get("token"),
                name: get("name"),
                time: get("time")
            }, function (VerificationResult) {
                $.each(VerificationResult, function (i, result) {
                    if(result.errMsg!="OK"){
                        remove();
                        if(result.cause!=null){
                            var r=confirm(result.errMsg+":"+result.cause)
                            if(r){
                                location.href="/";
                            }else{
                                location.href="/";
                            }
                        }else{
                            var r=confirm(result.errMsg)
                            if(r){
                                location.href="/";
                            }else{
                                location.href="/";
                            }
                        }
                    }
                });
            });
        }else{
            remove();
            /* 无效的token */
            console.log("Invalid token");
            location.href="/";
        }
    });
}