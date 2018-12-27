$(function() {
    var $newsTableForm = $(".news-table-form");
    var settings = {
        url: ctx + "news/list",
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                title: $newsTableForm.find("input[name='title']").val().trim(),
            };
        },
        columns: [{
                checkbox: true
            },
            {
                field: 'newsId',
                title: '新闻ID',
                width: 150
            }, {
                field: 'title',
                title: '标题'
            }, {
                field: 'status',
                title: '状态',
                formatter: function (value, row, index) {
                    if (value === 1) return '<span class="badge badge-success">已发布</span>';
                    if (value === 0) return '<span class="badge badge-warning">未发布</span>';
                    if (value === -1) return '<span class="badge badge-danger">失效</span>';
                }
            }
        ]
    };

    $MB.initTable('newsTable', settings);
});

function search() {
    $MB.refreshTable('newsTable');
}

function refresh() {
    $(".news-table-form")[0].reset();
    search();
}

function deleteNews() {
    var selected = $("#newsTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的新闻！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].newsId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的新闻？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'news/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportNewsExcel(){
	$.post(ctx+"news/excel",$(".news-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportNewsCsv(){
	$.post(ctx+"news/csv",$(".news-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}