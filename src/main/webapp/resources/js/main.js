KPS.util = KPS.util || {};
KPS.modal = KPS.modal || {};
KPS.data = KPS.data || {};
KPS.data.locations = KPS.data.locations || {};

function performLogin(siteRoot,role){
	$.post(siteRoot+"/login", {role: role}, function(data) {
		KPS.util.redirect(siteRoot+"/dashboard");
	});
}

(function (cls, $, undefined) {
	cls.redirect = function(url) {
		window.location = url;
	};

	cls.submitForm = function(form, url, callback) {
		var values = {};
		$(form).children("input, select").each(function(index, child) {
			values[child.name] = child.value;
		});
		$.post(url, values, function(data) {
			callback(data);
		});
	};
} (KPS.util, jQuery));

function show(obj){
	alert(JSON.stringify(obj, null, "\t"));
}

(function (cls, $, undefined) { // TODO: setters private and record configuration?
	// TODO: entirely js?
//	var $el; // TODO: use
	
	cls.setTitle = function(title) {
		$("#emptyLayoutModalTitle").html(title);
	};

	cls.setOkButtonTitle = function(name) {
		$("#emptyLayoutModalSubmit").html(name);
	};
	
	cls.setOkButtonAction = function(action) {
		$("#emptyLayoutModalSubmit").unbind();
		$("#emptyLayoutModalSubmit").click(function (){
			action();
		});
	};

	/**
	 * @param action false = no action, true = dismiss, function = action, anything else = no action
	 */
	cls.setCancelButtonAction = function(action) {
		$("#emptyLayoutModalCancel").unbind();
		
		if(action === true) {
			$("#emptyLayoutModalCancel").click(function () {
				cls.hide();
			});
		}
		else if(typeof action === 'function') {
			$("#emptyLayoutModalCancel").click(action);
		}
	};

	cls.show = function() {
		$('#emptyLayoutModal').modal('show');
	};

	cls.hide = function() {
		$('#emptyLayoutModal').modal('hide');
	};
	
//	cls.configure = function(configuration) {
//		this.setTitle(configuration.title);
//		this.setOkButtonTitle(configuration.okTitle);
//		this.setOkButtonAction(configuration.okAction);
//		this.setCancelAction(consfiguration.cancelAction);
//	};
	
	cls.load = function(url, callback) {
		$("#emptyLayoutModalBody").load(url, {}, callback);
	};
} (KPS.modal, jQuery));

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

(function (cls, $, undefined)  {
	var locationList = [];
	var locationNames = [];
	var locationDataDirty = true;
	
	cls.setNeedsUpdate = function() {
		locationDataDirty = true;
	};

	cls.load = function(callback) {
		if(locationDataDirty) {
			$.get(KPS.siteRoot+"/event/location?list&format=json", function(data) {
				response = eval(data);
				names = [];
				
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
	};

	cls.setupPortEntryTypeahead = function(iterator) {
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
	};

	cls.exists = function(name) {
		for(var n=0;n<locationList.length;n++) {
			if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
				return true;
			}
		}
		return false;
	};

	cls.isInternational = function(name) {
		for(var n=0;n<locationList.length;n++) {
			if(locationList[n].name.toLowerCase() == name.toLowerCase()) {
				return locationList[n].international;
			}
		}
		return undefined;
	};
}(KPS.data.locations, jQuery));