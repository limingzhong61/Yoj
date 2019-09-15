// Example starter JavaScript for disabling form submissions if there are invalid fields
$(function () {
    var cnt = 0;
    $("form").submit(function () {
        cnt = 0;
        $(this).find("input").trigger("change");
        // $("#secondPassword").trigger("change");
        return cnt === 2;
    });
    // $("form").find("input").each(function () {
    //     $(this).change(function () {
    //         validate(this,$(this).val() === "");
    //     });
    // })

    $("#userName").change(function () {
        cnt += validate(this,this.value != "");
    });
    $("#password").change(function () {
        validate(this,this.value != "");
        if($("#password").val() != ""){
            validate("#secondPassword",$("#secondPassword").val() == $("#password").val());
        }
    });
    $("#secondPassword").change(function () {
        if($("#password").val() != ""){
            cnt += validate(this,this.value == $("#password").val());
        }
    });

});