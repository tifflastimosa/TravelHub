package edu.neu.madcourse.pettin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.neu.madcourse.pettin.Classes.Dogs;
import edu.neu.madcourse.pettin.Classes.User;
import io.grpc.internal.JsonUtil;

public class PlayDateActivity extends AppCompatActivity implements DogPlayDateAdapter.OnDogListener {
    BottomNavigationView bottomNav;
    Button button_addPlaydate;

    RecyclerView recyclerView;
    DogPlayDateAdapter dogPlayDateAdapter;
    ArrayList<Dogs> dogs;
    List<String> dislikeDogs;
    List<String> ownedDogs;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    FirebaseUser curUser;
    String userName;

    ImageView searchButton;
    EditText searchText;

    ImageView filter;
    SmartMaterialSpinner<String> spinner_filter_breed;
    String filterBreed;
    String filterGender;
    String filterSpayed;
    int filterAgeLow, filterAgeHigh;
    int filterWeightLow, filterWeightHigh;
    int filterEnergyLow, filterEnergyHigh;
    List<String> filterPS = new ArrayList<>();

    @Override
    protected void onPause() {
        super.onPause();
        bottomNav.setSelectedItemId(R.id.nav_playdate);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_date);

        searchButton = findViewById(R.id.imageview_search);
        searchText = findViewById(R.id.editText_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchInput = searchText.getText().toString();
                applySearch(searchInput);
            }
        });

        button_addPlaydate = findViewById(R.id.button_addPlaydate);
        button_addPlaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddPlayDateActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerView_playdate);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        dislikeDogs = new ArrayList<>();
        ownedDogs = new ArrayList<>();

        curUser = firebaseAuth.getCurrentUser();


        if (curUser != null) {
            String userId = curUser.getUid();
            DocumentReference userRef = db.collection("users").document(userId);
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    userName = user.getUsername();
                    dislikeDogs = user.getDislikeDog();
                    ownedDogs = user.getDogs();
                }
            });
        }

        dogs = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dogPlayDateAdapter = new DogPlayDateAdapter(PlayDateActivity.this, dogs, this);
        recyclerView.setAdapter(dogPlayDateAdapter);

        fetchPlayDate();

        // swipe to refresh
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swipeLayout);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dogs.clear();
                fetchPlayDate();
                pullToRefresh.setRefreshing(false);
            }
        });

        // click for advanced filter
        filter = findViewById(R.id.imageview_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });

        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_playdate);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_chat:
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_playdate:
                    return true;
                case R.id.nav_post:
                    startActivity(new Intent(getApplicationContext(), PostActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_restaurant:
                    startActivity(new Intent(getApplicationContext(), RestaurantsNearByMeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void fetchPlayDate() {
        CollectionReference playRef = db.collection("dogs");
        playRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Dogs dog = document.toObject(Dogs.class);
                        if ((!dislikeDogs.contains(dog.getDog_id())) && (!ownedDogs.contains(dog.getDog_id()))) {
                            dogs.add(dog);
                            dogPlayDateAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Log.d("fetch playdate", "failed", task.getException());
                }
            }
        });
    }

    private void applyFilter() {
        dogs.removeIf(dog -> ((!filterBreed.equals("All") && !filterBreed.equals(dog.getBreed()))
                || dog.getAge() > filterAgeHigh || dog.getAge() < filterAgeLow) ||
                dog.getWeight() > filterWeightHigh || dog.getWeight() < filterWeightLow ||
                dog.getEnergyLevel() > filterEnergyHigh || dog.getEnergyLevel() < filterEnergyLow ||
                !filterGender.contains(dog.getGender()) || !filterSpayed.contains(dog.getSpayed()));
        List<Dogs> psFilter = new ArrayList<>();

        for (String ps: filterPS) {
            for (Dogs dog: dogs) {
                if (!dog.getPlayStyles().contains(ps)) {
                    psFilter.add(dog);
                }
            }
        }
        dogs.removeAll(psFilter);
        dogPlayDateAdapter.notifyDataSetChanged();
    }

    private void applySearch(String searchInput) {
        dogs.removeIf(dog -> !dog.getName().toLowerCase().contains(searchInput.toLowerCase()));
        if (dogs.isEmpty()) {
            Toast.makeText(this, "No dog found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "find possible matches", Toast.LENGTH_SHORT).show();
            dogPlayDateAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDogClick(int position) {
        Intent intent = new Intent(this, SingleDogActivity.class);
        String name = dogs.get(position).getName();
        String dogId = dogs.get(position).getDog_id();
        String gender = dogs.get(position).getGender();
        int age = dogs.get(position).getAge();
        int energyLevel = dogs.get(position).getEnergyLevel();
        Double weight = dogs.get(position).getWeight();
        String spayed = dogs.get(position).getSpayed();
        String breed = dogs.get(position).getBreed();
        String city = dogs.get(position).getLocation();
        intent.putExtra("name", name);
        intent.putExtra("dogId", dogId);
        intent.putExtra("age", age);
        intent.putExtra("gender", gender);
        intent.putExtra("energyLevel", energyLevel);
        intent.putExtra("weight", weight);
        intent.putExtra("spayed", spayed);
        intent.putExtra("breed", breed);
        intent.putExtra("city", city);
        startActivity(intent);
    }

    private void showFilterDialog() {
        final Dialog dialog = new Dialog(PlayDateActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.filter_dialog);
        spinner_filter_breed = dialog.findViewById(R.id.spinner_filter_breed);
        String[] breedArray = {"All", "Other mix", "Affenpinscher", "Afghan Hound", "Aidi", "Airedale Terrier", "Akbash Dog", "Akita", "Alano Español", "Alaskan Klee Kai", "Alaskan Malamute", "Alpine Dachsbracke", "Alpine Spaniel", "American Bulldog", "American Cocker Spaniel",
                "American Eskimo Dog", "American Foxhound", "American Hairless Terrier", "American Pit Bull Terrier", "American Staffordshire Terrier", "American Water Spaniel", "Anglo-Français de Petite Vénerie", "Appenzeller Sennenhund", "Ariege Pointer", "Ariegeois",
                "Armant", "Armenian Gampr dog", "Artois Hound", "Australian Cattle Dog", "Australian Kelpie", "Australian Shepherd", "Australian Silky Terrier", "Australian Stumpy Tail Cattle Dog", "Australian Terrier", "Azawakh", "Bakharwal Dog", "Barbet", "Basenji", "Basque Shepherd Dog", "Basset Artésien Normand", "Basset Bleu de Gascogne", "Basset Fauve de Bretagne", "Basset Hound", "Bavarian Mountain Hound", "Beagle", "Beagle-Harrier",
                "Bearded Collie", "Beauceron", "Bedlington Terrier", "Belgian Shepherd Dog (Groenendael)", "Belgian Shepherd Dog (Laekenois)", "Belgian Shepherd Dog (Malinois)", "Bergamasco Shepherd", "Berger Blanc Suisse", "Berger Picard", "Berner Laufhund", "Bernese Mountain Dog", "Billy", "Bichon Frise", "Black and Tan Coonhound", "Black and Tan Virginia Foxhound", "Black Norwegian Elkhound",
                "Black Russian Terrier", "Bloodhound", "Blue Lacy", "Blue Paul Terrier", "Boerboel", "Bohemian Shepherd", "Bolognese", "Border Collie", "Border Terrier", "Borzoi", "Boston Terrier", "Bouvier des Ardennes", "Bouvier des Flandres", "Boxer", "Boykin Spaniel", "Bracco Italiano", "Braque d'Auvergne", "Braque du Bourbonnais", "Braque du Puy", "Braque Francais", "Braque Saint-Germain", "Brazilian Terrier", "Briard", "Briquet Griffon Vendéen", "Brittany",
                "Broholmer", "Bruno Jura Hound", "Bucovina Shepherd Dog", "Bull and Terrier", "Bull Terrier (Miniature)", "Bull Terrier", "Bulldog", "Bullenbeisser", "Bullmastiff", "Bully Kutta", "Burgos Pointer", "Cairn Terrier", "Canaan Dog", "Canadian Eskimo Dog", "Cane Corso", "Cardigan Welsh Corgi", "Carolina Dog", "Carpathian Shepherd Dog", "Catahoula Cur", "Catalan Sheepdog", "Caucasian Shepherd Dog", "Cavalier King Charles Spaniel", "Central Asian Shepherd Dog", "Cesky Fousek", "Cesky Terrier", "Chesapeake Bay Retriever", "Chien Français Blanc et Noir", "Chien Français Blanc et Orange", "Chien Français Tricolore", "Chien-gris",
                "Chihuahua", "Chilean Fox Terrier", "Chinese Chongqing Dog", "Chinese Crested Dog", "Chinese Imperial Dog", "Chinook", "Chippiparai", "Chow Chow", "Cierny Sery", "Cimarrón Uruguayo", "Cirneco dell'Etna", "Clumber Spaniel", "Combai", "Cordoba Fighting Dog", "Coton de Tulear", "Cretan Hound", "Croatian Sheepdog", "Cumberland Sheepdog", "Curly Coated Retriever", "Cursinu", "Cão da Serra de Aires",
                "Cão de Castro Laboreiro", "Cão Fila de São Miguel", "Dachshund", "Dalmatian", "Dandie Dinmont Terrier", "Danish Swedish Farmdog", "Deutsche Bracke", "Doberman Pinscher", "Dogo Argentino", "Dogo Cubano", "Dogue de Bordeaux", "Drentse Patrijshond", "Drever", "Dunker", "Dutch Shepherd Dog", "Dutch Smoushond", "East Siberian Laika", "East-European Shepherd", "Elo", "English Cocker Spaniel", "English Foxhound", "English Mastiff", "English Setter", "English Shepherd", "English Springer Spaniel", "English Toy Terrier (Black &amp; Tan)", "English Water Spaniel", "English White Terrier", "Entlebucher Mountain Dog", "Estonian Hound", "Estrela Mountain Dog", "Eurasier", "Field Spaniel", "Fila Brasileiro",
                "Finnish Hound", "Finnish Lapphund", "Finnish Spitz", "Flat-Coated Retriever", "Formosan Mountain Dog", "Fox Terrier (Smooth)", "French Bulldog", "French Spaniel", "Galgo Español", "Gascon Saintongeois", "German Longhaired Pointer", "German Pinscher", "German Shepherd", "German Shorthaired Pointer", "German Spaniel", "German Spitz", "German Wirehaired Pointer", "Giant Schnauzer",
                "Glen of Imaal Terrier", "Golden Retriever", "Golden Doodle", "Gordon Setter", "Gran Mastín de Borínquen", "Grand Anglo-Français Blanc et Noir", "Grand Anglo-Français Blanc et Orange", "Grand Anglo-Français Tricolore", "Grand Basset Griffon Vendéen", "Grand Bleu de Gascogne", "Grand Griffon Vendéen", "Great Dane", "Great Pyrenees", "Greater Swiss Mountain Dog", "Greek Harehound",
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
        spinner_filter_breed.setItem(breedList);
        spinner_filter_breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(PlayDateActivity.this, breedList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        dialog.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);

        // button
        Button filter_ok = dialog.findViewById(R.id.filter_ok);
        Button filter_cancel = dialog.findViewById(R.id.filter_cancel);
        filter_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        RadioGroup genderGroup = dialog.findViewById(R.id.rg_gender);
        RadioGroup spayGroup = dialog.findViewById(R.id.rg_spay);
        RangeSlider ageSlider = dialog.findViewById(R.id.age_slider);
        RangeSlider weightSlider = dialog.findViewById(R.id.weight_slider);
        RangeSlider energySlider = dialog.findViewById(R.id.energy_slider);

        CheckBox ball = dialog.findViewById(R.id.ps_balls);
        CheckBox hiking = dialog.findViewById(R.id.ps_hiking);
        CheckBox jogging = dialog.findViewById(R.id.ps_jogging);
        CheckBox wrestle = dialog.findViewById(R.id.ps_wrestle);
        CheckBox tugger = dialog.findViewById(R.id.ps_tugger);
        CheckBox chaser = dialog.findViewById(R.id.ps_chaser);
        CheckBox other = dialog.findViewById(R.id.ps_other);


        filter_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // filter info
                if (spinner_filter_breed.getSelectedItem() != null) {
                    filterBreed = spinner_filter_breed.getSelectedItem().toString();
                }

                float alow = ageSlider.getValues().get(0);
                float ahigh = ageSlider.getValues().get(1);
                float wlow = weightSlider.getValues().get(0);
                float whigh = weightSlider.getValues().get(1);
                float elow = energySlider.getValues().get(0);
                float ehigh = energySlider.getValues().get(1);
                filterAgeLow = (int) alow;
                filterAgeHigh = (int) ahigh;
                filterWeightLow = (int) wlow;
                filterWeightHigh = (int) whigh;
                filterEnergyLow = (int) elow;
                filterEnergyHigh = (int) ehigh;
                int gender = genderGroup.getCheckedRadioButtonId();
                int spay = spayGroup.getCheckedRadioButtonId();
                RadioButton grb = (RadioButton) dialog.findViewById(gender);
                RadioButton srb = (RadioButton) dialog.findViewById(spay);
                if (grb != null && grb.getText().equals("Doesn't matter")) {
                    filterGender = "BoyGirl";
                } else if (grb != null) {
                    filterGender = (String) grb.getText();
                }
                if (srb != null && srb.getText().equals("Doesn't matter")) {
                    filterSpayed = "YesNo";
                } else if (srb != null) {
                    filterSpayed = (String) srb.getText();
                }
                if (ball.isChecked()) {
                    filterPS.add("Balls");
                }
                if (hiking.isChecked()) {
                    filterPS.add("Hiking");
                }
                if (jogging.isChecked()) {
                    filterPS.add("Jogging");
                }
                if (wrestle.isChecked()) {
                    filterPS.add("Wrestle");
                }
                if (tugger.isChecked()) {
                    filterPS.add("Tugger");
                }
                if (chaser.isChecked()) {
                    filterPS.add("Chaser");
                }
                if (other.isChecked()) {
                    filterPS.add("Other");
                }

                if (grb == null || srb == null) {
                    Toast.makeText(PlayDateActivity.this, "please fill out all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    applyFilter();
                    dialog.dismiss();
                }

            }

        });


    }

}

