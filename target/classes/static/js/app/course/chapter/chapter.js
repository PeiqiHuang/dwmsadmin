$(function() {
    initTreeTable();
});

function initTreeTable() {
    var setting = {
        id: 'chapterId',
        code: 'chapterId',
        url: ctx + 'courseChapter/list',
        pageSize: 10,
        expandAll: true,
        expandColumn: "2",
        ajaxParams: {
        	courseId: $(".entity-table-form").find("input[name='courseId']").val().trim(),
        	chapterTitle: $(".entity-table-form").find("input[name='chapterTitle']").val().trim()
        },
        columns: [{
                field: 'selectItem',
                checkbox: true
            },
            {
                title: '序号',
                field: 'chapterNo',
                width: '50px'
            },
            {
                title: '章节名称',
                field: 'chapterTitle'
            },
            {
            	title: '章节时长（分钟）',
            	field: 'timeLength',
            	width: '150px'
            },
            {
            	title: '内容',
            	field: 'content',
            	formatter: function (value, row, index) {
            		var content = value.content;
            		return "<textarea class='form-control' rows='3' readonly>"+content+"</textarea>";
            		//if (content.length < 50) return content;
            		//else return "<a href=\"javascript:alert('" + content + "');\">" + content.substring(0, 50) + "...</a>";
            	}
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

function deleteEntitys() {
    var ids = $("#entityTable").bootstrapTreeTable("getSelections");
    var ids_arr = "";
    if (!ids.length) {
        $MB.n_warning("请勾选需要删除的章节！");
        return;
    }
    for (var i = 0; i < ids.length; i++) {
        ids_arr += ids[i].id;
        if (i !== (ids.length - 1)) ids_arr += ",";
    }
    $MB.confirm({
        text: "确定删除选中章节？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'courseChapter/delete', { "ids": ids_arr }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}