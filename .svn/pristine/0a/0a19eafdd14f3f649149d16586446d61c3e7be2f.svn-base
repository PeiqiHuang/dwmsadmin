$(function() {
	initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
	var setting = {
		url : ctx + 'helpType/list',
		pageSize : 10,
		queryParams : function(params) {
			return {
				pageSize : params.limit,
				pageNum : params.offset / params.limit + 1,
				typeName : $tableForm.find("input[name='typeName']").val().trim(),
				typeStatus : $tableForm.find("select[name='typeStatus']").val().trim()
			};
		},
		columns : [ {
			checkbox : true
		},
		/*
		 * { title: '编号', field: 'courseId', width: '50px' },
		 */
		{
			title : '类型名称',
			field : 'typeName'
		}, {
			title : '状态',
			field : 'typeStatus',
			formatter : function(value, row, index) {
				if (value === 0)
					return '<span class="badge badge-warning">禁用</span>';
				if (value === 1)
					return '<span class="badge badge-success">正常</span>';
			}
		} ]
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
		$MB.n_warning('请勾选需要删除的分类！');
		return;
	}
	var ids = "";
	for (var i = 0; i < selected_length; i++) {
		ids += selected[i].typeId;
		if (i !== (selected_length - 1))
			ids += ",";
	}
	$MB.confirm({
		text : "确定删除选中的分类？",
		confirmButtonText : "确定删除"
	}, function() {
		$.post(ctx + 'helpType/delete', {
			"ids" : ids
		}, function(r) {
			if (r.code === 0) {
				$MB.n_success(r.msg);
				refresh();
			} else {
				$MB.n_danger(r.msg);
			}
		});
	});
}
