var totalRecord, currentPage;
// 1.页面加载完成以后，直接发送ajax请求，要到分页数据
$(function () {
    toPage(1);

    function toPage(pageNumber) {
        $.ajax({
            url: "/p/main/" + pageNumber,
            type: "GET",
            success: function (result) {
                console.log(result);
                // 1.解释并显示数据
                bulidTable(result);
                // 2.parse and show devide page information
                pageInfo(result);
                // 2.parse and show devide page navigator
                pageNav(result);
            }
        });
    }

    function bulidTable(result) {
        // 先清空
        $("tbody").empty();
        var solutions = result.extend.pageInfo.list;
        $.each(solutions, function (index, item) {
            var $tableTr = $("<tr></tr>");
            // var checkBoxTd = $("<td></td>").append("<input type='checkbox'
            // class='check_item'/>");
            $("<td></td>").append(this.problemId).appendTo($tableTr);
            $("<td></td>").append().appendTo($tableTr);
            //<a th:href="@{/p/1}">A+B Problem</a>
            var $a = $("<a></a>").append(this.title).attr("href", "/p/" + this.problemId)
            $("<td></td>").append($a).appendTo($tableTr);
            $("<td></td>").append("入门").appendTo($tableTr);
            $("<td></td>").append(this.submissions).appendTo($tableTr);
            $("<td></td>").append(this.acRate + "%").appendTo($tableTr);
            $tableTr.appendTo("tbody");
        })
    }

    function pageInfo(result) {
        $("#page-info-area").empty().append(
            "当前" + result.extend.pageInfo.pageNum + "页，总"
            + result.extend.pageInfo.pages + "页，共"
            + result.extend.pageInfo.total + "条记录");
        totalRecord = result.extend.pageInfo.total;
        // 更新员工信息后，需返回显示当前页
        currentPage = result.extend.pageInfo.pageNum;
    }

    function pageNav(result) {
        // page_nav_area
        $("#page_nav_area").empty();
        var ul = $("<ul></ul>").addClass("pagination");
        // li class=""><a class="page-link" href="#">Previous</a></li>
        // 构建元素
        var firstPageLi = $("<li></li>").addClass("page-item").append(
            $("<a></a>").addClass("page-link").append("首页").attr("href", "#"));
        var prePageLi = $("<li></li>").addClass("page-item").append(
            $("<a></a>").addClass("page-link").append("&laquo;").attr("href",
                "#"));
        if (result.extend.pageInfo.hasPreviousPage == false) {
            firstPageLi.addClass("disabled");
            prePageLi.addClass("disabled");
        } else {
            // 为元素添加翻页事件
            firstPageLi.click(function () {
                toPage(1);
            });
            prePageLi.click(function () {
                toPage(result.extend.pageInfo.pageNum - 1);
            });
        }

        var nextPageLi = $("<li></li>").addClass("page-item").append(
            $("<a></a>").addClass("page-link").append("&raquo;").attr("href",
                "#"));
        var lastPageLi = $("<li></li>").addClass("page-item").append(
            $("<a></a>").addClass("page-link").append("末页").attr("href", "#"));
        if (!result.extend.pageInfo.hasNextPage) {
            nextPageLi.addClass("disabled");
            lastPageLi.addClass("disabled");
        } else {
            nextPageLi.click(function () {
                toPage(result.extend.pageInfo.pageNum + 1);
            });
            lastPageLi.click(function () {
                toPage(result.extend.pageInfo.pages);
            });
        }

        // 页码1，2，3，4
        ul.append(firstPageLi).append(prePageLi);
        $.each(result.extend.pageInfo.navigatepageNums, function (index, item) {
            var numLi = $("<li></li>").addClass("page-item").append(
                $("<a></a>").addClass("page-link").append(item).attr("href",
                    "#"));
            if (result.extend.pageInfo.pageNum == item) {
                numLi.addClass("active");
            }
            numLi.click(function () {
                toPage(item);
            });
            ul.append(numLi);
        });
        ul.append(nextPageLi).append(lastPageLi);

        // 把ul加入到nav
        var navEle = $("<nav></nav>").append(ul);
        navEle.appendTo("#page_nav_area");
    }
})

