package com.example.livestockvendorapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class UploadActivity extends AppCompatActivity {

    private String Description, Price, Pname, weight;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPrice, InputProductweight;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl, Type;
    private StorageReference AnimalImagesRef;
    private DatabaseReference AnimalsRef;

    FirebaseFirestore db;
    ChipGroup chipGroup;
    ImagePicker imagePicker;
    AlertDialog dialog;


    private final int PERMISSION_ALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        final String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        };

        AnimalImagesRef = FirebaseStorage.getInstance().getReference();
        AnimalsRef = FirebaseDatabase.getInstance().getReference("Animal");

        db = FirebaseFirestore.getInstance();

        dialog = new SpotsDialog.Builder().setContext(UploadActivity.this).setCancelable(true).build();
        AddNewProductButton = findViewById(R.id.add_new_product);
        InputProductImage = findViewById(R.id.select_product_image);
        InputProductName = findViewById(R.id.product_name);
        InputProductDescription = findViewById(R.id.product_description);
        InputProductPrice = findViewById(R.id.product_price);
        InputProductweight = findViewById(R.id.product_weight);

        chipGroup = findViewById(R.id.chip_group);


        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasPermissions(UploadActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(UploadActivity.this, PERMISSIONS, PERMISSION_ALL);

                } else {
                    imagePicker.choosePicture(true /*show camera intents*/);
                }

            }
        });

        imagePicker = new ImagePicker(this, /* activity non null*/
                null, /* fragment nullable*/
                new OnImagePickedListener() {
                    @Override
                    public void onImagePicked(Uri imageUri) {
                        Picasso.get().load(imageUri).into(InputProductImage);
                        ImageUri = imageUri;
//                        UCrop.of(imageUri, getTempUri())
//                                .withAspectRatio(1, 1)
//                                .start(UploadActivity.this);


                    }
                });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });


        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                Chip chip = chipGroup.findViewById(i);
                if (chip != null) {
                    Toast.makeText(getApplicationContext(), "" + chip.getText(), Toast.LENGTH_SHORT).show();
                    Type = chip.getText().toString();

                }
            }
        });

    }


    private Uri getTempUri() {
        String dir = Environment.getExternalStorageDirectory() + File.separator + "Temp";
        File dirfile = new File(dir);
        dirfile.mkdir();

        String file = dir + File.separator + "temp.png";
        File tempFile = new File(file);
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Uri.fromFile(tempFile);
    }


    private void ValidateProductData() {
        weight = InputProductweight.getText().toString() + "kg";
        Price = "Rs:" + InputProductPrice.getText().toString();
        Pname = InputProductName.getText().toString();
        Description = InputProductDescription.getText().toString();

        if (ImageUri == null) {
            Toast.makeText(this, "Animal image is mandatory...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(weight) || TextUtils.isEmpty(Price) || TextUtils.isEmpty(Pname) || Type.isEmpty()) {
            Toast.makeText(this, "Please enter valid Details...", Toast.LENGTH_SHORT).show();

        } else {
            dialog.show();
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {

//

        productRandomKey = String.valueOf(System.currentTimeMillis());

        final StorageReference filePath = AnimalImagesRef.child("Animal/" + productRandomKey + ".png");
        filePath.putFile(ImageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Error", "On failure " + e.getMessage());
                Toast.makeText(getApplicationContext(), "Image not uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadImageUrl = uri.toString();
                        //InputProductImage.setImageURI(null);
                        //Picasso.get().load(uri).into(InputProductImage);
                        if (downloadImageUrl != null) {
                            SaveProductInfoToDatabase();
                        } else {

                            dialog.dismiss();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

            }
        });


    }

    private void SaveProductInfoToDatabase() {
        AnimalsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(productRandomKey).exists()) {
                    //Toast.makeText(getApplicationContext(), "Product exits", Toast.LENGTH_SHORT).show();
                } else {

                    Map<String, Object> map = new HashMap<>();

                    map.put("Cost", Double.parseDouble(InputProductPrice.getText().toString()));
                    map.put("Name", InputProductName.getText().toString());
                    map.put("Weight", weight);
                    map.put("Description", Description);
                    map.put("Image", downloadImageUrl);
                    map.put("Seller_id", Common.currentuser.getPhone());
                    map.put("Type", Type);
                    map.put("Status", "Not Sell");

                    db.collection("Animal")
                            .add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("fileupload", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("fileupload", "Error adding document", e);
                        }
                    });
//                    Animal object = new Animal(Price, Description, downloadImageUrl, Pname, Common.currentuser.getPhone(), Type, "NotSell");
//                    AnimalsRef.child(productRandomKey).setValue(object);
                    Toast.makeText(getApplicationContext(), "Object added", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    finish();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            InputProductImage.setImageURI(null);
            ImageUri = resultUri;
            //   Picasso.get().load(resultUri).into(InputProductImage);
            InputProductImage.setImageURI(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }

    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);

        switch (requestCode) {
            case PERMISSION_ALL: {
                if (grantResults.length > 0) {
                    boolean flag = true;
                    for (int i = 0; i < permissionsList.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            flag = false;

                        }

                    }
                    // Show permissionsDenied
                    if (flag) {
                        imagePicker.choosePicture(true /*show camera intents*/);

                    }

                }
                return;
            }
        }
    }

}