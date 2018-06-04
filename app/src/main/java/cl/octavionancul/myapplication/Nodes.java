package cl.octavionancul.myapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Nodes {

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    public DatabaseReference users(){
        return  root.child("users");
    }

    public DatabaseReference user(String key){
        return  users().child(key);
    }

    public DatabaseReference products(){
        return  root.child("products");
    }


    public Query productsByUid(String uid) {
        //  return root.child("movies").orderByChild("name").startAt("Movie");
        //   return root.child("movies").orderByChild("genre").equalTo("Action");
        // return root.child("movies").orderByChild("genre").equalTo("Drama").limitToFirst(1);
        // return root.child("movies").orderByChild("genre").equalTo("Drama").limitToLast(1);
        // return root.child("movies").orderByChild("year").startAt("1972").endAt("1994");
        //  return root.child("movies").orderByChild("score").startAt("7").endAt("8");
        //  return root.child("movies").orderByChild("name").startAt("M").endAt("e\uf8ff").limitToFirst(1);
        //combinar dos campos
        return root.child("products").orderByChild("uid").equalTo(uid);
    }

    public DatabaseReference product(String key){
        return  products().child(key);
    }

    public DatabaseReference reviews(){
        return  root.child("reviews");
    }

    public Query reviewsByProductId(String key) {

        return root.child("reviews").child(key);
    }

}
