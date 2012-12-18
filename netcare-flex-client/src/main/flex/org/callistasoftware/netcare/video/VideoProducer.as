package org.callistasoftware.netcare.video
{
	import flash.events.NetStatusEvent;
	import flash.media.Camera;
	import flash.media.Microphone;
	import flash.media.SoundCodec;

	public class VideoProducer extends VideoClientSupport
	{
		private var camera:Camera;
		private var mic:Microphone;
		
		public function VideoProducer(url:String, streamName:String, width:uint, height:uint)
		{
			super(url, streamName, width, height);
		}
		
		override protected function initStream():void
		{
			super.initStream();
			
			if (this.camera == null) {
				this.camera = Camera.getCamera();
				this.camera.setKeyFrameInterval(7);
				this.camera.setMode(this.width, this.height, 12, true);
				this.camera.setQuality(0, 90);
			}
			
			if (this.mic == null) {
				this.mic = Microphone.getEnhancedMicrophone();
				this.mic.codec = SoundCodec.SPEEX;
				this.mic.framesPerPacket = 1;
				this.mic.setSilenceLevel(0, 2000);
				this.mic.gain = 50;
			}
			
			this.stream.attachCamera(camera);
			this.stream.attachAudio(mic);
			this.stream.publish(this.streamName, 'live');
		}
		
		override protected function initVideo():void
		{
			super.initVideo();
			this.video.attachCamera(this.camera);
			
			dispatchEvent(new VideoEvent(VideoEvent.PRODUCER_AVAILABLE, this.video));
		}
		
		override public function disconnect():void {
			
			super.disconnect();
			
			this.camera = null;
			this.mic = null;
			
			dispatchEvent(new VideoEvent(VideoEvent.PRODUCER_UNAVAILABLE, null));
		}
		
	}
	
	
}