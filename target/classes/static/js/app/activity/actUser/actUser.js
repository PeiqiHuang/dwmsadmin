$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'actUser/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                actId: $tableForm.find("input[name='actId']").val().trim(),
                userName: $tableForm.find("input[name='userName']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim()
            };
        },
        columns: [/*{
	            checkbox: true
	        },*/
            /*{
                title: '编号',
                field: 'actId',
                width: '50px'
            },*/
	        {
	            title: '活动人员',
	            field: 'userName'
	        },
            {
            	title: '状态',
            	field: 'status',
            	formatter: function (value, row, index) {
                    if (value === -1) return '<span class="badge badge-danger">未确认</span>';
                    if (value === 0) return '<span class="badge badge-warning">取消报名</span>';
                    if (value === 1) return '<span class="badge badge-success">已报名</span>';
                }
            },
            {
            	title: '修改时间',
            	field: 'updateTime'
            },
            {
                title: '创建时间',
                field: 'createTime'
            },
            /*{
                title: '操作',
                formatter: function (value, row, index) {
                	if (row.status == 1)
                		return "<a href='javascript:dueConfirm(\"" + row.duId + "\")'>确认收到活动</a>";
                }
            }*/
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
	$.post(ctx+"actUser/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"actUser/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}