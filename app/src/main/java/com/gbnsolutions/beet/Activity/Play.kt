package com.gbnsolutions.beet.Activity

import android.R.attr
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import com.gbnsolutions.beet.Model.SongFetch
import com.gbnsolutions.beet.Model.TrackFiles
import com.gbnsolutions.beet.Notification.ActionPlay
import com.gbnsolutions.beet.Notification.ApplicationClass.*
import com.gbnsolutions.beet.Notification.MusicService
import com.gbnsolutions.beet.Notification.NotificationReciever
import com.gbnsolutions.beet.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import android.R.attr.src
import android.content.res.Resources
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class Play : AppCompatActivity(), ActionPlay, ServiceConnection {
    lateinit var toolbar: Toolbar
    lateinit var play: ImageButton
    lateinit var prev: ImageButton
    lateinit var Next: ImageButton
    lateinit var back: ImageView
    lateinit var repeat: ImageButton
    lateinit var shuffle: ImageButton
    lateinit var Simage: ImageView
    lateinit var upSimage: ImageView
    lateinit var upSname: TextView
    lateinit var upArtist: TextView
    lateinit var upList: TextView
    lateinit var playerSeekBar: SeekBar
    lateinit var title: TextView
    var isplaying = false
    var trackFilesArrayList: ArrayList<TrackFiles> = ArrayList()
    private var handler: Handler = Handler()
    lateinit var start: TextView
    lateinit var end: TextView
    lateinit var sname: TextView
    lateinit var player: MediaPlayer
    lateinit var playlist: ArrayList<SongFetch>
    var musicService: MusicService? = null
    var mediaSession: MediaSessionCompat? = null
    var position = 0
    var i1 = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        toolbar = findViewById(R.id.playtoolbar)
        play = findViewById(R.id.play)
        prev = findViewById(R.id.previous)
        Next = findViewById(R.id.next)
        back = findViewById(R.id.back)
        title = findViewById(R.id.title)
        sname = findViewById(R.id.sname)
        repeat = findViewById(R.id.repeat)
        shuffle = findViewById(R.id.shuffle)
        start = findViewById(R.id.starttime)
        end = findViewById(R.id.endtime)
        Simage = findViewById(R.id.songimage)
        playerSeekBar = findViewById(R.id.seekbar)
        upSimage = findViewById(R.id.upSimage)
        upArtist = findViewById(R.id.upArtist)
        upSname = findViewById(R.id.upSname)
        upList = findViewById(R.id.List)
        title.text = "Title"
        mediaSession = MediaSessionCompat(this, "Player")
        populateFiles()
        val sl = intent!!.getStringExtra("c")
        playlist = arrayListOf<SongFetch>()
        val ref = FirebaseDatabase.getInstance().reference.child("songs")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val get: SongFetch? = data.getValue(SongFetch::class.java)
                        if (get?.songsCategory == sl) {
                            playlist.add(get!!)
                            i1+=get.n
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val i = Intent(this@Play,SplashScreen::class.java)
                startActivity(i)
            }

        })
        if (intent!=null){
                try {
                    position = intent.getIntExtra("pos",0)
                    player = MediaPlayer()
                    val s = intent.getStringExtra("s")
                    player.setDataSource(s)
                    player.prepare()
                    player.start()
                    updateSeekBar()
                    title.text = intent.getStringExtra("t")
                    val im = intent.getStringExtra("i")
                    sname.text = intent.getStringExtra("a")
                    Picasso.get().load(im).placeholder(R.drawable.ic_launcher_foreground).into(Simage)
                    play.setImageResource(R.drawable.pause)
                    end.text = milliSecondsToTimer(player.duration.toLong())
                    upSname.text = playlist[0].songTitle.toString()
                    showNotification(R.drawable.pause)
                } catch (exception: Exception) {
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_SHORT).show()
                }
        }
        else{
            Toast.makeText(this, "Error Occured", Toast.LENGTH_SHORT).show()
            finish()
        }
        playerSeekBar.max = player.duration
        playerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player.seekTo(progress)
                    playerSeekBar.progress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
//        player.setOnCompletionListener {
//            play.setImageResource(R.drawable.ic_play)
//            position += 1
//            playerSeekBar.progress = 0
//        }
//        repeat.setOnClickListener {
//            player.setOnCompletionListener {
//                player.start()
//                Toast.makeText(this, "Repeat Mode Enabled!", Toast.LENGTH_LONG).show()
//            }
//            Toast.makeText(this, "Repeat Mode Enabled!", Toast.LENGTH_LONG).show()
//        }
//        sname.text = playlist[position].songTitle
        prev.setOnClickListener {
            PrevClicked()
        }
        Next.setOnClickListener {
            NextClicked()
        }
        play.setOnClickListener {
            PlayClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()
        unbindService(this)
    }

    fun populateFiles() {
        val trackFiles = TrackFiles("wolves", "english", R.drawable.wolves)
        trackFilesArrayList.add(trackFiles)
        val trackFiles1 = TrackFiles("samaja", "Thamman", R.drawable.samaja)
        trackFilesArrayList.add(trackFiles1)
        val trackFiles2 = TrackFiles("shapeofu", "english", R.drawable.shapeofu)
        trackFilesArrayList.add(trackFiles2)
//        val trackFiles3 = TrackFiles("pogiren", "tamil", R.drawable.pogiren)
//        trackFilesArrayList.add(trackFiles3)
    }
    private val updater = Runnable {
                updateSeekBar()
        val currentDuration = player.currentPosition.toLong()
        start.text = milliSecondsToTimer(currentDuration)
    }
    private fun updateSeekBar() {
        if (player.isPlaying) {
//            playerSeekBar.progress = (player.currentPosition.toFloat() / player.duration * 100).toInt()
//            sname.text = player.currentPosition.toString()
            playerSeekBar.progress = player.currentPosition
            handler.postDelayed(updater, 1000)
        }
    }
        private fun milliSecondsToTimer(milliseconds: Long): String? {
            var timeString = ""
            val secondsString: String
            val hours = (milliseconds / (1000 * 60 * 60)).toInt()
            val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
            val seconds = ((milliseconds % (1000 * 60 * 60)).toInt() % (1000 * 60) / 1000)
            if (hours > 0) {
                timeString = "$hours:"
            }
            val l = if (seconds < 10) {
                "0$minutes"
            } else {
                "" + minutes
            }
            secondsString = if (seconds < 10) {
                "0$seconds"
            } else {
                "" + seconds
            }
            timeString = "$timeString$l:$secondsString"
            return timeString
        }
    override fun NextClicked() {
        if (position == i1) {
            position = 0
        } else {
            position++
        }
        player.release()
//        player.reset()
        player.setDataSource(playlist[position].songLink)
        player.prepare()
        sname.text = playlist[position].songTitle
        Picasso.get().load(playlist[position].album_art).placeholder(R.drawable.ic_launcher_foreground).into(Simage)
        if (!isplaying) {
            showNotification(R.drawable.ic_play)
        } else {
            showNotification(R.drawable.pause)
            player.start()
        }
    }

    override fun PrevClicked() {
        if (position == 0) {
            position = i1
        } else {
            position--
        }
        player.release()
//        player.reset()
        player.setDataSource(playlist[position].songLink)
        player.prepare()
        sname.text = playlist[position].songTitle
        Picasso.get().load(playlist[position].album_art).placeholder(R.drawable.ic_launcher_foreground).into(Simage)
        if (!isplaying) {
            showNotification(R.drawable.ic_play)
        } else {
            showNotification(R.drawable.pause)
            player.start()
        }
    }

    override fun PlayClicked() {
        if (!isplaying) {
            player.start()
            isplaying = true
            play.setImageResource(R.drawable.pause)
            showNotification(R.drawable.pause)
        } else {
            player.pause()
            isplaying = false
            play.setImageResource(R.drawable.ic_play)
            showNotification(R.drawable.ic_play)
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MyBinder
        musicService = binder.getService()
        musicService!!.setCallBack(this@Play)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

    fun showNotification(play: Int) {
        val intent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val prevIntent = Intent(this, NotificationReciever::class.java).setAction(ACTION_PREV)
        val prevPendingIntent =
            PendingIntent.getBroadcast(this, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val playIntent = Intent(this, NotificationReciever::class.java).setAction(ACTION_PLAY)
        val playPendingIntent =
            PendingIntent.getBroadcast(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val nextIntent = Intent(this, NotificationReciever::class.java).setAction(ACTION_NEXT)
        val nextvPendingIntent =
            PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val picture: Bitmap = BitmapFactory.decodeResource(resources,trackFilesArrayList[position].getThumbnail()!!)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID_2)
            .setSmallIcon(trackFilesArrayList[position].getThumbnail()!!)
            .setLargeIcon(picture)
            .setContentTitle(playlist.get(position).songTitle)
            .setContentText(playlist.get(position).artist)
            .addAction(R.drawable.prev, "Previous", prevPendingIntent)
            .addAction(play, "Play", playPendingIntent)
            .addAction(R.drawable.next, "Next", nextvPendingIntent)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession!!.getSessionToken())
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(contentIntent)
            .setOnlyAlertOnce(true)
            .build()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }
}