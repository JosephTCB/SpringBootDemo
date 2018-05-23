$(function () {
    FastClick.attach(document.body);
    // 消息提示弹出框
    window.meng = {
        alert: function (msg) {
            var html = '<div class="meng-pop popAlert">' +
                '<div class="meng-alert">' +
                '<p class="alert-detail">消息</p>' +
                '<p class="btn" onclick="meng.closeAlert()"><a>确定</a></p>' +
                '</div>' +
                '</div>';
            if ($(".popAlert").length == 0) {
                $("body").append(html);
            }
            $(".alert-detail").text(msg);
            $(".popAlert").removeClass("none");
        },
        closeAlert: function () {
            // 弹出框关闭
            $(".popAlert").addClass("none");
        },
        toast: function (inMsg, inDuration) {
            var toastHtml = '<div class="meng-pop aload-wrapper toast" >' +
                '<div class="meng-alert meng-aload">' +
                '<img src="images/loading.gif" alt="">' +
                '<p class="toast-detail">加载中</p>' +
                '</div>' +
                '</div>';
            if ($('.toast').length == 0) {
                $('body').append(toastHtml);
            }
            if (inDuration && inDuration != '0' && parseInt(inDuration) > 0) {
                var T = setTimeout(function () {
                    meng.closeToast();
                }, inDuration);
            }
            $(".toast-detail").text(inMsg);
            $('.toast').removeClass("none");
        },
        closeToast: function () {
            var T = setTimeout(function () {
                $('.toast').addClass("none");
            }, 500);

        },
        confirm: function (msg) {
            var promptHtml = '<div class="meng-pop confirm">' +
                '<div class="meng-alert">' +
                '<p class="prompt-detail">消息</p>' +
                '<p class="btn">' +
                '<a onclick="meng._cbConfirm(1)" style="border-right:1px solid #333">确定</a>' +
                '<a onclick="meng._cbConfirm(0)">取消</a>' +
                '</p>' +
                '</div>' +
                '</div>';
            if ($(".confirm").length == 0) {
                $("body").append(promptHtml);
            }
            $(".prompt-detail").text(msg);
            $(".confirm").removeClass("none");
            $(".confirm").attr("style", "");
        },
        _cbConfirm: function (data) {
            $('.confirm').addClass("none");
            $(".confirm").attr("style", "");
            meng.cbConfirm(data);
        }
    }
})

/**
 * 获取绝对路径
 * @returns
 */
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0, index + 1);
    return result;
}