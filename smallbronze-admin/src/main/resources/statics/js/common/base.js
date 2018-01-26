/**
 * 服务端返回码
 * @type {{success: string, fail: string, un_login: string, without_auth: string}}
 */
const result_status = {
    success: "SUCCESS",
    fail: "FAIL",
    un_login: "UN_LOGIN",
    without_auth: "WITHOUT_AUTH"
};

/**
 * 上传到本地
 * @param file 上传文件
 * @param success 失败函数
 * @param fail 成功函数
 * @param before 公共请求之前的操作
 * @param after 公共请求之后的操作
 */
function upload2Local(file, success, fail, before, after) {
    if (before && typeof before === 'function') {
        before();
    }
    var formData = new FormData();
    formData.append('file', file);
    $.ajax({
        url: '/ajax/upload',
        type: 'post',
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (result) {
            if (after && typeof after === 'function') {
                after();
            }
            handlerResult(result, success, fail);
        },
        error: function (data) {
            console.log(data.status);
        }
    });
}

/**
 * 异步请求
 * @param url 请求地址
 * @param param 参数
 * @param success 成功回调
 * @param fail 失败回调
 * @param before 公共请求之前的操作
 * @param after 公共请求之后的操作
 */
function postRequest(url, param, success, fail, before, after) {

    if (before && typeof before === 'function') {
        before();
    }
    $.ajax({
        url: url,
        type: 'post',
        dataType: 'json',
        data: param,
        success: function (result) {
            if (after && typeof after === 'function') {
                after();
            }
            handlerResult(result, success, fail);
        },
        error: function () {

            console.error('ajax 请求数据失败,进入[error]');
        }
    });
}

/**
 * 公共处理服务端返回数据
 * @param result 返回结果
 * @param success 成功回调
 * @param fail 失败回调
 */
function handlerResult(result, success, fail) {

    if (result.status === result_status.success) {
        if (typeof success === 'function') {
            success(result['data']);
        }
    } else if (result.status === result_status.fail) {
        if (typeof fail === 'function') {
            fail(result['msg']);
        }
    } else if (result.status === result_status.un_login){
        if(confirm("您还未登录") || !confirm("您还未登录")) {
            window.location.href = '/admin/login';
        }
    } else if (result.status === result_status.without_auth) {
        alert("你没有权限进行此操作");
    }
}

function checkImageInfo(file, standard_width, standard_height, standard_size, success) {

    //读取图片数据
    var reader = new FileReader();
    reader.onload = function (e) {
        var data = e.target.result;
        //加载图片获取图片真实宽度和高度
        var image = new Image();
        image.onload = function () {
            var width = image.width;
            var height = image.height;
            var size = file['size'];
            if (width !== standard_width || height !== standard_height) {
                $.prompt("上传图片尺寸有误，请上传尺寸为【" + standard_width + " x " + standard_height + "】的图片");
            } else {
                if (size > (standard_size * 1024)) {
                    $.prompt("上传图片文件大小过大，请上传小于【" + standard_size + "K】的图片文件");
                } else {
                    if (typeof success === 'function') {
                        success();
                    }
                }
            }
        };
        image.src = data;
    };
    reader.readAsDataURL(file);
}

/**
 * 绑定回车事件
 * @param ele 要绑定对象
 * @param event 事件
 */
function enterEvent(ele, event) {
    $(ele).on("keydown", function (key) {
        if (key.keyCode === 13) {
            if (typeof event === 'function') {
                event();
            }
        }
    });
}

/**
 * 字符串为空
 * @param string
 * @returns {boolean}
 */
function stringIsNull(string) {
    return !string || (typeof string === 'string' && string.trim() === '');
}

/**
 * 字符串非空
 * @param string
 * @returns {boolean}
 */
function stringIsNotNull(string) {
    return string && (typeof string === 'string' && string.trim() !== '');
}

/**
 * 集合为空
 * @param collection
 * @returns {boolean}
 */
function collectionIsEmpty(collection) {
    return !collection || !collection[0];
}

/**
 * 集合非空
 * @param collection
 * @returns {*}
 */
function collectionIsNotEmpty(collection) {
    return collection && collection[0];
}

/**
 * 校验是否是url
 * @param url
 * @returns {boolean}
 */
function isUrl(url) {
    var strRegex = /^((https|http|ftp|rtsp|mms)?:\/\/)+[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
    var url_regex = new RegExp(strRegex);
    return url_regex.test(url);
}

/**
 * trim()
 * @param str
 * @returns {*}
 */
function stringTrim(str) {

    if (stringIsNull(str) || str === 'null') {
        return '';
    }
    else {
        if (typeof str === 'string') {
            return str.trim();
        }
        return str;
    }
}

/**
 * 给liger框架的列增加title
 * @param content
 * @returns {*}
 */
function divWithTitle(content) {

    if (stringIsNotNull(content)) {
        return '<div title="' + content + '" style="cursor: pointer">' + content + '</div>';
    }
    return "";
}

/**
 * 图片加载失败,用默认图片替换
 * @param ele
 */
function imageError(ele) {

    ele.src = 'http://daishumovie.oss-cn-shanghai.aliyuncs.com/img/empty_image.png';
}

/**
 * 校验文件格式
 * @param file_name
 * @param file_suffix
 * @param suffix_name
 * @returns {boolean}
 */
function checkFileType(file_name,file_suffix,suffix_name) {

    if (stringIsNull(file_name) || stringIsNull(file_suffix) || stringIsNull(suffix_name)) {
        return false;
    }
    return file_name.indexOf(file_suffix) > -1 && file_name.substr(file_name.lastIndexOf(file_suffix) + 1) === suffix_name;
}
