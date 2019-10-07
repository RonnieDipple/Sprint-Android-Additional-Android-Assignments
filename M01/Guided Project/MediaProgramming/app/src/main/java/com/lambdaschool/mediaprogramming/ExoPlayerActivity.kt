package com.lambdaschool.mediaprogramming

import android.net.Uri
import android.os.Bundle
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_exo_player.*

/*
This activity demonstrates how to use ExoPlayer to play a video from local file system and
also from the internet.
 */

//TODO this is for PlayerWithURL

//TODO 1: Make sure to turn on the permission for internet in the manifest.
//TODO 2: Enable 'targetCompatibility JavaVersion.VERSION_1_8' in app build.gradle file.
//TODO 3: Enable ExoPlayer dependency in the app build.gradle file.

//prepare should not be on MAIN UI THREAD!!!!

class ExoPlayerActivity : AppCompatActivity() {

    //TODO 4: Declare the variables needed. A simpleExoPlayer instance.
   lateinit var videoExoPlayer: SimpleExoPlayer

    //TODO 5: Notice the url we will be using to streaming mp4 over the internet.
    val URL = "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)

        //TODO 6: Create a videoplayer instance with default settings, implement createVideoPlayer function
        createVideoPlayer()

        //TODO 7: Create a function to setup video player from file system
        //This function can be switched out with the setupVideoPlayerWithURL to stream the video
        //from the internet.
       // setupVideoPlayerFromFileSystem()

       setupVideoPlayerWithURL() //TODO this is for PlayerWithURL

        //TODO 9: Setup clicklisteners for play and pause buttons
        play.setOnClickListener { videoExoPlayer.playWhenReady = true }
        pause.setOnClickListener { videoExoPlayer.playWhenReady = false }

        //TODO 9a: Set the player for the PlayerView
        playerView.player = videoExoPlayer
    }

    fun setupVideoPlayerFromFileSystem() {
        videoExoPlayer.prepare(createRawMediaSource(R.raw.live_views_of_starman))
    }

    //TODO 8: Create a function to setup video player with url to stream video through internet.

    //TODO this is for PlayerWithURL
    fun setupVideoPlayerWithURL() {
        videoExoPlayer.prepare(createUrlMediaSource(URL))
    }

    fun createVideoPlayer() {
        // Need a track selector
        val trackSelector = DefaultTrackSelector()
        // Need a load control
        val loadControl = DefaultLoadControl()
        // Need a renderers factory
        val renderersFactory = DefaultRenderersFactory(this)


        // Set up the ExoPlayer
        videoExoPlayer = ExoPlayerFactory.newSimpleInstance(this, renderersFactory,
            trackSelector,
            loadControl)

        // Set up the scaling mode to crop and fit the video to the screen
        videoExoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING //fits player to screen, C is an exo player class

    }

    //TODO 12: Do not forget to stop the player when the user navigates away from the screen
    override fun onStop() {
        super.onStop()
        videoExoPlayer.stop()
    }

    //TODO 10: Notice the code to implement and create a mediasource function using URL, returns a mediasource

    //TODO this is for PlayerWithURL
    fun createUrlMediaSource(url: String): MediaSource {
        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))
        return ExtractorMediaSource.Factory(DefaultDataSourceFactory(this, userAgent))
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(url))
    }

    //TODO 11: Notice the code to implement and create a mediasource function using raw resource, returns a mediasource
    //Great boilerplate for exoplayer files on internet

    fun createRawMediaSource(@RawRes rawId: Int): MediaSource {
        val rawResourceDataSource = RawResourceDataSource(this)
        val dataSpec = DataSpec(RawResourceDataSource.buildRawResourceUri(rawId))
        rawResourceDataSource.open(dataSpec)
        return ExtractorMediaSource.Factory(DataSource.Factory {
            rawResourceDataSource
        }).createMediaSource(rawResourceDataSource.uri)
    }

}
