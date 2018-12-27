$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
		id: 'applyId',
        url: ctx + 'apply/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyName: $tableForm.find("input[name='partyName']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
            };
        },
        columns: [{
	            checkbox: true
	        },
            {
                title: '编号',
                field: 'applyId',
                width: '50px'
            },
            {
                title: '党组织名称',
                field: 'partyName'
            },
            {
            	title: '党组织人数',
            	field: 'memNum'
            },
            {
            	title: '党组织地址',
            	field: 'address'
            },
            {
            	title: '联系人',
            	field: 'contract'
            },
            {
            	title: '联系电话',
            	field: 'mobile'
            },
            {
            	title: '邮箱',
            	field: 'email'
            },
            {
            	title: '状态',
            	field: 'status',
                formatter: function (value, row, index) {
                    if (value === 1) return '<span class="badge badge-success">审核通过</span>';
                    if (value === 0) return '<span class="badge badge-warning">待审核</span>';
                    if (value === -1) return '<span class="badge badge-danger">审核失败</span>';
                }
            },
            {
            	title: '党支部编号',
            	field: 'partyId'
            },
            {
                title: '申请时间',
                field: 'createTime'
            }
        ]
    };

    $MB.initTable('entityTable', setting);
}

function search() {
	//initTable();
	$MB.refreshTable('entityTable');
}

function refresh() {
    $(".entity-table-form")[0].reset();
    search();
}


function exportEntityExcel(){
	$.post(ctx+"apply/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"apply/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}