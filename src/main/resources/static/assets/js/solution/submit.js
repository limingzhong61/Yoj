$(function () {
    var cnt = 0;
    $("form").submit(function () {
        if(document.getElementById("user")){
            return;
        }
        cnt = 0;
        ("select").trigger("change");
        ("textarea").trigger("change");
        return cnt === 2;
    });

    $("select").change(function () {
        cnt += validate(this,this.value != "-1");
    });
    $("textarea").change(function () {
        cnt += validate(this,this.value != "");
    });

});