package com.fbo.formation.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fbo.formation.myapplication.model.Author;
import com.fbo.formation.myapplication.model.Book;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddBook extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference bookReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbook);
        firebaseDatabase = FirebaseDatabase.getInstance();
        bookReference = firebaseDatabase.getReference().child("Books");

    }

    public void addbook(View view){
        Log.i("TEXT","on execution laction du bouton");
        TextView nom = findViewById(R.id.nom);
        TextView prenom = findViewById(R.id.prenom);
        TextView nationalite = findViewById(R.id.nationalite);
        TextView prix = findViewById(R.id.prix);
        TextView titre = findViewById(R.id.titre);
        String formNom = nom.getText().toString();
        String formPrenom = prenom.getText().toString();
        String formNat = nationalite.getText().toString();
        String formPrix = prix.getText().toString();
        String formTitre = titre.getText().toString();

        String bookId = bookReference.push().getKey();
        Author auteur = new Author( formNom  , formPrenom, formNat);
        Book book = new Book(formTitre, Double.parseDouble(formPrix), auteur);

        bookReference.child((bookId)).setValue(book);
    }


}
