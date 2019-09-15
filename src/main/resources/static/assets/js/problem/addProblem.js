$(function () {
    // 最小高度
    var minRows = 2;
    // 最大高度，超过则出现滚动条
    var maxRows = 10;
    $("textarea").each(function () {
        $(this).keyup(function () {
            if (this.scrollTop == 0) this.scrollTop = 1;
            while (this.scrollTop == 0) {
                if (this.rows > minRows)
                    this.rows--;
                else
                    break;
                this.scrollTop = 1;
                if (this.rows < maxRows)
                    this.style.overflowY = "hidden";
                if (this.scrollTop > 0) {
                    this.rows++;
                    break;
                }
            }
            while (this.scrollTop > 0) {
                if (this.rows < maxRows) {
                    this.rows++;
                    if (this.scrollTop == 0) this.scrollTop = 1;
                }
                else {
                    this.style.overflowY = "auto";
                    break;
                }
            }
        })
    });
})