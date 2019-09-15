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
    $(".addDiv").each(function () {
        var editor = new E('#'+this.id);
        editor.customConfig.zIndex = 100;
        // 下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
        editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
        // editor.customConfig.uploadImgServer = '/upload'  // 上传图片到服务器
        editor.create();

        editors[this.id] = editor;
    })


    // var editor = new E('#description');
    // // 或者 var editor = new E( document.getElementById('editor') )
    // editor.customConfig.zIndex = 100;
    // // 下面两个配置，使用其中一个即可显示“上传图片”的tab。但是两者不要同时使用！！！
    // editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
    // // editor.customConfig.uploadImgServer = '/upload'  // 上传图片到服务器
    // editor.create();


    document.getElementById('btn').addEventListener('click', function () {
        // 读取 html
        console.log(editors.hint.txt.html());
        // var hint = new E('#hint');
        // console.log(hint.txt.html());
        var problem = {};
        $(".addInput").each(function () {
            // console.log(this.name + ":" + this.value);
            problem[this.name] =this.value;
        })
        for(var i in editors){
            console.log(i + ":" + editors[i].txt.html());
            problem[i] = editors[i].txt.html();
        }
        // var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var headers = {};
        headers[csrfHeader] = csrfToken;
        console.log(problem);
        $.ajax({
            url: "/p/add",
            type: "POST",
            headers : headers,
            data: problem,
            success(res){
                // $("id").add(res);
                console.log(res);
            },
            error(res){
                console.log(res);
            }
        })
    }, false)

})