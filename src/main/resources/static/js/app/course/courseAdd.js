var validator;

var $entityAddForm = $("#entity-add-form");
var $catAddSelect = $entityAddForm.find("select[name='categoryId']");
var catAddSelect = new PartySelect($catAddSelect, "courseCat/getCategorys", "categoryId", "categoryName");

//党员多选
var $usersSelect = $entityAddForm.find("select[name='usersSelect']");
var $users = $entityAddForm.find("input[name='users']");
var usersSelect = new PartyMulSelect($usersSelect, $users, "user/getUsers", "userId", "userName");

//开关控件
var $courseType_label = $("#courseTypeLabel");
var $courseType_check = $("#courseTypeCheck");
var $courseType = $("input[name='courseType']");

function setCourseType(courseType) {
	if (courseType || courseType == 1) {
		$courseType_check.prop("checked", true);
	    $courseType_label.html('必修');
	}
	if (!courseType || courseType == 0) {
		$courseType_check.prop("checked", false);
	    $courseType_label.html('选修');
	}
	$courseType.val(courseType);
}

$(function () {
    validateRule();
    createPartyTree();
    
    $MB.calenders('input[name="endTime"]', false, false);
    
    //file upload
    $("input[name='coverImg']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['jpeg', 'jpg', 'gif', 'png', 'bmp']
	});
    $("input[name='bannerImg']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['jpeg', 'jpg', 'gif', 'png', 'bmp']
    });
    $("input[name='file']").fileinput({
    	language: "zh",
    	showUpload: false, 
    	showCaption: false, 
    	dropZoneEnabled: false,
    	allowedFileExtensions: ['txt']
    });
    
    //选党支部切换分类/党员数据
    $('#partyTree').on("select_node.jstree", function (e, data) {
    	getParty();
    	var partyId = $("input[name='partyId']").val();
    	ajaxGetVals(partyId);
    })

	$courseType_check.change(function () {
        var checked = $(this).is(":checked");
        var courseType = checked ? 1 : 0;
        setCourseType(courseType);
        //必修联动全选党员
        if (checked == 1){
        	$usersSelect.multipleSelect('checkAll');
        } else {
        	$usersSelect.multipleSelect('uncheckAll');
        }
    });
    
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
        getParty();
        //cover已经有了不上传也可以
        if(!!$("#coverImg").attr("src")) {
        	$("input[name='coverImg']").addClass("no-need");
        }
        if(!!$("#bannerImg").attr("src")) {
        	$("input[name='bannerImg']").addClass("no-need");
        }
        //file已经有了不上传也可以
        if($("#courseFile").attr("href") != "") {
        	$("input[name='file']").addClass("no-need");
        }
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
        	if (name === "save") {
        		var options = {
		            url : ctx + "course/add",
		            dataType : "json",
		            type : "post",
		            success : function (r) {
		            	closeLoading();
		            	if (r.code === 0) {
	        				closeModal();
	        				refresh();
	        				$MB.n_success(r.msg);
	        			} else $MB.n_danger(r.msg);
		            }
		        };
        		openLoading();
        		$entityAddForm.ajaxSubmit(options);
        	}
        	if (name === "update") {
        		var options = {
		            url : ctx + "course/update",
		            dataType : "json",
		            type : "post",
		            success : function (r) {
		            	closeLoading();
		            	if (r.code === 0) {
	        				closeModal();
	        				refresh();
	        				$MB.n_success(r.msg);
	        			} else $MB.n_danger(r.msg);
		            }
		        };
        		openLoading();
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
	
	setCourseType(0);
	
	$("#coverImg").attr("src", "");
	$("#bannerImg").attr("src", "");
	$("#courseFile").attr("href", "").text("");
	$("input[name='coverImg']").removeClass("no-need");
	$("input[name='bannerImg']").removeClass("no-need");
	$("input[name='file']").removeClass("no-need");
	
	editable();
	catAddSelect.clean();
	usersSelect.clean();
	
	
    $("#entity-add-modal-title").html('新增党课');
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    refreshPartyTree();
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
    	ignore: ".no-need",
        rules: {
        	courseName: {
                required: true,
                maxlength: 200
            },
            courseDesc: {
            	//required: true,
            	maxlength: 500
            },
            coverImg: {
            	required: true
            },
            bannerImg: {
            	required: true
            },
            file: {
            	required: true
            },
            chapterNum: {
            	required: true,
            	digits: true,
            	min: 1
            	
            },
            endTime: {
            	required: true,
            	gtCurrent: true
            },
            users: {
            	required: true
            },
            status: {
            	required: true
            },
            partyId: {
            	required: true
            },
            categoryId: {
            	required: true
            }
        },
        errorPlacement: function (error, element) {
        	if ($(element).attr("name") == "cover" 
        		|| $(element).attr("name") == "banner"
        		|| $(element).attr("name") == "file") {
        		error.appendTo(element.parent().parent().parent());
        	} else if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
        	courseName: {
                required: icon + "请输入名称",
                maxlength: icon + "党课名称长度少于200个字符"
            },
            courseDesc: {
            	//required: icon + "请输入党课议题",
            	maxlength: icon + "党课简介长度少于500个字符"
            },
            coverImg: {
            	required: icon + "请上传封面"
            },
            bannerImg: {
            	required: icon + "请上传顶部图片"
            },
            file: {
            	required: icon + "请上传党课文件"
            },
            chapterNum: {
            	required: icon + "请输入总时长",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            },
            endTime: {
        		required: icon + "请选择结束时间"
        	},
        	users: icon + "请选择参会党员",
        	status: icon + "请选择党课状态",
        	partyId: icon + "请选择党支部",
        	categoryId: icon + "请选择分类"
        	
        }
    });
}


function ajaxGetVals(partyId) {
	catAddSelect.set(partyId);
	usersSelect.set(partyId);
}