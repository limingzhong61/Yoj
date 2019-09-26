
/**
 *
 * @param item
 * @param flag false添加错误信息
 * @returns {boolean} 返回 flag
 */
function validate(item,flag){
    if(flag){
        $(item).removeClass("is-invalid");
        $(item).addClass('is-valid');
    }else{
        $(item).removeClass("is-valid");
        $(item).addClass('is-invalid');
    }
    return flag;
}

/**
 *
 * @param item
 * @param flag
 * @param errorMsg "提示信息"
 * @returns {*}
 */
function validateWithMsg(item,flag,errorMsg){
    if(flag){
        $(item).removeClass("is-invalid");
        $(item).addClass('is-valid');
    }else{
        $(item).removeClass("is-valid");
        $(item).addClass('is-invalid');
        $(item).next(".invalid-feedback").text(errorMsg);
    }
    return flag;
}