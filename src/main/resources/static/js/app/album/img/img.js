var sortable = null;
$(function() {
	init();
	
	//瀑布流 排序冲突
	//$('.masonry').imagesLoaded(function() {
		/*$('.masonry').masonry({
			// options
			itemSelector: '.masonry-item',
			columnWidth: 350
		});*/
	//});
	
	/*$(document).on("click", "img", function() {
		if ($(this).hasClass("img-selected")) {
			$(this).removeClass("img-selected")
		} else {
			$(this).addClass("img-selected")
		}
	})*/
});

function deleteImg() {
	var ids = [];
	$("img.img-selected").each(function(){
		ids.push($(this).data("id"));
	})
	if (ids.length <= 0) {
		$MB.n_danger("请选择要删除的图片！");
		return;
	}
		
	$MB.confirm({
        text: "确定删除选中的图片？",
        confirmButtonText: "删除"
    }, function() {
    	openLoading();
        $.post(ctx + 'albumImg/delete', { "ids": ids.join(",") }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function setCover() {
	var ids = [];
	$("img.img-selected").each(function(){
		ids.push($(this).data("id"));
	})
	if (ids.length != 1) {
		$MB.n_danger("请选择一张图片设为封面！");
		return;
	}
	
	openLoading();
	$.post(ctx + 'albumImg/cover', { "aiId": ids[0] }, function(r) {
		if (r.code === 0) {
			$MB.n_success("设置封面成功！");
			$("#cover").val(r.msg);
			refresh();
		} else {
			$MB.n_danger(r.msg);
		}
	});
}

function init() {
	var $copy = $("#imgTpl").clone().removeAttr("id").prop("hidden", false);
	$.post(ctx + 'albumImg/list', {albumId: $("#albumId").val() }, function(r) {
		if(null != sortable)  {
			sortable.destroy();
			sortable = null;
		}
        if (r.code === 0) {
        	$("#imgTpl").siblings().remove();
        	var cover = $("#cover").val();
        	console.info(cover)
            $.each(r.msg, function(){
            	var $item = $copy.clone();
            	$item.find("img").data("id", this.aiId).attr("src", this.imgPath);
            	if (this.imgPath == cover) {
            		$item.prepend("<span class='actions__item zmdi zmdi-collection-image cover' title='封面图片'/>");
            	}
            	$item.appendTo("#sortable");
            })
            //拖拽排序
        	var el = document.getElementById('sortable');
        	sortable = Sortable.create(el, {
        		animation: 150,
        		onChoose: function (evt) {
        			$img = $(evt.item).find("img").first();
        			if ($img.hasClass("img-selected")) {
        				$img.removeClass("img-selected")
        			} else {
        				$img.addClass("img-selected")
        			}
        		},
        		onUpdate: function (evt){
        			$img = $("#sortable img:visible");
        			var aiNo = evt.newIndex;
        			var aiId = $img.eq(aiNo-1).data("id");
        	    	$.post(
            			ctx + "albumImg/move", 
            			{aiNo: aiNo, aiId: aiId, albumId: $("#albumId").val()}, 
            			function(r) {
        					if (r.code === 0) {
        		                $MB.n_success(r.msg);
        		                refresh();
        		            } else {
        		                $MB.n_danger(r.msg);
        		            }
            			}	
        	    	)
        		}
        	});
        } else {
            $MB.n_danger(r.msg);
        }
        closeLoading();
    });
	
}
