/*
 * Copyright (C) 2011,2012 Callista Enterprise AB <info@callistaenterprise.se>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
$(window).load(function() {
	$('#pageLoading').fadeOut('fast', function(e) {
		$(this).hide();
		$('#pageLoadingBox').hide();
	});
});

$(document).ready(function() {
	
	if (typeof console === "undefined") {
		console = {};
	}
	if (typeof console.log === "undefined") {
		console.log = function() {};
	}
	
	/*
	 * Select content when a text or number input field gains focus.
	 */
	$('input:text').focus( function (event) { 
		NC.focusGained($(this));
	});

	$('input:text').focusout( function () { 
		NC.focusLost($(this));
	});

	$('input:password').focus( function (event) { 
		NC.focusGained($(this));
	});

	$('input:password').focusout( function () { 
		NC.focusLost($(this));
	});
	
	$('input[type="number"]').focus( function (event) { 
		NC.focusGained($(this));
	});

	$('input[type="number"]').focusout( function () { 
		NC.focusLost($(this));
	});

	$('input[type="tel"]').focus( function (event) { 
		NC.focusGained($(this));
	});

	$('input[type="tel"]').focusout( function () { 
		NC.focusLost($(this));
	});

	$('input[type="email"]').focus( function (event) { 
		NC.focusGained($(this));
	});

	$('input[type="email"]').focusout( function () { 
		NC.focusLost($(this));
	});

	/*
	 * Fix for IE8, make sure enter submits a form.
	 */
	$('form').keypress(function(event) {
		// enter
		if (event.which == 13) {
			event.stopPropagation();
		}
	});
	
	/*
	 * Process all date fields that exist on the
	 * current page.
	 */
	$('.dateInput').each(function(i, v) {
		
		$(v).datepicker({
			dateFormat : 'yy-mm-dd',
			firstDay : 1,
			minDate : +0
		});
		
		$(v).siblings('span').css('cursor', 'pointer').click(function(e) {
			$(v).datepicker('show');
		});
	});
});

var NCV = {
	BOOKINGS : (function() {
		var self = {};
		
		self.init = function(params) {
			var self = this;
			this.renderBookings(self);
		};
		
		self.load = function(onBookings) {
			new NC.Ajax().get('/meeting/list', onBookings);
		};
		
		self.renderBookings = function(self) {
			NC.GLOBAL.showLoader('#bookings', 'Laddar videomöten...');
			self.load(function(data) {
				
				$.each(data.data, function(i, v) {
					if (v.started) {
						self.renderBookingItem(v);
					}
				});
				
				NC.GLOBAL.suspendLoader('#bookings');
				
				if ( $('#bookingsList li').size() > 0) {
					$('#bookingsContainer').show();
				} else {
					$('#bookingsContainer').hide();
				}
				
			});
		};
		
		self.renderBookingItem = function(booking) {
			var dom = _.template( $('#bookingItem').html() )(booking);
			$('#bookingsList').append($(dom));
			
			var liElem = $('#booking-item-' + booking.id);
			var detailsTemplate = _.template($('#bookingItemDetails').html());
			var detailsDom = detailsTemplate(booking);
			
			liElem.find('.row-fluid').after($(detailsDom));
			
			var parts = '';
			$.each(booking.participants, function(i, v) {
				NC.log('Processing ' + v.user.name);
				parts += v.user.name + ', ';
			});
			
			$('#participants-' + booking.id).html(parts);
			
			this.initItemListener(liElem, booking);
			
			var expander = $('<div>').addClass('mvk-icon toggle').click(function(e) {
				e.preventDefault();
				e.stopPropagation();
				$('#bo-details-' + booking.id).toggle();
				$(this).toggleClass('toggle').toggleClass('toggle-open');
			}) ;
			
			liElem.find('.actionBody').css('text-align', 'right').css('padding-right', '40px')
			.append(expander);
		};
		
		self.initItemListener = function(elem, booking) {
			$(elem).on('click', function(e) {
				e.preventDefault();
				e.stopPropagation();
				
				window.location = NC.getContextPath() + '/web/video?booking=' + booking.id;
			});
		};
		
		return self;
		
	})(),
	
	ADMIN : (function() {
		var self = {};
		
		self.init = function(params) {
			var self = this;
			this.params = params;
			
			self.render(self);
		};
		
		self.loadForAdminstration = function(careUnit, callback) {
			new NC.Ajax().get('/meeting/' + careUnit + '/list', callback);
		};
		
		self.render = function(self) {
			NC.GLOBAL.showLoader('#upcoming', 'Laddar bokningar på din vårdenhet...');
			this.loadForAdminstration(self.params.careUnit, function(data) {
				
				if (data.data.length > 0) {
					$('#upcomingList').empty();
					
					$.each(data.data, function(i, v) {
						if (!v.started) {
							self.renderItem(v);
						}
					});
				}
				
				if ($('#upcomingList li').size() > 0) {
					$('#upcomingContainer').show();
				}
				
				NC.GLOBAL.suspendLoader('#upcoming');
				
			});
		};
		
		self.renderItem = function(booking) {
			var dom = _.template( $('#adminItem').html() )(booking);
			$('#upcomingList').append($(dom));
			
			$('#admin-item-' + booking.id).click(function() {
				window.location = NC.getContextPath() + '/web/bookings/' + booking.id;
			});
		};
		
		return self;
	})(),
	
	CREATE_BOOKING : (function() {
		var _data;
		var my = {};
		
		my.init = function(params) {
			var that = this;
			this.params = params;
			
			if (params.booking != 'new') {
				
				my.loadBooking(that, function(data) {
					_data = data.data;
					
					delete _data.createdBy;
					
					// Load meeting and fill form
					my.initListeners(that);
					my.render(that);
				});
				
			} else {
				_data = {
					'id' : -1,
					'name' : '',
					'description' : '',
					'start' : '',
					'end' : '',
					'started' : false,
					'participants' : []
				};
				
				my.initListeners(that);
				my.render(that);
			}
		};
		
		var hackDate = function(date) {
			return date.substring(0, 10);
		};
		
		var hackTime = function(time) {
			return time.substring(11, time.length);
		}
		
		my.render = function(my) {
			
			$('#name').val(_data.name);
			$('#description').val(_data.description);
			$('#date').val(hackDate(_data.start));
			$('#start').val(hackTime(_data.start));
			$('#end').val(hackTime(_data.end));
			
			renderParticipants();
		}
		
		my.loadBooking = function(my, callback) {
			new NC.Ajax().get('/meeting/' + my.params.booking, callback);
		};
		
		my.initListeners = function(my) {
			
			$('#name').on('keyup change blur', function() {
				_data.name = $(this).val();
			});
			
			$('#description').on('keyup change blur', function() {
				_data.description = $(this).val();
			});
			
			$('#date').on('keyup change blur', function() {
				_data.start = $(this).val() + ' ' + $('#start').val();
				_data.end = $(this).val() + ' ' + $('#end').val();
			});
			
			$('#start').on('keyup change blur', function() {
				_data.start = $('#date').val() + ' ' + $(this).val();
			});
			
			$('#end').on('keyup change blur', function() {
				_data.end = $('#date').val() + ' ' + $(this).val();
			});
			
			$('#search').autocomplete({
				source : my.searchParticipants,
				select : my.selectParticipant
			});
			
			if (_data.id != -1 && _data.careUnit.hsaId == my.params.careUnitHsa) {
				$('#delete-booking').click(function(e) {
					new NC.Ajax().http_delete('/meeting/' + _data.id, function(data) {
						window.location = NC.getContextPath() + '/web/dashboard';
					});
				});
				
				$('#delete-booking').show();
			}
			
			$('#booking-form :submit').click(function(e) {
				e.preventDefault();
				
				var url = '/meeting/';
				if (_data.id != -1) {
					url += _data.id;
				}
				
				new NC.Ajax().post(url, _data, function(data) {
					window.location = NC.getContextPath() + '/web/dashboard';
				});
			});
		};
		
		my.searchParticipants = function(req, res) {
			new NC.Ajax().getWithParams('/user/search', { searchterm : req.term}, function(data) {
				res($.map(data.data, function(value) {
					var display = '';
					if (value.careGiver) {
						display = value.name + ' (' + value.hsaId + ')';
					} else {
						display = value.name + ' (' + util.formatCrn(value.civicRegistrationNumber) + ')';
					}
					
					return { label : display, value : display, user : value };
				}));
			});
		};
		
		my.selectParticipant = function(e, ui) {
			
			var part = new Object();
			part.user = ui.item.user;
			part.stream = '';
			part.connected = false;
			
			if (part.user.authorities != undefined) {
				delete part.user.authorities;
			}
			
			_data.participants.push(part);
			renderParticipants();
		};
		
		var renderParticipants = function() {
			$('#selectedUsers ul').empty();
			$.each(_data.participants, function(i, v) {
				createParticipant(i, v);
			});
		};
		
		var createParticipant = function(idx, participant) {
			var part = participant;
			
			if (part.user.authorities != undefined) {
				delete part.user.authorities;
			}
			
			var li = $('<li>').prop('id', 'user-' + part.user.id);
			var rem = $('<a>').prop('href', '#').css('text-decoration', 'none').html('×');
			rem.on('click', function(e) {
				e.preventDefault();
				_data.participants.splice(idx, 1);
				
				renderParticipants();
				
				$(this).unbind('click');
			});
			
			li.append($('<span>').html(part.user.name));
			li.append(rem);
			
			$('#selectedUsers ul').append(li);
		}
		
		return my;
	})(),
	
	VIDEO : (function() {
		var _participants;
		var _currentMovieStar;
		
		var findParticipant = function(id) {
			for (var i = 0; i < _participants.length; i++) {
				var part = _participants[i];
				if (part.user.id == id) {
					return part;
				}
			}
		};
		
		var my = {};
		
		my.init = function(params) {
			var that = this;
			this.params = params;
			
			my.loadParticipants(that);
		};
		
		my.loadParticipants = function(my) {
			new NC.Ajax().get('/meeting/' + my.params.meetingId, function(data) {
				
				var meeting = data.data;
				_participants = meeting.participants;
				
				$.each(meeting.participants, function(i, v) {
					
					$('#participants').append(
						$('<div>').prop('id', 'thumbnail-' + v.user.id)
					);
					
					my.renderVideo(v);
					
				});
			});
		};
		
		var getThumbnailObject = function(part) {
			var serverUrl = '';
			if (part.user.id == my.params.userId) {
				serverUrl = constructUrl(my.params.serverUrl, part.stream, 'producer', 225, 169);
			} else {
				serverUrl = constructUrl(my.params.serverUrl, part.stream, 'consumer', 225, 169);
			}
			
			return {
				id : part.user.id,
				url : serverUrl,
				width : 225,
				height : 169,
				name : part.user.name
			};
		};
		
		var getScreenObject = function(part) {
			var serverUrl = '';
			if (part.user.id == my.params.userId) {
				serverUrl = constructUrl(my.params.serverUrl, part.stream, 'producer', 640, 480);
			} else {
				serverUrl = constructUrl(my.params.serverUrl, part.stream, 'consumer', 640, 480);
			}
			
			return {
				id : part.user.id,
				url : serverUrl,
				width : 640,
				height : 480,
				name : part.user.name
			};
		};
		
		var constructUrl = function(url, stream, type, width, height) {
			return NC.getContextPath() + '/video-client.swf?server=' + url + '&stream=' + stream + '&type=' + type + '&width=' + width +'&height=' + height;
		};
		
		var constructObject = function(id, url, width, height, name) {
			return {
				id : id,
				url : url,
				width : width,
				height : height,
				name : name
			};
		};
		
		my.initListener = function(my, elem) {
			$(elem).click(function(e) {
				e.preventDefault();
				
				if (_currentMovieStar != undefined) {
					$('#movieFrame').find('object').remove();
					my.renderVideo(_currentMovieStar);
				}
				
				var userId = $(this).prop('id');
				NC.log('User ' + userId + ' clicked... zooming');
				
				var part = findParticipant(userId);
				my.renderMovieVideo(part);
				_currentMovieStar = part;
			});
		};
		
		my.renderVideo = function(part) {
			NC.log('Render thumbnail video for: ' + part.user.id);
			var obj = getThumbnailObject(part);
			var dom = _.template( $('#videoThumbnail').html() )(obj);
			$('#thumbnail-' + part.user.id).replaceWith($(dom));
			
			my.initListener(my, '#thumbnail-' + part.user.id + ' a');
		};
		
		my.renderMovieVideo = function(part) {
			NC.log('Suspending thumbnail video for ' + part.user.id);
			$('#thumbnail-' + part.user.id).find('.video-participant').empty().append(
				$('<p>').css('font-style', 'italic').css('text-align', 'center').css('padding-top', '80px').html('Visas nu i videorutan')
			);
			
			NC.log('Render movie screen for ' + part.user.id);
			var obj = getScreenObject(part);
			var dom = _.template( $('#videoScreen').html() )(obj);
			$('#movieFrame').empty().removeClass('thumbnail').append($(dom));
		};
		
		return my;
	})()
}








