/**
 * Created by cgw on 2016-11-17.
 */

//路径
var contextPath = window.location.protocol+"//"+window.location.host+"/"+window.location.pathname.split("/")[1];

//检验不为空
function checkNotBlank(obj) {
    var valid = true;
    $(obj).find("input").each(function() {
        if ($(this).attr("valid") == "NotBlank" && $(this).val() == "") {
            showError($(this).attr("placeholder"));
            checkErrorHandle(this);
            valid = false;
            return false;
        }
    });
    return valid;
}

//输入框校验错误执行
function checkErrorHandle(obj) {
    $(obj).parent().addClass("has-error");
    $(obj).next().addClass("glyphicon-warning-sign");
    $(obj).focus();
    $(obj).attr("oninput", "valChange(this)");
}

//错误输入框重新输入后  样式恢复成正常
function valChange(obj) {
    //js Dom 转为 jqery对象    obj     $(obj)
    $(obj).parent().removeClass("has-error");
    $(obj).next().removeClass("glyphicon-warning-sign");
    $(obj).removeAttr("oninput");
}

//错误提示Alert
function showError(errorModelBody) {
    BootstrapDialog.alert({
        title: "温馨提示",
        message: errorModelBody
    });
}
//错误提示confirm
function showConfirm(confirmBody,confirmFn) {
	BootstrapDialog.show({
		title:"温馨提示",
        message: confirmBody,
        buttons: [
        {
            label: '确定',
            cssClass: 'btn-primary',
            action: function(dialogItself){
            	dialogItself.close();
            	confirmFn();
            }
        }, {
            label: '取消',
            action: function(dialogItself){
                dialogItself.close();
            }
        }]
    });
}

//日期控件
function initDatetimepicker(obj) {
	$(obj).datetimepicker({
		autoclose: 1,
		minView: 2
	});
}

//分页查询固定参数
function buildPageForm(data) {
    return "&start=" + data.start + "&length=" + data.length + "&draw=" + data.draw;
}

//地址栏改变触发的事件   用户后退操作
function  hashChange(){
	var href=location.hash;
	href=href.substring(2);
	var webInfo = href.split(";@");
	toPage(webInfo[1], webInfo[0], "");
}

/**===================================   字典模块start   =========================================*/
//定义字典
var dictionaryList;
function getAllDict() {
	$.post( contextPath+"/getAllDictionary", "", function(result){
		dictionaryList = result;
	});
}
//定时任务 30分钟更新一次字典项 也可手动直接调用getAllDict()更新
setInterval("getAllDict()", 30*60*1000);

//获取指定字典子项列表
function getDictionaryList(dictEntry){
	var dictEntryList = dictionaryList[dictEntry];
	return dictEntryList;
}

//根据数据字典项和子项查询，返回Dictionary对象
function getDictionary(dictEntry, subEntry) {
	var dictEntryList = getDictionaryList(dictEntry);
	for(i in dictEntryList){
		if(dictEntryList[i].subEntry == subEntry) {
			return dictEntryList[i];
		}
	}
}

//根据数据字典项和子项查询，返回字典子项中文，不存在则返回子项值
function getDictCaption(dictEntry, subEntry) {
	var dictionary = getDictionary(dictEntry, subEntry);
	return dictionary.dictPrompt;
}

/**===================================   字典模块end     =========================================*/

//删除修改操作封装
var Manage = {
    del :  function del(table, url, obj) {
        BootstrapDialog.show({
            title: '提示',
            message: '亲，确定删除该条记录吗?',
            buttons: [
                {
                    label: '确定',
                    action: function (dialog) {
                        $.ajax({
                            url : url+"?rnd=" + new Date().getTime(),
                            type: "POST",
                            data: $.param(obj),
                            success: function(result) {
                                debugger;
                                dialog.close();
                                if (result.error_no == 0) {
                                    showError("删除成功！");
                                    table.ajax.reload();
                                } else {
                                    showError("删除失败:" + result.error_info);
                                }
                            },
                            error: function(result) {
                                debugger;
                                dialog.close();
                                showError("删除失败:" + result.error_info);
                            }

                        });
                    }
                },
                {
                    label: '取消',
                    action: function(dialog) {
                        dialog.close();
                    }
                }]
        });

    },
    edit :  function edit(table, url, obj) {
    	obj.operatType = "1";
    	toPage("修改用户", url, $.param(obj));
    }
}