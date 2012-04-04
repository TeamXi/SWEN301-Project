var locationList;
var locationNames;

function performLogin(siteRoot,role){
	$.post(siteRoot+"/login", {role: role}, function(data) {
		redirect(siteRoot+"/dashboard");
	});
}

function redirect(url) {
	window.location = url;
}

function submitForm(form, url, callback) {
	var values = {};
	$(form).children("input, select").each(function(index, child) {
		values[child.name] = child.value;
	});
	$.post(url, values, function(data) {
		callback(data);
	});
}

function show(obj){
	alert(JSON.stringify(obj, null, "\t"));
}

$(document).ready(function() {
	var checkWindowHash = function () {
		$(".switch-container > *").hide();
		$(".switch-container > *[data-window-hash='"+window.location.hash.substring(1)+"']").show();
	};
	
	$(window).bind('hashchange', function() {
		checkWindowHash();
	});
	
	checkWindowHash();
	
	$('#emptyLayoutModal').modal({show:false, keyboard:true, backdrop: 'static'});

});

function setEmptyModalTitle(title) {
	$("#emptyLayoutModalTitle").html(title);
}

function setEmptyModalOkButton(name, callback) {
	$("#emptyLayoutModalSubmit").html(name);
	$("#emptyLayoutModalSubmit").unbind();
	$("#emptyLayoutModalSubmit").click(function (){
		callback();
	});
}

/**
 * @param action false = no action, true = dismiss, function = action, anything else = no action
 */
function setEmptyModalCancelButton(action) {
	$("#emptyLayoutModalCancel").unbind();
	
	if(action === true) {
		$("#emptyLayoutModalCancel").click(function () {
			hideEmptyModal();
		});
	}
	else if(typeof action === 'function') {
		$("#emptyLayoutModalCancel").click(action);
	}
}

function showEmptyModal() { // TODO? all use this? change to object wraper?
	$('#emptyLayoutModal').modal('show');
}

function hideEmptyModal() {
	$('#emptyLayoutModal').modal('hide');
}

function configureMailModal() {
	setEmptyModalTitle("New mail delivery");
	setEmptyModalOkButton("Submit", createNewMail);
	setEmptyModalCancelButton(true);
	$("#emptyModalSuccessMessage").html("Mail added!");
}

function showMailModal(path){
	$("#emptyLayoutModalBody").load("/kpsmart/event/mail",{},function(){
		configureMailModal();
		showEmptyModal();
	});
}

var locationDataDirty = true;

function setNeedsLocationDataUpdate() {
	locationDataDirty = true;
}

function initilizeLocationData(callback) {
	if(locationDataDirty) {
		$.get(kSiteRoot+"/event/location?list&format=json", function(data) {
			response = eval(data);
			names = new Array();
			
			for(var n=0;n<response.length;n++) {
				var location = response[n];
				names.push(location.name);
			}
			
			locationList = response;
			locationNames = names;
			
			locationDataDirty = false;
			
			callback();
		});
	}
	else {
		callback();
	}
}

function initilizePortEntryTypeahead(iterator) {
	$(".portEntry").each(function(index, child) { // TODO: ????
		if(child.typeahead) { // Update
			child.typeahead.source = locationNames;
		}
		else { // New
			$(child).typeahead({source: locationNames});
			if(iterator) {
				iterator(child);
			}
		}
	});
}

function isValidLocation(name) {
	for(var n=0;n<locationList.length;n++) {
		if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
			return true;
		}
	}
	return false;
}

function isLocationInternational(name) {
	for(var n=0;n<locationList.length;n++) {
		if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
			return locationList[n].international;
		}
	}
	return undefined;
}