$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'dueUser/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                dueId: $tableForm.find("input[name='dueId']").val().trim(),
                userName: $tableForm.find("input[name='userName']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
                payWay: $tableForm.find("select[name='payWay']").val().trim()
            };
        },
        columns: [/*{
	            checkbox: true
	        },*/
            /*{
                title: '编号',
                field: 'dueId',
                width: '50px'
            },*/
	        {
	            title: '缴费人员',
	            field: 'userName'
	        },
            {
                title: '缴费金额',
                field: 'due',
            	formatter: function (value, row, index) {
            		if (row.status != 0) {
            			return value;
            		}
            	}
            },
            {
            	title: '缴费方式',
            	field: 'payWay',
            	formatter: function (value, row, index) {
            		if (value === 1) return '正考';
            		if (value === 2) return '补考';
            	}
            },
            {
            	title: '状态',
            	field: 'status',
            	formatter: function (value, row, index) {
                    if (value === 0) return '<span class="badge badge-warning">未缴费</span>';
                    if (value === 1) return '<span class="badge badge-info">已缴费</span>';
                    if (value === 2) return '<span class="badge badge-success">管理员确认</span>';
                }
            },
            {
                title: '创建时间',
                field: 'createTime'
            },
            {
            	title: '缴费时间',
            	field: 'dueTime'
            },
            {
	        	title: '凭证图片',
	        	field: 'imgPath',
	        	formatter: function (value, row, index) {
	        		if (!value) return '';
	        		return "<a href='javascript:showBigImg(\"" + value + "\");'><img src='" + value + "' class='img-responsive' style='height:80px;max-width:200px;'></a>";
	        	}
	        },
            {
                title: '操作',
                formatter: function (value, row, index) {
                	if (row.status == 1)
                		return "<a href='javascript:dueConfirm(\"" + row.duId + "\")'>确认收到缴费</a>";
                }
            }
        ]
    };
    $MB.initTable('entityTable', setting);
}

function dueConfirm(duId) {
	$MB.confirm({
        text: "确定已缴费？",
        confirmButtonText: "确定"
    }, function() {
        $.post(ctx + 'dueUser/confirm', { "duId": duId }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function showBigImg(src) {
	$("#imgInModal").attr("src", src);
	$("#imgModal").modal("show");
}

function search() {
	$MB.refreshTable('entityTable');
}

function refresh() {
    $(".entity-table-form")[0].reset();
    search();
}

function exportEntityExcel(){
	$.post(ctx+"dueUser/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"dueUser/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}