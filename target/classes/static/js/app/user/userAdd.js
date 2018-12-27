var validator;
var $entityAddForm = $("#entity-add-form");

//status控件
var $status_label = $("#statusLabel");
var $status_check = $("#statusCheck");
var $status = $("input[name='status']");

function setStatus(status) {
	if (status || status == 1) {
		$status_check.prop("checked", true);
	    $status_label.html('正常');
	}
	if (!status || status == 0) {
		$status_check.prop("checked", false);
	    $status_label.html('禁用');
	}
	$status.val(status);
}

$(function () {
	$("#templateFile").attr("href", ctx + "file/党员上传模板.xls");
    validateRule();
    createPartyTree();
    
    $MB.calenders('input[name="probationaryTime"]', false, false);
    $MB.calenders('input[name="fullTime"]', false, false);
    
    //file upload
    $("input[name='file']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['jpeg', 'jpg', 'gif', 'png', 'bmp']
	});
    $("input[name='batchFile']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['xls']
	});

    $status_check.change(function () {
        var checked = $(this).is(":checked");
        var status = checked ? 1 : 0;
        setStatus(status);
    });
    
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
        getParty();
        validator = $entityAddForm.validate();
        var flag = validator.form();
        /*console.info(flag)
        console.info(validator.errorMap)*/
        if (flag) {
        	if (name === "batch") {
        		var options = {
        			url : ctx + "user/batch",
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
        	if (name === "save") {
        		var options = {
		            url : ctx + "user/add",
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
        	if (name === "update") {
        		var options = {
		            url : ctx + "user/update",
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
	$("#avatar").attr("src", "");
    setStatus(1);
    $("#entity-add-modal-title").html('新增党员');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
    setTimeout(function() {batchToggle(false)}, 500);
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
    	ignore: ".no-need",
        rules: {
            userName: {
                required: true,
                maxlength: 100
            },
            mobile: {
            	required: true,
                checkPhone: true
            },
            /*file: {
            	required: true
            },*/
            batchFile: {
            	required: true
            },
            gender: {
            	required: true
            },
            partyType: {
            	required: true
            },
            partyPosts: {
            	required: true
            },
            workStatus: {
            	required: true
            },
            probationaryTime: {
            	dateISO: true,
            	ltCurrent: true
            },
            fullTime: {
            	dateISO: true,
            	ltCurrent: true,
            	compareTime: "input[name='probationaryTime']"
            },
            status: {
            	required: true
            },
            partyId: {
            	required: true
            }
        },
        errorPlacement: function (error, element) {
            if (element.is("input[name='batchFile']") || element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
        	userName: {
                required: icon + "请输入党员名称",
                maxlength: icon + "党员名称长度少于100个字符"
            },
            mobile: {
            	required: icon + "请输入手机"
            },
            //file: icon + "请上传头像",
            batchFile: icon + "请上传文件",
        	gender: icon + "请选择性别",
        	partyType: icon + "请选择党员类型",
        	partyPosts: icon + "请选择党内职务",
        	workStatus: icon + "请选择工作状态",
        	fullTime: {
        		compareTime: icon + "转为正式党员日期要大于转为预备党员日期"
        	},
        	partyId: icon + "请选择党支部",
        	
        }
    });
}


function batchToggle(batch) {
	$("#singleAdd").prop("hidden", batch);
	$("#batchAdd").prop("hidden", !batch);
	$("#entity-add-button").attr("name", batch ? "batch" : "save");
	if (batch) {
		$("#singleAdd input").addClass("no-need");
		$("#batchAdd input").removeClass("no-need");
	} else {
		$("#singleAdd input").removeClass("no-need");
		$("#batchAdd input").addClass("no-need");
		
	}
}

function batch() {
	batchToggle(true);
	$("#entity-add").modal("show");
}