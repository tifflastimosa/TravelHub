package edu.neu.madcourse.pettin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.madcourse.pettin.Classes.Dogs;
import edu.neu.madcourse.pettin.Classes.User;

public class AddPlayDateActivity extends AppCompatActivity {
    // layout
    SmartMaterialSpinner<String> spinner_breed;
    Spinner spinner_gender;
    Spinner spinner_spayed;
    Spinner spinner_energyLevel;
    TextView textView_playstyles;

    // date input
    EditText editText_name;
    EditText editText_age;
    EditText editText_location;
    EditText editText_weight;
    ImageView imageView1, imageView, imageView3, imageView4, imageView5, imageView6;
    Uri ImageUri =null;
    // permission
    String[] permissionsRequest = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // data output
    String currentUserId;
    String name;
    String gender;
    String spayed;
    int age;
    String breed;
    ArrayList<String> playStyles;
    Double weight;
    int energyLevel;
    String location;
    ProgressBar progressBar;
    Button button_save;
    Button button_cancel;

    // firebase
    FirebaseAuth auth;
    StorageReference storageReference;
    FirebaseFirestore db;
    FirebaseUser curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_play_date);
        Context context = this;
        //init breed spinner
        initSpinner();
        //photos
        imageView = findViewById(R.id.image_view_2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("image", "clicked");
                System.out.println("permission: " + checkPermissions());
                if (!checkPermissions()) {
                    getPermissions();
                }
                if (checkPermissions()) {
                    CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(AddPlayDateActivity.this);
                    builder.setTitle("Add a photo of your four-leg friend");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (options[item].equals("Take Photo"))
                            {
                                cameraIntent();
                            } else if (options[item].equals("Choose from Gallery"))
                            {
                                galleryIntent();
                            } else if (options[item].equals("Cancel")) {
                                dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
            }
        });

        //name
        editText_name = findViewById(R.id.editText_name);
        //age
        editText_age = findViewById(R.id.editText_age);
        // weight
        editText_weight = findViewById(R.id.editText_weight);

        //gender
        spinner_gender = findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(gender_adapter);
        //spayed
        spinner_spayed = findViewById(R.id.spinner_spayed);
        String[] spayed_array = {"Yes", "No"};
        ArrayAdapter<String> spayed_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spayed_array);
        spinner_spayed.setAdapter(spayed_adapter);
        //Energy Level
        spinner_energyLevel = findViewById(R.id.spinner_energyLevel);
        Integer[] energyLevel_array = {1, 2, 3, 4, 5};
        ArrayAdapter<Integer> energyLevel_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, energyLevel_array);
        spinner_energyLevel.setAdapter(energyLevel_adapter);
        //play style
        textView_playstyles = findViewById(R.id.textView_playStyles);
        String[] playArray = {"Balls", "Hiking", "Jogging", "Wrestle", "Tugger", "Chaser", "Other"};
        boolean[] selectedPlayStyles = new boolean[playArray.length];
        ArrayList<Integer> playList = new ArrayList<>();
        playStyles = new ArrayList<>();
        textView_playstyles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddPlayDateActivity.this);
                builder.setTitle("Select Play Styles");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(playArray, selectedPlayStyles, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            playList.add(i);
                            Collections.sort(playList);
                        } else {
                            playList.remove(Integer.valueOf(i));
                        }
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder playListStringBuilder = new StringBuilder();
                        for (int j=0; j<playList.size(); j++) {
                            playListStringBuilder.append(playArray[playList.get(j)]);
                            playStyles.add(playArray[playList.get(j)]);
                            if (j!=playList.size()-1){
                                playListStringBuilder.append(", ");
                            }
                        }
                        textView_playstyles.setText(playListStringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        //location
        editText_location = findViewById(R.id.editText_loc);

        progressBar = findViewById(R.id.progressBar_addPlayDate);
        progressBar.setVisibility(View.INVISIBLE);

        // Firebase
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        curUser = auth.getCurrentUser();

        button_save = findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                name = editText_name.getText().toString();
                gender = spinner_gender.getSelectedItem().toString();
                spayed = spinner_spayed.getSelectedItem().toString();



                energyLevel = Integer.parseInt(spinner_energyLevel.getSelectedItem().toString());
                location = editText_location.getText().toString();

                if ( curUser != null) {
                    if (!name.isEmpty() && ImageUri != null) {

                        if (isInteger(editText_age.getText().toString())) {
                            age = Integer.parseInt(editText_age.getText().toString());
                            if (isNumeric(editText_weight.getText().toString())) {
                                weight = Double.parseDouble(editText_weight.getText().toString());
                                if (spinner_breed.getSelectedItem()!=null) {
                                    breed = spinner_breed.getSelectedItem().toString();
                                    StorageReference dogPhotoRef = storageReference.child("dog_photo").child(FieldValue.serverTimestamp().toString() + ".jpg");
                                    dogPhotoRef.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                dogPhotoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        Map<String, Object> playdatePost = new HashMap<>();
                                                        Timestamp ts = new Timestamp(new Date());
                                                        Dogs dog = new Dogs(name, gender, spayed, age, breed, playStyles, weight, energyLevel, uri.toString(), location, currentUserId, ts, new HashMap<>(), new HashMap<>());
                                                        CollectionReference dogRef = db.collection("dogs");
                                                        String dog_id = dog.getDog_id();
                                                        dogRef.document(dog_id).set(dog);
                                                        Toast.makeText(AddPlayDateActivity.this, "Playdate added successfully", Toast.LENGTH_SHORT).show();
                                                        String userId = curUser.getUid();
                                                        DocumentReference userRef = db.collection("users").document(userId);
                                                        userRef.update("dogs", FieldValue.arrayUnion(dog.getDog_id()));
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        finish();
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(AddPlayDateActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(AddPlayDateActivity.this, "Please choose a breed.", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                Toast.makeText(AddPlayDateActivity.this, "Please input numbers for weight.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            Toast.makeText(AddPlayDateActivity.this, "Please input an integer for age.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Toast.makeText(AddPlayDateActivity.this, "Please input your dog's name and attach a photo.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(AddPlayDateActivity.this, "Please sign in.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }
        });

        button_cancel = findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean checkPermissions() {
        return (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED);
    }

    private void getPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},100);
    }

    private void galleryIntent() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openGallery.launch(gallery);

    }

    private void cameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startCamera.launch(cameraIntent);

    }

    private boolean isNumeric(String weight) {
        if (weight == null) {
            return false;
        }
        try {
            Double.parseDouble(weight);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean isInteger(String age) {
        if (age == null) {
            return false;
        }
        try {
            Integer.parseInt(age);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    ActivityResultLauncher<Intent> startCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                // TODO keep photo on imageView when rotate
                // TODO get correct direction for photo taken by camera
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Bundle bundle = data.getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 350, 350, true);
                        Matrix mtx = new Matrix();
                        mtx.postRotate(90);
                        scaled = Bitmap.createBitmap(scaled, 0, 0, 350, 350, mtx, true);
                        imageView.setImageBitmap(scaled);
                    }
                }
            });
    ActivityResultLauncher<Intent> openGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        ImageUri = data.getData();
                        System.out.println(ImageUri);
                        imageView.setImageURI(ImageUri);
                    }
                }
            });

    private void initSpinner() {
        //breed
        spinner_breed = findViewById(R.id.spinner_breed);
        String[] breedArray = {"Other mix","Affenpinscher", "Afghan Hound", "Aidi", "Airedale Terrier", "Akbash Dog", "Akita", "Alano Español", "Alaskan Klee Kai", "Alaskan Malamute", "Alpine Dachsbracke", "Alpine Spaniel", "American Bulldog", "American Cocker Spaniel",
                "American Eskimo Dog", "American Foxhound", "American Hairless Terrier", "American Pit Bull Terrier", "American Staffordshire Terrier", "American Water Spaniel", "Anglo-Français de Petite Vénerie", "Appenzeller Sennenhund", "Ariege Pointer", "Ariegeois",
                "Armant", "Armenian Gampr dog", "Artois Hound", "Australian Cattle Dog", "Australian Kelpie", "Australian Shepherd", "Australian Silky Terrier", "Australian Stumpy Tail Cattle Dog", "Australian Terrier", "Azawakh", "Bakharwal Dog", "Barbet", "Basenji", "Basque Shepherd Dog", "Basset Artésien Normand", "Basset Bleu de Gascogne", "Basset Fauve de Bretagne", "Basset Hound", "Bavarian Mountain Hound", "Beagle", "Beagle-Harrier",
                "Bearded Collie", "Beauceron", "Bedlington Terrier", "Belgian Shepherd Dog (Groenendael)", "Belgian Shepherd Dog (Laekenois)", "Belgian Shepherd Dog (Malinois)", "Bergamasco Shepherd", "Berger Blanc Suisse", "Berger Picard", "Berner Laufhund", "Bernese Mountain Dog", "Billy", "Bichon Frise", "Black and Tan Coonhound", "Black and Tan Virginia Foxhound", "Black Norwegian Elkhound",
                "Black Russian Terrier", "Bloodhound", "Blue Lacy", "Blue Paul Terrier", "Boerboel", "Bohemian Shepherd", "Bolognese", "Border Collie", "Border Terrier", "Borzoi", "Boston Terrier", "Bouvier des Ardennes", "Bouvier des Flandres", "Boxer", "Boykin Spaniel", "Bracco Italiano", "Braque d'Auvergne", "Braque du Bourbonnais", "Braque du Puy", "Braque Francais", "Braque Saint-Germain", "Brazilian Terrier", "Briard", "Briquet Griffon Vendéen", "Brittany",
                "Broholmer", "Bruno Jura Hound", "Bucovina Shepherd Dog", "Bull and Terrier", "Bull Terrier (Miniature)", "Bull Terrier", "Bulldog", "Bullenbeisser", "Bullmastiff", "Bully Kutta", "Burgos Pointer", "Cairn Terrier", "Canaan Dog", "Canadian Eskimo Dog", "Cane Corso", "Cardigan Welsh Corgi", "Carolina Dog", "Carpathian Shepherd Dog", "Catahoula Cur", "Catalan Sheepdog", "Caucasian Shepherd Dog", "Cavalier King Charles Spaniel", "Central Asian Shepherd Dog", "Cesky Fousek", "Cesky Terrier", "Chesapeake Bay Retriever", "Chien Français Blanc et Noir", "Chien Français Blanc et Orange", "Chien Français Tricolore", "Chien-gris",
                "Chihuahua", "Chilean Fox Terrier", "Chinese Chongqing Dog", "Chinese Crested Dog", "Chinese Imperial Dog", "Chinook", "Chippiparai", "Chow Chow", "Cierny Sery", "Cimarrón Uruguayo", "Cirneco dell'Etna", "Clumber Spaniel", "Combai", "Cordoba Fighting Dog", "Coton de Tulear", "Cretan Hound", "Croatian Sheepdog", "Cumberland Sheepdog", "Curly Coated Retriever", "Cursinu", "Cão da Serra de Aires",
                "Cão de Castro Laboreiro", "Cão Fila de São Miguel", "Dachshund", "Dalmatian", "Dandie Dinmont Terrier", "Danish Swedish Farmdog", "Deutsche Bracke", "Doberman Pinscher", "Dogo Argentino", "Dogo Cubano", "Dogue de Bordeaux", "Drentse Patrijshond", "Drever", "Dunker", "Dutch Shepherd Dog", "Dutch Smoushond", "East Siberian Laika", "East-European Shepherd", "Elo", "English Cocker Spaniel", "English Foxhound", "English Mastiff", "English Setter", "English Shepherd", "English Springer Spaniel", "English Toy Terrier (Black &amp; Tan)", "English Water Spaniel", "English White Terrier", "Entlebucher Mountain Dog", "Estonian Hound", "Estrela Mountain Dog", "Eurasier", "Field Spaniel", "Fila Brasileiro",
                "Finnish Hound", "Finnish Lapphund", "Finnish Spitz", "Flat-Coated Retriever", "Formosan Mountain Dog", "Fox Terrier (Smooth)", "French Bulldog", "French Spaniel", "Galgo Español", "Gascon Saintongeois", "German Longhaired Pointer", "German Pinscher", "German Shepherd", "German Shorthaired Pointer", "German Spaniel", "German Spitz", "German Wirehaired Pointer", "Giant Schnauzer",
                "Glen of Imaal Terrier", "Golden Retriever","Golden Doodle", "Gordon Setter", "Gran Mastín de Borínquen", "Grand Anglo-Français Blanc et Noir", "Grand Anglo-Français Blanc et Orange", "Grand Anglo-Français Tricolore", "Grand Basset Griffon Vendéen", "Grand Bleu de Gascogne", "Grand Griffon Vendéen", "Great Dane", "Great Pyrenees", "Greater Swiss Mountain Dog", "Greek Harehound",
                "Greenland Dog", "Greyhound", "Griffon Bleu de Gascogne", "Griffon Bruxellois", "Griffon Fauve de Bretagne", "Griffon Nivernais", "Hamiltonstövare", "Hanover Hound", "Hare Indian Dog", "Harrier", "Havanese", "Hawaiian Poi Dog", "Himalayan Sheepdog", "Hokkaido", "Hovawart", "Huntaway", "Hygenhund", "Ibizan Hound", "Icelandic Sheepdog", "Indian pariah dog", "Indian Spitz", "Irish Red and White Setter", "Irish Setter", "Irish Terrier", "Irish Water Spaniel",
                "Irish Wolfhound", "Istrian Coarse-haired Hound", "Istrian Shorthaired Hound", "Italian Greyhound", "Jack Russell Terrier", "Jagdterrier", "Jämthund", "Kai Ken", "Kaikadi", "Kanni", "Karelian Bear Dog", "Karst Shepherd", "Keeshond", "Kerry Beagle", "Kerry Blue Terrier", "King Charles Spaniel", "King Shepherd", "Kintamani", "Kishu", "Komondor", "Kooikerhondje", "Koolie", "Korean Jindo Dog", "Kromfohrländer", "Kumaon Mastiff", "Kurī", "Kuvasz", "Kyi-Leo",
                "Labrador Husky", "Labrador Retriever", "Lagotto Romagnolo", "Lakeland Terrier", "Lancashire Heeler", "Landseer", "Lapponian Herder", "Large Münsterländer", "Leonberger", "Lhasa Apso", "Lithuanian Hound", "Longhaired Whippet", "Löwchen", "Mahratta Greyhound", "Maltese", "Manchester Terrier", "Maremma Sheepdog", "McNab", "Mexican Hairless Dog", "Miniature American Shepherd",
                "Miniature Australian Shepherd", "Miniature Fox Terrier", "Miniature Pinscher", "Miniature Schnauzer", "Miniature Shar Pei", "Molossus", "Montenegrin Mountain Hound", "Moscow Watchdog", "Moscow Water Dog", "Mountain Cur", "Mucuchies", "Mudhol Hound", "Mudi", "Neapolitan Mastiff", "New Zealand Heading Dog", "Newfoundland", "Norfolk Spaniel", "Norfolk Terrier", "Norrbottenspets", "North Country Beagle", "Northern Inuit Dog", "Norwegian Buhund", "Norwegian Elkhound", "Norwegian Lundehund", "Norwich Terrier", "Old Croatian Sighthound", "Old Danish Pointer", "Old English Sheepdog", "Old English Terrier", "Old German Shepherd Dog", "Olde English Bulldogge", "Otterhound", "Pachon Navarro", "Paisley Terrier", "Pandikona", "Papillon", "Parson Russell Terrier", "Patterdale Terrier", "Pekingese", "Pembroke Welsh Corgi", "Perro de Presa Canario",
                "Perro de Presa Mallorquin", "Peruvian Hairless Dog", "Petit Basset Griffon Vendéen", "Petit Bleu de Gascogne", "Phalène", "Pharaoh Hound", "Phu Quoc ridgeback dog", "Picardy Spaniel", "Plott Hound", "Podenco Canario", "Pointer (dog breed)", "Polish Greyhound", "Polish Hound", "Polish Hunting Dog", "Polish Lowland Sheepdog", "Polish Tatra Sheepdog", "Pomeranian", "Pont-Audemer Spaniel", "Poodle", "Porcelaine", "Portuguese Podengo", "Portuguese Pointer",
                "Portuguese Water Dog", "Posavac Hound", "Pražský Krysařík", "Pudelpointer", "Pug", "Puli", "Pumi", "Pungsan Dog", "Pyrenean Mastiff", "Pyrenean Shepherd", "Rafeiro do Alentejo", "Rajapalayam", "Rampur Greyhound", "Rastreador Brasileiro", "Rat Terrier", "Ratonero Bodeguero Andaluz", "Redbone Coonhound", "Rhodesian Ridgeback", "Rottweiler", "Rough Collie", "Russell Terrier", "Russian Spaniel", "Russian tracker", "Russo-European Laika", "Sabueso Español",
                "Saint-Usuge Spaniel", "Sakhalin Husky", "Saluki", "Samoyed", "Sapsali", "Schapendoes", "Schillerstövare", "Schipperke", "Schweizer Laufhund", "Schweizerischer Niederlaufhund", "Scotch Collie", "Scottish Deerhound", "Scottish Terrier", "Sealyham Terrier", "Segugio Italiano", "Seppala Siberian Sleddog", "Serbian Hound", "Serbian Tricolour Hound", "Shar Pei", "Shetland Sheepdog", "Shiba Inu", "Shih Tzu", "Shikoku", "Shiloh Shepherd Dog", "Siberian Husky", "Silken Windhound", "Sinhala Hound", "Skye Terrier", "Sloughi",
                "Slovak Cuvac", "Slovakian Rough-haired Pointer", "Small Greek Domestic Dog", "Small Münsterländer", "Smooth Collie", "South Russian Ovcharka", "Southern Hound", "Spanish Mastiff", "Spanish Water Dog", "Spinone Italiano", "Sporting Lucas Terrier", "St. Bernard", "St. John's water dog", "Stabyhoun", "Staffordshire Bull Terrier", "Standard Schnauzer", "Stephens Cur", "Styrian Coarse-haired Hound", "Sussex Spaniel", "Swedish Lapphund", "Swedish Vallhund", "Tahltan Bear Dog", "Taigan",
                "Talbot", "Tamaskan Dog", "Teddy Roosevelt Terrier", "Telomian", "Tenterfield Terrier", "Thai Bangkaew Dog", "Thai Ridgeback", "Tibetan Mastiff", "Tibetan Spaniel", "Tibetan Terrier", "Tornjak", "Tosa", "Toy Bulldog", "Toy Fox Terrier", "Toy Manchester Terrier", "Toy Trawler Spaniel", "Transylvanian Hound", "Treeing Cur", "Treeing Walker Coonhound",
                "Trigg Hound", "Tweed Water Spaniel", "Tyrolean Hound", "Vizsla", "Volpino Italiano", "Weimaraner", "Welsh Sheepdog", "Welsh Springer Spaniel", "Welsh Terrier", "West Highland White Terrier", "West Siberian Laika", "Westphalian Dachsbracke", "Wetterhoun", "Whippet", "White Shepherd", "Wire Fox Terrier", "Wirehaired Pointing Griffon", "Wirehaired Vizsla", "Yorkshire Terrier", "Šarplaninac"};
        List<String> breedList = new ArrayList<String>(Arrays.asList(breedArray));


        spinner_breed.setItem(breedList);
        spinner_breed.setSelected(true);
        spinner_breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(AddPlayDateActivity.this, breedList.get(position), Toast.LENGTH_SHORT).show();}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}