package org.callistasoftware.netcare.video
{
	public class VideoConsumer extends VideoClientSupport
	{
		public function VideoConsumer(url:String, streamName:String, width:uint, height:uint) {
			super(url, streamName, width, height);
		}
		
		override protected function initStream():void
		{
			super.initStream();
			this.stream.play(this.streamName, -1);
		}
		
		override protected function initVideo():void
		{
			super.initVideo();
			this.video.attachNetStream(this.stream);
			
			this.dispatchEvent(new VideoEvent(VideoEvent.CONSUMER_AVAILABLE, this.video));
		}
		
		override public function disconnect():void
		{
			super.disconnect();
			
			dispatchEvent(new VideoEvent(VideoEvent.CONSUMER_UNAVAILABLE, null));
		}
	}
}