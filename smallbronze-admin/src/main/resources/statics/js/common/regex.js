/**
 * Created by gou_feifan on 2017/7/29 10:57.
 */

const regex_expression = {
    mobile: /^1[3|5][0-9]\d{4,8}$/
};
function is_mobile(mobile) {
    return regex_expression.mobile.test(mobile);
}