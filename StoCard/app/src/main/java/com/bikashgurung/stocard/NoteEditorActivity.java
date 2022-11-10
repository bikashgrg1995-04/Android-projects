package com.bikashgurung.stocard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bikashgurung.stocard.DB.AppDatabase;
import com.bikashgurung.stocard.DB.NoteModel;
import com.google.android.material.button.MaterialButton;

public class NoteEditorActivity extends AppCompatActivity {

    String cardId;
    String et_notes;
    String db_notes;
    String note_id;
    MaterialButton back, save, update;
    EditText note;
    AppDatabase db;
    NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_editor);

        db = AppDatabase.getDbInstance(this.getApplicationContext());

        cardId = getIntent().getExtras().getString("card_id");

        note = findViewById(R.id.etNotes);


        update = findViewById(R.id.btnUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote();
            }
        });

        save = findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        back = findViewById(R.id.btnBackNote);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteEditorActivity.this, Show_Card_Details.class);
                //Create the bundle
                Bundle bundle = new Bundle();
//Add your data from getFactualResults method to bundle
                bundle.putString("cardId", String.valueOf(cardId));
//Add the bundle to the intent
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
        noteModel = db.noteDao().findById(cardId);

        if (noteModel != null) {
            checkforNotes();

            db_notes = noteModel.note_text;

            if (db_notes != null) {
                note.setText(db_notes);
            }
        }
    }

    private void saveNote() {
        et_notes = note.getText().toString().trim();

        if (et_notes == null) {
            Toast.makeText(this, "Note is Empty. Please enter some notes.", Toast.LENGTH_SHORT).show();
        } else {
            NoteModel noteModel = new NoteModel();

            noteModel.cardID = cardId;
            noteModel.note_text = et_notes;
            db.noteDao().insert(noteModel);

            Toast.makeText(this, "Saved: Success", Toast.LENGTH_SHORT).show();
            onStart();
        }

    }

    private void checkforNotes() {
        noteModel = db.noteDao().findById(cardId);
        if (noteModel != null) {
            update.setVisibility(View.VISIBLE);
            save.setVisibility(View.GONE);
        } else {
            update.setVisibility(View.GONE);
            save.setVisibility(View.VISIBLE);
        }
    }

    private void updateNote() {
        et_notes = note.getText().toString().trim();

        noteModel = db.noteDao().findById(cardId);
        if (et_notes.equals(noteModel.note_text)) {
            Toast.makeText(this, "Nothing Changed...", Toast.LENGTH_SHORT).show();
        } else {
            note_id = String.valueOf(noteModel.getIdNote());
            noteModel.idNote = Integer.parseInt(note_id);
            noteModel.note_text = et_notes;
            db.noteDao().update(Integer.parseInt(note_id), cardId, et_notes);
        }
    }


}