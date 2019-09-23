$(function () {
    $("form").submit(function () {
        if(document.getElementById("user")){
            return false;
        }
        if(!validate($("select"),$("select").val() != "-1")){
            return false;
        }
        if(!validate($("textarea"),$("textarea").val() != "")){
            return false;
        }
        // return false;
    });
    $("select").change(function () {
        validate(this,this.value != "-1");
    });
    $("textarea").change(function () {
        validate(this,this.value != "");
    });

});