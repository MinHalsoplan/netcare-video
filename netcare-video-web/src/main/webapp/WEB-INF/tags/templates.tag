<%--

    Copyright (C) 2011,2012 Callista Enterprise AB <info@callistaenterprise.se>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mvk" uri="http://www.callistasoftware.org/mvk/tags"%>
<%@ taglib prefix="netcare" uri="http://www.callistasoftware.org/netcare/tags"%>
<%@ taglib prefix="video" tagdir="/WEB-INF/tags"%>

<script id="bookingItem" type="text/template">
<li id="booking-item-{{id}}" class="item withNavigation" style="cursor: pointer;">
<mvk:touch-item>
<div id="booking-{{id}}" class="listItemBase">
	<div class="row-fluid">
		<div class="mainBody span6">
			<h4 class="titel">{{name}}</h4>
			<div class="subRow">{{start}}</div>
		</div>
		<div class="mainBody actionBody span6"></div>
	</div>
</div>
<a href="#" class="itemNavigation assistiveText"></a>
</mvk:touch-item>
</li>
</script>

<script id="bookingItemDetails" type="text/template">
<div id="bo-details-{{id}}" class="item-with-form" style="display: none; margin-right: 15px;">
	<div class="row-fluid">
		<div class="span4">
			Skapad av:
		</div>
		<div class="span8">
			{{createdBy.name}} ({{createdBy.hsaId}})
		</div>
	</div>
	<div class="row-fluid">
		<div class="span4">
			Deltagare:
		</div>
		<div id="participants-{{id}}" class="span8">
			
		</div>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<a id="show-notes-{{id}}" href="#">Visa anteckningar</a>
		</div>
	</div>
</div>
</script>

<script id="adminItem" type="text/template">
<li id="admin-item-{{id}}" class="item withNavigation" style="cursor: pointer;">
<mvk:touch-item>
<div id="admin-{{id}}" class="listItemBase">
	<div class="row-fluid">
		<div class="mainBody span6">
			<h4 class="titel">{{name}}</h4>
			<div class="subRow">{{start}}</div>
		</div>
		<div class="mainBody actionBody span6"></div>
	</div>
</div>
<a href="#" class="itemNavigation assistiveText"></a>
</mvk:touch-item>
</li>
</script>

<script id="videoThumbnail" type="text/template">
<div id="thumbnail-{{id}}">
	<div class="thumbnail video-participant" style="width: {{width}}px; height: {{height}}px;">
		<object width="{{width}}" height="{{height}}">
			<param name="movie" value="{{url}}" />
			<embed src="{{url}}" type="application/x-shockwave-flash" width="{{width}}" height="{{height}}"/>
		</object>
	</div>
	<div class="caption">
		<span>
			<strong>{{name}}</strong> 
			<small> | </small>
		</span>
		<span> 
			<a id="{{id}}" href="#"><small>Visa i storbild</small></a>
		</span>
		<span id="quit-{{id}}" style="display: none;">
			<small> | </small><a href="#">Avsluta</a>
		</span>
	</div>
</div>
</script>

<script id="videoScreen" type="text/template">
<div id="star-{{id}}">
	<div class="thumbnail video-participant" style="width: {{width}}px; height: {{height}}px;">
		<object width="{{width}}" height="{{height}}">
			<param name="movie" value="{{url}}" />
			<embed src="{{url}}" type="application/x-shockwave-flash" width="{{width}}" height="{{height}}"/>
		</object>
	</div>
</div>
</script>

