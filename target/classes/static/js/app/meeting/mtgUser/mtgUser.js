$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'mtgUser/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                mtgId: $tableForm.find("input[name='mtgId']").val().trim(),
                userName: $tableForm.find("input[name='userName']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
                signWay: $tableForm.find("select[name='signWay']").val().trim()
                /*readSummary: $tableForm.find("select[name='readSummary']").val().trim()*/
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
                title: '参会人员',
                field: 'userName'
            },
            {
            	title: '签到情况',
            	field: 'status',
            	formatter: function (value, row, index) {
                    if (value === -9) return '<span class="badge badge-danger">请假</span>';
                    if (value === -1) return '<span class="badge badge-warning">未确认</span>';
                    if (value === 0) return '<span class="badge badge-info">已确认</span>';
                    if (value === 1) return '<span class="badge badge-success">已签到</span>';
                }
            },
            {
            	title: '签到方式',
            	field: 'signWay',
            	formatter: function (value, row, index) {
            		if (value === 1) return '自签';
            		if (value === 2) return '代签';
            		if (value === 3) return '补签';
            	}
            },
            {
                title: '签到时间',
                field: 'endTime'
            },
            {
            	title: '请假理由',
            	field: 'offReason'
            },
            /*{
            	title: '查看会议纪要',
            	field: 'readSummary',
            	formatter: function (value, row, index) {
            		if (value === 0) return '<span class="badge badge-warning">禁止</span>';
            		if (value === 1) return '<span class="badge badge-success">允许</span>';
            	}
            },*/
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
	$.post(ctx+"mtgUser/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"mtgUser/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}