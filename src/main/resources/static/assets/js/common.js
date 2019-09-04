// sessionStorage.removeItem('Author');
// sessionStorage.clear();

$(function () {
    //顶部栏跳转，有bug
    var activeIndex = sessionStorage.getItem('activeIndex');
    console.log(activeIndex);
    $("#navbarNav").find("a").each(function () {
        $(this).removeClass("active")
        this.onclick = function () {
            var index=$("#navbarNav a").index(this);
            sessionStorage.setItem('activeIndex',index);
            return true;
        };
    });
    if(activeIndex != null){
        $("#navbarNav a").eq(activeIndex).addClass("active");
    }

})