var $tableForm = $(".entity-table-form");
var $partySelect = $tableForm.find("select[name='partyId']");
var $catSelect = $tableForm.find("select[name='categoryId']");
var catSelect = new PartySelect($catSelect, "courseCat/getCategorys", "categoryId", "categoryName");

$(function() {
    initTable();
    initPartySearch();
    
    catSelect.set();
    $partySelect.change(function(){
    	catSelect.set(this.value);
    });
});

function initTable() {
	var $party = $tableForm.find("select[name='partyId']");
    var setting = {
        url: ctx + 'course/list',
        pageSize: 10,
        queryParams: function(params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                partyId: $party[0] == undefined ? '' : $party.val().trim(),
                courseName: $tableForm.find("input[name='courseName']").val().trim(),
                timeField: $tableForm.find("input[name='timeField']").val().trim(),
                status: $tableForm.find("select[name='status']").val().trim(),
                categoryId: $tableForm.find("select[name='categoryId']").val().trim()
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
	        	title: '党课封面',
	        	field: 'cover',
	        	formatter: function (value, row, index) {
	        		if (!value) return '';
	        		return "<img src='" + value + "' class='img-responsive' style='height:80px;max-width:200px;'>";
	        	}
	        },
            {
                title: '党课主题',
                field: 'courseName',
                formatter: function (value, row, index) {
            		if (!row.filePath) return value;
            		return "<a href='" + row.filePath + "' target='_blank'>"+value+"</a>";
            	}
            },
            /*{
            	title: '党课简介',
            	field: 'courseDesc'
            },*/
            {
            	title: '分类',
            	field: 'categoryName'
            },
            {
            	title: '总课时',
            	field: 'chapterNum'
            },
            {
            	title: '总时长（分钟）',
            	field: 'timeLength'
            },
            {
            	title: '类型',
            	field: 'courseType',
            	formatter: function (value, row, index) {
            		if (value === 0) return '<span class="badge badge-warning">选修</span>';
            		if (value === 1) return '<span class="badge badge-success">必修</span>';
            	}
            },
            {
            	title: '状态',
            	field: 'status',
                formatter: function (value, row, index) {
                	if (value === -9) return '<span class="badge badge-danger">已删除</span>';
                    if (value === -1) return '<span class="badge badge-danger">已取消</span>';
                    if (value === 0) return '<span class="badge badge-warning">待发布</span>';
                    if (value === 1) return '<span class="badge badge-success">已发布</span>';
                }
            },
            {
                title: '结束时间',
                field: 'endTime'
            },
            {
                title: '创建时间',
                field: 'createTime'
            }
        ]
    };
    $MB.initTable('entityTable', setting);
    $MB.calenders('input[name="timeField"]', true, false);
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
        $MB.n_warning('请勾选需要删除的党课！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].courseId;
        if (i !== (selected_length - 1)) ids += ",";
    }
    $MB.confirm({
        text: "确定删除选中的党课？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'course/delete', { "ids": ids }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportEntityExcel(){
	$.post(ctx+"course/excel",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportEntityCsv(){
	$.post(ctx+"course/csv",$(".entity-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function openCourseUser() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length || selected_length > 1) {
        $MB.n_warning('请勾选一个党课！');
        return;
    }
    var entityId = selected[0].courseId;
    var courseName = selected[0].courseName;
	var url = "courseUser?courseId=" + entityId;
	openPage(url, courseName + "统计");
}

function openCourseChapter() {
	var selected = $("#entityTable").bootstrapTable('getSelections');
	var selected_length = selected.length;
	if (!selected_length || selected_length > 1) {
		$MB.n_warning('请勾选一个党课！');
		return;
	}
	var entityId = selected[0].courseId;
	var courseName = selected[0].courseName;
	var url = "courseChapter?courseId=" + entityId;
	openPage(url, courseName + "章节");
}

function openCategory() {
	var url = "courseCat";
	openPage(url, "微党课分类");
}

/*function setCatSelect(partyId, $select) {
	url = "courseCat/getCategorys";
	param = {partyId : partyId};
    $.post(ctx + url, param, function (r) {
        if (r.code === 0) {
            var data = r.msg;
            cleanCatSelect($select);
        	$.each(data, function(){
        		$select.append("<option value='"+this.categoryId+"'>"+this.categoryName+"</option>");
        	})
        	if (!!catSelectVal) {
        		$select.val(catSelectVal);
        	}
        	catSelectVal = null;
        } else {
            $MB.n_danger(r.msg);
        }
    })
}

function cleanCatSelect($select) {
	$select.children().first().siblings().remove();
}*/