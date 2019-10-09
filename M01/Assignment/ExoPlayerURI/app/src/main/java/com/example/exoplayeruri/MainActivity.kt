package com.example.exoplayeruri


import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YouTubeUriExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random



class MainActivity : AppCompatActivity() {


    companion object {
        const val VIDEO_URL = "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4"
        const val VIDEO_POSITION = "MainActivity.POSITION"
    }

    lateinit var player: SimpleExoPlayer
    private var videoPosition: Long = 0L
    lateinit var mediaSession: MediaSessionCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Tracking of video position
        savedInstanceState?.let { videoPosition = savedInstanceState.getLong(VIDEO_POSITION) }


    }

    override fun onStart() {
        super.onStart()

        //Declaring a factory
        player = ExoPlayerFactory
            .newSimpleInstance(this, DefaultTrackSelector())

        playerView.player = player

        //Declaring a player
       VIDEO_URL?.let { setPlayerMedia() }//setPlayerMedia()

        //When everything is ready just automatically plays
        player.playWhenReady = true

        //Exoplayer doesn't automatically give you media session, media session makes other apps aware that your app is using media session
        //Also gives you controls although exo has them anyway
        mediaSession = MediaSessionCompat(this, packageName)
        val mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlayer(player, null)
        mediaSession.isActive = true
    }

    //Function prepares media which includes media on phone and URI/URL, this will be great code to use in the future
    //Currently being passed a constvalue but that can be altered
    fun setPlayerMedia() {
        val dataSourceFactory =
            DefaultDataSourceFactory(
                this,
                Util.getUserAgent(
                    this,
                    applicationInfo.loadLabel(packageManager)
                        .toString()
                )
            )

        when (Util.inferContentType(Uri.parse(VIDEO_URL))) {
            C.TYPE_HLS -> {
                val mediaSource = HlsMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(VIDEO_URL))
                player.prepare(mediaSource)
            }


            C.TYPE_OTHER -> {
                val mediaSource = ExtractorMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(VIDEO_URL))
                player.prepare(mediaSource)
            }

            else -> {
                //This is to catch unsupported types
                finish()
            }
        }
    }


    override fun onStop() {
        super.onStop()
        mediaSession.isActive = false
        playerView.player = null
        player.release()
    }

    //Seek and resume
    override fun onPause() {
        videoPosition = player.currentPosition
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (videoPosition > 0L) {
            player.seekTo(videoPosition)
        }
        player.playWhenReady = true
    }


    @SuppressLint("StaticFieldLeak")
    private fun extractYoutubeUrl(youtubeLink: String) {
        object : YouTubeExtractor(this) {
            public override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                vMeta: VideoMeta
            ) {
                if (ytFiles != null) {
                    val itag = 22
                    val downloadUrl = ytFiles.get(itag).url

                    // play url
                    val uri = Uri.parse(downloadUrl)
                    val mediaSource = buildMediaSource(uri)
                    player.prepare(mediaSource, true, false)

                }
            }
        }.extract(youtubeLink, true, true)

    }


    private fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(
            DefaultHttpDataSourceFactory("exoplayer")
        ).createMediaSource(uri)
    }




}


