$(function () {
    $(".form_datetime").datetimepicker({
        format: "dd MM yyyy - hh:ii",
        autoclose: true,
        todayBtn: true,
        startDate: "2013-02-14 10:00",
        minuteStep: 10
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