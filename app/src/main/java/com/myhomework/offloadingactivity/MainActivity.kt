package com.myhomework.offloadingactivity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.myhomework.offloadingactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var videoURL = "https://media.istockphoto.com/id/1311613463/video/the-graphic-designer-chooses-colors-from-the-color-palette-guide-discover-the-best-pantone.mp4?s=mp4-640x640-is&k=20&c=OokAqg-6Ro8hmfE7U6iI7Mi8IprsCgGAap7yMhN20wA="
    lateinit var player: ExoPlayer

    var playWhenReady = true
    var currentWindow = 0
    var playBackPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun initVideo(){
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player;

        var uri = Uri.parse(videoURL)
        var dataSource = DefaultDataSourceFactory(this, "exoplayer-codelab")
        val mediaItem: MediaItem = MediaItem.fromUri(uri)
        var media = ProgressiveMediaSource.Factory(dataSource).createMediaSource(mediaItem)

        player.playWhenReady = playWhenReady
        player.seekTo(currentWindow,playBackPosition.toLong())
        player.prepare(media,false,false)

    }

    fun releaseVideo(){
        if(player != null){
            playWhenReady = player.playWhenReady
            playBackPosition = player.currentPosition.toInt()
            currentWindow = player.currentWindowIndex
            player.release()
        }
    }

    override fun onStart() {
        super.onStart()
        initVideo()
    }

    override fun onResume() {
        super.onResume()
        if(player != null){
            initVideo()
        }
    }

    override fun onPause() {
        super.onPause()
        releaseVideo()
    }

    override fun onStop() {
        super.onStop()
        releaseVideo()
    }
}