var validator;
var $entityAddForm = $("#entity-add-form");
var firstSearch = true;
$(function () {
    validateRule();
    
    hiddenQu(true);
    
	//搜索
    $("#searchQu").click(function(){
    	if ($("#pattern").val().trim() != "") {
    		if (firstSearch) {
    			firstSearch = false;
	    		initSearchTable();
    		} else {
    			reSearch();
    		}
    	}
    });
    
    $("#entity-add .btn-save").click(function () {
    	var name = $(this).attr("name");
    	// 验证试题选中
    	var selected = $("#searchTable").bootstrapTable('getSelections');
    	var selected_length = selected.length;
    	var selectedQuId = selected[0].quId;
    	if (!!selectedQuId) {
    		$entityAddForm.find("input[name='quId']").val(selectedQuId);
    	} else if (name === "save") {
    		$MB.n_danger("请搜索并选择试题");
    		return;
    	}
    	
        validator = $entityAddForm.validate();
        var flag = validator.form();
        if (flag) {
        	if (name === "save") {
	            $.post(ctx + "examQu/add", $entityAddForm.serialize(), function (r) {
	                if (r.code === 0) {
	                    closeModal();
	                    refresh();
	                    $MB.n_success(r.msg);
	                } else $MB.n_danger(r.msg);
	            });
        	}
        	if (name === "update") {
        		$.post(ctx + "examQu/update", $entityAddForm.serialize(), function (r) {
        			if (r.code === 0) {
        				closeModal();
        				refresh();
        				$MB.n_success(r.msg);
        			} else $MB.n_danger(r.msg);
        		});
        	}
        }
    });

    $("#entity-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
	$("#entity-add-button").attr("name", "save");
    $("#entity-add-modal-title").html('新增试题');
    //搜索清空
    cleanSearch();
    hiddenQu(true);
    
    validator.resetForm();
    $MB.closeAndRestModal("entity-add");
    
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $entityAddForm.validate({
        rules: {
        	quId: {
                required: true
            },
            score: {
            	required: true,
            	digits: true,
            	min: 1
            },
            quNo: {
            	required: true,
            	digits: true,
            	min: 1
            }
        },
        errorPlacement: function (error, element) {
            if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
        	quId: {
                required: icon + "请搜索并选择试题"
            },
            score: {
            	required: icon + "请输入试题分数",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            },
            quNo: {
            	required: icon + "请输入试题序号",
            	digits: icon + "请输入整数",
            	min: icon + "请输入大于0的整数"
            }
        }
    });
}

function initSearchTable() {
	var $tableForm = $entityAddForm;
    var setting = {
        url: ctx + 'examQu/searchQu',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                examId: $tableForm.find("input[name='examId']").val().trim(),
                title: $tableForm.find("input[id='pattern']").val().trim()
                /*quType: $tableForm.find("select[name='quType']").val().trim()*/
            };
        },
        columns: [{
	            radio: true
	        },
	        {
	            title: '试题题目',
	            field: 'title'
	        },
            {
            	title: '题目类型',
            	field: 'quType',
            	formatter: function (value, row, index) {
            		if (value === 1) return '单选题';
            		if (value === 2) return '多选题';
            		if (value === 3) return '填空题';
            		if (value === 4) return '简答题';
            	}
            },
            {
            	title: '答案选项',
            	field: 'answers'
            },
            {
            	title: '正确答案',
            	field: 'rightKey'
            }
        ]
    };
    $MB.initTable('searchTable', setting);
}

function reSearch() {
	$MB.refreshTable('searchTable');
}

function cleanSearch() {
	$MB.destroyTable("searchTable");
	firstSearch = true;
}

function hiddenQu(hidden) {
	var items = ['title','answers','rightKey','quType'];
	$.each(items, function() {
		$entityAddForm.find("input[id='"+this+"']").parents(".row").prop("hidden", hidden);
	})
}