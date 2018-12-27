$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'courseUser/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                courseId: $tableForm.find("input[name='courseId']").val().trim(),
                userName: $tableForm.find("input[name='userName']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim()
            };
        },
        columns: [/*{
	            checkbox: true
	        },*/
            /*{
                title: '编号',
                field: 'mtgId',
                width: '50px'
            },*/
            {
                title: '学习党员',
                field: 'userName'
            },
            {
            	title: '学习时长（分钟）',
            	field: 'timeLength'
            },
            {
            	title: '状态',
            	field: 'status',
            	formatter: function (value, row, index) {
                    if (value === -1) return '<span class="badge badge-warning">未生效</span>';
                    if (value === 0) return '<span class="badge badge-info">已生效</span>';
                    if (value === 1) return '<span class="badge badge-success">已完成</span>';
                }
            },
            {
            	title: '进度',
            	field: 'progress',
            	formatter: function (value, row, index) {
            		return value + "%";
                }
            },
            {
            	title: '剩余课时',
            	field: 'restChapter'
            },
            {
            	title: '完成时间',
            	field: 'finishTime'
            },
            {
                title: '创建时间',
                field: 'createTime'
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
	$.post(ctx+"courseUser/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"courseUser/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}