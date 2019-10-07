package com.lambdaschool.mediaprogramming

import android.content.Intent
import android.graphics.drawable.Animatable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/*
This activity demonstrates how to use the VideoView class to play a locally stored file in the Raw
resources directory.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO 1: Disable the play/pause button by default (you'll enable it later)
        play_pause_button.isEnabled = false

        //TODO 2: Add the play/pause functionality including the animation for the button and
        //TODO playing or pausing the VideoView.
        playOrPauseFunctionality()

        //TODO 3: Add functionality to listen to seekbar drag events to set videoview to the
        //TODO appropriate part of the video.
        seekBarFunctionality()
    }

    private fun playOrPauseFunctionality() {
        // In the on-click listener for the button,
        // If the video is not playing, start it
        // else pause the video
        // Start the animation

        play_pause_button.setOnClickListener {
            if(video_view.isPlaying){
                video_view.pause()
                play_pause_button.setImageDrawable(getDrawable(R.drawable.avd_anim_pause_play))
                //pause
            }else{
                video_view.start()
                play_pause_button.setImageDrawable(getDrawable(R.drawable.avd_anim_play_pause))

            }

           val drawable = play_pause_button.drawable
            if (drawable is Animatable){
                (drawable as Animatable).start()
            }
        }
    }

    private fun seekBarFunctionality() {
        // In the SeekBar listener, when the seekbar progress is changed,
        // update the video progress
        video_seek_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //if seek bar moved seek another location in the video ie move to different section of video
                seekBar?.let {
                    video_view.seekTo(it.progress)
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

    override fun onStart() {
        super.onStart()

        //TODO 4: Set the video URI to the videoview (This video is an mp4 file in the raw folder )
        //Ignore name not actually it is in the apk
        video_view.setVideoURI(Uri.parse("android.resource://" + packageName
                + "/" + R.raw.live_views_of_starman))

        //TODO 5: Set an onPreparedListener to the videoview and enable the play/pause button in the
        //TODO callback
        video_view.setOnPreparedListener { mp ->
            play_pause_button.isEnabled = true
            mp?.let {
                video_seek_bar.max = mp.duration // sets the seek bar end to video end,
                // duration is an int, it is millisecond scrubbing
            }
        }
    }

    //TODO 8: Pause the video when onStop is called, if user navigates away from the screen
    override fun onStop() {
        super.onStop()
        video_view.pause()
    }

    //TODO 7: Notice that we create an overflow menu to handle navigation between activities
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_file, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //TODO 6: Notice that we will use an overflow menu to navigate to the ExoPlayerActivity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
                startActivity(Intent(this@MainActivity, ExoPlayerActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

}
