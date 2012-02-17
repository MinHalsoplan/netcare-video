package org.callistasoftware.netcare.video
{
	import flash.events.Event;
	import flash.media.Video;

	/**
	 * Simple event that is sent when video becomes available 
	 * or unavailable
	 * @author Marcus Krantz [marcus.krantz@callistaenterprise.se]
	 */
	public class VideoEvent extends Event
	{
		public static const PRODUCER_AVAILABLE:String = "0";
		public static const PRODUCER_UNAVAILABLE:String = "1";
		public static const CONSUMER_AVAILABLE:String = "2";
		public static const CONSUMER_UNAVAILABLE:String = "3";
		
		private var eventType:String;
		private var video:Video;
		
		public function VideoEvent(eventType:String, video:Video)
		{
			super(eventType);
			
			this.eventType = eventType;
			this.video = video;
		}
		
		public function getEventType():String {
			return this.eventType;
		}
		
		public function getVideo():Video {
			return this.video;
		}
	}
}