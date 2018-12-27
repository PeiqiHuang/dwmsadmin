function updateEntity() {
    var selected = $("#entityTable").bootstrapTreeTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的章节！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个章节！');
        return;
    }
    var entityId = selected[0].id;
    $.post(ctx + "courseChapter/getChapter", {"chapterId": entityId}, function (r) {
        if (r.code === 0) {
            var $form = $('#entity-add');
            var $entityTree = $('#entityTree');
            $form.modal();
            var entity = r.msg;
            $("#entity-add-modal-title").html('修改章节');
            $form.find("input[name='chapterTitle']").val(entity.chapterTitle);
            $form.find("input[name='chapterId']").val(entity.chapterId);
            $form.find("input[name='chapterNo']").val(entity.chapterNo);
            $form.find("textarea[name='content']").text(entity.content);
            $form.find("input[name='timeLength']").val(entity.timeLength);
            
            setType(entity.chapterType);
            //类型不能修改
            $type.parent().prop("hidden", true);
            //类型是节才能选择上一级
            $("#parent").prop("hidden", entity.chapterType == 1);
            $entityTree.jstree('select_node', entity.parentId, true);
            $entityTree.jstree('disable_node', entity.chapterId);
            $("#entity-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}