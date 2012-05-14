var KPS = KPS || {};
KPS.dashboard = KPS.dashboard || {};

(function(cls, $, undefined) {
	var numberToShow = 12;
	var currentBase = 0;
	
	var currentList = null;
	var eventList = null;
	var eventListContainer = null;
	
	var larrEl = null;
	var rarrEl = null;
	
	var pollInterval = 1000*5; // 5 seconds
	
	var lastEvent = 0;
	
	function createSection(from, count) {
		if(from < 0) {
			count += from;
			from = 0;
		}
		
		var table = document.createElement('table');
		table.setAttribute('class', 'nested');
		var eventList = document.createElement('tr');
		for(var n=from;n<from+count&&n<cls.events.length;n++) {
			var eventEl = document.createElement('a');
			eventEl.innerHTML = cls.events[n].id;
			(function(event) {
				$(eventEl).tooltip({title: KPS.data.format.date(new Date(event.timestamp))}).click(function() {
					var id = event.id;
					if(event.id == cls.events.length) {
						id = 0;
					}
					window.location = KPS.siteRoot + "/dashboard?atevent="+id;
				});
			}(cls.events[n]));
			var td = document.createElement('td');
			if(cls.currentEvent == cls.events[n].id) {
				td.setAttribute("class", "disabled");
			}
			td.appendChild(eventEl);
			eventList.appendChild(td);
		}
		
			table.appendChild(eventList);
			
			return table;
	}
	
	function eventListContainerWidth(list) {
		return {
			width: $(list).width()+"px",
			maxWidth: $(list).width()+"px",
			overflow: "hidden"
		};
	}
	
	function setupEventListener() {
		lastEvent = cls.currentEvent;
		setTimeout(checkForEventUpdates, pollInterval);
	}
	
	function checkForEventUpdates() {
		$.ajax("dashboard?eventcount", {
			global: false,
			success: function(data, textStatus, jqXHR) {
				latestEvent = eval(data);
				
				if(latestEvent != lastEvent) {
					lastEvent = latestEvent;
					var back = latestEvent-cls.currentEvent;
					document.getElementById('eventbacklogcount').innerHTML = back + (back>1?' events have':' event has');
					$("#eventbacklogalert").fadeIn();
				}
				
				setTimeout(checkForEventUpdates, pollInterval);
			}
		});
	}
	
	cls.hideEventAlert=function(){
		$("#eventbacklogalert").fadeOut();
	};
	
	$(document).ready(function() {
		eventList = document.getElementById("eventList");
		if(eventList) {
			eventListContainer = document.getElementById("eventListContainer");
			larrEl = document.getElementById("larrId");
			rarrEl = document.getElementById("rarrId");
			
			currentBase = cls.currentEvent - Math.floor(numberToShow/2)-1;
			if(currentBase < 0) {
				currentBase = 0;
			}
			else if(currentBase + numberToShow > cls.events.length){
				currentBase = cls.events.length - numberToShow;
			}
			
			currentList = createSection(currentBase, numberToShow);
			eventList.appendChild(currentList);
			$(eventListContainer).css(eventListContainerWidth(currentList));
			
			checkArrows();
		}
		
		if(cls.currentEvent == cls.events.length) {
			setupEventListener();
		}
		
		KPS.graphs.refreshCharts();
	});
	
	function checkArrows() {
		var max = cls.events.length-numberToShow;
		if(currentBase >= max) {
			$(rarrEl).addClass('disabled');
		}
		else {
			$(rarrEl).removeClass('disabled');
		}
		
		if(currentBase <= 0) {
			$(larrEl).addClass('disabled');
		}
		else {
			$(larrEl).removeClass('disabled');
		}
	}
	
	(function activateTabs(){
		$('#nav-tabs a').click(function (e) { e.preventDefault();  $(this).tab('show'); });
	})();
	

	cls.goRight = function() {
		var max = cls.events.length-numberToShow;
		if(currentBase < max) {
			currentBase = currentBase+numberToShow;
			if(currentBase >= max) {
				currentBase = max;
			}
			
			checkArrows();
			
			var newList = createSection(currentBase, numberToShow);
			eventList.appendChild(newList);
			$(currentList).animate({marginLeft:-$(currentList).width()+"px"}, function(){
				eventList.removeChild(currentList);
				currentList = newList;
			});
			$(eventListContainer).animate(eventListContainerWidth(newList));
		}
	};
	
	cls.goLeft = function() {
		if(currentBase > 0) {
			currentBase -= numberToShow;
			
			checkArrows();
			
			var newList = createSection(currentBase, numberToShow);
			eventList.insertBefore(newList, currentList);
			$(newList).css({marginLeft:-$(newList).width()+"px"});
			$(newList).animate({marginLeft:0}, function(){
				eventList.removeChild(currentList);
				currentList = newList;
			});
			$(eventListContainer).animate(eventListContainerWidth(newList));
		}
	};
}(KPS.dashboard, jQuery));