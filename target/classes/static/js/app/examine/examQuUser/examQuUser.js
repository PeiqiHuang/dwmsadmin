$(function() {
    initTable();
});

function initTable() {
	var $tableForm = $(".entity-table-form");
    var setting = {
        url: ctx + 'examQuUser/list',
        pagination: false,
        //pageSize: 10,
        queryParams: function(params) {
            return {
                //pageSize: params.limit,
                //pageNum: params.offset / params.limit + 1,
                euId: $tableForm.find("input[name='euId']").val().trim(),
                title: $tableForm.find("input[name='title']").val().trim(),
                quType: $tableForm.find("select[name='quType']").val().trim(),
                answers: $tableForm.find("input[name='answers']").val().trim(),
                rightKey: $tableForm.find("input[name='rightKey']").val().trim()
            };
        },
        columns: [/*{
	            checkbox: true
	        },*/
            /*{
                title: '编号',
                field: 'examId',
                width: '50px'
            },*/
	        {
	            title: '试题序号',
	            field: 'quNo',
	            width: '50px'
	        },
	        {
	        	title: '题目类型',
	        	field: 'quType',
	        	formatter: function (value, row, index) {
	        		if (value === 1) return '单选题';
	        		if (value === 2) return '多选题';
	        		if (value === 3) return '填空题';
	        		if (value === 4) return '简答题';
	        	}
	        },
	        {
	            title: '试题题目',
	            field: 'title'
	        },
            {
            	title: '答案选项',
            	field: 'answers'
            },
            {
            	title: '正确答案',
            	field: 'rightKey'
            },
            {
            	title: '题目分数',
            	field: 'score'
            },
            {
            	title: '回答',
            	field: 'answer',
            	formatter: function (value, row, index) {
            		//if (value != row.rightKey)
        			if (row.status == -1)//回答错误显示红色
            			return "<span style='color:red;'>"+value+"</span>";
            		else 
            			return value;
	        	}
            },
            {
            	title: '题目得分',
            	field: 'gotScore'
            }, 
            {
                title: '操作',
                formatter: function (value, row, index) {
                	if (row.quType == 4)
                		return "<a href='javascript:judgeScore(\"" + row.equId + "\",\"" + row.score + "\",\"" + row.title + "\",\"" + row.answer + "\",\"" + row.rightKey  + "\",\"" + row.score + "\")'>判分</a>";
                }
            }
        ]
    };
    $MB.initTable('entityTable', setting);
}

//简答题判分
function judgeScore(equId, score, title, answer, rightKey, gotScore) {
	$("input[name='equId']").val(equId);
	$("#score").val(score);
	$("#title").val(title);
	$("#answer").val(answer);
	$("#rightKey").val(rightKey);
	$("input[name='score']").val(gotScore);
	
	$("#entity-add").modal();
}

function search() {
	$MB.refreshTable('entityTable');
}

function refresh() {
    $(".entity-table-form")[0].reset();
    search();
}