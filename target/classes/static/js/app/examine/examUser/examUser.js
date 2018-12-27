$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'examUser/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                examId: $tableForm.find("input[name='examId']").val().trim(),
                userName: $tableForm.find("input[name='userName']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
                examType: $tableForm.find("select[name='examType']").val().trim()
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
	            title: '考试人员',
	            field: 'userName'
	        },
	        {
	        	title: '考试得分',
	        	field: 'score'
	        },
	        {
	        	title: '考试用时（秒）',
	        	field: 'timeLength'
	        },
            {
            	title: '考试类型',
            	field: 'examType',
            	formatter: function (value, row, index) {
            		if (value === 1) return '正考';
            		if (value === 2) return '补考';
            	}
            },
            {
            	title: '状态',
            	field: 'status',
            	formatter: function (value, row, index) {
                    if (value === -1) return '<span class="badge badge-danger">未提交</span>';
                    if (value === 0) return '<span class="badge badge-warning">未通过</span>';
                    if (value === 1) return '<span class="badge badge-info">待判分</span>';
                    if (value === 2) return '<span class="badge badge-success">已通过</span>';
                }
            },
            {
            	title: '提交时间',
            	field: 'finishTime'
            },
            {
                title: '创建时间',
                field: 'createTime'
            },
            {
                title: '操作',
                formatter: function (value, row, index) {
                	if (row.status != -1)
                		return "<a href='javascript:openDetail(\"" + row.euId + "\",\"" + row.userName + "\")'>答卷详情</a>";
                }
            }
        ]
    };
    $MB.initTable('entityTable', setting);
}

function search() {
	$MB.refreshTable('entityTable');
}

function refresh() {
    $(".entity-table-form")[0].reset();
    search();
}

function exportEntityExcel(){
	$.post(ctx+"examUser/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"examUser/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

//答卷详情
function openDetail(entityId, userName) {
	/*var selected = $("#entityTable").bootstrapTable('getSelections');
	var selected_length = selected.length;
	if (!selected_length || selected_length > 1) {
		$MB.n_warning('请勾选一个考试人员！');
		return;
	}
	var entityId = selected[0].euId;
	var userName = selected[0].userName;*/
	var url = "examQuUser?euId=" + entityId;
	openPage(url, userName + "答卷详情");
}