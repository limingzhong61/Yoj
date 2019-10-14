// sessionStorage.removeItem('Author');
// sessionStorage.clear();

$(function () {
    //顶部栏跳转，有bug
    var activeIndex = sessionStorage.getItem('activeIndex');
    // console.log(activeIndex);
    $("#navbarSupportedContent").find("a").each(function () {
        $(this).removeClass("active")
        this.onclick = function () {
            var index=$("#navbarSupportedContent a").index(this);
            sessionStorage.setItem('activeIndex',index);
            return true;
        };
    });
    if(activeIndex != null){
        $("#navbarSupportedContent a").eq(activeIndex).addClass("active");
    }

    // $("#login").click(function () {
    //     window.open("/login.html")
    //     return false;
    // });
})