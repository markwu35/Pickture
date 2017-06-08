package edu.ucsb.ece.ece150.pickture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/*
 * Whatever you do, remember: whatever gets the job done is a good first solution.
 * Then start to redo it, keeping the job done, but the solutions more and more elegant.
 *
 * Don't satisfy yourself with the first thing that comes out.
 * Dig through the documentation, read your error logs.
 */
public class MainActivity extends AppCompatActivity {
    static final String PREFS_NAME = "MyPrefFile";
    static final int REQUEST_IMAGE_PICK = 2;
    Uri path = Uri.parse("android.resource://edu.ucsb.ece.ece150.pickture/" + R.drawable.gaucho);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView profileImage = (ImageView) this.findViewById(R.id.profile_image);
        SharedPreferences myPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedImage = myPreferences.getString("savedImage", "");
        if (savedImage.isEmpty()) {
            profileImage.setImageURI(path);
        } else {
            profileImage.setImageURI(Uri.parse(savedImage));
        }
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: the click event should lead us to the gallery here:
                //Intent intent = new Intent(MainActivity.this, GallaryActivity.this);
                //startActivity(intent);

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,""), REQUEST_IMAGE_PICK);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // TODO: this part may need some coding
        SharedPreferences myPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putString("savedImage", path.toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO: this part may need some coding
        SharedPreferences myPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedImage = myPreferences.getString("savedImage", "");
    }

    // depending on how you are going to pass information back and forth, you might need this
    // uncommented and filled out:

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: I bring news from the nether!
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_PICK){
            if(resultCode == RESULT_OK){
                path = data.getData();
                ImageView imageView = (ImageView) findViewById(R.id.profile_image);
                imageView.setImageURI(path);
            }
        }
    }

}
