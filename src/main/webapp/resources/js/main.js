KPS.util = KPS.util || {};
KPS.modal = KPS.modal || {};
KPS.data = KPS.data || {};
KPS.data.locations = KPS.data.locations || {};

function performLogin(siteRoot,role){
	$.post(siteRoot+"/login", {role: role}, function(data) {
		KPS.util.redirect(siteRoot+"/dashboard");
	});
}

// KPS.util
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

// KPS.carrousel object
(function (pack, $, undefined) {
	pack.carrousel = function() {
		this.el = document.createElement('div');
		this.$el = $(this.el);
		this.$el.addClass('kps-carrousel');
		this.body = document.createElement('div');
		this.el.appendChild(this.body);
		this.$body = $(this.body);
		this.$body.addClass('kps-carrousel-body');
		
		this.setClips(true);
		this.width = 200;
		this.spacing = 20;
		this.currentPage = 0;
	};
	
	pack.carrousel.prototype.setClips = function(clips) {
		if(clips) {
			this.$el.addClass('clip');
		}
		else {
			this.$el.removeClass('clip');
		}
	};
	
	pack.carrousel.prototype.layout = function() {
		this.$body.find('> div').css({
			width: this.width+'px',
			marginRight: this.spacing+'px'
		});
	};
	
	pack.carrousel.prototype.show = function(num) {
		if(this.currentPage != num) {
			this.$body.animate({marginLeft:(num*-(this.width+this.spacing))+"px"},400);
		}
		this.currentPage = num;
	};
	
	pack.carrousel.prototype.next = function() {
		this.show(this.currentPage+1);
	};
	
	pack.carrousel.prototype.previous = function() {
		this.show(this.currentPage-1);
	};
	
	pack.carrousel.prototype.load = function(url, callback) {
		var self = this;
		self.$body.load(url, {}, function (responseText, textStatus, XMLHttpRequest) {
			self.layout();
			callback(responseText, textStatus, XMLHttpRequest);
		});
	};
	
	pack.carrousel.prototype.add = function(element) {
		// TODO: checks
		this.body.appendChild(element);
		this.layout();
	};
}(KPS, jQuery));

// KPS.modal
(function (cls, $, undefined) { // TODO: common alert area in modals?
//<div class="modal fade">
//	<div class="modal-header">
//		<a class="close" data-dismiss="modal"></a>
//		<h3></h3>
//	</div>
//	<div class="modal-body">
//	</div>
//	<div class="modal-footer">
//		<a href="#" class="btn btn-warning"></a>
//		<a href="#" class="btn btn-success"></a>
//	</div>
//</div>
	
	// El setup

	cls.el = document.createElement('div');
	cls.$el = $(cls.el);
	cls.$el.addClass('modal fade');
	
	var header = document.createElement('div');
	$(header).addClass('modal-header');
	var cross = document.createElement('a');
	$(cross).addClass('close').html('&times;').attr('data-dismiss', 'modal');
	header.appendChild(cross);
	var title = document.createElement('h3');
	var $title = $(title);
	header.appendChild(title);
	
	cls.el.appendChild(header);
	
	var body = document.createElement('div');
	var $body = $(body);
	$body.addClass('modal-body');
	
	cls.el.appendChild(body);
	
	var footer = document.createElement('div');
	$(footer).addClass('modal-footer');
	var cancelButton = document.createElement('a');
	var $cancelButton = $(cancelButton);
	$cancelButton.addClass('btn btn-warning');
	footer.appendChild(cancelButton);
	var okButton = document.createElement('a');
	var $okButton = $(okButton);
	$okButton.addClass('btn btn-success');
	footer.appendChild(okButton);
	
	cls.el.appendChild(footer);
	
	// Carrousel setup
	
	cls.carrousel = new KPS.carrousel();
	cls.carrousel.width = 550;
	cls.carrousel.spacing = 30;
	cls.carrousel.setClips(false);
	cls.carrousel.layout();
	body.appendChild(cls.carrousel.el);
	
	// Configurations

	var defaultConfiguration = {
			title: "Modal",
			okButton: {
				title: "Ok",
				action: function () {
					cls.hide();
				}
			},
			cancelButton: {
				title: "Cancel",
				action: function () {
					cls.hide();
				}
			}
	};
	var currentConfiguration = defaultConfiguration;

	function setTitle(title) {
		$title.html(title);
	};

	function setOkButtonTitle(name) {
		$okButton.html(name);
	};

	function setOkButtonAction(action) {
		$okButton.unbind('click');
		$okButton.click(action);
	};

	function setCancelButtonTitle(name) {
		$cancelButton.html(name);
	};

	function setCancelButtonAction(action) {
		$cancelButton.unbind('click');
		$cancelButton.click(action);
	};

	cls.show = function() {
		cls.$el.modal('show');
	};

	cls.hide = function() {
		cls.$el.modal('hide');
	};

	cls.configure = function(configuration) {
		setTitle(configuration.title || defaultConfiguration.title);
		setOkButtonTitle((configuration.okButton || {}).title || defaultConfiguration.okButton.title);
		setOkButtonAction((configuration.okButton || {}).action || defaultConfiguration.okButton.action);
		setCancelButtonTitle((configuration.cancelButton || {}).title || defaultConfiguration.cancelButton.title);
		setCancelButtonAction((configuration.cancelButton || {}).action || defaultConfiguration.cancelButton.action);

		var tmp = currentConfiguration;
		currentConfiguration = configuration;
		return tmp;
	};

	cls.load = function(url, callback) {
		cls.carrousel.load(url, callback);
	};
	
	$(document).ready(function() {
		document.body.appendChild(cls.el);
		cls.$el.modal({show:false, keyboard:true, backdrop: 'static'});
	});
} (KPS.modal, jQuery));

// KPS.data.locations
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