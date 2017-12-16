package com.inderjs.olaplaystudios;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SongFragment extends Fragment implements MediaPlayer.OnCompletionListener {

    private String PLAY_URL =
            "http://starlord.hackerearth.com/studio";


    private SongsAdapter mAdapter;
    private CardView mSongCard;
    private TextView mSongCardTv, mArtistCardTv;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_song, container, false);


        SongAsyncTask task = new SongAsyncTask();
        task.execute(PLAY_URL);


        mSongCard = (CardView)rootView.findViewById(R.id.playCard);
        mSongCardTv = (TextView)rootView.findViewById(R.id.songNameCardTv);
        mArtistCardTv = (TextView)rootView.findViewById(R.id.artistCardTv);

        mSongCard.setVisibility(View.GONE);

        final ListView songListView = (ListView)rootView.findViewById(R.id.list);
        mAdapter = new SongsAdapter(getActivity(), new ArrayList<Song>());
        songListView.setAdapter(mAdapter);

        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song = mAdapter.getItem(i);
                Uri songUri = Uri.parse(song.getmUrl());

                Toast.makeText(getActivity(), songUri.toString(), Toast.LENGTH_SHORT).show();


                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }


                mSongCard.setVisibility(View.VISIBLE);
                mSongCardTv.setText(song.getmSongName());
                mArtistCardTv.setText(song.getmArtist());
                SongPlayAsyncTask taskPlay = new SongPlayAsyncTask();
                taskPlay.execute(songUri.toString());

            }
        });

        return rootView;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }


    private class SongAsyncTask extends AsyncTask<String,Void,List<Song>> {

        private ProgressDialog progressDialog;

        @Override
        protected List<Song> doInBackground(String... strings) {
            List<Song> result = QueryUtils.fetchSongData(PLAY_URL);
            return result;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(List<Song> data) {
            mAdapter.clear();
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            if (data != null && !data.isEmpty()){
                mAdapter.addAll(data);
            }
        }
    }




    private class SongPlayAsyncTask extends AsyncTask<String,Void,String> {

        private ProgressDialog progressDialog;


        @Override
        protected String doInBackground(String... params) {

            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(params[0]);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception e) {
                // TODO: handle exception
            }

            return null;
        }

        @Override
        protected void onPreExecute() {

        }

    }





}
