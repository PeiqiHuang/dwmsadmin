$(function() {
    initTreeTable();
});

function initTreeTable() {
    var setting = {
        id: 'partyId',
        code: 'partyId',
        url: ctx + 'party/list',
        pageSize: 10,
        expandAll: true,
        expandColumn: "2",
        ajaxParams: {
            partyName: $(".entity-table-form").find("input[name='partyName']").val().trim(),
            source: $(".entity-table-form").find("select[name='source']").val().trim(),
            status: $(".entity-table-form").find("select[name='status']").val().trim()
        },
        columns: [{
                field: 'selectItem',
                checkbox: true
            },
            {
                title: '编号',
                field: 'partyId',
                width: '50px'
            },
            {
                title: '党支部名称',
                field: 'partyName'
            },
            {
            	title: '所在地址',
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
            	title: '来源',
            	field: 'source',
                formatter: function (value, row, index) {
                    if (value.source === 1) return '后台创建';
                    if (value.source === 2) return 'app申请入驻';
                }
            },
            {
            	title: '状态',
            	field: 'status',
                formatter: function (value, row, index) {
                    if (value.status === 1) return '<span class="badge badge-success">正常</span>';
                    if (value.status === 0) return '<span class="badge badge-danger">禁用</span>';
                }
            },
            {
                title: '创建时间',
                field: 'createTime'
            }
        ]
    };

    $MB.initTreeTable('entityTable', setting);
}

function search() {
    initTreeTable();
}

function refresh() {
    $(".entity-table-form")[0].reset();
    search();
    $MB.refreshJsTree("entityTree", createEntityTree());
}

/*function deletePartys() {
    var ids = $("#entityTable").bootstrapTreeTable("getSelections");
    var ids_arr = "";
    if (!ids.length) {
        $MB.n_warning("请勾选需要删除的党支部！");
        return;
    }
    for (var i = 0; i < ids.length; i++) {
        ids_arr += ids[i].id;
        if (i !== (ids.length - 1)) ids_arr += ",";
    }
    $MB.confirm({
        text: "确定删除选中党支部？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'party/delete', { "ids": ids_arr }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}*/

function exportEntityExcel(){
	$.post(ctx+"party/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"party/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}