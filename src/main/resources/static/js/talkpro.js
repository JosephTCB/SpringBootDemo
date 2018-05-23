var startPosition, endPosition, deltaX, deltaY, moveLength;
var overHeight = false;
var voice = {
    localId: '',
    serverId: ''
};
var openid = null;
var nickname = null;
var headimgurl = "images/me.jpg";
var pathName = window.document.location.pathname
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1)
var code = getQueryString("code");
if (code != null) {
    $.ajax({
        type: "get",
        url: projectName + "/getUserInfo?code=" + code,
        async: false,
        dataType: "json",
        success: function (msg) {
            if (msg.openid != undefined && msg.openid != null && $.trim(msg.openid) != "") {
                openid = msg.openid;
                headimgurl = msg.headimgurl;
                nickname = msg.nickname;
            }
        }
    });
}
$(function () {
//	window.ontouchstart = function(e) { e.preventDefault(); };
    /**
     * 切换语音和文字发送
     */
    $(".opt1").find(".change").on("click", function () {
        $(".opt1").addClass("dis-none-imp");
        $(".opt2").removeClass("dis-none-imp");
    })
    $(".opt2").find(".change").on("click", function () {
        $(".opt2").addClass("dis-none-imp");
        $(".opt1").removeClass("dis-none-imp");
    })
    /**
     * 调用语音录入接口
     */
        //手指按下
    var currentDate, minsstart, minsend;
    $(".voice").on("touchstart", function (e) {
        currentDate = new Date();
        minsstart = currentDate.getMinutes() * 60 + currentDate.getSeconds();
//		console.log(minsstart);
        $(".pop-send").removeClass("dis-none-imp");
        var touch = e.originalEvent.touches[0];
        startPosition = {
            x: touch.pageX,
            y: touch.pageY
        }
        wx.startRecord({
            cancel: function () {
                $(".pop-send").addClass("dis-none-imp");
                meng.alert('用户拒绝授权录音');
            }
        });
        e.preventDefault();
    })
    /*//手指移动
     $(".voice").on("touchmove",function(e){
     var touch = e.originalEvent.touches[0];
     endPosition = {
     x: touch.pageX,
     y: touch.pageY
     }

     deltaX = endPosition.x - startPosition.x;
     deltaY = endPosition.y - startPosition.y;
     moveLength = deltaY<0?Math.sqrt(Math.pow(Math.abs(deltaX), 2) + Math.pow(Math.abs(deltaY), 2)):Math.sqrt(Math.pow(Math.abs(deltaX), 2) + Math.pow(Math.abs(deltaY), 2));
     //        console.log(moveLength);
     if(moveLength>50){
     $(".pop-send").addClass("dis-none-imp");
     $(".pop-cancel").removeClass("dis-none-imp");
     //超过滑动距离取消录音
     overHeight = true;
     }
     })*/
    //手指离开
    $(".voice").on("touchend", function (e) {
        e.preventDefault();
        currentDate = new Date();
        minsend = currentDate.getMinutes() * 60 + currentDate.getSeconds();
//		console.log(minsend);
        if (minsend - minsstart < 1) {
            meng.alert("录音时间太短");
            $(".pop-send").addClass("dis-none-imp");
            $(".pop-cancel").addClass("dis-none-imp");
            wx.stopRecord({
                success: function (res) {
                    voice.localId = res.localId;
                },
                fail: function (res) {
                    meng.alert(JSON.stringify(res));
                }
            });
            return;
        }
        $(".pop-send").addClass("dis-none-imp");
        $(".pop-cancel").addClass("dis-none-imp");
        //语音发送
        wx.stopRecord({
            success: function (res) {
                voice.localId = res.localId;
                showAskVoice(res.localId);
                if (overHeight == false) {
                    //语音识别
                    wx.translateVoice({
                        localId: voice.localId, // 需要识别的音频的本地Id，由录音相关接口获得
                        isShowProgressTips: 1, // 默认为1，显示进度提示
                        success: function (res) {
//					        alert(res.translateResult); // 语音识别的结果
                            showAnswerMsg("识别出您的语音：" + res.translateResult);
                            send(res.translateResult);
                        }
                    });
                    /*//先上传到微信服务器
                     wx.uploadVoice({
                     localId: voice.localId, // 需要上传的音频的本地ID，由stopRecord接口获得
                     isShowProgressTips: 1, // 默认为1，显示进度提示
                     success: function (res) {
                     var serverId = res.serverId; // 返回音频的服务器端ID
                     //
                     $("#msg").val(serverId);
                     }
                     });*/
                }
            },
            fail: function (res) {
                meng.alert(JSON.stringify(res));
            }
        });
        e.preventDefault();
    })

    //监听录音自动停止
    wx.onVoiceRecordEnd({
        complete: function (res) {
            voice.localId = res.localId;
            meng.alert('录音已停止');
        }
    });


    /**
     * 文字发送
     */
    $(".send-btn").on("click", function () {
        var msg = $("#msg").val();
        if ($.trim(msg) != "") {
            showAsk(msg);
        }
    });
    $("#msg").on("focus", function () {
        var _this = this;
        setTimeout(function () {
            _this.scrollIntoView(true);
        }, 300);
    });
})
/**
 * 发送成功返回
 * @param result
 */
function serverResponseSuccess(question, result) {
    var code = result.code;
    var answer = "";
    if (code == "000") {
        answer = result.content;
    } else {
        answer = "这个小微现在还没有学会。";
    }
    showAnswerPro(question, answer);
}
/**
 * 回答
 * @param msg
 */
function showAnswerMsg(msg) {
    var sendHTML = '<div class="group clear left">' +
        '<img src="images/portrait.jpg" class="portrait left" alt=""/>' +
        '<p class="message left">' + msg + '</p>' +
        '</div>';
    $(".content").append(sendHTML);
    //滚动到底部
    $(".content").scrollTop($(".content")[0].scrollHeight);
}
/**
 * pro回答
 * @param msg
 */
function showAnswerPro(question, answer) {
    var sendHTML = '<div class="group clear left">' +
        '<img src="images/portrait.jpg" class="portrait left" alt=""/>' +
        '<p class="message left" question="' + question + '">' +
        '<span>' + answer + '</span>' +
        '<br/><br/>' +
        '<a onclick="satisfy(this)" style="color: #0B5591">满意</a>' +
        '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
        '<a onclick="normal(this)" style="color: #0B5591">一般</a>' +
        '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
        '<a onclick="unsatisfy(this)" style="color: #0B5591">不满意</a>' +
        '</p>' +
        '</div>';
    $(".content").append(sendHTML);
    //滚动到底部
    $(".content").scrollTop($(".content")[0].scrollHeight);
}
function unsatisfy(obj, answer) {
    var _this = $(obj);
    _this.css("color", "#F2F2F0");
    _this.attr("onclick", "");
    _this.siblings().last().attr("onclick", "");
    _this.siblings().last().css("color", "#F2F2F0");
    _this.siblings().eq(-2).attr("onclick", "");
    _this.siblings().eq(-2).css("color", "#F2F2F0");
    var question = _this.parent().attr("question");
    var answer = _this.siblings().first().text();
    var data = new FormData();
    data.append("question", question);
    data.append("answer", answer);
    data.append("openid", openid);
    data.append("username", nickname);
    data.append("bz", "N");
    $.ajax({
        url: projectName + "/add_one",
        type: 'POST',
        dataType: 'json',
        processData: false,
        contentType: false,
        data: data,//$.toJSON(serverProtocol),
    });
}
function satisfy(obj, answer) {
    var _this = $(obj);
    _this.css("color", "#F2F2F0");
    _this.attr("onclick", "");
    _this.siblings().last().attr("onclick", "");
    _this.siblings().last().css("color", "#F2F2F0");
    _this.siblings().eq(-2).attr("onclick", "");
    _this.siblings().eq(-2).css("color", "#F2F2F0");
    var question = _this.parent().attr("question");
    var answer = _this.siblings().first().text();
    var data = new FormData();
    data.append("question", question);
    data.append("answer", answer);
    data.append("openid", openid);
    data.append("username", nickname);
    data.append("bz", "Y");
    $.ajax({
        url: projectName + "/add_one",
        type: 'POST',
        dataType: 'json',
        processData: false,
        contentType: false,
        data: data,//$.toJSON(serverProtocol),
    });
}
function normal(obj, answer) {
    var _this = $(obj);
    _this.css("color", "#F2F2F0");
    _this.attr("onclick", "");
    _this.siblings().last().attr("onclick", "");
    _this.siblings().last().css("color", "#F2F2F0");
    _this.siblings().eq(-2).attr("onclick", "");
    _this.siblings().eq(-2).css("color", "#F2F2F0");
    var question = _this.parent().attr("question");
    var answer = _this.siblings().first().text();
    var data = new FormData();
    data.append("question", question);
    data.append("answer", answer);
    data.append("openid", openid);
    data.append("username", nickname);
    data.append("bz", "M");
    $.ajax({
        url: projectName + "/add_one",
        type: 'POST',
        dataType: 'json',
        processData: false,
        contentType: false,
        data: data,//$.toJSON(serverProtocol),
    });
}
/**
 * 提问
 * @param msg
 */
function showAsk(msg) {
    var sendHTML = '<div class="group clear right">' +
        '<img src="' + headimgurl + '" class="portrait right" alt=""/>' +
        '<p class="message right">' + msg + '</p>' +
        '</div>';
    $(".content").append(sendHTML);
    //滚动到底部
    $(".content").scrollTop($(".content")[0].scrollHeight);
    //清空输入框
    $("#msg").val("");
    send(msg);
}
/**
 * 提问语音
 * @param id
 */
function showAskVoice(id) {
    var sendHTML = '<div class="group clear right">' +
        '<img src="' + headimgurl + '" class="portrait right" alt=""/>' +
        '<p class="message right" onclick="playVoice(\'' + id + '\')"><img src="images/voice.png" class="right" style="width:20px;height:20px;"></p>' +
        '</div>';
    $(".content").append(sendHTML);
    //滚动到底部
    $(".content").scrollTop($(".content")[0].scrollHeight);
}
/**
 * 播放语音
 * @param id
 */
function playVoice(id) {
    wx.playVoice({
        localId: id // 需要播放的音频的本地ID，由stopRecord接口获得
    });
}
/**
 * 发送
 * @param msg
 */
function send(msg) {
    var data = new FormData();
    data.append("question", msg);
    data.append("userid", openid);
    $.ajax({
        url: projectName + "/service",
        type: 'POST',
        dataType: 'json',
        processData: false,
        contentType: false,
        data: data,//$.toJSON(serverProtocol),
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            showAnswerMsg("网络连接错误");
        },
        success: function (result) {
            serverResponseSuccess(msg, result);
        }
    });
}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
