package com.fbo.formation.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fbo.formation.myapplication.model.Author;
import com.fbo.formation.myapplication.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference bookReference;
    private List<Book> bookList = new ArrayList<>();
    private ListView bookListView;
    private BookArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance();
        bookReference = firebaseDatabase.getReference().child("Books");
        bookListView = findViewById(R.id.bookListView);
        //creation de la vue qui liste les livres
        adapter = new BookArrayAdapter(this, R.layout.book_list_item, bookList);
        bookListView.setAdapter(adapter);
        //recuperation des données avec abonnement au modif ulterieurs
        bookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //reinitialiser la list
                bookList.clear();
                //boucler sur lensemble des noeuds
                for (DataSnapshot bookSnap : dataSnapshot.getChildren()) {
                    //creation d'une ainstance darticle et hydratation avec les données du snapshot
                    Book book = bookSnap.getValue(Book.class);
                    //ajout du livre a la liste
                    bookList.add(book);
                }
                Log.d("MAIN", "Fin de recuperation des données");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Log.d("MAIN", "Fin de onCreate()");
        //addBooks();
    }

    private class BookArrayAdapter extends ArrayAdapter<Book> {

        private Activity context;
        int resource;
        List<Book> data;

        public BookArrayAdapter(@NonNull Activity context, int resource, @NonNull List<Book> data) {
            super(context, resource, data);
            this.context = context;
            this.resource = resource;
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = context.getLayoutInflater().inflate(this.resource, parent, false);
            Book currentBook = bookList.get(position);
            TextView textView = view.findViewById(R.id.bookListText);
            textView.setText(currentBook.getTitle() + " par " + currentBook.getAuthor().getFirstname());
            return view;
        }
    }

    public void addForm(View view) {
            Intent form = new Intent(this,AddBook.class);
        startActivity(form);

      // TextView nom = findViewById(R.id.nom);
      // TextView prenom = findViewById(R.id.prenom);
      // TextView nationalite = findViewById(R.id.nationalite);
      // TextView prix = findViewById(R.id.prix);
      // TextView titre = findViewById(R.id.titre);
      // String formNom = nom.getText().toString();
      // String formPrenom = prenom.getText().toString();
      // String formNat = nationalite.getText().toString();
      // String formPrix = prix.getText().toString();
      // String formTitre = titre.getText().toString();

      // String bookId = bookReference.push().getKey();
      // Author auteur = new Author( formNom  , formPrenom, formNat);
      // Book book = new Book(formTitre, Double.parseDouble(formPrix), auteur);
      // bookReference.child((bookId)).setValue(book);

       // //creation d'un livre
       // String bookId1 = bookReference.push().getKey();
       // String bookId2 = bookReference.push().getKey();
       // String bookId3 = bookReference.push().getKey();
       // Author hugo = new Author("Hugo", "Victor", "Francais");
       // Author rachid = new Author("Arabe", "Rachid", "Francais");
       // Author ibnKhaldoun = new Author("Khaldoun", "Ibn", "Tunisiens");
       // Book book1 = new Book("Les Miserables", 25.0, hugo);
       // Book book2 = new Book("Les Journalistes Etrangers", 20.0, rachid);
       // Book book3 = new Book("Les civilisations", 45.0, ibnKhaldoun);
       // bookReference.child((bookId1)).setValue(book1);
       // bookReference.child((bookId2)).setValue(book2);
       // bookReference.child((bookId3)).setValue(book3);
    }
}
