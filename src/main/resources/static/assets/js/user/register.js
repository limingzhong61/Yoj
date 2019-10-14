var maxTime = 60;
var vue = new Vue({
    el: "#app",
    data: {
        userName: '',
        userNameJudge: true,
        userNameMsg: '',

        password: '',
        passwordJudge: true,
        passwordMsg: '',

        secondPassword: '',
        secondPasswordMsg: '',
        email: '',
        emailJudge: false,
        emailMsg: '',

        emailCheckCode: '',
        headers: null,
    },
    methods: {
        countDown() {
            if (maxTime == 0) {
                checkCode = "";
                $("#emailBtn").removeAttr("disabled")
                $("#emailBtn").text("获取验证码");
                maxTime = 60;
            } else {
                $("#emailBtn").attr("disabled", "");
                $("#emailBtn").text(maxTime + "秒后重新获取");
                maxTime--;
                window.setTimeout(vue.$options.methods.countDown, 1000);
            }
        },
        sendEmail(){
            if(!this.emailJudge){
                return
            }
            $('#myModal').modal('show');
            $.ajax({
                url: "/u/r/getEmailCheckCode/" + this.email,
                method: "GET",
                success: res => {
                    $('#myModal').modal('hide');
                    this.emailJudge = res.success;
                    if (res.success) {
                        this.$options.methods.countDown();
                    } else {
                        this.emailMsg = res.msg
                    }
                },
                error: err => {
                    // console.log(res);
                    $('#myModal').modal('hide');
                }
            })
        },
        register(){
            if(!this.userNameJudge || !this.passwordJudge || !this.secondPasswordJudge || !this.emailJudge){
                return
            }
            console.log("register")
            $.ajax({
                url: "/u/r/register",
                method: "POST",
                data: {
                    userName: this.userName,
                    password: this.password,
                    email: this.email,
                    emailCheckCode: this.emailCheckCode,
                },
                headers: this.headers,
                success: res => {
                    console.log(res);
                    if (res.success) {
                        window.location.href = "/user/login";
                    }else{
                        console.log('error')
                    }
                },
                error(res) {
                    console.log(res);
                }
            })
        }
    },
    watch: {
        userName: function (value) {
            // console.log(value)
            if (value === "") {
                // validateWithMsg(this, false, "");
                this.userNameJudge = false
                this.userNameMsg = '用户名不能为空'
                return
            }
            $.ajax({
                url: "/u/r/validateUserName/" + value,
                method: "GET",
                headers: this.headers,
                success: res => {
                // console.log(res);
                    if(res.success){
                    this.userNameJudge = true
                    this.userNameMsg = ''
                }
            else
                {
                    this.userNameJudge = false
                    this.userNameMsg = "用户名已存在"
                }
            },
                error(res)
                {
                    // console.log(res);
                }
            })
        },
        password: function (value) {
            // console.log(value)
            if(value === ''){
                this.passwordJudge = false
                this.passwordMsg = '密码不能为空.'
                return
            }
            this.passwordJudge = true
            this.passwordMsg = ''
        },
        email: function (value) {
            var emailReg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/i;
            this.emailJudge = emailReg.test(value)
            if(!this.emailJudge){
                this.emailMsg = '请输入一个有效的电子邮件地址.'
                return
            }
            $.ajax({
                url: "/u/r/validateEmail/" + value,
                method: "GET",
                success: res => {
                    // console.log(res);
                    this.emailJudge = res.success
                    if (this.emailJudge) {
                        this.emailMsg = ''
                    } else {
                        this.emailMsg = res.msg
                    }
                },
                error(res) {
                    // console.log(res);
                }
            })
        }
    },
    computed:{
        secondPasswordJudge: function (value) {
            if(this.secondPassword != this.password){
                this.secondPasswordMsg = '两次密码不一致'
                return false
            }
            this.secondPasswordMsg = ''
            return true
        }
    },
    mounted() {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var headers = {};
        headers[csrfHeader] = csrfToken;
    }
})