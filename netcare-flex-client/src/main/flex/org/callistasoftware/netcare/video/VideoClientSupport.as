package org.callistasoftware.netcare.video
{
	import flash.events.EventDispatcher;
	import flash.events.NetStatusEvent;
	import flash.media.Video;
	import flash.net.NetConnection;
	import flash.net.NetStream;
	import flash.net.ObjectEncoding;
	
	import mx.controls.Alert;

	public class VideoClientSupport extends EventDispatcher
	{
		private var url:String;
		protected var streamName:String;
		
		private var con:NetConnection;
		protected var stream:NetStream;
		
		protected var video:Video;
		
		protected var width:uint;
		protected var height:uint;
		
		public function VideoClientSupport(url:String, streamName:String, width:uint, height:uint)
		{
			this.url = url;
			this.streamName = streamName;
			
			this.width = width;
			this.height = height;
		}
		
		public function connect():void {
			if (this.con == null || !this.con.connected) {
				this.con = new NetConnection();
				
				this.con.client = this;
				this.con.objectEncoding = ObjectEncoding.AMF3;
				this.con.addEventListener(NetStatusEvent.NET_STATUS, eventHandler);
				this.con.connect(this.url);
			}
		}
		
		public function disconnect():void {
			
			if (this.con != null && this.con.connected) {
				
				if (this.video != null) {
					this.video.clear();
					this.video = null;
				}
				
				if (this.stream != null) {
					this.stream.close();
					this.stream = null;
				}
			}
			
			this.con.close();
			this.con = null;
		}
		
		protected function eventHandler(event:NetStatusEvent):void {
			
			switch(event.info.code) {
				case "NetConnection.Connect.Success": {
					this.initStream();
					break;
				}
					
				case "NetConnection.Connect.Failed": {
					Alert.show("Connection error. Could not connect to: " + this.url);
					break;
				}
					
				case "NetStream.Publish.Start": {
					this.initVideo();
					break;
				}
					
				case "NetStream.Play.Start": {
					this.initVideo();
					break;
				}
			}
		}
		
		protected function initStream():void {
			this.stream = new NetStream(this.con);
			this.stream.addEventListener(NetStatusEvent.NET_STATUS, eventHandler);
		}
		
		protected function initVideo():void {
			if (this.video == null) {
				video = new Video(this.width, this.height);
			}
		}
	}
}