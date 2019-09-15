
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