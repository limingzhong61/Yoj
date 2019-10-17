new Vue({
    el: "#app",
    components:{
        'judge-case': {
            template: '#JudgeCase',
            data(){
                return {
                    caseIn: '',
                    caseOut: ''
                }
            },
            wacth:{
                caseIn(value){
                    console.log(value)
                }
            }
        }
    },
    data: {
        headers: {},
        title: '',
        titleJudge: true,

        timeLimit: '',
        timeLimitJudge: true,

        memoryLimit: '',
        memoryLimitJudge: true,

        judgeData:[{in:'1',out:'2'}],
        judgeCase: {in:'',out:''}
    },
    methods: {
        addCase(){
            this.judgeData.push({in:'',out:''})
        },
        delCase(index){
            console.log(this.judgeData)
            this.judgeData.splice(index-1,1)
            console.log(this.judgeData)
            console.log(index)
        },
        changeInData(index){

        },
        addProblem(){
            console.log(this.$options.data)
            console.log(this)
            var judgeData = [];
            for (var i in editors) {
                problem[i] = editors[i].txt.html();
            }
            $(".judge-data").each(function () {
                var data = {};
                var inputData = $(this).find(".judge-input").val();
                data["in"] = inputData.trim();
                var outputData = $(this).find(".judge-output").val();
                data["out"] = outputData.trim();
                judgeData.push(data);
            })
            console.log(judgeData)
            // problem["judgeData"] = JSON.stringify(judgeData);
            // $.ajax({
            //     url: "/p/add",
            //     type: "POST",
            //     headers: this.headers,
            //     success: function (result) {
            //
            //     }
            //
            // })
        }
    },
    watch:{
        title(value){
            this.titleJudge = value != ""
            // console.log(value)
        },
        timeLimit(value){
            this.timeLimitJudge = value != ""
            // console.log(value)
        },
        memoryLimit(value){
            this.memoryLimitJudge = value != ""
            // console.log(value)
        }
    },
    computed:{
    },
    mounted() {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var headers = {};
        headers[csrfHeader] = csrfToken
        this.headers = headers

        //富文本框
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
    }
})


Vue.component('JudgeCase', {
    template: '#JudgeCase'
});