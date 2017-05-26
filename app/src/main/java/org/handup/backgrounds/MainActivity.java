package org.handup.backgrounds;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.handup.backgrounds.adapters.ImagesAdapter;
import org.handup.backgrounds.fragments.ImageFragment;
import org.handup.backgrounds.models.ImageData;
import org.handup.backgrounds.utils.GridSpacingItemDecoration;
import org.handup.backgrounds.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rvImages)
    RecyclerView rvImages;

    private DatabaseReference mDatabase;
    private List<ImageData> imageDatas;
    private ImagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {

            // Set the status bar to dark-semi-transparentish
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        setSupportActionBar(toolbar);

        imageDatas = new ArrayList<>();
        rvImages.setLayoutManager(new GridLayoutManager(this, 2));
        rvImages.addItemDecoration(new GridSpacingItemDecoration(2, 4, true, 0));

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ImageData imageData = snapshot.getValue(ImageData.class);
                    imageDatas.add(imageData);
                }

                adapter = new ImagesAdapter(MainActivity.this, imageDatas);
                rvImages.setAdapter(adapter);

                adapter.setOnItemClickListener(new ImagesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Utils.slideFragment(R.id.container, ImageFragment.newInstance(adapter.getImageData(position).getLink()), getSupportFragmentManager());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
