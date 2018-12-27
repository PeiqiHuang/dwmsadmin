$(function() {
    initTable();
    initPartySearch();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
        url: ctx + 'wishTpl/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                wishText: $tableForm.find("input[name='wishText']").val().trim()
            };
        },
        columns: [{
	            checkbox: true
	        },
            /*{
                title: '编号',
                field: 'courseId',
                width: '50px'
            },*/
            {
            	title: '模板内容',
            	field: 'wishText',
            	formatter: function (value, row, index) {
            		return "<textarea class='form-control' rows='2' readonly>"+value+"</textarea>";
            	}
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

function deleteEntity() {
    var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的模板！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].tplId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的模板？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'wishTpl/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}
