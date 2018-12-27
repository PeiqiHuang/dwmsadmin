var allPartyIdArray = [];
function setIdArray(data) {
	$.each(data.children, function() {
    	allPartyIdArray.push(this.id);
		setIdArray(this);//递归
    });
}
function createPartyTree() {
	if(isAdmin) {
	    $.post(ctx + "party/tree", {}, function (r) {
	        if (r.code === 0) {
	            var data = r.msg;
	            setIdArray(data);
	            $('#partyTree').jstree({
	                "core": {
	                    'data': data.children,
	                    'multiple': false
	                },
	                "state": {
	                    "disabled": true
	                },
	                "checkbox": {
	                    "three_state": false
	                },
	                "plugins": ["wholerow", "checkbox"]
	            });
	        } else {
	            $MB.n_danger(r.msg);
	        }
	    })
	}
}

function getParty() {
	if(isAdmin) {
		var ref = $('#partyTree').jstree(true);
		var ids = ref.get_checked();
		$("input[name='partyId']").val(ids[0]);
	}
}

function refreshPartyTree() {
	if(isAdmin) {
		$MB.refreshJsTree("partyTree", createPartyTree());
	}
}

function initPartySearch() {
	//console.info("isAdmin = " + isAdmin)
	if(isAdmin) {
		getPartyAjax();
	}
}

function getPartyAjax() {
	$.get(ctx + "party/all", function (partys) {
        $.each(partys, function(){
        	var $party = $("select[name='partyId']");
        	$party.append("<option value='"+this.partyId+"'>"+this.partyName+"</option>");
        	
        })
    });
}

function partyTreeSelect(id, disable) {
	if(isAdmin) {
		var $partyTree = $('#partyTree');
		$partyTree.jstree('select_node', id, true);
		
		if (disable) {// all node disable
			$partyTree.jstree('disable_node', allPartyIdArray);
		}
	} else {
		$("input[name='partyId']").val(id);
	}
}