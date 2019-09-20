$(function () {
    // // 最小高度
    // var minRows = 2;
    // // 最大高度，超过则出现滚动条
    // var maxRows = 10;
    // $("textarea").each(function () {
    //     $(this).keyup(function () {
    //         if (this.scrollTop == 0) this.scrollTop = 1;
    //         while (this.scrollTop == 0) {
    //             if (this.rows > minRows)
    //                 this.rows--;
    //             else
    //                 break;
    //             this.scrollTop = 1;
    //             if (this.rows < maxRows)
    //                 this.style.overflowY = "hidden";
    //             if (this.scrollTop > 0) {
    //                 this.rows++;
    //                 break;
    //             }
    //         }
    //         while (this.scrollTop > 0) {
    //             if (this.rows < maxRows) {
    //                 this.rows++;
    //                 if (this.scrollTop == 0) this.scrollTop = 1;
    //             }
    //             else {
    //                 this.style.overflowY = "auto";
    //                 break;
    //             }
    //         }
    //     })
    // });
    var E = window.wangEditor;
    var editors = {};
    $(".editor").each(function () {
        var editor = new E('#' + this.id + " .toolbar", '#' + this.id + " .text");
        // var editor = new E(".toolbar",".text");
        editor.customConfig.zIndex = 100;
        // 下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
        editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
        // editor.customConfig.uploadImgServer = '/upload'  // 上传图片到服务器
        editor.create();

        editors[this.id] = editor;
    });

    var cnt = 0;
    document.getElementById('btn').addEventListener('click', function () {
        //先校验
        var length = $("input").length;
        cnt = 0;
        $("input").trigger("change");
        if (cnt !== length) {
            $("#submit-msg").prop("hidden", "");
            return;
        } else {
            $("#submit-msg").prop("hidden", "hidden");
        }


        // 读取数据
        var problem = {};
        $(".addInput").each(function () {
            problem[this.name] = this.value;
        })
        for (var i in editors) {
            problem[i] = editors[i].txt.html();
        }
        var judgeData = [];
        $(".judge-data").each(function () {
            var data = {};
            var inputData = $(this).find(".judge-input").val();
            data["in"] = inputData.trim();
            var outputData = $(this).find(".judge-output").val();
            data["out"] = outputData.trim();
            judgeData.push(data);
        })
        problem["judgeData"] = JSON.stringify(judgeData);
        // console.log(problem);
        // return;

        // var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var headers = {};
        headers[csrfHeader] = csrfToken;
        var url;
        if(prob){
            url = "/p/alter";
            problem["problemId"] = prob.problemId;
        }else{
            url = "/p/add";
        }
        console.log(problem);
        $.ajax({
            url: url,
            type: "POST",
            headers: headers,
            data: problem,
            success(res) {
                console.log(res);
                if (res.success) {
                    window.location.href = "/p/" + res.extend.pid;
                }
            },
            error(res) {
                console.log(res);
            }
        })
    }, false);
    $("input").each(function () {
        $(this).change(function () {
            cnt += validate(this, this.value != "");
        })
    });
    //添加判题案例事件
    $("#addCase").click(function () {
        var caseNum = $(".caseItem").length + 1;
        if (caseNum > 10) {
            return;
        }
        var str = $("#judge-area").html();
        // console.log($("#judge-area").html());

        var $div = $("<div><div").addClass("row caseItem");

        $div.html("<div class=\"col-12 \">\n" +
            "<span class=\"caseNum\">用例"+caseNum+"</span>\n" +
            "<button id=\"caseBtn"+ caseNum + "\" class=\"btn btn-danger btn-sm\">删除用例</button>\n" +
            "</div>\n" +
            "<div class=\"col-12 row judge-data\">\n" +
            "<div class=\"col-6\">判题输入：\n" +
            "<textarea name=\"code\" placeholder=\"\" class=\"form-control judge-input\" autofocus=\"\"\n" +
            "data-autofocus=\"\"></textarea>\n" +
            "</div>\n" +
            "<div class=\"col-6\">判题输出：\n" +
            "<textarea name=\"code\" placeholder=\"\" class=\"form-control judge-output\" autofocus=\"\"\n" +
            "data-autofocus=\"\"></textarea>\n" +
            "</div>\n" +
            "</div>")

        $("#judge-area").append($div);

    });
    //给不存在的元素添加事件
    for(var i = 1; i <= 10; i++){
        $(document).on('click','#caseBtn'+i,function(){
            alert("确定删除此用例吗？");
            $(this).parents("div.caseItem").remove();
            $(".caseNum").each(function (index,item) {
                this.innerText = "用例"+ (index+1);
            })
        });
    }
})