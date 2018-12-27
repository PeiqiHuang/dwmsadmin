var validator;
var $entityAddForm = $("#entity-add-form");
var $adKeyAdd = $entityAddForm.find("select[name='adKey']");
var adKeyAdd = new Select($adKeyAdd, "adKey/getAdKeys", "adKey", "keyName");
//status控件
var $status_label = $("#statusLabel");
var $status_check = $("#statusCheck");
var $status = $("input[name='status']");

function setStatus(status) {
	if (status || status == 1) {
		$status_check.prop("checked", true);
	    $status_label.html('启用');
	}
	if (!status || status == 0) {
		$status_check.prop("checked", false);
	    $status_label.html('禁用');
	}
	$status.val(status);
}

$(function () {
    validateRule();
    createPartyTree();
    adKeyAdd.set();
    
    $status_check.change(function () {
        var checked = $(this).is(":checked");
        var status = checked ? 1 : 0;
        setStatus(status);
    });
    
    //file upload
    $("input[name='file']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['jpeg', 'jpg', 'gif', 'png', 'bmp']
	});
    
    $MB.calenders('input[name="beginTime"]', false, true);
    $MB.calenders('input[name="endTime"]', false, true);
    
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
        getParty();
        //cover已经有了不上传也可以
        if(!!$("#cover").attr("src")) {
        	$("input[name='file']").addClass("no-need");
        }
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
        	if (name === "save") {
        		console.info("save")
        		var options = {
		            url : ctx + "ad/add",
		            dataType : "json",
		            type : "post",
		            success : function (r) {
		            	if (r.code === 0) {
	        				closeModal();
	        				refresh();
	        				$MB.n_success(r.msg);
	        			} else $MB.n_danger(r.msg);
		            }
		        };
        		$entityAddForm.ajaxSubmit(options);
        		console.info("ajaxSubmit")
        	}
        	if (name === "update") {
        		var options = {
		            url : ctx + "ad/update",
		            dataType : "json",
		            type : "post",
		            success : function (r) {
		            	if (r.code === 0) {
	        				closeModal();
	        				refresh();
	        				$MB.n_success(r.msg);
	        			} else $MB.n_danger(r.msg);
		            }
		        };
        		$entityAddForm.ajaxSubmit(options);
        	}
        }
    });

    $("#entity-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
	$("#entity-add-button").attr("name", "save");
	setStatus(1);
	$("input[name='file']").attr("src", "");
	$("input[name='file']").removeClass("no-need");
	
    $("#entity-add-modal-title").html('新增广告');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
    	ignore: ".no-need",
        rules: {
        	name: {
                required: true,
                maxlength: 30
            },
            summary: {
            	maxlength: 200
            },
            file: {
            	required: true
            },
            action: {
            	maxlength: 200
            },
            weight: {
            	required: true,
            	digits: true,
            	min: 0
            },
            adKey: {
            	required: true
            },
            beginTime: {
            	required: true,
            	gtCurrent: true
            },
            endTime: {
            	required: true,
            	gtCurrent: true,
            	compareTime: "input[name='beginTime']"
            },
            status: {
            	required: true
            },partyId: {
            	required: true
            }
        },
        errorPlacement: function (error, element) {
        	if ($(element).attr("name") == "file") {
        		error.appendTo(element.parent().parent().parent());
        	} else if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
        	name: {
                required: icon + "请输入广告名称",
                maxlength: icon + "广告标题长度少于30个字符"
            },
            summary: {
            	maxlength: icon + "广告简介长度少于200个字符"
            },
            file: {
            	required: icon + "请上传封面"
            },
            action: {
            	maxlength: icon + "广告动作长度少于200个字符"
            },
            weight: {
            	required: icon + "请输入权重",
            	digits: icon + "请输入非负整数",
            	min: icon + "请输入非负整数"
            },
            adKey: icon + "请选择广告位置",
            beginTime: {
            	required: icon + "请选择开始时间"
            },
        	endTime: {
        		required: icon + "请选择结束时间"
        	},
        	status: icon + "请选择状态",
        	partyId: icon + "请选择党支部"
        	
        }
    });
}

/*function statusHidden(hidden) {
	$("#status").prop("hidden", hidden);
}*/