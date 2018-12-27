$(function() {
    initTable();
    getTotalScore();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'examQu/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                examId: $tableForm.find("input[name='examId']").val().trim(),
                title: $tableForm.find("input[name='title']").val().trim(),
                quType: $tableForm.find("select[name='quType']").val().trim(),
                answers: $tableForm.find("input[name='answers']").val().trim(),
                rightKey: $tableForm.find("input[name='rightKey']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'examId',
                width: '50px'
            },*/
	        {
	            title: '试题序号',
	            field: 'quNo',
	            width: '50px'
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
	            title: '试题题目',
	            field: 'title',
            	formatter: function (value, row, index) {
            		return shortContent(value);
            	}
	        },
            {
            	title: '答案选项',
            	field: 'answers',
            	formatter: function (value, row, index) {
            		value = value.replace(new RegExp("\\|\\|\\|","gm"), ".");
            		value = value.replace(new RegExp("===","gm"), "<br>");
            		return value;
                }
            },
            {
            	title: '正确答案',
            	field: 'rightKey',
            	formatter: function (value, row, index) {
            		if (value.indexOf("===") < 0) {
            			return shortContent(value);
            		}
            		value = value.replace(new RegExp("\\|\\|\\|","gm"), ".");
            		value = value.replace(new RegExp("===","gm"), "<br>");
            		return value;
                }
            },
            {
            	title: '分数',
            	field: 'score'
            }
        ]
    };
    $MB.initTable('entityTable', setting);
}

function search() {
	$MB.refreshTable('entityTable');
	//更新totalScore
	getTotalScore();
}

function refresh() {
    $(".entity-table-form")[0].reset();
    search();
}

function deleteEntity() {
    var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的试题！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].eqId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的试题？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'examQu/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function getTotalScore() {
	var examId = $("input[name='examId']").val();
	$.get(ctx + "examQu/getTotalScore", {examId : examId}, function(r) {
		if (r.code === 0) {
			$("#totalScore").val(r.msg);
		}
	})
}