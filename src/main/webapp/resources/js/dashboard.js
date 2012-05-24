var KPS = KPS || {};
KPS.dashboard = KPS.dashboard || {};

(function(cls, $, undefined) {
	var numberToShow = 10;
	var currentBase = 0;
	
	var currentList = null;
	var eventList = null;
	var eventListContainer = null;
	
	var larrEl = null;
	var rarrEl = null;
	
	var pollInterval = 1000*5; // 5 seconds 
	
	var lastEvent = 0;
	
	/** Creates a section in the dashboard-scrubber**/
	function createSection(from, count) {
		if(from < 0) {
			count += from;
			from = 0;
		}
		
		var table = document.createElement('table');
		table.setAttribute('class', 'nested');
		var eventList = document.createElement('tr');
		
		//Sets the tool-tip and click function for each event
		for(var n=from;n<from+count&&n<cls.events.length;n++) {
			var eventEl = document.createElement('a');
			eventEl.innerHTML = cls.events[n].id;
			
			(function(event) {
				$(eventEl).tooltip({title: event.description + " on " + KPS.data.format.date(new Date(event.timestamp))}).click(function() {
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
			//Adds the event to the list.
			
			td.appendChild(eventEl);
			eventList.appendChild(td);
		}
			//Adds the list to the table.
			table.appendChild(eventList);
			
			return table;
	}
	
	/** Measures the width fo the event-list container **/
	function eventListContainerWidth(list) {
		return {
			width: $(list).width()+"px",
			maxWidth: $(list).width()+"px",
			overflow: "hidden"
		};
	}
	
	/** Sets the a timer to periodically check for event updates per every `pollInterval` **/
	function setupEventListener() {
		lastEvent = cls.currentEvent;
		setTimeout(checkForEventUpdates, pollInterval);
	}
	
	/** Checks for eventUpdates **/
	function checkForEventUpdates() {
		$.ajax("dashboard?eventcount", {
			global: false,
			success: function(data, textStatus, jqXHR) {
				latestEvent = eval(data);
				
				/** If lastest and last are different, an event has occurred. Lets the user know **/
				if(latestEvent != lastEvent) {
					lastEvent = latestEvent;
					var back = latestEvent-cls.currentEvent;
					document.getElementById('eventbacklogcount').innerHTML = back + (back>1?' events have':' event has');
					$("#eventbacklogalert").fadeIn();
				}
				// Have timer continue checking
				setTimeout(checkForEventUpdates, pollInterval);
			}
		});
	}
	
	cls.hideEventAlert=function(){
		$("#eventbacklogalert").fadeOut();
	};
	
	/** Initiates the dashboard**/
	$(document).ready(function() {
		$('#main-tabs a[href="'+($.cookie('dashboard-main-tab') || '#dashboard-tab-revenue-expenditure')+'"]').tab('show');
		/** Set click binders for tabs to store current tab as cookie **/
		$("#main-tabs > li > a").click(function(el) {
			$.cookie('dashboard-main-tab', $(el.currentTarget).attr('href'));
		});
		
		eventList = document.getElementById("eventList");
		
		/** If there is an event list, it creates the dashboard scrubber**/
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
		
		$("#eventscrubber").css({opacity:1});
		
		/** If the latest event is the one showing, notify user of updates.**/
		if(cls.currentEvent == cls.events.length) {
			setupEventListener();
		}
	});
	
	/** Toggle arrows depending on where in the event list the user is located.**/
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
	
	/** GO right function for the event scrubber. Adjusts the size of the list dynamically and shows only the appropriate events**/
	cls.goRight = function() {
		var max = cls.events.length-numberToShow;
		if(currentBase < max) {
			currentBase = currentBase+numberToShow;
			if(currentBase >= max) {
				currentBase = max;
			}
			
			checkArrows();
			
			var newList = createSection(currentBase, numberToShow);
			// Create new section to reveal.
			eventList.appendChild(newList);
			
			//Move scrubber
			$(currentList).animate({marginLeft:-$(currentList).width()+"px"}, function(){
				eventList.removeChild(currentList);
				currentList = newList;
			});
			$(eventListContainer).animate(eventListContainerWidth(newList));
		}
	};
	
	/** GO left function for the event scrubber. Adjusts the size of the list dynamically and shows only the appropriate events**/
	cls.goLeft = function() {
		if(currentBase > 0) {
			currentBase -= numberToShow;
			
			checkArrows();
			
			var newList = createSection(currentBase, numberToShow);
			// Create new section to reveal.
			eventList.insertBefore(newList, currentList);
			$(newList).css({marginLeft:-$(newList).width()+"px"});
			
			//Move scrubber
			$(newList).animate({marginLeft:0}, function(){
				eventList.removeChild(currentList);
				currentList = newList;
			});
			$(eventListContainer).animate(eventListContainerWidth(newList));
		}
	};
}(KPS.dashboard, jQuery));